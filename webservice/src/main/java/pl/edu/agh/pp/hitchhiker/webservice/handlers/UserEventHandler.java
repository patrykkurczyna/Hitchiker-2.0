package pl.edu.agh.pp.hitchhiker.webservice.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.repository.annotation.HandleBeforeSave;
import org.springframework.data.rest.repository.annotation.RepositoryEventHandler;

import pl.edu.agh.pp.hitchhiker.webservice.model.User;

@RepositoryEventHandler(User.class)
public class UserEventHandler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserEventHandler.class);
	
	//Used for hashing password 
	@HandleBeforeSave
	public void handleUserSave(User user) {
		final String login = user.getLogin();
		user.hashPassword(user.getPassword());
		LOGGER.debug("Password for user: " + login + " successfully hashed!");
	}
	
}
