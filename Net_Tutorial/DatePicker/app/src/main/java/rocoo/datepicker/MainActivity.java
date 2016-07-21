package rocoo.datepicker;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, GetDateOrTimeCallback {

    // Layout
    private TextView timeTextView, dateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button timeButton = (Button) findViewById(R.id.timePickerbutton);
        Button dateButton = (Button) findViewById(R.id.datePickerButton);
        timeButton.setOnClickListener(this);
        dateButton.setOnClickListener(this);

        timeTextView = (TextView) findViewById(R.id.timeTextView);
        dateTextView = (TextView) findViewById(R.id.dateTextView);


    }

    public void showTimePickerDialog(FragmentManager manager) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(manager, "timePicker");

    }

    public void showDatePickerDialog(FragmentManager manager) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(manager, "datePicker");
    }

    /**
     * MARK - View.OnClickListener
     **/
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.timePickerbutton:
                showTimePickerDialog(getFragmentManager());
                break;
            case R.id.datePickerButton:
                showDatePickerDialog(getFragmentManager());
                break;
        }
    }

    /**
     * MARK - GetDateOrTimeCallback
     **/
    @Override
    public void onClickFromTime(int hour, int minute) {
        timeTextView.setText(getString(R.string.timeFromFragment, hour, minute));
    }

    @Override
    public void onClickFromDate(int year, int month, int day) {
        dateTextView.setText(getString(R.string.dateFromFragment, year, month, day));
    }
}
