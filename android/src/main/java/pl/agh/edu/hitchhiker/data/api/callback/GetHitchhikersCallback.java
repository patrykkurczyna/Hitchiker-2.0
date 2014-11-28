package pl.agh.edu.hitchhiker.data.api.callback;


import java.util.List;

import de.greenrobot.event.EventBus;
import pl.agh.edu.hitchhiker.data.api.event.GetHitchhikersFailure;
import pl.agh.edu.hitchhiker.data.api.event.GetHitchhikersSuccess;
import pl.agh.edu.hitchhiker.data.models.Hitchhiker;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class GetHitchhikersCallback implements Callback<List<Hitchhiker>> {

    @Override
    public void success(List<Hitchhiker> hitchhikers, Response response) {
        EventBus.getDefault().post(new GetHitchhikersSuccess(hitchhikers));
    }

    @Override
    public void failure(RetrofitError error) {
        EventBus.getDefault().post(new GetHitchhikersFailure());
    }
}
