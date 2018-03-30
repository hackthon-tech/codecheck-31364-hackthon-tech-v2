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

public class MessageDialog extends DialogFragment {
    public interface DialogListener {
        public void onOkClick(DialogFragment dlg);
    }

    private MessageDialog.DialogListener mListener;

    private String mDlgTitle = "";
    private String mMessage = "";

    public MessageDialog() {}


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
                            mListener.onOkClick(MessageDialog.this);
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

    public void set(MessageDialog.DialogListener listener) {
        mListener = listener;
    }

    public void set(String title, String message, MessageDialog.DialogListener listener) {
        this.mDlgTitle = title;
        this.mMessage = message;
        set(listener);
    }

}
