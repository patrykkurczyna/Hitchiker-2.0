package pl.agh.edu.hitchhiker.data.api;

import java.util.Map;

import pl.agh.edu.hitchhiker.data.api.callback.AuthorizeUserCallback;
import pl.agh.edu.hitchhiker.data.api.callback.NearestHitchhikersCallback;
import pl.agh.edu.hitchhiker.data.api.callback.RegisterDriverCallback;
import pl.agh.edu.hitchhiker.data.api.callback.RegisterHitchhikerCallback;
import pl.agh.edu.hitchhiker.data.api.callback.UnregisterDriverCallback;
import pl.agh.edu.hitchhiker.data.api.callback.UnregisterHitchhikerCallback;
import pl.agh.edu.hitchhiker.data.models.Driver;
import pl.agh.edu.hitchhiker.data.models.DriverUnregister;
import pl.agh.edu.hitchhiker.data.models.Hitchhiker;
import pl.agh.edu.hitchhiker.data.models.HitchhikerUnregister;
import pl.agh.edu.hitchhiker.data.models.User;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.QueryMap;

public interface ApiInterface {

    @POST("/users")
    void authorizeUser(@Body User user, AuthorizeUserCallback callback);

    @POST("/hitchhikers")
    void registerHitchhiker(@Body Hitchhiker hitchhiker, RegisterHitchhikerCallback callback);

    @POST("/drivers")
    void registerDriver(@Body Driver driver, RegisterDriverCallback callback);

    @PUT("/drivers/{id}")
    void unregisterDriver(@Path("id") int driverId, @Body DriverUnregister driverUnregister,
                          UnregisterDriverCallback callback);

    @PUT("/hitchhikers/{id}")
    void unregisterHitchiker(@Path("id") int hitchhikerId, @Body HitchhikerUnregister hitchhikerUnregister,
                             UnregisterHitchhikerCallback callback);

    @GET("/findHitchhikers")
    void getNearestHitchhikers(@QueryMap Map<String, String> queryParams, NearestHitchhikersCallback callback);
}
