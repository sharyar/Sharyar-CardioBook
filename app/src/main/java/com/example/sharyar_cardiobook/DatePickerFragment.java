package com.example.sharyar_cardiobook;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Locale;

/**
 * The datePicker fragment creates a dialog box for user to pick the date of the record.
 * It ensures that the user uses the correct input type when entering data.
 * Please note that I created this on my own after review the tutorial for TimePicker dialog from
 * Saiful Alam.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    @Override
    public Dialog onCreateDialog(Bundle SavedInstanceState) {
        //Use current date as the default values for the datePicker
        final Calendar c = Calendar.getInstance();
        int date = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);

        return new DatePickerDialog(getActivity(), this, year, month, date);
    }

    /**
     * Please note that the month+1 ensures that the correct value is provided to the
     * java.util.dateclass. The datePicker uses the java calendar class which uses 0-11 for months.
     */
    public void onDateSet(DatePicker view, int year, int month, int date) {
        EditText et = getActivity().findViewById(R.id.date);
        et.setText(String.format(Locale.getDefault(), "%d/%d/%d", (month + 1), date, year));
    }

}
