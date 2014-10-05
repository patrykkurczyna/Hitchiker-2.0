package pl.agh.edu.hitchhiker.data.gcm;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import pl.agh.edu.hitchhiker.R;
import pl.agh.edu.hitchhiker.ui.SavedLocationActivity;

public class GcmIntentService extends IntentService {
    public static final int NOTIFICATION_ID = 1;

    public static final String MSG = "message";

    public GcmIntentService() {
        super("GcmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();

        if(extras != null) {
            if(extras.containsKey(MSG)) {
                sendNotification(extras.getString(MSG));
            }
        }

        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    private void sendNotification(String msg) {
        NotificationManager mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent = new Intent(this, SavedLocationActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);

        String content = "Za 10km czeka 2 autospowiczów do Zakopanego";

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this).
        setContentTitle(getResources().getString(R.string.new_hitchhiker_notif_title))
                .setContentText(content).setSmallIcon(R.drawable.thumb2)
                .setContentIntent(pIntent);

        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }


}
