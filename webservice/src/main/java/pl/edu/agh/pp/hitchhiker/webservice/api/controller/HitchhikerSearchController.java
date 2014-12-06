package pl.edu.agh.pp.hitchhiker.webservice.api.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
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
import pl.edu.agh.pp.hitchhiker.webservice.api.HitchhikerSearchProvider;
import pl.edu.agh.pp.hitchhiker.webservice.model.Hitchhiker;
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
	
	/**
	 * Method used for find and return hitchhikers that match criteria, given as URL parameters
	 * Firstly it takes all active hitchhikers from database using {@link HitchhikerRepository} findActiveInRadiusFrom method
	 * and then it filters them using {@link HitchhikerSearchCriteria}
	 * @param criteria criteria to filter hitchhikers
	 * @return Json containing all hitchhikers that match criteria given in URL
	 */
	@ResponseBody
	@RequestMapping(value = "/findHitchhikers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	HttpEntity<Resources<Resource<Hitchhiker>>> findMatchingHitchhikers(HitchhikerSearchCriteria criteria) {
		List<Hitchhiker> hitchhikers = hitchhikerRepository.findActiveInRadiusFrom(criteria.getRadius(), criteria.getLatitude(), criteria.getLongitude());
		hitchhikers = hitchikerSearchProvider.find(hitchhikers, criteria);
		
		Collection<Resource<Hitchhiker>> hitchhikerCollection = new ArrayList<Resource<Hitchhiker>>();
		for (Hitchhiker hitch : hitchhikers) {
			hitchhikerCollection.add(hitchhikerResourceAssembler.toResource(hitch));
		}

		Resources<Resource<Hitchhiker>> hitchhikerResources = new Resources<Resource<Hitchhiker>>(
				hitchhikerCollection);

		return new ResponseEntity<Resources<Resource<Hitchhiker>>>(
				hitchhikerResources, ApiUtil.createHeaders(), HttpStatus.OK);
	}
}
