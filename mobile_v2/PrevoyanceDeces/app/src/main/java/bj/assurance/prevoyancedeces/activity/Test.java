package bj.assurance.prevoyancedeces.activity;

import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import bj.assurance.prevoyancedeces.R;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tsongkha.spinnerdatepicker.DatePicker;
import com.tsongkha.spinnerdatepicker.DatePickerDialog;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class Test extends AppCompatActivity implements   DatePickerDialog.OnDateSetListener , DatePickerDialog.OnCancelListener {

    EditText dateButton;
    SimpleDateFormat simpleDateFormat;
    SimpleDateFormat dtYYYY = new SimpleDateFormat("YYYY");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        dateButton =  findViewById(R.id.set_date_button);
        simpleDateFormat = new SimpleDateFormat("dd MM yyyy", Locale.FRANCE);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDate(Integer.valueOf(dtYYYY.format(new Date())) - 20, 0, 1, R.style.DatePickerSpinner);
            }
        });

    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = new GregorianCalendar(year, monthOfYear, dayOfMonth);
        dateButton.setText(simpleDateFormat.format(calendar.getTime()));
    }

    @VisibleForTesting
    void showDate(int year, int monthOfYear, int dayOfMonth, int spinnerTheme) {


        String newDate = dtYYYY.format(new Date()).split(" ")[0];

        int minYear = Integer.valueOf(newDate) - 74;
        int maxYear = Integer.valueOf(newDate) - 18;

        System.out.println(newDate);
        //System.out.println(maxYear + " " + minYear);

        new SpinnerDatePickerDialogBuilder()
                .context(Test.this)
                .callback(Test.this)
                .spinnerTheme(spinnerTheme)
                .defaultDate(year, monthOfYear, dayOfMonth)
                .maxDate(maxYear, 12, 31)
                .minDate(minYear, 12, 31)
                .build()
                .show();
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        dateButton.setText("cancel");
    }
}
