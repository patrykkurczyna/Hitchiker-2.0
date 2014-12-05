package pl.edu.agh.pp.hitchhiker.webservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Driver {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
	
	@Column(unique = true)
	@NotNull
	private String deviceId;
	
	@ManyToOne
	@NotNull
	@JoinColumn(name = "user_id")
	private User user;
	
	private Boolean children;
	
	@Enumerated(EnumType.STRING)
	private Age ageType;
	
	@Enumerated(EnumType.STRING)
	private SexType sexType;
	
	@Enumerated(EnumType.STRING)
	private Baggage baggage;
	
	private String carType;
	
	private Double geoLatitude, geoLongitude;
	
	private String carColor;
	
	private boolean active = true;
	
	private String destination;
	
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	public String getDeviceId() {
		return this.deviceId;
	}
	
	public User getUser() {
		return this.user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public Double getGeoLatitude() {
		return geoLatitude;
	}

	public void setGeoLatitude(Double geoLatitude) {
		this.geoLatitude = geoLatitude;
	}

	public Double getGeoLongitude() {
		return geoLongitude;
	}

	public void setGeoLongitude(Double geoLongitude) {
		this.geoLongitude = geoLongitude;
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

	public Baggage getBaggage() {
		return baggage;
	}

	public void setBaggage(Baggage baggage) {
		this.baggage = baggage;
	}

	public boolean isChildren() {
		return children;
	}

	public void setChildren(boolean children) {
		this.children = children;
	}

	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}

	public String getCarColor() {
		return carColor;
	}

	public void setCarColor(String carColor) {
		this.carColor = carColor;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}
	
}
