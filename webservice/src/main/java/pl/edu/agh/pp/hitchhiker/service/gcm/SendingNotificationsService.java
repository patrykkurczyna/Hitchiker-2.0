package pl.edu.agh.pp.hitchhiker.service.gcm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.annotation.Resource;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import pl.edu.agh.pp.hitchhiker.webservice.model.Driver;
import pl.edu.agh.pp.hitchhiker.webservice.model.Hitchhiker;
import pl.edu.agh.pp.hitchhiker.webservice.model.User;
import pl.edu.agh.pp.hitchhiker.webservice.repository.DriverRepository;

import com.google.android.gcm.server.Constants;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Message.Builder;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

/**
 * Class is a service for sending nnotifications to google GCM server
 * @author patrykkurczyna
 *
 */
@PropertySource("classpath:application.properties")
@Service
public class SendingNotificationsService {
	
	private static final String SEX_TYPE = "sexType";
	private static final String AGE_TYPE = "ageType";
	private static final String BAGGAGE = "baggage";
	private static final String USER_BIRTHDATE = "userBirthdate";
	private static final String USER_ID = "userId";
	private static final String USER_LASTNAME = "userLastname";
	private static final String USER_FIRSTNAME = "userFirstname";
	private static final String USER_LOGIN = "userLogin";
	private static final String DESTINATIONS = "destinations";
	private static final String FINAL_DESTINATION = "finalDestination";
	private static final String CHILDREN = "children";
	private static final String NUMBER_OF_PASSENGERS = "numberOfPassengers";
	private static final String GEO_LATITUDE = "geoLatitude";
	private static final String GEO_LONGITUDE = "geoLongitude";
	private static final String CAR_COLOR = "carColor";
	private static final String CAR_TYPE = "carType";
	private static final String ID = "id";
	private static final String MSG_TYPE_KEY = "msgType";
	private static final String MSG_TYPE_NEW_HITCHHIKER = "newHitchhiker";
	private static final String MSG_TYPE_DRIVER_WANTS_TO_TAKE_YOU = "driverWantsToTakeYou";
	private static final String MSG_TYPE_DRIVER_TOOK_YOU = "driverTookYou";
	private static final String PROPERTY_NAME_API_KEY = "api.key";
	private static final String PROPERTY_NAME_NOTIFICATION_SPREAD = "notification.spread.in.km";
	private static final Logger LOGGER = LoggerFactory.getLogger(SendingNotificationsService.class);
	private static final int MULTICAST_SIZE = 1000;
	private static final Executor threadPool = Executors.newFixedThreadPool(100);
	
	@Resource
	private Environment environment;

	private Sender sender;

	@Autowired
	@Qualifier("driverRepository")
	private DriverRepository driverRepository;
	
	/**
	 * Method for sending notification containing new hitchiker, this message is sent to drivers in specific range
	 * which is set up in properties
	 * @param hitch {@link Hitchhiker} hitchhiker entity being sent
	 */
	public void sendHitchhiker(Hitchhiker hitch) {
		final String RADIUS = environment.getRequiredProperty(PROPERTY_NAME_NOTIFICATION_SPREAD);
		List<String> devices = driverRepository.findDevicesInRadiusFrom(Double.parseDouble(RADIUS),				
				hitch.getGeoLatitude(), hitch.getGeoLongitude());
		JsonObject json = convertHitchhikerToJson(hitch, prepareJsonBuilderWithMessageType(MSG_TYPE_NEW_HITCHHIKER));
		sendMessagesToSpecifiedDevices(devices, json);
	}
	
	/**
	 * Method for sendind notification to hitchhiker when driver want to take him
	 * @param driver {@link Driver} who wants to take a hitchhiker
	 * @param hitchhiker {@link Hitchhiker} who is going to be taken
	 */
	public void sendDriverWantsToTakeYou(Driver driver, Hitchhiker hitchhiker) {
		List<String> devices = new ArrayList<String>(Arrays.asList(hitchhiker.getDeviceId()));
		JsonObject json = convertDriverToJson(driver, prepareJsonBuilderWithMessageType(MSG_TYPE_DRIVER_WANTS_TO_TAKE_YOU));
		sendMessagesToSpecifiedDevices(devices, json);
	}
	
	/**
	 * Method for sendind notification to hitchhiker when driver took him
	 * @param driver {@link Driver} who took hitchhiker
	 * @param hitchhiker {@link Hitchhiker} who is taken
	 */
	public void sendDriverTookYou(Driver driver, Hitchhiker hitchhiker) {
		List<String> devices = new ArrayList<String>(Arrays.asList(hitchhiker.getDeviceId()));
		JsonObject json = convertDriverToJson(driver, prepareJsonBuilderWithMessageType(MSG_TYPE_DRIVER_TOOK_YOU));
		sendMessagesToSpecifiedDevices(devices, json);
	}

