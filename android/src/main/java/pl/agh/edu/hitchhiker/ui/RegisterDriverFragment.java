package pl.agh.edu.hitchhiker.ui;


import android.app.Fragment;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
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


public class RegisterDriverFragment extends Fragment {
    @InjectView(R.id.age_spinner)
    Spinner ageSpinner;
    @InjectView(R.id.baggageSpinner)
    Spinner baggageSpinner;
    @InjectView(R.id.sexSpinner)
    Spinner sexSpinner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View driverFormView = inflater.inflate(R.layout.fragment_register_driver, container, false);
        ButterKnife.inject(this, driverFormView);

        setUpSpinner(ageSpinner, R.array.age_array);
        setUpSpinner(baggageSpinner, R.array.baggage_array);
        setUpSpinner(sexSpinner, R.array.sex_type_array);

        return driverFormView;
    }

    private void setUpSpinner(Spinner spinner, int itemsArrayId) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), itemsArrayId, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

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
        intent.putExtra(SavedLocationActivity.IS_DRIVER, true);
        startActivityForResult(intent, MainActivity.EDIT_FORM_CODE);

        Toast.makeText(getActivity(), R.string.saved_info, Toast.LENGTH_LONG).show();
    }

    private void showNotif() {
        NotificationManager nm = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent = new Intent(getActivity(), SavedLocationActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(getActivity(), 0, intent, 0);

        String content = "Za 10km czeka 2 autospowiczów do Zakopanego";

        Notification notification = new Notification.Builder(getActivity()).
                setContentTitle(getResources().getString(R.string.new_hitchhiker_notif_title))
                .setContentText(content).setSmallIcon(R.drawable.thumb2)
                .setContentIntent(pIntent)
                .getNotification();

        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.defaults |= Notification.DEFAULT_SOUND;
        nm.notify(0, notification);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }


}
