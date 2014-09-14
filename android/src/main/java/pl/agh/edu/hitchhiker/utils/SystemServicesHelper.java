package pl.agh.edu.hitchhiker.utils;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import pl.agh.edu.hitchhiker.HitchhikerApp;

public class SystemServicesHelper {

    public static boolean isInternetConnection() {
        ConnectivityManager cm =
                (ConnectivityManager) HitchhikerApp.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

    public static boolean isGpsEnabled() {
        LocationManager locationManager = (LocationManager) HitchhikerApp.getContext().getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }
}
