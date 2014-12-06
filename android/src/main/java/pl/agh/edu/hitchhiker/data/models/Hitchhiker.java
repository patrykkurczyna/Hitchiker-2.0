package pl.agh.edu.hitchhiker.data.models;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import pl.agh.edu.hitchhiker.utils.CredentialStorage;

public class Hitchhiker {
    private String finalDestination;
    private List<String> destinations = new ArrayList<String>();
    private Double geoLatitude;
    private Double geoLongitude;
    private Integer numberOfPassengers;
    @SerializedName("ageType")
    private Age passengersAge;
    @SerializedName("baggage")
    private Baggage passengersBaggage;
    @SerializedName("sexType")
    private SexType passengersSexType;
    @SerializedName("children")
    private boolean withChildren;
    @SerializedName("user")
    private Location location;
    private String deviceId;
    private boolean active = true;

    public Hitchhiker() {
        location = CredentialStorage.INSTANCE.getUserLocation();
        deviceId = CredentialStorage.INSTANCE.getDeviceId();
    }

    public String getFinalDestination() {
        return finalDestination;
    }

    public void setFinalDestination(String finalDestination) {
        this.finalDestination = finalDestination;
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

    public Integer getNumberOfPassengers() {
        return numberOfPassengers;
    }

    public void setNumberOfPassengers(Integer numberOfPassengers) {
        this.numberOfPassengers = numberOfPassengers;
    }

    public Age getPassengersAge() {
        return passengersAge;
    }

    public void setPassengersAge(Age passengersAge) {
        this.passengersAge = passengersAge;
    }

    public Baggage getPassengersBaggage() {
        return passengersBaggage;
    }

    public void setPassengersBaggage(Baggage passengersBaggage) {
        this.passengersBaggage = passengersBaggage;
    }

    public SexType getPassengersSexType() {
        return passengersSexType;
    }

    public void setPassengersSexType(SexType passengersSexType) {
        this.passengersSexType = passengersSexType;
    }

    public boolean isWithChildren() {
        return withChildren;
    }

    public void setWithChildren(boolean withChildren) {
        this.withChildren = withChildren;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<String> getDestinations() {
        return destinations;
    }

    public void setDestinations(List<String> destinations) {
        this.destinations = destinations;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
