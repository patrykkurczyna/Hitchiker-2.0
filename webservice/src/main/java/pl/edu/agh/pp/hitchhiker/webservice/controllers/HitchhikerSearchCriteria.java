package pl.edu.agh.pp.hitchhiker.webservice.controllers;

import pl.edu.agh.pp.hitchhiker.webservice.model.Age;
import pl.edu.agh.pp.hitchhiker.webservice.model.Baggage;
import pl.edu.agh.pp.hitchhiker.webservice.model.SexType;


public class HitchhikerSearchCriteria {
	private String destination;
	private Double latitude;
	private Double longitude;	
	private Boolean children;
	private Age ageType;
	private SexType sexType;
	private Baggage baggageTo;
	private Integer numberOfPassengersTo;
	
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

	public Age getAgeType() {
		return ageType;
	}

	public void setAgeType(Age ageType) {
		this.ageType = ageType;
	}

	public SexType getSexType() {
		return sexType;
	}

	public void setSexType(SexType sexType) {
		this.sexType = sexType;
	}

	public Baggage getBaggageTo() {
		return baggageTo;
	}

	public void setBaggageTo(Baggage baggageTo) {
		this.baggageTo = baggageTo;
	}

	public Integer getNumberOfPassengersTo() {
		return numberOfPassengersTo;
	}

	public void setNumberOfPassengersTo(Integer numberOfPassengersTo) {
		this.numberOfPassengersTo = numberOfPassengersTo;
	}
	
}
