package pl.edu.agh.pp.hitchhiker.webservice.api.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.edu.agh.pp.hitchhiker.webservice.api.ApiUtil;
import pl.edu.agh.pp.hitchhiker.webservice.model.AuthenticationCredentials;
import pl.edu.agh.pp.hitchhiker.webservice.model.User;
import pl.edu.agh.pp.hitchhiker.webservice.repository.UserRepository;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * Controller class for getting user authentication info
 * @author patrykkurczyna
 */
@Controller
@RequestMapping(value = "/authenticateUser")
public class UserAuthenticationController {
	
	@Autowired
	private UserRepository userRepository;
	
	/**
	 * Method indicating whether or not {@link User} should be authenticated positively
	 * @param login {@link User} login
	 * @param password {@link User} password
	 * @return Boolean entity, true when login and password are valid, false otherwise
	 */
	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	HttpEntity<Resource<Boolean>> authenticateUser(@RequestBody AuthenticationCredentials credentials) {
		User user = userRepository.findByLogin(credentials.getLogin());
		
		if (user != null) {	
			Boolean passwordIsCorrect = user.matches(credentials.getPassword());
			Resource<Boolean> resource = new Resource<Boolean>(passwordIsCorrect);
			
			HttpHeaders headers = ApiUtil.createHeaders();
			StringBuilder builder = new StringBuilder(linkTo(UserAuthenticationController.class).toString().replaceFirst("authenticateUser", "users"));
			String userURL = builder.append("/").append(user.getId()).toString();
			headers.add("Location", userURL);
			
			return new ResponseEntity<Resource<Boolean>>(resource, headers, HttpStatus.OK);
		}
		return new ResponseEntity<Resource<Boolean>>(HttpStatus.NO_CONTENT);
	}
}
