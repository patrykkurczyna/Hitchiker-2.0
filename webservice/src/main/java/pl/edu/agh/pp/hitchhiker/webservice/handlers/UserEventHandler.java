package pl.edu.agh.pp.hitchhiker.webservice.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.repository.annotation.HandleBeforeSave;
import org.springframework.data.rest.repository.annotation.RepositoryEventHandler;

import pl.edu.agh.pp.hitchhiker.webservice.model.User;

/**
 * Handler for {@link User} entity
 * @author patrykkurczyna
 *
 */
@RepositoryEventHandler(User.class)
public class UserEventHandler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserEventHandler.class);
	
	/**
	 * Method used for hashing user password before he is saved to database
	 * @param user {@link User} which is going to be save
	 */
	@HandleBeforeSave
	public void handleUserSave(User user) {
		final String login = user.getLogin();
		user.hashPassword(user.getPassword());
		LOGGER.debug("Password for user: " + login + " successfully hashed!");
	}
	
}
