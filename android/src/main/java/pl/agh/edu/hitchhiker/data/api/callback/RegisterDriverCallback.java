package pl.agh.edu.hitchhiker.data.api.callback;

import android.util.Log;

import de.greenrobot.event.EventBus;
import pl.agh.edu.hitchhiker.data.api.event.RegisterDriverFailure;
import pl.agh.edu.hitchhiker.data.api.event.RegisterDriverSuccess;
import pl.agh.edu.hitchhiker.data.models.Driver;
import pl.agh.edu.hitchhiker.utils.CredentialStorage;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Header;
import retrofit.client.Response;

public class RegisterDriverCallback implements Callback<Driver> {
    private static final String LOCATION = "Location";

    @Override
    public void success(Driver driver, Response response) {
        for (Header header : response.getHeaders()) {
            if (LOCATION.equals(header.getName())) {
                String value = header.getValue();
                CredentialStorage.INSTANCE.setRegisteredDriver(
                        Integer.valueOf(value.substring(value.lastIndexOf("/") + 1)));
                Log.d("RegisterDriverCallback ", "driver id: " + CredentialStorage.INSTANCE.getRegisteredDriver());
                Log.d("RegisterDriverCallback ", "driver: " + value);
                break;
            }
        }
        EventBus.getDefault().post(new RegisterDriverSuccess());
    }

    @Override
    public void failure(RetrofitError error) {
        int status = -1;
        if (error.getResponse() != null) {
            status = error.getResponse().getStatus();
        }

        EventBus.getDefault().post(new RegisterDriverFailure(status));
    }
}
