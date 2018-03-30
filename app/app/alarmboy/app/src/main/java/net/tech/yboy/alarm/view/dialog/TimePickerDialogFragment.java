package net.tech.yboy.alarm.view.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by manabu on 2018/03/21.
 */

public class TimePickerDialogFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    public interface DialogListener {
        public void onTimeSet(int hourOfDay, int minute);
    }

    private TimePickerDialogFragment.DialogListener mListener;

    public void set(TimePickerDialogFragment.DialogListener listener) {
        mListener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        int h = getArguments().getInt("h", -1);
        int m = getArguments().getInt("m", -1);

        if (h >= 0 && m >= 0) {
            hour = h;
            minute = m;
        }

        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),this, hour, minute, true);

        return timePickerDialog;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        //時刻が選択されたときの処理
        if (mListener != null) {
            mListener.onTimeSet(hourOfDay, minute);
        }
    }

}