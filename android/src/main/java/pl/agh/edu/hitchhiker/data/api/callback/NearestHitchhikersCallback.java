package pl.agh.edu.hitchhiker.data.api.callback;

import de.greenrobot.event.EventBus;
import pl.agh.edu.hitchhiker.data.api.event.GetNearestHitchhikersFailure;
import pl.agh.edu.hitchhiker.data.api.event.GetNearestHitchhikersSuccess;
import pl.agh.edu.hitchhiker.data.models.NearestHitchhikersResponse;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class NearestHitchhikersCallback implements Callback<NearestHitchhikersResponse> {

    @Override
    public void success(NearestHitchhikersResponse nearestHitchhikersResponse, Response response) {
        EventBus.getDefault().post(new GetNearestHitchhikersSuccess(nearestHitchhikersResponse));
    }

    @Override
    public void failure(RetrofitError error) {
        EventBus.getDefault().post(new GetNearestHitchhikersFailure());
    }
}
