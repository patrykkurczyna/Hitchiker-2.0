package pl.agh.edu.hitchhiker.data.api.callback;

import de.greenrobot.event.EventBus;
import pl.agh.edu.hitchhiker.data.api.event.AuthorizeUserFailure;
import pl.agh.edu.hitchhiker.data.api.event.AuthorizeUserSuccess;
import pl.agh.edu.hitchhiker.data.models.Location;
import pl.agh.edu.hitchhiker.data.models.User;
import pl.agh.edu.hitchhiker.utils.CredentialStorage;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Header;
import retrofit.client.Response;

public class AuthorizeUserCallback implements Callback<User> {
    private static final String LOCATION = "Location";

    @Override
    public void success(User user, Response response) {
        for(Header header : response.getHeaders()) {
            if(LOCATION.equals(header.getName())) {
                CredentialStorage.INSTANCE.setLocation(new Location(header.getValue()));
                break;
            }
        }
        EventBus.getDefault().post(new AuthorizeUserSuccess());
    }

    @Override
    public void failure(RetrofitError error) {
        EventBus.getDefault().post(new AuthorizeUserFailure());
    }
}
