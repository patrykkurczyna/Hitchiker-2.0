package pl.agh.edu.hitchhiker.utils;

import pl.agh.edu.hitchhiker.data.models.Location;

public enum CredentialStorage {
    INSTANCE;

    private String deviceId;
    private Location location;

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
}
