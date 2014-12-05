package pl.edu.agh.pp.hitchhiker.webservice.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pl.edu.agh.pp.hitchhiker.webservice.model.Age;
import pl.edu.agh.pp.hitchhiker.webservice.model.Baggage;
import pl.edu.agh.pp.hitchhiker.webservice.model.Hitchhiker;
import pl.edu.agh.pp.hitchhiker.webservice.model.SexType;

@Repository
@Transactional
public class HitchikerSearchProvider {
	
	public final List<Hitchhiker> find(List<Hitchhiker> hitchhikers, final HitchhikerSearchCriteria criteria) {
		List<Hitchhiker> resultList;
		resultList = findByCriteria(hitchhikers, criteria);
		return resultList;
	}
	
	private final List<Hitchhiker> findByCriteria(List<Hitchhiker> hitchhikers, final HitchhikerSearchCriteria criteria) {
		List<Hitchhiker> resultList = new ArrayList<Hitchhiker>();
		for (Hitchhiker hitch : hitchhikers) {
			if (matchesCriteria(hitch, criteria)) {
				resultList.add(hitch);
			}
		}
		return resultList;
	}

	private boolean matchesCriteria(Hitchhiker hitch,
			HitchhikerSearchCriteria criteria) {
		return (matchChildren(hitch.isChildren(), criteria.getChildren()) &&
				matchAgeType(hitch.getAgeType(), criteria.getAgeType()) &&
				matchSexType(hitch.getSexType(), criteria.getSexType()) &&
				matchNumberOfPassengers(hitch.getNumberOfPassengers(), criteria.getNumberOfPassengers()) &&
				matchBaggageType(hitch.getBaggage(), criteria.getBaggageFrom()) &&
				matchDestination(hitch.getDestinations(), hitch.getFinalDestination(), criteria.getDestination()));
	}

	private boolean matchDestination(List<String> destinations,
			String finalDestination, String destination) {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean matchBaggageType(Baggage baggage, Baggage baggageFrom) {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean matchNumberOfPassengers(int numberOfPassengers,
			int numberOfPassengers2) {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean matchSexType(SexType sexType, SexType sexType2) {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean matchAgeType(Age ageType, Age ageType2) {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean matchChildren(boolean children, Boolean children2) {
		// TODO Auto-generated method stub
		return false;
	}
}
