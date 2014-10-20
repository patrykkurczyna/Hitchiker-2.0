package pl.edu.agh.pp.hitchhiker.webservice.handlers;

import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.rest.repository.annotation.HandleAfterSave;
import org.springframework.data.rest.repository.annotation.HandleBeforeSave;
import org.springframework.data.rest.repository.annotation.RepositoryEventHandler;

import pl.edu.agh.pp.hitchhiker.webservice.model.Hitchhiker;
import pl.edu.agh.pp.hitchhiker.webservice.model.User;
import pl.edu.agh.pp.hitchhiker.webservice.repository.HitchhikerRepository;

@PropertySource("classpath:application.properties")
@RepositoryEventHandler(Hitchhiker.class)
public class HitchhikerEventHandler {
	
	@Resource
	private Environment environment;
	
	@Autowired
	private HitchhikerRepository hitchhikerRepository;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HitchhikerEventHandler.class);
	
	private final static String PROPERTY_NAME_NOTIFICATION_URL = "notification.url";
	
	@HandleBeforeSave
	public void checkIfThereAreNoActive(Hitchhiker hitchhiker) throws TooManyActiveException{
		Long numberOfActive = hitchhikerRepository.countActive(hitchhiker.getUser().getId());
		if (numberOfActive > 0.0) {
			LOGGER.error("There's already an active Hitchhiker, cannot create another");
			throw new TooManyActiveException();
		}
	}
	
	@HandleAfterSave
	public void handleHitchhikerSave(Hitchhiker hitchhiker) {
		
		final String NOTIFICATION_URL = environment.getRequiredProperty(PROPERTY_NAME_NOTIFICATION_URL);
		
		JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
		
		for (String destination : hitchhiker.getDestinations()) {
			arrayBuilder.add(destination);
		}
		
		final User user = hitchhiker.getUser();
		
		JsonObjectBuilder builder = Json.createObjectBuilder()
				.add("id", hitchhiker.getId())
				.add("login", user.getLogin())
				.add("firstname", user.getFirstname())
				.add("lastname", user.getLastname())
				.add("userId", user.getId())
				.add("geoLongitude", hitchhiker.getGeoLongitude())
				.add("geoLatitude", hitchhiker.getGeoLatitude())
				.add("numberOfPassengers", hitchhiker.getNumberOfPassengers())
				.add("children", hitchhiker.isChildren())
				.add("finalDestination", hitchhiker.getFinalDestination())
				.add("destinations", arrayBuilder);
		
		if (user.getBirthdate() != null) {
			builder.add("birthdate", hitchhiker.getUser().getBirthdate().toString());
		}
		if (hitchhiker.getBaggage() != null) {
			builder.add("baggage", hitchhiker.getBaggage().toString());
		}
		if (hitchhiker.getAgeType() != null) {
			builder.add("ageType", hitchhiker.getAgeType().toString());
		}
		if (hitchhiker.getSexType() != null) {
			builder.add("sexType", hitchhiker.getSexType().toString());
		}
		
		JsonObject hitchikerJsonObject = builder.build();
		
		
		HttpClient httpclient = new DefaultHttpClient();
		HttpResponse response;
		
		try {
			StringEntity se = new StringEntity(hitchikerJsonObject.toString());

			HttpPost httpPost = new HttpPost(NOTIFICATION_URL);
			httpPost.setEntity(se);
			httpPost.setHeader("Content-type", "application/json");
			httpPost.setHeader("Accept", "application/json");
			
			response = httpclient.execute(httpPost);
			
			LOGGER.info("Status of executing HttpPost to: " + NOTIFICATION_URL + " is: " + response.getStatusLine().getStatusCode());
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("Cannot encode JSON Object!");
		} catch (Exception e) {
			LOGGER.error("Cannot execute HttPost to " + NOTIFICATION_URL);
		}
	}

	
}
