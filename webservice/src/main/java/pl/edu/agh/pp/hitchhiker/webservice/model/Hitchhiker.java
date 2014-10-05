package pl.edu.agh.pp.hitchhiker.webservice.model;

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
	
	@ManyToOne
	@NotNull
	@JoinColumn(name = "user_id")
	private User user;
	
	private String firstname, lastname;
	
	private Double geoLatitude;
	private Double geoLongitude;
	
	private boolean children;
	
	private String destination;
	
	@Enumerated(EnumType.STRING)
	private Age ageType;
	
	@Enumerated(EnumType.STRING)
	private SexType sexType;
	
	@Enumerated(EnumType.STRING)
	private Baggage baggage;
	
	private int numberOfPassengers;
	
	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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
