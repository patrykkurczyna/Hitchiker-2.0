package pl.agh.edu.hitchhiker.data.api.callback;

import android.util.Log;

import de.greenrobot.event.EventBus;
import pl.agh.edu.hitchhiker.data.api.event.RegisterHitchhikerFailure;
import pl.agh.edu.hitchhiker.data.api.event.RegisterHitchhikerSuccess;
import pl.agh.edu.hitchhiker.data.models.Hitchhiker;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class RegisterHitchhikerCallback implements Callback<Hitchhiker> {
    @Override
    public void success(Hitchhiker h, Response response) {
        EventBus.getDefault().post(new RegisterHitchhikerSuccess());
    }

    @Override
    public void failure(RetrofitError error) {
        Log.d("RegisterHitchhikerCallback", "failure");
        EventBus.getDefault().post(new RegisterHitchhikerFailure());
    }
}
