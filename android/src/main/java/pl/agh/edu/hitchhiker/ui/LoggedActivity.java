package pl.agh.edu.hitchhiker.ui;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import pl.agh.edu.hitchhiker.HitchhikerApp;
import pl.agh.edu.hitchhiker.R;
import pl.agh.edu.hitchhiker.data.api.ApiService;
import pl.agh.edu.hitchhiker.utils.CredentialStorage;

public class LoggedActivity extends Activity implements HitchhikerInterface, GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener {

    public static final String TAG = "LoggedActivity";

    PersonRegistered personRegistered = PersonRegistered.NONE;
    @Inject
    ApiService apiService;
    @InjectView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @InjectView(R.id.left_drawer)
    ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private String[] mStrings;
    private String title;
    private LocationClient mLocationClient;
    private int currentSelected = -1;
    private Location savedLocation;
    private Bundle notiExtras;
    private boolean initial = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((HitchhikerApp) getApplicationContext()).inject(this);
//        EventBus.getDefault().register(this);

        setContentView(R.layout.activity_logged);
        ButterKnife.inject(this);

        if (CredentialStorage.INSTANCE.getRegisteredDriver() != -1) {
            personRegistered = PersonRegistered.DRIVER;
            Log.d(TAG, "started as driver");
        } else if (CredentialStorage.INSTANCE.getRegisteredHitchhiker() != -1) {
            personRegistered = PersonRegistered.HITCHHIKER;
            Log.d(TAG, "started as hitchhiker");
        }

        mLocationClient = new LocationClient(this, this, this);
        mLocationClient.connect();

        mStrings = getResources().getStringArray(R.array.drawer_array);

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mStrings));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(title);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                // hide keyboard
                InputMethodManager imm = (InputMethodManager) getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindow().getDecorView().getRootView().getWindowToken(), 0);

                getActionBar().setTitle(getString(R.string.app_name));
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

    }

    @Override
    protected void onDestroy() {
        ButterKnife.reset(this);
        mLocationClient.disconnect();
//        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setTitle(CharSequence title) {
        this.title = title.toString();
        getActionBar().setTitle(title);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        setResult(MainActivity.RESULT_CODE_QUIT);
        super.onBackPressed();
    }

    /**
     * LOCALIZATION
     */

    @Override
    public void onConnected(Bundle bundle) {
        Log.d(TAG, "onConnected");
        afterConnected();
    }

    @Override
    public void onDisconnected() {
        Log.d(TAG, "onDisconnected");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "connectionFailed: " + connectionResult.toString());
    }

    /**
     * HITCHHIKER INTERFACE
     */

    @Override
    public Location getLocation() {
        return mLocationClient.getLastLocation();
    }

    @Override
    public void showMap() {
        selectItem(0);
    }

    @Override
    public void hitchhikerRegistered(Location location) {
        savedLocation = location;
        personRegistered = PersonRegistered.HITCHHIKER;
        selectItem(0);
    }

    @Override
    public void driverRegistered(Location location) {
        savedLocation = location;
        personRegistered = PersonRegistered.DRIVER;
        selectItem(0);
    }

    @Override
    public void unregister() {
        savedLocation = null;
        switch (personRegistered) {
            case DRIVER:
                CredentialStorage.INSTANCE.setRegisteredDriver(-1);
                apiService.unregisterDriver(CredentialStorage.INSTANCE.getRegisteredDriver());
                break;
            case HITCHHIKER:
                CredentialStorage.INSTANCE.setRegisteredHitchhiker(-1);
                apiService.unregisterHitchhiker(CredentialStorage.INSTANCE.getRegisteredHitchhiker());
                break;
        }

        personRegistered = PersonRegistered.NONE;
    }

    @Override
    public PersonRegistered whoRegistered() {
        return personRegistered;
    }

    private void selectItem(int position) {
        Log.d(TAG, "selectItem: " + position);
        if (currentSelected == position) {
            mDrawerLayout.closeDrawer(mDrawerList);
            return;
        }

        Fragment fragment;
        switch (position) {
            case 0:
                fragment = new MapFragment();
                if (savedLocation == null) {
                    break;
                }
                Log.d(TAG, "show map");
                Bundle args = new Bundle();
                args.putDouble(MapFragment.LATITUDE, savedLocation.getLatitude());
                args.putDouble(MapFragment.LONGITUDE, savedLocation.getLongitude());
                args.putBoolean(MapFragment.IS_DRIVER, personRegistered == PersonRegistered.DRIVER);
                if (notiExtras != null) {
                    Log.d(TAG, "pass notification extras");
                    args.putAll(notiExtras);
                }
                fragment.setArguments(args);
                break;
            case 1:
                fragment = new RegisterHitchhikerFragment();
                break;
            case 2:
                fragment = new RegisterDriverFragment();
                break;
            case 3:
                fragment = new TripsFragment();
                break;
            case 4:
                fragment = new ProfileFragment();
                break;
            default:
                return;
        }
        currentSelected = position;
        setTitle(mStrings[position]);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    private void afterConnected() {
        if (initial) {
            Intent intent = getIntent();
            if (intent != null && intent.hasExtra(MainActivity.FROM_NOTIFICATION)) {
                Log.d(TAG, "from notification");
                notiExtras = intent.getExtras();
            }
            savedLocation = getLocation();
            selectItem(0);
            initial = false;
        }
    }

    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }
}
