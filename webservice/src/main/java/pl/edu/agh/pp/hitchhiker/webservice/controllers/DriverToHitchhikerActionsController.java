package pl.edu.agh.pp.hitchhiker.webservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import pl.edu.agh.pp.hitchhiker.service.gcm.SendingNotificationsService;
import pl.edu.agh.pp.hitchhiker.webservice.repository.DriverRepository;
import pl.edu.agh.pp.hitchhiker.webservice.repository.HitchhikerRepository;

@Controller
public class DriverToHitchhikerActionsController {
		
	@Autowired
	private DriverRepository driverRepository;
	
	@Autowired
	private HitchhikerRepository hitchhikerRepository;
	
	@Autowired
	private SendingNotificationsService service;
	
	@RequestMapping(value = "/driverWantsToTakeHitch", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void driverWantsToTakeHitch(@RequestParam("hitchId") final Integer hitchId, @RequestParam("driverId") final Integer driverId) {
		service.sendDriverWantsToTakeYou(driverRepository.findById(driverId), hitchhikerRepository.findById(hitchId));
	}
	
	@RequestMapping(value = "/driverTookHitch", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void driverTookHitch(@RequestParam("hitchId") final Integer hitchId, @RequestParam("driverId") final Integer driverId) {
		service.sendDriverTookYou(driverRepository.findById(driverId), hitchhikerRepository.findById(hitchId));
	}
}
