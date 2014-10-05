package pl.agh.edu.hitchhiker.data.api;

import pl.agh.edu.hitchhiker.data.api.callback.AuthorizeUserCallback;
import pl.agh.edu.hitchhiker.data.api.callback.RegisterDriverCallback;
import pl.agh.edu.hitchhiker.data.api.callback.RegisterHitchhikerCallback;
import pl.agh.edu.hitchhiker.data.models.Driver;
import pl.agh.edu.hitchhiker.data.models.Hitchhiker;
import pl.agh.edu.hitchhiker.data.models.User;
import retrofit.http.Body;
import retrofit.http.POST;

public interface ApiInterface {

    @POST("/register/hitchhiker")
    void registerHitchhiker(@Body Hitchhiker hitchhiker, RegisterHitchhikerCallback callback);

    @POST("/register/driver")
    void registerDriver(@Body Driver driver, RegisterDriverCallback callback);

    @POST("/authorize/user")
    void authorizeUser(@Body User user, AuthorizeUserCallback callback);
}
