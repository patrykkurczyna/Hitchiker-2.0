package pl.agh.edu.hitchhiker.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;
import java.util.Random;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;
import pl.agh.edu.hitchhiker.HitchhikerApp;
import pl.agh.edu.hitchhiker.data.api.ApiService;
import pl.agh.edu.hitchhiker.data.api.event.AuthorizeUserFailure;
import pl.agh.edu.hitchhiker.data.api.event.AuthorizeUserSuccess;
import pl.agh.edu.hitchhiker.data.models.User;
import pl.agh.edu.hitchhiker.utils.CredentialStorage;


public class MainActivity extends Activity {
    public static final String PROPERTY_REG_ID = "registration_id";
    public static final String FROM_NOTIFICATION = "from notification";
    public final static int SAVE_FORM_CODE = 1000;
    public final static int EDIT_FORM_CODE = 1001;
    public final static int RESULT_CODE_QUIT = 10001;
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String SENDER_ID = "441315978791";
    @Inject
    ApiService apiService;
    private Bundle notiExtras;
    private GoogleCloudMessaging gcm;

    public void onEventMainThread(AuthorizeUserSuccess event) {
        Log.d(TAG, "authorize success");
        afterAuthorization();
    }

    public void onEventMainThread(AuthorizeUserFailure event) {
        Log.d(TAG, "authorize failure");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CredentialStorage.INSTANCE.init();

        ((HitchhikerApp) getApplicationContext()).inject(this);
        EventBus.getDefault().register(this);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(FROM_NOTIFICATION)) {
            notiExtras = intent.getExtras();
            Log.d(TAG, "from notification");
        }

        String regId = getRegistrationId();
        if (regId.isEmpty()) {
            registerInBackground();
        } else {
            saveRegistrationId(regId);
        }


//        Intent intent = new Intent(this, LoggedActivity.class);
//        startActivityForResult(intent, SAVE_FORM_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode) {
            case RESULT_CODE_QUIT:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
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
                    Log.d(TAG, "Error: " + ex.getMessage());
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
        return CredentialStorage.INSTANCE.getDeviceId();
    }

    private void saveRegistrationId(String regId) {
        Log.d(TAG, "Save regId: " + regId);

        CredentialStorage.INSTANCE.setDeviceId(regId);

        if (!CredentialStorage.INSTANCE.isLogged()) {
            User user = new User();
            user.setLogin("test" + new Random().nextInt());
            user.setPassword("test");
            user.setFirstname("ala");
            user.setLastname("bala");
            apiService.authorizeUser(user);
        } else {
            afterAuthorization();
        }
    }

    private void afterAuthorization() {
        Intent intent = new Intent(this, LoggedActivity.class);
        if (notiExtras != null)
            intent.putExtras(notiExtras);
        startActivityForResult(intent, SAVE_FORM_CODE);
    }
}
