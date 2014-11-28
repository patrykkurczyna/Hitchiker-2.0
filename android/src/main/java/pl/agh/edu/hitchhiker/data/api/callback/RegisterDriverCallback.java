package pl.agh.edu.hitchhiker.data.api.callback;

import de.greenrobot.event.EventBus;
import pl.agh.edu.hitchhiker.data.api.event.RegisterDriverFailure;
import pl.agh.edu.hitchhiker.data.api.event.RegisterDriverSuccess;
import pl.agh.edu.hitchhiker.data.models.Driver;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class RegisterDriverCallback implements Callback<Driver> {
    @Override
    public void success(Driver driver, Response response) {
        EventBus.getDefault().post(new RegisterDriverSuccess());
    }

    @Override
    public void failure(RetrofitError error) {
        int status = -1;
        if(error.getResponse() != null) {
            status = error.getResponse().getStatus();
        }

        EventBus.getDefault().post(new RegisterDriverFailure(status));
    }
}
