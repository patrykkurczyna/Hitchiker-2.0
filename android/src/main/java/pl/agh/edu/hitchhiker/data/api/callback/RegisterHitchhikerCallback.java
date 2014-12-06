package pl.agh.edu.hitchhiker.data.api.callback;

import android.util.Log;

import de.greenrobot.event.EventBus;
import pl.agh.edu.hitchhiker.data.api.event.RegisterHitchhikerFailure;
import pl.agh.edu.hitchhiker.data.api.event.RegisterHitchhikerSuccess;
import pl.agh.edu.hitchhiker.data.models.Hitchhiker;
import pl.agh.edu.hitchhiker.utils.CredentialStorage;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Header;
import retrofit.client.Response;

public class RegisterHitchhikerCallback implements Callback<Hitchhiker> {
    private static final String LOCATION = "Location";

    @Override
    public void success(Hitchhiker h, Response response) {
        for (Header header : response.getHeaders()) {
            if (LOCATION.equals(header.getName())) {
                String value = header.getValue();
                CredentialStorage.INSTANCE.setRegisteredHitchhiker(
                        Integer.valueOf(value.substring(value.lastIndexOf("/") + 1)));
                break;
            }
        }
        EventBus.getDefault().post(new RegisterHitchhikerSuccess());
    }

    @Override
    public void failure(RetrofitError error) {
        Log.d("RegisterHitchhikerCallback", "failure");
        EventBus.getDefault().post(new RegisterHitchhikerFailure());
    }
}
