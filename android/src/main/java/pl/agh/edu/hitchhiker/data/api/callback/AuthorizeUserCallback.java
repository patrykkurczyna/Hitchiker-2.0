package pl.agh.edu.hitchhiker.data.api.callback;

import de.greenrobot.event.EventBus;
import pl.agh.edu.hitchhiker.data.api.event.AuthorizeUserFailure;
import pl.agh.edu.hitchhiker.data.api.event.AuthorizeUserSuccess;
import pl.agh.edu.hitchhiker.data.models.User;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AuthorizeUserCallback implements Callback<User> {
    @Override
    public void success(User user, Response response) {
        EventBus.getDefault().post(new AuthorizeUserSuccess());
    }

    @Override
    public void failure(RetrofitError error) {
        EventBus.getDefault().post(new AuthorizeUserFailure());
    }
}
