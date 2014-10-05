package pl.agh.edu.hitchhiker.data.api;

import pl.agh.edu.hitchhiker.data.api.callback.AuthorizeUserCallback;
import pl.agh.edu.hitchhiker.data.api.callback.RegisterDriverCallback;
import pl.agh.edu.hitchhiker.data.api.callback.RegisterHitchhikerCallback;
import pl.agh.edu.hitchhiker.data.models.Driver;
import pl.agh.edu.hitchhiker.data.models.Hitchhiker;
import pl.agh.edu.hitchhiker.data.models.User;

public class HitchhikerService {

    private ApiInterface mInterface;

    public HitchhikerService(ApiInterface mInterface) {
        this.mInterface = mInterface;
    }

    public void registerHitchhiker(Hitchhiker hitchhiker) {
        mInterface.registerHitchhiker(hitchhiker, new RegisterHitchhikerCallback());
    }

    public void registerDriver(Driver driver) {
        mInterface.registerDriver(driver, new RegisterDriverCallback());
    }

    public void authorizeUser(User user) {
        mInterface.authorizeUser(user, new AuthorizeUserCallback());
    }
}
