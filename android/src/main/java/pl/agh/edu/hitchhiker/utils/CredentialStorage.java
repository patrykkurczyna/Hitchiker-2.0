package pl.agh.edu.hitchhiker.utils;

import pl.agh.edu.hitchhiker.data.models.Location;

public enum CredentialStorage {
    INSTANCE;

    private static final String DEVICE_ID = "hitchhiker.device_id";
    private static final String USER_LOCATION = "hitchhiker.user_location";
    private static final String DRIVER_REGISTERED = "hitchhiker.driver_id";
    private static final String HITCHHIKER_REGISTERED = "hitchhiker.hitchhiker_id";

    private String deviceId;
    private Location userLocation;
    private int registeredDriver = -1;
    private int registeredHitchhiker = -1;

    public void init() {
        deviceId = SharedPreferencesHelper.getString(DEVICE_ID, "");
        String href = SharedPreferencesHelper.getString(USER_LOCATION, "");
        if (!href.isEmpty()) {
            userLocation = new Location(href);
        }
        registeredDriver = SharedPreferencesHelper.getInt(DRIVER_REGISTERED, -1);
        registeredHitchhiker = SharedPreferencesHelper.getInt(HITCHHIKER_REGISTERED, -1);
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
        SharedPreferencesHelper.setString(DEVICE_ID, deviceId);
    }

    public Location getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(Location userLocation) {
        this.userLocation = userLocation;
        SharedPreferencesHelper.setString(USER_LOCATION, userLocation != null ? userLocation.getHref() : "");
    }

    public int getRegisteredDriver() {
        return registeredDriver;
    }

    public void setRegisteredDriver(int registeredDriver) {
        this.registeredDriver = registeredDriver;
        SharedPreferencesHelper.setInt(DRIVER_REGISTERED, registeredDriver);
    }

    public int getRegisteredHitchhiker() {
        return registeredHitchhiker;
    }

    public void setRegisteredHitchhiker(int registeredHitchhiker) {
        this.registeredHitchhiker = registeredHitchhiker;
        SharedPreferencesHelper.setInt(HITCHHIKER_REGISTERED, registeredHitchhiker);
    }

    public boolean isLogged() {
        return userLocation != null;
    }
}
