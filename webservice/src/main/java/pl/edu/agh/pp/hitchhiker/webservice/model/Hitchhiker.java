package pl.edu.agh.pp.hitchhiker.webservice.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
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
public class Hitchhiker {
	
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
	
	@NotNull
	private String finalDestination;
	
    @Column
    @ElementCollection(targetClass=String.class)
	private List<String> destinations;
	
    @NotNull
    @Column(name = "active")
    private boolean active = true;
	
	private Double geoLatitude, geoLongitude;
	
	private Boolean children;
	
	@Enumerated(EnumType.STRING)
	private Age ageType;
	
	@Enumerated(EnumType.STRING)
	private SexType sexType;
	
	@Enumerated(EnumType.STRING)
	private Baggage baggage;
	
	private int numberOfPassengers;
	
	public User getUser() {
		return this.user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

	public String getFinalDestination() {
		return finalDestination;
	}

	public void setFinalDestination(String destination) {
		this.finalDestination = destination;
	}

	public int getNumberOfPassengers() {
		return numberOfPassengers;
	}

	public void setNumberOfPassengers(int numberOfPassengers) {
		this.numberOfPassengers = numberOfPassengers;
	}

	public List<String> getDestinations() {
		return this.destinations;
	}

	public void setDestinations(List<String> destinations) {
		this.destinations = destinations;
	}

	public boolean isActive() {
		return active;
	}
	
	public String getDeviceId() {
		return deviceId;
	}
	
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public void setActive(boolean active) {
		this.active = active;
	}


}
