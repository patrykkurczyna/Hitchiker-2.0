package pl.edu.agh.pp.hitchhiker.webservice.handlers;

import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;
import javax.json.Json;
import javax.json.JsonObject;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.rest.repository.annotation.HandleAfterSave;
import org.springframework.data.rest.repository.annotation.RepositoryEventHandler;

import pl.edu.agh.pp.hitchhiker.webservice.model.Hitchhiker;

@PropertySource("classpath:application.properties")
@RepositoryEventHandler(Hitchhiker.class)
public class HitchhikerEventHandler {
	
	@Resource
	private Environment environment;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HitchhikerEventHandler.class);
	
	private final static String PROPERTY_NAME_NOTIFICATION_URL = "notification.url";
	
	@HandleAfterSave
	public void handleHitchhikerSave(Hitchhiker hitchhiker) {
		
		final String NOTIFICATION_URL = environment.getRequiredProperty(PROPERTY_NAME_NOTIFICATION_URL);
		
		JsonObject hitchikerJsonObject = Json.createObjectBuilder()
				.add("firstname", hitchhiker.getFirstname())
				.add("lastname", hitchhiker.getLastname())
				.add("baggage", hitchhiker.getBaggage().toString())
				.add("ageType", hitchhiker.getAgeType().toString())
				.add("geoLongitude", hitchhiker.getGeoLongitude())
				.add("geoLatitude", hitchhiker.getGeoLatitude())
				.add("numberOfPassengers", hitchhiker.getNumberOfPassengers())
				.add("children", hitchhiker.isChildren())
				.add("sexType", hitchhiker.getSexType().toString())
				.add("destination", hitchhiker.getDestination())
				.build();
		
		
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
