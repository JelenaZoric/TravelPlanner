package com.ftn.uns.travelplaner;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.ftn.uns.travelplaner.util.DateTimeFormatter;

import java.time.LocalTime;

public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    public Spinner view;
    public Context context;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LocalTime now = LocalTime.now();
        return new TimePickerDialog(context, this, now.getHour(), now.getMinute(), true);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.support_simple_spinner_dropdown_item);
        adapter.add(DateTimeFormatter.formatTime(LocalTime.of(hourOfDay, minute, 0)));
        this.view.setAdapter(adapter);
    }
}
