package pl.agh.edu.hitchhiker;

import android.app.Application;
import android.content.Context;

public class HitchhikerApp extends Application {

    private static Context context;

    public static Context getContext() {
        return HitchhikerApp.context;
    }

    public void onCreate() {
        super.onCreate();
        HitchhikerApp.context = getApplicationContext();
    }

}
