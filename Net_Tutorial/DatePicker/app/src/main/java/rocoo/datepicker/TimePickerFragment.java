package rocoo.datepicker;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.TimePicker;


import java.util.Calendar;

/**
 * Created by geosat-rd01 on 2016/7/21.
 */
public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    private GetDateOrTimeCallback callback;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Initialize instance of callback.
        callback = (GetDateOrTimeCallback) getActivity();

        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);


        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Do something with the time chosen by the user
        Log.i("rocoo", hourOfDay + ":" + minute);
        callback.onClickFromTime(hourOfDay, minute);
    }
}
