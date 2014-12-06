package pl.agh.edu.hitchhiker;

import dagger.Module;
import pl.agh.edu.hitchhiker.data.api.ApiModule;
import pl.agh.edu.hitchhiker.ui.UiModule;

@Module(
        injects = HitchhikerApp.class,
        includes = {
                ApiModule.class,
                UiModule.class
        }

)
public class AppModule {
}
