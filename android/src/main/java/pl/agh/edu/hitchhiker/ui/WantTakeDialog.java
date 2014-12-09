package pl.agh.edu.hitchhiker.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import pl.agh.edu.hitchhiker.R;

public class WantTakeDialog extends DialogFragment {
    private WantTakeListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        setCancelable(false);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.want_take)
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (listener != null) {
                            listener.onCancel();
                        }
                    }
                })
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (listener != null) {
                            listener.onTake();
                        }
                    }
                });


        return builder.create();
    }

    public WantTakeListener getListener() {
        return listener;
    }

    public void setListener(WantTakeListener listener) {
        this.listener = listener;
    }

    public interface WantTakeListener {
        void onTake();
        void onCancel();
    }
}
