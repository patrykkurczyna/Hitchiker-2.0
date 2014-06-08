package pl.agh.edu.hitchhiker.models;

public class Driver {

    private String destination;
    private Double geoLatitude;
    private Double geoLangitude;
    private Integer freeSeats;
    private Age prefAge;
    private Baggage prefBaggage;
    private SexType prefSexType;
    private boolean withoutChildren;

    public Driver() {
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
}
