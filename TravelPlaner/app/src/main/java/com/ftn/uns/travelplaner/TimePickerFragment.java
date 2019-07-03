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
import java.util.Date;

public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    public Spinner view;
    public Context context;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //LocalTime now = LocalTime.now();
        Date now = new Date();
        return new TimePickerDialog(context, this, now.getHours(), now.getMinutes(), true);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.support_simple_spinner_dropdown_item);
        //adapter.add(DateTimeFormatter.formatTime(LocalTime.of(hourOfDay, minute, 0)));
        Date date = new Date();
        date.setHours(hourOfDay);
        date.setMinutes(minute);
        adapter.add(DateTimeFormatter.formatTime(date));
        this.view.setAdapter(adapter);
    }
}
