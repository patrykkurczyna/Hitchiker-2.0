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
import pl.agh.edu.hitchhiker.data.api.event.AuthorizeUserFailure;
import pl.agh.edu.hitchhiker.data.api.event.AuthorizeUserSuccess;
import pl.agh.edu.hitchhiker.data.models.User;

public class RegisterFragment extends Fragment {
    private static final String TAG = RegisterFragment.class.getSimpleName();

    @Inject
    ApiService apiService;

    @InjectView(R.id.login)
    FloatLabeledEditText login;

    @InjectView(R.id.password)
    FloatLabeledEditText password;

    @InjectView(R.id.mail)
    FloatLabeledEditText mail;

    @InjectView(R.id.firstname)
    FloatLabeledEditText firstname;

    @InjectView(R.id.lastname)
    FloatLabeledEditText lastname;

    @OnClick(R.id.have_account)
    void onHaveAccountClicked() {
        LoginFragment loginFragment = new LoginFragment();
        ((NotLoggedActivity) getActivity()).showFragment(loginFragment);
    }

    @OnClick(R.id.registerButton)
    void onRegisterClicked() {
        if(!checkFields(login, password, mail, firstname, lastname)) {
            Toast.makeText(getActivity(), R.string.incorrect_fields, Toast.LENGTH_LONG).show();
            return;
        }
        User user = new User();
        user.setLogin(login.getTextString());
        user.setPassword(password.getTextString());
        user.setFirstname(firstname.getTextString());
        user.setLastname(lastname.getTextString());
        user.setMail(mail.getTextString());
        apiService.authorizeUser(user);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
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

    private boolean checkFields(FloatLabeledEditText... fields) {
        for (FloatLabeledEditText field : fields) {
            if (field.getTextString().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public void onEventMainThread(AuthorizeUserSuccess event) {
        Log.d(TAG, "authorize success");
        getActivity().setResult(MainActivity.RESULT_CODE_LOGIN_SUCCESS);
        getActivity().finish();
    }

    public void onEventMainThread(AuthorizeUserFailure event) {
        Log.d(TAG, "authorize failure");
        Toast.makeText(getActivity(), R.string.register_error, Toast.LENGTH_LONG).show();
    }

}
