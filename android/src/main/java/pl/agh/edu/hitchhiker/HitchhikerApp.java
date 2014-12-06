package pl.agh.edu.hitchhiker;

import android.app.Application;
import android.content.Context;

import dagger.ObjectGraph;

public class HitchhikerApp extends Application {

    private static Context context;
    private ObjectGraph objectGraph;

    public static Context getContext() {
        return HitchhikerApp.context;
    }

    public void onCreate() {
        super.onCreate();
        HitchhikerApp.context = getApplicationContext();
        buildObjectGraph();
    }

    public void inject(Object object) {
        objectGraph.inject(object);
    }

    private void buildObjectGraph() {
        objectGraph = ObjectGraph.create(AppModule.class);
        objectGraph.inject(this);
    }

}
