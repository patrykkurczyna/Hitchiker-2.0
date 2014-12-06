package pl.edu.agh.pp.hitchhiker.webservice.api;

import pl.edu.agh.pp.hitchhiker.webservice.model.AgeType;
import pl.edu.agh.pp.hitchhiker.webservice.model.BaggageType;
import pl.edu.agh.pp.hitchhiker.webservice.model.SexType;

/**
 * See {@link HitchhikerSearchCriteria}
 * @author patrykkurczyna
 *
 */
public class HitchhikerSearchCriteriaImpl implements HitchhikerSearchCriteria {
	private String destination;
	private Double latitude;
	private Double longitude;	
	private Boolean children;
	private AgeType ageType;
	private SexType sexType;
	private BaggageType baggageTo;
	private Integer numberOfPassengersTo;
	private Double radius;
	
	public String getDestination() {
		return destination;
	}
	
	public Double getLatitude() {
		return latitude;
	}
	
	public Double getLongitude() {
		return longitude;
	}
	
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	
	public void setDestination(String destination) {
		this.destination = destination;
	}

	public Boolean getChildren() {
		return children;
	}

	public void setChildren(Boolean children) {
		this.children = children;
	}

	public AgeType getAgeType() {
		return ageType;
	}

	public void setAgeType(AgeType ageType) {
		this.ageType = ageType;
	}

	public SexType getSexType() {
		return sexType;
	}

	public void setSexType(SexType sexType) {
		this.sexType = sexType;
	}

	public BaggageType getBaggageTo() {
		return baggageTo;
	}

	public void setBaggageTo(BaggageType baggageTo) {
		this.baggageTo = baggageTo;
	}

	public Integer getNumberOfPassengersTo() {
		return numberOfPassengersTo;
	}

	public void setNumberOfPassengersTo(Integer numberOfPassengersTo) {
		this.numberOfPassengersTo = numberOfPassengersTo;
	}

	public Double getRadius() {
		return radius;
	}

	public void setRadius(Double radius) {
		this.radius = radius;
	}
	
}
