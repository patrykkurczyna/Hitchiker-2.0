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
				matchNumberOfPassengers(hitch.getNumberOfPassengers(), criteria.getNumberOfPassengersTo()) &&
				matchBaggageType(hitch.getBaggage(), criteria.getBaggageTo()) &&
				matchDestination(hitch.getDestinations(), hitch.getFinalDestination(), criteria.getDestination()));
	}

	private boolean matchDestination(List<String> hitchhikerDestinations,
			String hitchhikerFinalDestination, String driverDestination) {
		if (driverDestination != null) {
			return (driverDestination.equals(hitchhikerFinalDestination) || hitchhikerDestinations.contains(driverDestination));
		}
		return true;
	}

	private boolean matchBaggageType(Baggage baggage, Baggage driverMaxBaggage) {
		if (driverMaxBaggage != null) {
			return baggage.ordinal() <= driverMaxBaggage.ordinal();
		}
		return true;
	}

	private boolean matchNumberOfPassengers(Integer numberOfPassengers,
			Integer driverMaxNumberOfPassengers) {
		if (driverMaxNumberOfPassengers != null) {
			return (numberOfPassengers <= driverMaxNumberOfPassengers);
		}
		return true;
	}

	private boolean matchSexType(SexType sexType, SexType driverSexTypePref) {
		if (driverSexTypePref != null) {
			return (sexType == driverSexTypePref);
		}
		return true;
	}

	private boolean matchAgeType(Age ageType, Age driverAgeTypePref) {
		if (driverAgeTypePref != null) {
			return (ageType == driverAgeTypePref);
		}
		return true;
	}

	private boolean matchChildren(boolean children, Boolean driverWantsToTakeChildren) {
		if (driverWantsToTakeChildren != null) {
			return (children == driverWantsToTakeChildren);
		}
		return true;
	}
}