	private void sendMessagesToSpecifiedDevices(List<String> devices,
			JsonObject json) {
		final String API_KEY = environment.getRequiredProperty(PROPERTY_NAME_API_KEY);
		sender = new Sender(API_KEY);
		if (devices.isEmpty()) {
			LOGGER.info("Message ignored as there is no device registered!");
		} else {
			// send a multicast message using JSON
			// must split in chunks of 1000 devices (GCM limit)
			int total = devices.size();
			List<String> partialDevices = new ArrayList<String>(total);
			int counter = 0;
			int tasks = 0;
			for (String device : devices) {
				counter++;
				partialDevices.add(device);
				int partialSize = partialDevices.size();
				if (partialSize == MULTICAST_SIZE || counter == total) {
					asyncMessageSend(partialDevices, json);
					partialDevices.clear();
					tasks++;
				}
			}
			LOGGER.debug("Asynchronously sending " + tasks
					+ " multicast messages to " + total + " devices");
		}
	}
	
	private JsonObjectBuilder prepareJsonBuilderWithMessageType(String msgType) {
		return Json.createObjectBuilder().add(MSG_TYPE_KEY, msgType);
	}
	
	private JsonObject convertDriverToJson(Driver driver, JsonObjectBuilder builder) {
		JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
		final User user = driver.getUser();
		
		builder.add(GEO_LONGITUDE, driver.getGeoLongitude())
				.add(GEO_LATITUDE, driver.getGeoLatitude())
				.add(CAR_COLOR, driver.getCarColor())
				.add(CAR_TYPE, driver.getCarType())
				.add(DESTINATIONS, arrayBuilder);
		
		builder = prepareUserJson(builder, user);
		return builder.build();
	}

	private JsonObject convertHitchhikerToJson(Hitchhiker hitch, JsonObjectBuilder builder) {
		JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
		final User user = hitch.getUser();

		builder.add(ID, hitch.getId())
				.add(GEO_LONGITUDE, hitch.getGeoLongitude())
				.add(GEO_LATITUDE, hitch.getGeoLatitude())
				.add(NUMBER_OF_PASSENGERS, hitch.getNumberOfPassengers())
				.add(CHILDREN, hitch.isChildren())
				.add(FINAL_DESTINATION, hitch.getFinalDestination())
				.add(DESTINATIONS, arrayBuilder);

		builder = prepareUserJson(builder, user);

		if (hitch.getBaggage() != null) {
			builder.add(BAGGAGE, hitch.getBaggage().toString());
		}
		if (hitch.getAgeType() != null) {
			builder.add(AGE_TYPE, hitch.getAgeType().toString());
		}
		if (hitch.getSexType() != null) {
			builder.add(SEX_TYPE, hitch.getSexType().toString());
		}
		if (hitch.getDestinations() != null) {
			for (String destination : hitch.getDestinations()) {
				arrayBuilder.add(destination);
			}
		}
		return builder.build();
	}

	private JsonObjectBuilder prepareUserJson(JsonObjectBuilder builder, final User user) {
		if (user != null) {
			builder.add(USER_LOGIN, user.getLogin())
					.add(USER_FIRSTNAME, user.getFirstname())
					.add(USER_LASTNAME, user.getLastname())
					.add(USER_ID, user.getId());

			if (user.getBirthdate() != null) {
				builder.add(USER_BIRTHDATE, user.getBirthdate()
						.toString());
			}
		}
		return builder;
	}

	private void asyncMessageSend(List<String> partialDevices,
			final JsonObject json) {
		// make a copy
		final List<String> devices = new ArrayList<String>(partialDevices);
		threadPool.execute(new Runnable() {

			public void run() {
				Builder messageBuilder = new Message.Builder();

				for (String key : json.keySet()) {
					try {
						messageBuilder.addData(key, json.get(key).toString());
					} catch (JsonException e) {
						e.printStackTrace();
					}
				}

				Message message = messageBuilder.build();

				MulticastResult multicastResult;
				try {
					multicastResult = sender.send(message, devices, 5);
				} catch (IOException e) {
					LOGGER.warn("Error posting messages", e);
					return;
				}
				List<Result> results = multicastResult.getResults();
				// analyze the results
				for (int i = 0; i < devices.size(); i++) {
					String regId = devices.get(i);
					Result result = results.get(i);
					String messageId = result.getMessageId();
					if (messageId != null) {
						LOGGER.info("Succesfully sent message to device: "
								+ regId + "; messageId = " + messageId);
					} else {
						String error = result.getErrorCodeName();
						if (error.equals(Constants.ERROR_NOT_REGISTERED)) {
							LOGGER.warn("Unregistered device: " + regId);
						} else {
							LOGGER.error("Error sending message to " + regId
									+ ": " + error);
						}
					}
				}
			}
		});
	}

}
