package pl.agh.edu.hitchhiker.data.models;

import com.google.gson.annotations.SerializedName;

import pl.agh.edu.hitchhiker.utils.CredentialStorage;

public class Driver {

    private String destination;
    private Double geoLatitude;
    private Double geoLongitude;
    private Integer freeSeats;
    @SerializedName("ageType")
    private Age prefAge;
    @SerializedName("baggage")
    private Baggage prefBaggage;
    private SexType prefSexType;
    @SerializedName("children")
    private boolean withoutChildren;
    @SerializedName("user")
    private Location location;
    private String deviceId;
    private String carType;
    private boolean active = true;

    public Driver() {
        location = CredentialStorage.INSTANCE.getUserLocation();
        deviceId = CredentialStorage.INSTANCE.getDeviceId();
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

    public Integer getFreeSeats() {
        return freeSeats;
    }

    public void setFreeSeats(Integer freeSeats) {
        this.freeSeats = freeSeats;
    }

    public Age getPrefAge() {
        return prefAge;
    }

    public void setPrefAge(Age prefAge) {
        this.prefAge = prefAge;
    }

    public Baggage getPrefBaggage() {
        return prefBaggage;
    }

    public void setPrefBaggage(Baggage prefBaggage) {
        this.prefBaggage = prefBaggage;
    }

    public SexType getPrefSexType() {
        return prefSexType;
    }

    public void setPrefSexType(SexType prefSexType) {
        this.prefSexType = prefSexType;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public boolean isWithoutChildren() {
        return withoutChildren;
    }

    public void setWithoutChildren(boolean withoutChildren) {
        this.withoutChildren = withoutChildren;
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

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }
}
