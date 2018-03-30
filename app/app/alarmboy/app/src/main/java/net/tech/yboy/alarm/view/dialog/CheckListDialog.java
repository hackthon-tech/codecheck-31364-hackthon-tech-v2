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

public class CheckListDialog extends DialogFragment {
    public interface DialogListener {
        public void onCheck(DialogFragment dlg, int item);
    }

    private int mClickItem;

    private CheckListDialog.DialogListener mListener;

    private String mDlgTitle = "";

    public CheckListDialog() {}

    String [] items = {};

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(mDlgTitle)
                .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mClickItem = which;
                    }
                });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (mListener != null) {
                    mListener.onCheck(CheckListDialog.this, mClickItem);
                }
            }
        });
        return builder.create();
    }

    public void setText(String[] str) {
        items = str;
    }

    public void set(String title) {
        this.mDlgTitle = title;
    }

    public void set(CheckListDialog.DialogListener listener) {
        mListener = listener;
    }

    public void set(String title, CheckListDialog.DialogListener listener) {
        this.mDlgTitle = title;
        set(listener);
    }

}
