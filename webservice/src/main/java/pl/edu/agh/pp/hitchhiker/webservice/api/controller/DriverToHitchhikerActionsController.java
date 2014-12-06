package pl.edu.agh.pp.hitchhiker.webservice.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pl.edu.agh.pp.hitchhiker.service.gcm.SendingNotificationsService;
import pl.edu.agh.pp.hitchhiker.webservice.model.Driver;
import pl.edu.agh.pp.hitchhiker.webservice.model.Hitchhiker;
import pl.edu.agh.pp.hitchhiker.webservice.repository.DriverRepository;
import pl.edu.agh.pp.hitchhiker.webservice.repository.HitchhikerRepository;

/**
 * Controller for handling notifications from {@link Driver} to {@link Hitchhiker}
 * @author patrykkurczyna
 *
 */
@Controller
public class DriverToHitchhikerActionsController {
		
	@Autowired
	private DriverRepository driverRepository;
	
	@Autowired
	private HitchhikerRepository hitchhikerRepository;
	
	@Autowired
	private SendingNotificationsService service;
	
	/**
	 * Request handler in situation where {@link Driver} wants to take {@link Hitchhiker}
	 * The notification is then being send to this specific hitchhiker containing this message
	 * @param hitchId id of {@link Hitchhiker} who should be notified
	 * @param driverId id of {@link Driver} who sends notification
	 */
	@RequestMapping(value = "/driverWantsToTakeHitch", method = RequestMethod.GET)
	ResponseEntity<String> driverWantsToTakeHitch(@RequestParam("hitchId") final Integer hitchId, @RequestParam("driverId") final Integer driverId) {
		Hitchhiker hitch = hitchhikerRepository.findById(hitchId);
		if (hitch != null && hitch.isActive())  {
			service.sendDriverWantsToTakeYou(driverRepository.findById(driverId), hitch);
			return new ResponseEntity<String>(HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<String>(HttpStatus.EXPECTATION_FAILED);
	}
	
	/**
	 * Request handler in situation where {@link Driver} took {@link Hitchhiker}
	 * The notification is then being send to this specific hitchhiker containing this message
	 * @param hitchId id of {@link Hitchhiker} who should be notified
	 * @param driverId id of {@link Driver} who sends notification
	 */
	@RequestMapping(value = "/driverTookHitch", method = RequestMethod.GET)
	ResponseEntity<String> driverTookHitch(@RequestParam("hitchId") final Integer hitchId, @RequestParam("driverId") final Integer driverId) {
		Hitchhiker hitch = hitchhikerRepository.findById(hitchId);
		if (hitch != null && hitch.isActive())  {
			service.sendDriverTookYou(driverRepository.findById(driverId), hitch);
			return new ResponseEntity<String>(HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<String>(HttpStatus.EXPECTATION_FAILED);
	}
}
