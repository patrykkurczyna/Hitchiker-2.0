package pl.agh.edu.hitchhiker.ui;

import android.app.Fragment;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;
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
import pl.agh.edu.hitchhiker.data.api.event.RegisterHitchhikerFailure;
import pl.agh.edu.hitchhiker.data.api.event.RegisterHitchhikerSuccess;
import pl.agh.edu.hitchhiker.data.models.Hitchhiker;
import pl.agh.edu.hitchhiker.utils.SystemServicesHelper;


public class RegisterHitchhikerFragment extends Fragment {
    public static final String LOG = RegisterHitchhikerFragment.class.getSimpleName();
    @Inject
    ApiService service;
    @InjectView(R.id.editDestination)
    FloatLabeledEditText destination;
    @InjectView(R.id.peopleNumber)
    FloatLabeledEditText numberPeople;
    @InjectView(R.id.age_spinner)
    Spinner ageSpinner;
    @InjectView(R.id.baggageSpinner)
    Spinner baggageSpinner;
    @InjectView(R.id.sexSpinner)
    Spinner sexSpinner;
    @InjectView(R.id.childrenButton)
    RadioButton withChildren;
    private Location registeredLocation;

    @OnClick(R.id.saveButton)
    void save() {
        if (destination.getTextString().isEmpty()) {
            destination.setError(getString(R.string.error_destination));
            return;
        }
        if (SystemServicesHelper.isInternetConnection() == false) {
            Toast.makeText(getActivity(), R.string.error_internet, Toast.LENGTH_SHORT).show();
            return;
        }
        if (SystemServicesHelper.isGpsEnabled() == false) {
            Toast.makeText(getActivity(), R.string.turn_on_gps, Toast.LENGTH_SHORT).show();
            return;
        }

        registeredLocation = ((HitchhikerInterface) getActivity()).getLocation();
        if (registeredLocation == null) {
            Toast.makeText(getActivity(), R.string.error_location, Toast.LENGTH_SHORT).show();
            return;
        }
//        ((HitchhikerInterface) getActivity()).hitchhikerRegistered(registeredLocation);
//        if (true)
//            return;

        double latitude = registeredLocation.getLatitude();
        double longitude = registeredLocation.getLongitude();

        Hitchhiker hitchhiker = new Hitchhiker();
        hitchhiker.setFinalDestination(destination.getTextString());
        hitchhiker.setGeoLongitude(longitude);
        hitchhiker.setGeoLatitude(latitude);
//        hitchhiker.setPassengersAge(Age.values()[ageSpinner.getSelectedItemPosition()]);
//        hitchhiker.setPassengersBaggage(Baggage.values()[baggageSpinner.getSelectedItemPosition()]);
//        hitchhiker.setPassengersSexType(SexType.values()[sexSpinner.getSelectedItemPosition()]);
        if (numberPeople.getEditText().length() > 0) {
            hitchhiker.setNumberOfPassengers(
                    Integer.valueOf(numberPeople.getTextString()));
        }
        if (withChildren.isChecked()) {
            hitchhiker.setWithChildren(true);
        }
        service.registerHitchhiker(hitchhiker);

    }

    public void onEventMainThread(RegisterHitchhikerSuccess event) {
//        Intent intent = new Intent(getActivity(), SavedLocationActivity.class);
//        intent.putExtra(SavedLocationActivity.LATITUDE, latitude);
//        intent.putExtra(SavedLocationActivity.LONGITUDE, longitude);
//        startActivity(intent);

        if (getActivity() instanceof HitchhikerInterface) {
            Toast.makeText(getActivity(), R.string.saved_info, Toast.LENGTH_LONG).show();
            ((HitchhikerInterface) getActivity()).hitchhikerRegistered(registeredLocation);
        }
    }

    public void onEventMainThread(RegisterHitchhikerFailure event) {
        Toast.makeText(getActivity(), R.string.error_register_form, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG, "onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View driverFormView = inflater.inflate(R.layout.fragment_register_hitchhiker, container, false);
        ButterKnife.inject(this, driverFormView);
        EventBus.getDefault().register(this);

        setUpSpinner(ageSpinner, R.array.age_array_hic);
        setUpSpinner(baggageSpinner, R.array.baggage_array_hic);
        setUpSpinner(sexSpinner, R.array.sex_type_array_hic);

        return driverFormView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((HitchhikerApp) getActivity().getApplicationContext()).inject(this);

        if (getActivity() instanceof HitchhikerInterface) {
            final HitchhikerInterface hitchhikerInterface = (HitchhikerInterface) getActivity();
            if (hitchhikerInterface.whoRegistered() != HitchhikerInterface.PersonRegistered.NONE) {
                final AlreadyRegisteredDialog dialog = new AlreadyRegisteredDialog();
                dialog.setListener(new AlreadyRegisteredDialog.AlreadyRegisteredDialogListener() {
                    @Override
                    public void onUnregiser() {
                        hitchhikerInterface.unregister();
                        dialog.dismiss();
                    }

                    @Override
                    public void onFinish() {
                        dialog.dismiss();
                        hitchhikerInterface.unregister();
                    }

                    @Override
                    public void onCancel() {
                        dialog.dismiss();
                        hitchhikerInterface.showMap();
                    }
                });
                dialog.setDriver(hitchhikerInterface.whoRegistered() == HitchhikerInterface.PersonRegistered.DRIVER);
                dialog.show(getFragmentManager(), "already_registered");
            }
        }

    }

    private void setUpSpinner(Spinner spinner, int itemsArrayId) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), itemsArrayId, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
        EventBus.getDefault().unregister(this);
    }
}
