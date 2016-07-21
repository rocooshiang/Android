package rocoo.datepicker;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import java.util.Calendar;


/**
 * Created by geosat-rd01 on 2016/7/21.
 */
public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    private GetDateOrTimeCallback callback;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Initialize instance of callback.
        callback = (GetDateOrTimeCallback) getActivity();

        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        callback.onClickFromDate(year, month+1, day);
    }
}
