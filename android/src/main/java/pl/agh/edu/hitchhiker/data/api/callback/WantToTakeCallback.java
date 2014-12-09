package pl.agh.edu.hitchhiker.data.api.callback;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class WantToTakeCallback implements Callback<Object> {

    @Override
    public void success(Object o, Response response) {

    }

    @Override
    public void failure(RetrofitError error) {

    }
}
