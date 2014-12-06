package pl.edu.agh.pp.hitchhiker.webservice.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.edu.agh.pp.hitchhiker.webservice.api.ApiUtil;
import pl.edu.agh.pp.hitchhiker.webservice.repository.UserRepository;

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
	 * @return Boolean entity, true when login and password are valid, fale otherwise
	 */
	@RequestMapping(method = RequestMethod.GET)
	HttpEntity<Resource<Boolean>> authenticateUser(final String login, final String password) {
		
		Boolean passwordIsCorrect = userRepository.findByLogin(login).matches(password);
		Resource<Boolean> resource = new Resource<Boolean>(passwordIsCorrect);

		return new ResponseEntity<Resource<Boolean>>(resource, 
			ApiUtil.createHeaders(), HttpStatus.OK);
	}
}
