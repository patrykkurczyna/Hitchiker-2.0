package pl.agh.edu.hitchhiker.data.gcm;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import pl.agh.edu.hitchhiker.R;
import pl.agh.edu.hitchhiker.ui.MainActivity;
import pl.agh.edu.hitchhiker.ui.MapFragment;
import pl.agh.edu.hitchhiker.ui.SavedLocationActivity;

public class GcmIntentService extends IntentService {
    public static final int NOTIFICATION_ID = 1;

    public static final String LONGITUDE = "geoLongitude";
    public static final String LATITUDE = "geoLatitude";
    public static final String FINAL_DESTINATION = "finalDestination";
    public static final String LOGIN = "login";
    public static final String TAG = "GcmIntentService";

    public GcmIntentService() {
        super("GcmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "onHandleIntent");

        Bundle extras = intent.getExtras();
        if(extras != null) {
            for (String key : extras.keySet()) {
                Log.d(TAG, key + " : " + extras.getString(key));
            }
            sendNotification(extras);
        }

        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    private void sendNotification(Bundle extras) {
        NotificationManager mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MainActivity.FROM_NOTIFICATION, true);
        intent.putExtra(MapFragment.IS_DRIVER, true);
        intent.putExtra(MapFragment.NOTI_LONGITUDE, Double.valueOf(extras.getString(LONGITUDE)));
        intent.putExtra(MapFragment.NOTI_LATITUDE, Double.valueOf(extras.getString(LATITUDE)));
        intent.putExtra(MapFragment.NOTI_LOGIN, extras.getString(LOGIN));
        intent.putExtra(MapFragment.NOTI_DESTINATION, extras.getString(FINAL_DESTINATION));
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        String content = String.format("Za 10km czeka 1 autospowicz do %s", extras.getString(FINAL_DESTINATION));

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this).
        setContentTitle(getResources().getString(R.string.new_hitchhiker_notif_title))
                .setContentText(content).setSmallIcon(R.drawable.thumb2)
                .setContentIntent(pIntent).setAutoCancel(true);
        Notification noti = mBuilder.build();
        noti.defaults |= Notification.DEFAULT_VIBRATE;
        noti.defaults |= Notification.DEFAULT_SOUND;

        mNotificationManager.notify(NOTIFICATION_ID, noti);
    }


}
