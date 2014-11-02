package pl.agh.edu.hitchhiker.ui;

import android.location.Location;

public interface HitchhikerInterface {
    enum PersonRegistered {
        NONE, HITCHHIKER, DRIVER;
    }
    Location getLocation();
    void showMap();
    void hitchhikerRegistered(Location location);
    void driverRegistered(Location location);
    void unregister();
    PersonRegistered whoRegistered();
}
