package pl.edu.agh.pp.hitchhiker.webservice.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.edu.agh.pp.hitchhiker.webservice.api.ApiUtil;
import pl.edu.agh.pp.hitchhiker.webservice.api.HitchhikerResourceAssembler;
import pl.edu.agh.pp.hitchhiker.webservice.api.HitchhikerSearchCriteria;
import pl.edu.agh.pp.hitchhiker.webservice.api.HitchhikerSearchCriteriaImpl;
import pl.edu.agh.pp.hitchhiker.webservice.api.HitchhikerSearchProvider;
import pl.edu.agh.pp.hitchhiker.webservice.model.Driver;
import pl.edu.agh.pp.hitchhiker.webservice.model.Hitchhiker;
import pl.edu.agh.pp.hitchhiker.webservice.repository.DriverRepository;
import pl.edu.agh.pp.hitchhiker.webservice.repository.HitchhikerRepository;

/**
 * Controller for {@link Hitchhiker} search requests
 * @author patrykkurczyna
 *
 */
@Controller
public class HitchhikerSearchController {
	
	@Autowired
	HitchhikerSearchProvider hitchikerSearchProvider;
	
	@Autowired
	HitchhikerResourceAssembler hitchhikerResourceAssembler;
	
	@Autowired
	private HitchhikerRepository hitchhikerRepository;
	
	@Autowired
	private DriverRepository driverRepository;
	
	/**
	 * Method used for find and return hitchhikers that match criteria, given as URL parameters
	 * Firstly it takes all active hitchhikers from database using {@link HitchhikerRepository} findActiveInRadiusFrom method
	 * and then it filters them using {@link HitchhikerSearchCriteria} retrieved from {@link Driver} of giveb id
	 * @param driverId id of driver for whom we are matching
	 * @param radius spread of search
	 * @return Json containing all hitchhikers that match criteria given in URL
	 */
	@ResponseBody
	@RequestMapping(value = "/findHitchhikers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	HttpEntity<Resources<Hitchhiker>> findMatchingHitchhikers(@Param("driverId") Integer driverId, @Param("radius") Double radius) {
		Driver driver = driverRepository.findById(driverId);
		HitchhikerSearchCriteria criteria = new HitchhikerSearchCriteriaImpl(driver.getDestination(), driver.getGeoLatitude(),
				driver.getGeoLongitude(), driver.isChildren(), driver.getAgeType(), driver.getSexType(), driver.getBaggage(),
				driver.getNumberOfPassengers(), radius);
		
		List<Hitchhiker> hitchhikers = hitchhikerRepository.findActiveInRadiusFrom(criteria.getRadius(), criteria.getLatitude(), criteria.getLongitude());
		hitchhikers = hitchikerSearchProvider.find(hitchhikers, criteria);
		
		Resources<Hitchhiker> hitchhikerResources = new Resources<Hitchhiker>(hitchhikers);

		return new ResponseEntity<Resources<Hitchhiker>>(
				hitchhikerResources, ApiUtil.createHeaders(), HttpStatus.OK);
	}
}
