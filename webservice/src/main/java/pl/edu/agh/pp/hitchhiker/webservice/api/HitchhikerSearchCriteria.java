package pl.edu.agh.pp.hitchhiker.webservice.api;

import pl.edu.agh.pp.hitchhiker.webservice.api.controller.HitchhikerSearchController;
import pl.edu.agh.pp.hitchhiker.webservice.model.AgeType;
import pl.edu.agh.pp.hitchhiker.webservice.model.BaggageType;
import pl.edu.agh.pp.hitchhiker.webservice.model.SexType;

/**
 * Search criteria for hitchhikers. They are used to retrieve specific group of hitchhikers
 * specified by parameters in URL, these parmeters should have names which are identical to this
 * class field names. See {@link HitchhikerSearchController}
 * @author patrykkurczyna
 *
 */
public interface HitchhikerSearchCriteria {

	public abstract String getDestination();

	public abstract Double getLatitude();

	public abstract Double getLongitude();

	public abstract Boolean getChildren();

	public abstract AgeType getAgeType();

	public abstract SexType getSexType();

	public abstract BaggageType getBaggageTo();

	public abstract Integer getNumberOfPassengersTo();

	public abstract Double getRadius();

}