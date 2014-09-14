package pl.agh.edu.hitchhiker.ui;

import android.app.Fragment;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import pl.agh.edu.hitchhiker.R;
import pl.agh.edu.hitchhiker.utils.SystemServicesHelper;


public class RegisterHitchhikerFragment extends Fragment {

    @InjectView(R.id.age_spinner)
    Spinner ageSpinner;
    @InjectView(R.id.baggageSpinner)
    Spinner baggageSpinner;
    @InjectView(R.id.sexSpinner)
    Spinner sexSpinner;

    @OnClick(R.id.saveButton)
    void save() {
        if (SystemServicesHelper.isInternetConnection() == false) {
            Toast.makeText(getActivity(), R.string.error_internet, Toast.LENGTH_SHORT).show();
            return;
        }
        if (SystemServicesHelper.isGpsEnabled() == false) {
            Toast.makeText(getActivity(), R.string.turn_on_gps, Toast.LENGTH_SHORT).show();
            return;
        }

        Location location = ((RegisterLocationActivity) getActivity()).getLastLocation();
        if (location == null) {
            Toast.makeText(getActivity(), R.string.error_location, Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(getActivity(), SavedLocationActivity.class);
        intent.putExtra(SavedLocationActivity.LATITUDE, location.getLatitude());
        intent.putExtra(SavedLocationActivity.LONGITUDE, location.getLongitude());
        startActivityForResult(intent, MainActivity.EDIT_FORM_CODE);

        Toast.makeText(getActivity(), R.string.saved_info, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View driverFormView = inflater.inflate(R.layout.fragment_register_hitchhiker, container, false);
        ButterKnife.inject(this, driverFormView);

        setUpSpinner(ageSpinner, R.array.age_array_hic);
        setUpSpinner(baggageSpinner, R.array.baggage_array_hic);
        setUpSpinner(sexSpinner, R.array.sex_type_array_hic);

        return driverFormView;
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
    }
}
