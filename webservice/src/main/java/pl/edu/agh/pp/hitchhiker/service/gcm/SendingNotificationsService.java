package pl.edu.agh.pp.hitchhiker.service.gcm;

import pl.edu.agh.pp.hitchhiker.webservice.model.Driver;
import pl.edu.agh.pp.hitchhiker.webservice.model.Hitchhiker;

/**
 * Class for sending notifications to users
 * @author patrykkurczyna
 *
 */
public interface SendingNotificationsService {

	/**
	 * Method for sending notification containing new hitchiker, this message is sent to drivers in specific range
	 * which is set up in properties
	 * @param hitch {@link Hitchhiker} hitchhiker entity being sent
	 */
	public abstract void sendHitchhiker(Hitchhiker hitch);

	/**
	 * Method for sendind notification to hitchhiker when driver want to take him
	 * @param driver {@link Driver} who wants to take a hitchhiker
	 * @param hitchhiker {@link Hitchhiker} who is going to be taken
	 */
	public abstract void sendDriverWantsToTakeYou(Driver driver,
			Hitchhiker hitchhiker);

	/**
	 * Method for sendind notification to hitchhiker when driver took him
	 * @param driver {@link Driver} who took hitchhiker
	 * @param hitchhiker {@link Hitchhiker} who is taken
	 */
	public abstract void sendDriverTookYou(Driver driver, Hitchhiker hitchhiker);

}