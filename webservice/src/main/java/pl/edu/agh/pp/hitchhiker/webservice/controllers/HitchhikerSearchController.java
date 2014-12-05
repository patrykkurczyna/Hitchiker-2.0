package pl.edu.agh.pp.hitchhiker.webservice.controllers;

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

import pl.edu.agh.pp.hitchhiker.webservice.model.Hitchhiker;
import pl.edu.agh.pp.hitchhiker.webservice.repository.HitchhikerRepository;

@Controller
public class HitchhikerSearchController {
	
	@Autowired
	HitchhikerResourceAssembler hitchhikerResourceAssembler;
	
	@Autowired
	private HitchhikerRepository hitchhikerRepository;
	
	@ResponseBody
	@RequestMapping(value = "/findMatching", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	HttpEntity<Resources<Resource<Hitchhiker>>> findMatchingHitchhikers(HitchhikerSearchCriteria criteria) {
		Double radius = 10.0;
		
		List<Hitchhiker> hitchhikers = hitchhikerRepository.findActiveInRadiusFrom(radius, criteria.getLatitude(), criteria.getLongitude());

		Collection<Resource<Hitchhiker>> hitchhikerCollection = new ArrayList<Resource<Hitchhiker>>();
		for (Hitchhiker hitch : hitchhikers) {
			hitchhikerCollection.add(hitchhikerResourceAssembler.toResource(hitch));
		}

		Resources<Resource<Hitchhiker>> hitchhikerResources = new Resources<Resource<Hitchhiker>>(
				hitchhikerCollection);

		return new ResponseEntity<Resources<Resource<Hitchhiker>>>(
				hitchhikerResources, ControllersUtil.createHeaders(), HttpStatus.OK);
	}
}
