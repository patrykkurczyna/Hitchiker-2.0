package pl.edu.agh.pp.hitchhiker.webservice.handlers;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.rest.repository.annotation.HandleBeforeSave;
import org.springframework.data.rest.repository.annotation.RepositoryEventHandler;

import pl.edu.agh.pp.hitchhiker.webservice.model.Driver;
import pl.edu.agh.pp.hitchhiker.webservice.repository.DriverRepository;

@RepositoryEventHandler(Driver.class)
public class DriverEventHandler {
	
	@Resource
	private Environment environment;
	
	@Autowired
	private DriverRepository driverRepository;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DriverEventHandler.class);
		
	@HandleBeforeSave
	public void checkIfThereAreNoActive(Driver driver) throws TooManyActiveException{
		Long numberOfActive = driverRepository.countActive(driver.getUser().getId());
		if (numberOfActive > 0.0) {
			LOGGER.error("There's already an active Driver, cannot create another");
			throw new TooManyActiveException();
		}
	}
	
}
