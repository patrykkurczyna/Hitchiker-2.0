package pl.edu.agh.pp.hitchhiker.webservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.edu.agh.pp.hitchhiker.webservice.repository.UserRepository;

/**
 * Controller class for memorial {@link Display} resources 
 * @author patrykkurczyna
 */
@Controller
@RequestMapping(value = "/authenticateUser")
public class UserAuthenticationController {
	
	@Autowired
	private UserRepository userRepository;

	@RequestMapping(method = RequestMethod.GET)
	HttpEntity<Resource<Boolean>> authenticateUser(final String login, final String password) {
		
		Boolean passwordIsCorrect = userRepository.findByLogin(login).matches(password);
		Resource<Boolean> resource = new Resource<Boolean>(passwordIsCorrect);

		return new ResponseEntity<Resource<Boolean>>(resource, 
			createHeaders(), HttpStatus.OK);
	}	
	HttpHeaders createHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Access-Control-Allow-Origin", "*");
		headers.add("Content-Type", "application/json");
		headers.add("Accept", "application/json");
		return headers;
	}
}
