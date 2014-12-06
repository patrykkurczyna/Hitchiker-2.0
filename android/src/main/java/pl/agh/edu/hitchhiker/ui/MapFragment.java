package pl.agh.edu.hitchhiker.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import pl.agh.edu.hitchhiker.R;

public class MapFragment extends Fragment {
    public static final String TAG = MapFragment.class.getSimpleName();
    public static final String LONGITUDE = TAG + ".longitude";
    public static final String LATITUDE = TAG + ".latitude";
    public static final String IS_DRIVER = TAG + ".is_driver";
    public static final String NOTI_LATITUDE = "SavedLocationActivity.noti_latitude";
    public static final String NOTI_LONGITUDE = "SavedLocationActivity.noti_longitude";
    public static final String NOTI_LOGIN = "SavedLocationActivity.noti_login";
    public static final String NOTI_DESTINATION = "SavedLocationActivity.noti_destination";


    private GoogleMap map;
    private LatLng savedLocation;
    private boolean isDriver;
    private Bundle args;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");

        args = getArguments();
        if (args != null) {
            double longitude = args.getDouble(LONGITUDE, -1);
            double latitude = args.getDouble(LATITUDE, -1);
            if (longitude != -1 && latitude != -1) {
                savedLocation = new LatLng(latitude, longitude);
            }
            isDriver = args.getBoolean(IS_DRIVER, false);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        map = ((com.google.android.gms.maps.MapFragment) getFragmentManager()
                .findFragmentById(R.id.map)).getMap();
        map.setMyLocationEnabled(true);
        if (savedLocation != null) {
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(savedLocation, 13));
            if (!isDriver) {
                map.addMarker(new MarkerOptions().title(getString(R.string.my_location))
                        .position(savedLocation)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            }
        }
        if (isDriver) {
            Log.d(TAG, "Driver");
            map.addMarker(new MarkerOptions().title("Hitchiker1").snippet("Kierunek: Gdańsk, 2 osoby")
                    .position(new LatLng(savedLocation.latitude + 0.01, savedLocation.longitude + 0.01))
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
            map.addMarker(new MarkerOptions().title("Hitchiker2").snippet("Kierunek: Wrocław, 1 osoba")
                    .position(new LatLng(savedLocation.latitude + 0.01, savedLocation.longitude - 0.01))
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
            if (args.getBoolean(MainActivity.FROM_NOTIFICATION, false)) {
                addNewHitchiker(args);
            }
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, "onDestroyView");
        if (getActivity() != null && !getActivity().isFinishing()) {
            com.google.android.gms.maps.MapFragment f = (com.google.android.gms.maps.MapFragment) getFragmentManager()
                    .findFragmentById(R.id.map);
            if (f != null)
                getFragmentManager().beginTransaction().remove(f).commit();
        }
        super.onDestroyView();
    }

    public void addNewHitchiker(Bundle info) {
        Log.d(TAG, "new hitchhiker");
        Log.d(TAG, "login: " + info.getString(NOTI_LOGIN));
        Log.d(TAG, "latitude: " + info.getDouble(NOTI_LATITUDE, 0));
        Log.d(TAG, "longitude: " + info.getDouble(NOTI_LONGITUDE, 0));
        Log.d(TAG, "destination: " + info.getString(NOTI_DESTINATION));
        LatLng pos = new LatLng(info.getDouble(NOTI_LATITUDE, 0),
                info.getDouble(NOTI_LONGITUDE, 0));
        map.addMarker(new MarkerOptions()
                .title(info.getString(NOTI_LOGIN))
                .snippet(String.format("Kierunek: %s", info.getString(NOTI_DESTINATION)))
                .position(pos)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, 13));
    }
}
