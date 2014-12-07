package pl.agh.edu.hitchhiker.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wrapp.floatlabelededittext.FloatLabeledEditText;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import pl.agh.edu.hitchhiker.HitchhikerApp;
import pl.agh.edu.hitchhiker.R;
import pl.agh.edu.hitchhiker.data.api.ApiService;
import pl.agh.edu.hitchhiker.data.api.event.LoginFailure;
import pl.agh.edu.hitchhiker.data.api.event.LoginSuccess;
import pl.agh.edu.hitchhiker.data.models.Login;

public class LoginFragment extends Fragment {
    private static final String TAG = LoginFragment.class.getSimpleName();

    @Inject
    ApiService apiService;

    @InjectView(R.id.login)
    FloatLabeledEditText login;

    @InjectView(R.id.password)
    FloatLabeledEditText password;

    @OnClick(R.id.loginButton)
    public void onLoginClicked() {
        Login cred = new Login();
        cred.setLogin(login.getTextString());
        cred.setPassword(password.getTextString());
        apiService.login(cred);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.inject(this, view);
        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((HitchhikerApp) getActivity().getApplicationContext()).inject(this);
    }

    @Override
    public void onDestroyView() {
        ButterKnife.reset(this);
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    public void onEventMainThread(LoginSuccess event) {
        Log.d(TAG, "login success");
        getActivity().setResult(MainActivity.RESULT_CODE_LOGIN_SUCCESS);
        getActivity().finish();
    }

    public void onEventMainThread(LoginFailure event) {
        Log.d(TAG, "login failure");
        Toast.makeText(getActivity(), R.string.login_error, Toast.LENGTH_LONG).show();
    }
}
