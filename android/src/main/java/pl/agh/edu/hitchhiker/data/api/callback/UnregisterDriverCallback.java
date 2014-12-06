package pl.agh.edu.hitchhiker.data.api.callback;

import pl.agh.edu.hitchhiker.data.models.Driver;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class UnregisterDriverCallback implements Callback<Driver> {
    @Override
    public void success(Driver driver, Response response) {

    }

    @Override
    public void failure(RetrofitError error) {

    }
}
