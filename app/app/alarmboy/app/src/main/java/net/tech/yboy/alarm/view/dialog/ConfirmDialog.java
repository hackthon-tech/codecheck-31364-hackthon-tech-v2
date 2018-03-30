package net.tech.yboy.alarm.view.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

/**
 * Created by manabu on 2018/03/21.
 */

public class ConfirmDialog extends DialogFragment {
    public interface DialogListener {
        public void onOkClick(DialogFragment dlg);
        public void onCancelClick(DialogFragment dlg);
    }

    private DialogListener mListener;

    private String mDlgTitle = "";
    private String mMessage = "";

    public ConfirmDialog() {}


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(mDlgTitle)
                .setMessage(mMessage)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (mListener != null) {
                            mListener.onOkClick(ConfirmDialog.this);
                        }
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (mListener != null) {
                            mListener.onCancelClick(ConfirmDialog.this);
                        }
                    }
                })
        ;
        return builder.create();
    }

    public void set(String title, String message) {
        this.mDlgTitle = title;
        this.mMessage = message;
    }

    public void set(DialogListener listener) {
        mListener = listener;
    }

    public void set(String title, String message, DialogListener listener) {
        this.mDlgTitle = title;
        this.mMessage = message;
        set(listener);
    }

}
