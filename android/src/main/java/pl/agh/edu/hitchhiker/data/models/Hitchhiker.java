package pl.agh.edu.hitchhiker.data.models;


public class Hitchhiker {

    private String destination;
    private Double geoLatitude;
    private Double geoLangitude;
    private Integer numberPassengers;
    private Age passengersAge;
    private Baggage passengersBaggage;
    private SexType passengersSexType;
    private boolean withChildren;

    public Hitchhiker() {
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Double getGeoLatitude() {
        return geoLatitude;
    }

    public void setGeoLatitude(Double geoLatitude) {
        this.geoLatitude = geoLatitude;
    }

    public Double getGeoLangitude() {
        return geoLangitude;
    }

    public void setGeoLangitude(Double geoLangitude) {
        this.geoLangitude = geoLangitude;
    }

    public Integer getNumberPassengers() {
        return numberPassengers;
    }

    public void setNumberPassengers(Integer numberPassengers) {
        this.numberPassengers = numberPassengers;
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
}
