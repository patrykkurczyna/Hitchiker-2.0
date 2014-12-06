package pl.edu.agh.pp.hitchhiker.webservice.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pl.edu.agh.pp.hitchhiker.webservice.model.AgeType;
import pl.edu.agh.pp.hitchhiker.webservice.model.BaggageType;
import pl.edu.agh.pp.hitchhiker.webservice.model.Hitchhiker;
import pl.edu.agh.pp.hitchhiker.webservice.model.SexType;

import com.mysql.jdbc.Driver;

/**
 * See {@link HitchhikerSearchProvider}
 * @author patrykkurczyna
 *
 */
@Repository
@Transactional
public class HitchhikerSearchProviderImpl implements HitchhikerSearchProvider {
	
	public final List<Hitchhiker> find(List<Hitchhiker> hitchhikers, final HitchhikerSearchCriteria criteria) {
		List<Hitchhiker> resultList = new ArrayList<Hitchhiker>();
		for (Hitchhiker hitch : hitchhikers) {
			if (matchesCriteria(hitch, criteria)) {
				resultList.add(hitch);
			}
		}
		return resultList;
	}
	
	/**
	 * Checks if hitchhiker matches given criteria. If any of the criteria is null
	 * than hitchhikers automatically matches this criteria (because driver don't care about it)
	 * @param hitch Hitchhiker to confront with given criteria
	 * @param criteria {@link HitchhikerSearchCriteria}
	 * @return true if hitchhikers matches given criteria, false otherwise
	 */
	private boolean matchesCriteria(Hitchhiker hitch,
			HitchhikerSearchCriteria criteria) {
		return (matchChildren(hitch.isChildren(), criteria.getChildren()) &&
				matchAgeType(hitch.getAgeType(), criteria.getAgeType()) &&
				matchSexType(hitch.getSexType(), criteria.getSexType()) &&
				matchNumberOfPassengers(hitch.getNumberOfPassengers(), criteria.getNumberOfPassengersTo()) &&
				matchBaggageType(hitch.getBaggage(), criteria.getBaggageTo()) &&
				matchDestination(hitch.getDestinations(), hitch.getFinalDestination(), criteria.getDestination()));
	}
	
	/**
	 * Indicates whether or not {@link Driver} destination is suitable also for hitchhiker
	 * @param hitchhikerDestinations list of all additional destinations specified by hitchhiker
	 * @param hitchhikerFinalDestination final destination of hitchhiker
	 * @param driverDestination driver destination
	 * @return true if destinations match, false otherwise
	 */
	private boolean matchDestination(List<String> hitchhikerDestinations,
			String hitchhikerFinalDestination, String driverDestination) {
		if (driverDestination != null) {
			return (driverDestination.equals(hitchhikerFinalDestination) || hitchhikerDestinations.contains(driverDestination));
		}
		return true;
	}
	
	/**
	 * Checks if hitchhiker baggage is not bigger than the one specified in driver preferences
	 * @param baggage hitchhiker {@link BaggageType} baggage type
	 * @param driverMaxBaggage driver preferred max baggage size
	 * @return true if baggage types match, false otherwise
	 */
	private boolean matchBaggageType(BaggageType baggage, BaggageType driverMaxBaggage) {
		if (driverMaxBaggage != null && baggage != null) {
			return baggage.ordinal() <= driverMaxBaggage.ordinal();
		}
		return true;
	}
	
	/**
	 * Checks if there are no more hitchhikers than driver can take
	 * @param numberOfPassengers number of hitchhikers
	 * @param driverMaxNumberOfPassengers number of hitchhikers that driver can take 
	 * @return true if number of passengers match, false otherwise
	 */
	private boolean matchNumberOfPassengers(Integer numberOfPassengers,
			Integer driverMaxNumberOfPassengers) {
		if (driverMaxNumberOfPassengers != null && numberOfPassengers != null) {
			return (numberOfPassengers <= driverMaxNumberOfPassengers);
		}
		return true;
	}
	
	/**
	 * Checks if driver's preferred sex type of passengers matches actual
	 * @param sexType passengers sex type
	 * @param driverSexTypePref driver preferred sex type
	 * @return true if sex type matches, false otherwise
	 */
	private boolean matchSexType(SexType sexType, SexType driverSexTypePref) {
		if (driverSexTypePref != null && sexType != null) {
			return (sexType == driverSexTypePref);
		}
		return true;
	}
	
	/**
	 * Checks if driver's preferred age type of passengers matches actual
	 * @param ageType passengers age type
	 * @param driverAgeTypePref driver preferred age type
	 * @return true if age type matches, false otherwise
	 */
	private boolean matchAgeType(AgeType ageType, AgeType driverAgeTypePref) {
		if (driverAgeTypePref != null && ageType != null) {
			return (ageType == driverAgeTypePref);
		}
		return true;
	}
	
	/**
	 * Checks if children preferences match actual situation
	 * @param children true when hitchhikers have children with them
	 * @param driverWantsToTakeChildren true if driver wants to take children
	 * @return true if children preferences match, false otherwise
	 */
	private boolean matchChildren(Boolean children, Boolean driverWantsToTakeChildren) {
		if (driverWantsToTakeChildren != null && children != null) {
			return (children == driverWantsToTakeChildren);
		}
		return true;
	}
}
