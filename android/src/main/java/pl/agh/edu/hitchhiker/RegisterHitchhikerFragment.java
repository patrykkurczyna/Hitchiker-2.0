package pl.agh.edu.hitchhiker;

import android.app.Activity;
import android.app.Fragment;
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


public class RegisterHitchhikerFragment extends Fragment {

    @InjectView(R.id.age_spinner)
    Spinner ageSpinner;
    @InjectView(R.id.baggageSpinner) Spinner baggageSpinner;
    @InjectView(R.id.sexSpinner) Spinner sexSpinner;

    public RegisterHitchhikerFragment() {
        // Required empty public constructor
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

    @OnClick(R.id.saveButton) void save() {
        Toast.makeText(getActivity(), R.string.saved_info, Toast.LENGTH_SHORT).show();
        getActivity().setResult(RegisterLocationActivity.FORM_SAVED_CODE);
        getActivity().finish();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }



}
