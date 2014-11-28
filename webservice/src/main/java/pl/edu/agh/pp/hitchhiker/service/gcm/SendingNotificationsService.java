package pl.edu.agh.pp.hitchhiker.service.gcm;

import java.io.IOException;
import java.util.ArrayList;
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
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import pl.edu.agh.pp.hitchhiker.webservice.model.Hitchhiker;
import pl.edu.agh.pp.hitchhiker.webservice.model.User;
import pl.edu.agh.pp.hitchhiker.webservice.repository.DriverRepository;

import com.google.android.gcm.server.Constants;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Message.Builder;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

@PropertySource("classpath:application.properties")
@Service
public class SendingNotificationsService {

	@Resource
	private Environment environment;

	private Sender sender;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(SendingNotificationsService.class);
	private static final int MULTICAST_SIZE = 1000;
	private static final Executor threadPool = Executors
			.newFixedThreadPool(100);
	private final static String PROPERTY_NAME_API_KEY = "api.key";

	@Autowired
	private DriverRepository driverRepository;

	public void sendHitchhiker(Hitchhiker hitch) {
		final String API_KEY = environment
				.getRequiredProperty(PROPERTY_NAME_API_KEY);
		sender = new Sender(API_KEY);
		List<String> devices = driverRepository.findAllDevices();
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
				JsonObject json = convertHitchhikerToJson(hitch);
				if (partialSize == MULTICAST_SIZE || counter == total) {
					asyncHitchhikerSend(partialDevices, json);
					partialDevices.clear();
					tasks++;
				}
			}
			LOGGER.debug("Asynchronously sending " + tasks
					+ " multicast messages to " + total + " devices");
		}
	}

	private JsonObject convertHitchhikerToJson(Hitchhiker hitch) {
		JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
		final User user = hitch.getUser();

		JsonObjectBuilder builder = Json.createObjectBuilder()
				.add("id", hitch.getId())
				.add("geoLongitude", hitch.getGeoLongitude())
				.add("geoLatitude", hitch.getGeoLatitude())
				.add("numberOfPassengers", hitch.getNumberOfPassengers())
				.add("children", hitch.isChildren())
				.add("finalDestination", hitch.getFinalDestination())
				.add("destinations", arrayBuilder);

		if (user != null) {
			builder.add("login", user.getLogin())
					.add("firstname", user.getFirstname())
					.add("lastname", user.getLastname())
					.add("userId", user.getId());

			if (user.getBirthdate() != null) {
				builder.add("birthdate", hitch.getUser().getBirthdate()
						.toString());
			}
		}

		if (hitch.getBaggage() != null) {
			builder.add("baggage", hitch.getBaggage().toString());
		}
		if (hitch.getAgeType() != null) {
			builder.add("ageType", hitch.getAgeType().toString());
		}
		if (hitch.getSexType() != null) {
			builder.add("sexType", hitch.getSexType().toString());
		}
		if (hitch.getDestinations() != null) {
			for (String destination : hitch.getDestinations()) {
				arrayBuilder.add(destination);
			}
		}
		JsonObject hitchikerJsonObject = builder.build();
		return hitchikerJsonObject;
	}

	private void asyncHitchhikerSend(List<String> partialDevices,
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
