package pl.edu.agh.pp.hitchhiker.webservice.api.controller;

import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ApiDocController {
	
	@RequestMapping(value = "/api-docs", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String getApiDoc() throws IOException {
		Resource resource = new ClassPathResource("apiDoc.json");
		return FileUtils.readFileToString(resource.getFile());
	}
	
	@RequestMapping(value = "/api-docs/hitchhikers", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String getApiDocHitchhikers() throws IOException {
		Resource resource = new ClassPathResource("apiDocHitchhikers.json");
		return FileUtils.readFileToString(resource.getFile());
	}
	
	@RequestMapping(value = "/api-docs/drivers", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String getApiDocDrivers() throws IOException {
		Resource resource = new ClassPathResource("apiDocDrivers.json");
		return FileUtils.readFileToString(resource.getFile());
	}
	
	@RequestMapping(value = "/api-docs/users", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String getApiDocUsers() throws IOException {
		Resource resource = new ClassPathResource("apiDocUsers.json");
		return FileUtils.readFileToString(resource.getFile());
	}
	
	@RequestMapping(value = "/api-docs/other", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String getApiDocOtherEndpoints() throws IOException {
		Resource resource = new ClassPathResource("apiDocOther.json");
		return FileUtils.readFileToString(resource.getFile());
	}
}
