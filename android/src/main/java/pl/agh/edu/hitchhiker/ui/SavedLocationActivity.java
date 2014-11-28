package pl.agh.edu.hitchhiker.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import pl.agh.edu.hitchhiker.R;


public class SavedLocationActivity extends Activity {
    public static final String TAG = SavedLocationActivity.class.getSimpleName();
    public static final String LONGITUDE = "SavedLocationActivity.longitude";
    public static final String LATITUDE = "SavedLocationActivity.latitude";
    public static final String IS_DRIVER = "SavedLocationActivity.is_driver";
    public static final String FROM_NOTI = "SavedLocationActivity.from_noti";
    public static final String NOTI_LATITUDE = "SavedLocationActivity.noti_latitude";
    public static final String NOTI_LOGIN = "SavedLocationActivity.noti_login";
    public static final String NOTI_LONGITUDE = "SavedLocationActivity.noti_longitude";
    public static final String NOTI_DESTINATION = "SavedLocationActivity.noti_destination";

    private boolean isDriver;
    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
//        setContentView(R.layout.fragment_map);
        getActionBar().setDisplayShowTitleEnabled(true);

        map = ((MapFragment) getFragmentManager()
                .findFragmentById(R.id.map)).getMap();

        map.getUiSettings().setMyLocationButtonEnabled(false);

        Double longitude, latitude;
        Intent intent = getIntent();
        if (intent != null) {
            Log.d(TAG, "intent not null");
            if (intent.getExtras() != null) {
                for (String key : intent.getExtras().keySet()) {
//                    Log.d(TAG, key + " : " + intent.getExtras().getString(key));
                }
            } else {
                Log.d(TAG, "intent extras == null");
            }

            longitude = intent.getDoubleExtra(LONGITUDE, 19.91243);
            latitude = intent.getDoubleExtra(LATITUDE, 50.06771);
            isDriver = intent.getBooleanExtra(IS_DRIVER, false);
            LatLng myLocation = new LatLng(latitude, longitude);

            Log.d(TAG, "isDriver: " + intent.getBooleanExtra(IS_DRIVER, false));
            Log.d(TAG, "from notification: " + intent.getBooleanExtra(FROM_NOTI, false));
            Log.d(TAG, "login: " + intent.getStringExtra(NOTI_LOGIN));
            Log.d(TAG, "destination: " + intent.getStringExtra(NOTI_DESTINATION));

            map.setMyLocationEnabled(true);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 13));
            if (isDriver) {
                Log.d(TAG, "driver");
                map.addMarker(new MarkerOptions().title("Hitchiker1").snippet("Kierunek: Gdańsk, 2 osoby")
                        .position(new LatLng(latitude + 0.01, longitude + 0.01))
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
            } else {
                map.addMarker(new MarkerOptions().title(getString(R.string.my_location))
                        .position(myLocation)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            }
            if (intent.getBooleanExtra(FROM_NOTI, false)) {
                Log.d(TAG, "from notification");
                map.addMarker(new MarkerOptions()
                        .title(intent.getStringExtra(NOTI_LOGIN))
                        .snippet(String.format("Kierunek: %s", intent.getStringExtra(NOTI_DESTINATION)))
                        .position(new LatLng(intent.getDoubleExtra(NOTI_LATITUDE, 0),
                                intent.getDoubleExtra(NOTI_LONGITUDE, 0)))
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.d(TAG, "onNewIntent");
        Log.d(TAG, "isDriver: " + isDriver);
        if (isDriver && intent.getBooleanExtra(FROM_NOTI, false)) {
            Log.d(TAG, "new hitchhiker");
            Log.d(TAG, "login: " + intent.getStringExtra(NOTI_LOGIN));
            Log.d(TAG, "latitude: " + intent.getDoubleExtra(NOTI_LATITUDE, 0));
            Log.d(TAG, "longitude: " + intent.getDoubleExtra(NOTI_LONGITUDE, 0));
            Log.d(TAG, "destination: " + intent.getStringExtra(NOTI_DESTINATION));
            map.addMarker(new MarkerOptions()
                    .title(intent.getStringExtra(NOTI_LOGIN))
                    .snippet(String.format("Kierunek: %s", intent.getStringExtra(NOTI_DESTINATION)))
                    .position(new LatLng(intent.getDoubleExtra(NOTI_LATITUDE, 0),
                            intent.getDoubleExtra(NOTI_LONGITUDE, 0)))
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        }
    }
}
