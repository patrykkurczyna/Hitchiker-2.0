package pl.edu.agh.pp.hitchhiker.webservice.api;

import org.springframework.http.HttpHeaders;

/**
 * Util class for api
 * @author patrykkurczyna
 *
 */
public abstract class ApiUtil {
	/**
	 * Method for create http response headers
	 * @return {@link HttpHeaders} 
	 */
	public static HttpHeaders createHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Access-Control-Allow-Origin", "*");
		headers.add("Content-Type", "application/json");
		headers.add("Accept", "application/json");
		return headers;
	}
}
