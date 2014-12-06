package pl.edu.agh.pp.hitchhiker.webservice.handlers;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.rest.repository.annotation.HandleAfterSave;
import org.springframework.data.rest.repository.annotation.HandleBeforeSave;
import org.springframework.data.rest.repository.annotation.RepositoryEventHandler;

import pl.edu.agh.pp.hitchhiker.service.gcm.SendingNotificationsService;
import pl.edu.agh.pp.hitchhiker.webservice.model.Hitchhiker;
import pl.edu.agh.pp.hitchhiker.webservice.model.User;
import pl.edu.agh.pp.hitchhiker.webservice.repository.HitchhikerRepository;

/**
 * Handler for {@link Hitchhiker} entity
 * @author patrykkurczyna
 *
 */
@RepositoryEventHandler(Hitchhiker.class)
public class HitchhikerEventHandler {
	
	@Resource
	private Environment environment;
	
	@Autowired
	private HitchhikerRepository hitchhikerRepository;
	
	@Autowired
	private SendingNotificationsService sendNotificationsService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HitchhikerEventHandler.class);
	
	/**
	 * Method that prevent from adding a {@link Hitchhiker} for a {@link User} that already has active driver
	 * @param hitchhiker {@link Hitchhiker} to be added
	 * @throws TooManyActiveException
	 */
	@HandleBeforeSave
	public void checkIfThereAreNoActive(Hitchhiker hitchhiker) throws TooManyActiveException{
		Long numberOfActive = hitchhikerRepository.countActive(hitchhiker.getUser().getId());
		if (hitchhiker.isActive() && numberOfActive > 0.0) {
			LOGGER.error("There's already an active Hitchhiker, cannot create another");
			throw new TooManyActiveException();
		}
	}
	
	/**
	 * Method that triggers notifications send to specific hitchhiker, after save 
	 * @param hitchhiker {@link Hitchhiker} being saved to database, and ot which notification send is triggered
	 */
	@HandleAfterSave
	public void handleHitchhikerSave(Hitchhiker hitchhiker) {
		sendNotificationsService.sendHitchhiker(hitchhiker);		
	}

	
}
