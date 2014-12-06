package pl.agh.edu.hitchhiker.data.api.callback;

import pl.agh.edu.hitchhiker.data.models.Hitchhiker;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class UnregisterHitchhikerCallback implements Callback<Hitchhiker> {
    @Override
    public void success(Hitchhiker hitchhiker, Response response) {

    }

    @Override
    public void failure(RetrofitError error) {

    }
}
