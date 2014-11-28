package pl.agh.edu.hitchhiker.data.api;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.Endpoint;
import retrofit.Endpoints;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

@Module(
        library = true
)
public class ApiModule {
    private final static String ENDPOINT = "http://87.206.242.147:1111/webservice";

    @Provides
    GsonConverter provideGsonConverter() {
        final GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.IDENTITY);
        return new GsonConverter(gsonBuilder.create());
    }

    @Provides
    OkClient provideOkClient() {
        return new OkClient(new OkHttpClient());
    }

    @Provides
    RestAdapter provideRestAdapter(Endpoint endpoint, OkClient okClient, GsonConverter gsonConverter) {
        RestAdapter.Builder builder = new RestAdapter.Builder();
        builder.setEndpoint(endpoint);
        builder.setClient(okClient);
        builder.setConverter(gsonConverter);
        builder.setLogLevel(RestAdapter.LogLevel.FULL);
        return builder.build();
    }

    @Provides
    @Singleton
    ApiService provideHitchhikerService(RestAdapter restAdapter) {
        return new ApiService(restAdapter.create(ApiInterface.class));
    }

    @Provides
    @Singleton
    Endpoint provideEndpoint() {
        return Endpoints.newFixedEndpoint(ENDPOINT);
    }

}
