package pl.agh.edu.hitchhiker.data.api;

import java.util.HashMap;
import java.util.Map;

import pl.agh.edu.hitchhiker.data.api.callback.AuthorizeUserCallback;
import pl.agh.edu.hitchhiker.data.api.callback.LoginCallback;
import pl.agh.edu.hitchhiker.data.api.callback.NearestHitchhikersCallback;
import pl.agh.edu.hitchhiker.data.api.callback.RegisterDriverCallback;
import pl.agh.edu.hitchhiker.data.api.callback.RegisterHitchhikerCallback;
import pl.agh.edu.hitchhiker.data.api.callback.UnregisterDriverCallback;
import pl.agh.edu.hitchhiker.data.api.callback.UnregisterHitchhikerCallback;
import pl.agh.edu.hitchhiker.data.api.callback.WantToTakeCallback;
import pl.agh.edu.hitchhiker.data.models.Driver;
import pl.agh.edu.hitchhiker.data.models.DriverUnregister;
import pl.agh.edu.hitchhiker.data.models.Hitchhiker;
import pl.agh.edu.hitchhiker.data.models.HitchhikerUnregister;
import pl.agh.edu.hitchhiker.data.models.Login;
import pl.agh.edu.hitchhiker.data.models.User;

public class ApiService {

    private ApiInterface mInterface;

    public ApiService(ApiInterface mInterface) {
        this.mInterface = mInterface;
    }

    public void authorizeUser(User user) {
        mInterface.authorizeUser(user, new AuthorizeUserCallback());
    }

    public void login(Login login) {
        mInterface.login(login, new LoginCallback());
    }

    public void registerHitchhiker(Hitchhiker hitchhiker) {
        mInterface.registerHitchhiker(hitchhiker, new RegisterHitchhikerCallback());
    }

    public void registerDriver(Driver driver) {
        mInterface.registerDriver(driver, new RegisterDriverCallback());
    }

    public void unregisterHitchhiker(int hitchhikerId) {
        mInterface.unregisterHitchiker(hitchhikerId, new HitchhikerUnregister(), new UnregisterHitchhikerCallback());
    }

    public void unregisterDriver(int driverId) {
        mInterface.unregisterDriver(driverId, new DriverUnregister(), new UnregisterDriverCallback());
    }

    public void getNearestHitchhikers(int driverId) {
        Map<String, String> params = new HashMap<>();
        params.put("driverId", String.valueOf(driverId));
        params.put("radius", String.valueOf(150));
        mInterface.getNearestHitchhikers(params, new NearestHitchhikersCallback());
    }

    public void wantTake(int hitchhikerId, int driverId) {
        mInterface.wantToTakeHitchhiker(hitchhikerId, driverId, new WantToTakeCallback());
    }

}
