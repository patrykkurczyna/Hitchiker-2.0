package pl.agh.edu.hitchhiker.data.api;

import pl.agh.edu.hitchhiker.data.api.callback.AuthorizeUserCallback;
import pl.agh.edu.hitchhiker.data.api.callback.RegisterDriverCallback;
import pl.agh.edu.hitchhiker.data.api.callback.RegisterHitchhikerCallback;
import pl.agh.edu.hitchhiker.data.models.Driver;
import pl.agh.edu.hitchhiker.data.models.Hitchhiker;
import pl.agh.edu.hitchhiker.data.models.User;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

public interface ApiInterface {

    @POST("/hitchhikers")
    void registerHitchhiker(@Body Hitchhiker hitchhiker, RegisterHitchhikerCallback callback);

    @POST("/drivers")
    void registerDriver(@Body Driver driver, RegisterDriverCallback callback);

    @POST("/users")
    void authorizeUser(@Body User user, AuthorizeUserCallback callback);

    @GET("/users/{userId}")
    User getAuthorizeUser(@Path("userId") Integer userId);

}
