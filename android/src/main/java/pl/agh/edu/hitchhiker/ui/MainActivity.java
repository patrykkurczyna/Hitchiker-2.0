package pl.agh.edu.hitchhiker.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

import javax.inject.Inject;

import pl.agh.edu.hitchhiker.HitchhikerApp;
import pl.agh.edu.hitchhiker.data.api.HitchhikerService;
import pl.agh.edu.hitchhiker.data.models.User;


public class MainActivity extends Activity {
    public static final String PROPERTY_REG_ID = "registration_id";
    public final static int SAVE_FORM_CODE = 1000;
    public final static int EDIT_FORM_CODE = 1001;
    private static final String SENDER_ID = "441315978791";
    private static final String TAG = MainActivity.class.getSimpleName();
    private GoogleCloudMessaging gcm;

    @Inject
    HitchhikerService service;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((HitchhikerApp)getApplicationContext()).inject(this);

        String regId = getRegistrationId();
        if(regId.isEmpty()) {
            registerInBackground();
        } else {
            saveRegistrationId(regId);
        }

        Intent intent = new Intent(this, RegisterLocationActivity.class);
        startActivityForResult(intent, SAVE_FORM_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode) {
            case RegisterLocationActivity.FORM_SAVED_CODE:
                Intent intent = new Intent(this, SavedLocationActivity.class);
                startActivityForResult(intent, EDIT_FORM_CODE);
                break;
            case RegisterLocationActivity.FORM_REJECTED_CODE:
                finish();
                break;
        }
    }

    private void registerInBackground() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String registrationId = null;
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(HitchhikerApp.getContext());
                    }
                    registrationId = gcm.register(SENDER_ID);

                } catch (IOException ex) {
                    Log.d(TAG, "Error :" + ex.getMessage());
                }
                return registrationId;
            }

            @Override
            protected void onPostExecute(String registrationId) {
                if (registrationId != null) {
                    saveRegistrationId(registrationId);
                }
            }
        }.execute(null, null);
    }

    private String getRegistrationId() {
        final SharedPreferences prefs = getSharedPreferences(MainActivity.class.getSimpleName(),
                Context.MODE_PRIVATE);
        String registrationId = prefs.getString(PROPERTY_REG_ID, "");
        if (registrationId.isEmpty()) {
            Log.d(TAG, "Registration id not found.");
            return "";
        }
        return registrationId;
    }

    private void saveRegistrationId(String regId) {
        Log.d(TAG, "Save regId: " + regId);
        User user = new User();
        user.setDeviceId(regId);
        service.authorizeUser(user);
    }
}
