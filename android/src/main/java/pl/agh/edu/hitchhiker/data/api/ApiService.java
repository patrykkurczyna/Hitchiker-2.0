package pl.agh.edu.hitchhiker.data.api;

import pl.agh.edu.hitchhiker.data.api.callback.AuthorizeUserCallback;
import pl.agh.edu.hitchhiker.data.api.callback.RegisterDriverCallback;
import pl.agh.edu.hitchhiker.data.api.callback.RegisterHitchhikerCallback;
import pl.agh.edu.hitchhiker.data.models.Driver;
import pl.agh.edu.hitchhiker.data.models.Hitchhiker;
import pl.agh.edu.hitchhiker.data.models.User;

public class ApiService {

    private ApiInterface mInterface;

    public ApiService(ApiInterface mInterface) {
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
