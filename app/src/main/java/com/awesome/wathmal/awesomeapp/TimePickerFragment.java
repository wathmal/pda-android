package com.awesome.wathmal.awesomeapp;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by wathmal on 4/07/15.
 */
public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Calendar c= Calendar.getInstance();
        int hours= c.get(Calendar.HOUR_OF_DAY);
        int minutes= c.get(Calendar.MINUTE);


        return new TimePickerDialog(getActivity(),this, hours, minutes,false);
    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        ((AddEventActivity)getActivity()).setTime(hourOfDay, minute);
    }
}