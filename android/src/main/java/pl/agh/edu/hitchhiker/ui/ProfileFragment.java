package pl.agh.edu.hitchhiker.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.agh.edu.hitchhiker.R;
import pl.agh.edu.hitchhiker.utils.CredentialStorage;

public class ProfileFragment extends Fragment {

    @OnClick(R.id.logout)
    void onLogoutClicked() {
        ((HitchhikerInterface)getActivity()).unregister();
        CredentialStorage.INSTANCE.setUserLocation(null);
        getActivity().onBackPressed();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        ButterKnife.reset(this);
        super.onDestroyView();
    }
}
