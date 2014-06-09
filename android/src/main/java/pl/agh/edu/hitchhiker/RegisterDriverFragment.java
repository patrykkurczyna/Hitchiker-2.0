package pl.agh.edu.hitchhiker;


import android.app.Fragment;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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


public class RegisterDriverFragment extends Fragment {
    @InjectView(R.id.age_spinner) Spinner ageSpinner;
    @InjectView(R.id.baggageSpinner) Spinner baggageSpinner;
    @InjectView(R.id.sexSpinner) Spinner sexSpinner;

    public RegisterDriverFragment() {
        // Required empty public constructor
    }

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

    @OnClick(R.id.saveButton) void save() {
        Toast.makeText(getActivity(), R.string.saved_info, Toast.LENGTH_SHORT).show();

        // for now
        showNotif();

        getActivity().setResult(RegisterLocationActivity.FORM_SAVED_CODE);
        getActivity().finish();
    }

    private void showNotif() {
        NotificationManager nm = (NotificationManager)getActivity().getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent = new Intent(getActivity(), NotificationReceiverActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(getActivity(), 0, intent, 0);

        Notification notification = new Notification.Builder(getActivity()).
                setContentTitle(getResources().getString(R.string.new_hitchhiker_notif_title)).
                setContentText("Za 10km czeka 2 autospowiczów do Zakopanego").setSmallIcon(R.drawable.thumb2)
                .setAutoCancel(true).getNotification();

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
