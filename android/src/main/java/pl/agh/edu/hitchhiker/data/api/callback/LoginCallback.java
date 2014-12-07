package pl.agh.edu.hitchhiker.data.api.callback;

import de.greenrobot.event.EventBus;
import pl.agh.edu.hitchhiker.data.api.event.LoginFailure;
import pl.agh.edu.hitchhiker.data.api.event.LoginSuccess;
import pl.agh.edu.hitchhiker.data.models.Location;
import pl.agh.edu.hitchhiker.data.models.User;
import pl.agh.edu.hitchhiker.utils.CredentialStorage;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Header;
import retrofit.client.Response;

public class LoginCallback implements Callback<User> {
    private static final String LOCATION = "Location";

    @Override
    public void success(User user, Response response) {
        for(Header header : response.getHeaders()) {
            if(LOCATION.equals(header.getName())) {
                CredentialStorage.INSTANCE.setUserLocation(new Location(header.getValue()));
                break;
            }
        }
        EventBus.getDefault().post(new LoginSuccess());
    }

    @Override
    public void failure(RetrofitError error) {
        EventBus.getDefault().post(new LoginFailure());
    }
}
