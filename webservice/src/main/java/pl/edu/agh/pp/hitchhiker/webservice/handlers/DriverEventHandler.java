package pl.edu.agh.pp.hitchhiker.webservice.handlers;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.rest.repository.annotation.HandleBeforeSave;
import org.springframework.data.rest.repository.annotation.RepositoryEventHandler;

import pl.edu.agh.pp.hitchhiker.webservice.model.Driver;
import pl.edu.agh.pp.hitchhiker.webservice.model.Hitchhiker;
import pl.edu.agh.pp.hitchhiker.webservice.repository.DriverRepository;

/**
 * Handler for {@link Driver} entity
 * @author patrykkurczyna
 *
 */
@RepositoryEventHandler(Driver.class)
public class DriverEventHandler {
	
	@Resource
	private Environment environment;
	
	@Autowired
	private DriverRepository driverRepository;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DriverEventHandler.class);
	
	/**
	 * Method that prevent from adding a {@link Driver} for a {@link User} that already has active driver
	 * @param driver {@link Driver} to be added
	 * @throws TooManyActiveException
	 */
	@HandleBeforeSave
	public void checkIfThereAreNoActive(Driver driver) throws TooManyActiveException{
		Driver driverInDb = driverRepository.findById(driver.getId());
		Long numberOfActive = driverRepository.countActive(driver.getUser().getId());
		if (driverInDb != null) {
			//driver is already in db so it is update
			if (driver.isActive() && !driverInDb.isActive() && numberOfActive > 0.0) {
				//if he wants to change from no active to active
				LOGGER.error("There's already an active Driver, cannot update to active");
				throw new TooManyActiveException();
			}
		} else {
			//driver is new
			if (driver.isActive() && numberOfActive > 0.0) {
				LOGGER.error("There's already an active Driver, cannot create another");
				throw new TooManyActiveException();
			}
		}	
	}
	
}
