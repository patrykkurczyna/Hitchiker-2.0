package pl.edu.agh.pp.hitchhiker.webservice.controllers;

import org.springframework.http.HttpHeaders;

public abstract class ControllersUtil {
	public static HttpHeaders createHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Access-Control-Allow-Origin", "*");
		headers.add("Content-Type", "application/json");
		headers.add("Accept", "application/json");
		return headers;
	}
}
