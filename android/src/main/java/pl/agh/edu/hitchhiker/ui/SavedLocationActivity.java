package pl.agh.edu.hitchhiker.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import pl.agh.edu.hitchhiker.R;


public class SavedLocationActivity extends Activity {
    public static final String LONGITUDE = "SavedLocationActivity.longitude";
    public static final String LATITUDE = "SavedLocationActivity.latitude";
    public static final String IS_DRIVER = "SavedLocationActivity.is_driver";

    private boolean isDriver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_map);
        getActionBar().setDisplayShowTitleEnabled(true);

        GoogleMap map = ((MapFragment) getFragmentManager()
                .findFragmentById(R.id.map)).getMap();

        map.getUiSettings().setMyLocationButtonEnabled(false);

        Double longitude, latitude;
        Intent intent = getIntent();
        if (intent != null) {
            longitude = intent.getDoubleExtra(LONGITUDE, 19.91243);
            latitude = intent.getDoubleExtra(LATITUDE, 50.06771);
            isDriver = intent.getBooleanExtra(IS_DRIVER, false);
            LatLng myLocation = new LatLng(latitude, longitude);

            map.setMyLocationEnabled(true);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 13));
            if (isDriver) {
                map.addMarker(new MarkerOptions().title("Hitchiker1").snippet("Kierunek: Gdańsk, 2 osoby")
                        .position(new LatLng(latitude + 0.01, longitude + 0.01))
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
            } else {
                map.addMarker(new MarkerOptions().title(getString(R.string.my_location))
                        .position(myLocation)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            }
        }
    }

    @Override
    public void onBackPressed() {
        setResult(RegisterLocationActivity.FORM_REJECTED_CODE);
        super.onBackPressed();
    }
}
