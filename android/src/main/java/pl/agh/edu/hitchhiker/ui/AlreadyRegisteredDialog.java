package pl.agh.edu.hitchhiker.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import pl.agh.edu.hitchhiker.R;

public class AlreadyRegisteredDialog extends DialogFragment {
    private AlreadyRegisteredDialogListener listener;
    private boolean isDriver = false;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        setCancelable(false);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(isDriver ? R.string.already_registered_driver : R.string.already_registered_hitchhiker)
                .setNegativeButton(R.string.finish_trip, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (listener != null) {
                            listener.onFinish();
                        }
                    }
                })
                .setNeutralButton(R.string.unregister_trip, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (listener != null) {
                            listener.onUnregiser();
                        }
                    }
                })
                .setPositiveButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (listener != null) {
                            listener.onCancel();
                        }
                    }
                });


        return builder.create();
    }

    public AlreadyRegisteredDialogListener getListener() {
        return listener;
    }

    public void setListener(AlreadyRegisteredDialogListener listener) {
        this.listener = listener;
    }

    public boolean isDriver() {
        return isDriver;
    }

    public void setDriver(boolean isDriver) {
        this.isDriver = isDriver;
    }

    public interface AlreadyRegisteredDialogListener {
        void onFinish();

        void onUnregiser();

        void onCancel();
    }
}
