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

import com.fasterxml.jackson.annotation.JsonIgnore;

import pl.edu.agh.pp.hitchhiker.service.gcm.SendingNotificationsService;

/**
 * driver entity which represents driver preferences and information for a single trip
 * @author patrykkurczyna
 *
 */
@Entity
public class Driver {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
	
	/**
	 * android device unique id, used for sending gcm notifications, see {@link SendingNotificationsService}
	 */
	@Column(unique = true)
	@NotNull
	private String deviceId;
	
	@JsonIgnore
	@ManyToOne
	@NotNull
	@JoinColumn(name = "user_id")
	private User user;
	
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
	
	private String carType;
	
	/**
	 * Max number of passengers that driver can take
	 */
	private int numberOfPassengers;
	
	/**
	 * Driver coordinates
	 */
	private Double geoLatitude, geoLongitude;
	
	private String carColor;
	
    /**
     * Indicates whether or not driver is active
     * If it is false, it means that driver is historical
     */
    @NotNull
    @Column(name = "active")
	private boolean active = true;
	
	/**
	 * Indicating a place where driver is heading
	 */
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

	public int getNumberOfPassengers() {
		return numberOfPassengers;
	}

	public void setNumberOfPassengers(int numberOfPassengers) {
		this.numberOfPassengers = numberOfPassengers;
	}
	
}
