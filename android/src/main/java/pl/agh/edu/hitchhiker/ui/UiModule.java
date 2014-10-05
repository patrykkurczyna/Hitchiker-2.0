package pl.agh.edu.hitchhiker.ui;

import dagger.Module;

@Module(

        injects = {
                MainActivity.class,
                NotificationReceiverActivity.class,
                RegisterDriverFragment.class,
                RegisterHitchhikerFragment.class,
                RegisterLocationActivity.class,
                SavedLocationActivity.class
        },
        complete = false

)
public class UiModule {
}
