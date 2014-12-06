package pl.edu.agh.pp.hitchhiker.webservice.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import pl.edu.agh.pp.hitchhiker.service.gcm.SendingNotificationsService;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * hitchhiker entity which represents hitchhiker preferences and information for a single trip
 * @author patrykkurczyna
 *
 */
@Entity
public class Hitchhiker {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
	
	/**
	 * android device unique id, used for sending gcm notifications, see {@link SendingNotificationsService}
	 */
	@Column
	@NotNull
	private String deviceId;
	
	/**
	 * Every hitchhiker should be created by a {@link User}
	 */
	@JsonIgnore
	@ManyToOne
	@NotNull
	@JoinColumn(name = "user_id")
	private User user;
	
	/**
	 * Indicating a place where hitchhiker is heading
	 */
	@NotNull
	private String finalDestination;
	
	/**
	 * Additional places to which hitchhikers wants to go
	 * It doesn't have to be specified
	 */
    @Column
    @ElementCollection(targetClass=String.class, fetch = FetchType.EAGER)
	private List<String> destinations;
	
    /**
     * Indicates whether or not hitchhiker is active
     * If it is false, it means that hitchhiker is historical
     */
    @NotNull
    @Column(name = "active")
    private boolean active;
	
    /**
     * Coordinates of hitchhiker
     */
	private Double geoLatitude, geoLongitude;
	
	/**
	 * Indicates whether or not there are children in a group of hitchhikers
	 */
	private Boolean children;
	
	/**
	 * See {@link AgeType}
	 */
	@Enumerated(EnumType.STRING)
	private AgeType ageType;
	
	/**
	 * See {@link SexType}
	 */
	@Enumerated(EnumType.STRING)
	private SexType sexType;
	
	/**
	 * See {@link BaggageType}
	 */
	@Enumerated(EnumType.STRING)
	private BaggageType baggage;
	
	/**
	 * Number of passengers that are involved in this trip
	 */
	private Integer numberOfPassengers;
	
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

	public BaggageType getBaggage() {
		return baggage;
	}

	public void setBaggage(BaggageType baggage) {
		this.baggage = baggage;
	}

	public Boolean isChildren() {
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

	public Integer getNumberOfPassengers() {
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
