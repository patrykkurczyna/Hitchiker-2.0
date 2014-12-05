package pl.edu.agh.pp.hitchhiker.webservice.controllers;


public class HitchhikerSearchCriteria {
	private String destination;
	private Double latitude;
	private Double longitude;
	
	public String getDestination() {
		if (destination != null) {
			return destination;
		}
		return null;
	}
	
	public Double getLatitude() {
		if (latitude != null) {
			return latitude;
		}
		return null;
	}
	
	public Double getLongitude() {
		if (longitude != null) {
			return longitude;
		}
		return null;
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
	
}
