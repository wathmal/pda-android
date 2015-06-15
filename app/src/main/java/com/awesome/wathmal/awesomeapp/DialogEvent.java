package com.awesome.wathmal.awesomeapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by wathmal on 6/15/15.
 */

public class DialogEvent extends DialogFragment {
    Context context;
    boolean isViewing= false;
    boolean isEditing= false;
    Event event= null;

    public DialogEvent() {
    }

    @SuppressLint("ValidFragment")
    public DialogEvent(Context context, boolean isViewing, boolean isEditing, Event event) {
        this.context = context;
        this.isViewing = isViewing;
        this.isEditing = isEditing;
        this.event = event;
    }

    @SuppressLint("ValidFragment")
    public DialogEvent(Context context) {
        this.context = context;
    }

    @Override

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View viewEvent= inflater.inflate(R.layout.dialog_event, null);

        final TextView dialogTitle= (TextView)viewEvent.findViewById(R.id.textViewDialogTitle);
        final EditText editTextEventTitle= (EditText)viewEvent.findViewById(R.id.editTextEventTitle);
        final EditText editTextEventDescription= (EditText)viewEvent.findViewById(R.id.editTextEventDescription);
        final EditText editTextDate= (EditText)viewEvent.findViewById(R.id.editTextDate);
        final EditText editTextTime= (EditText)viewEvent.findViewById(R.id.editTextTime);
        final Switch switchNotify= (Switch)viewEvent.findViewById(R.id.switch1);
        DateFormat dateOnly= new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        DateFormat timeOnly= new SimpleDateFormat("hh:mm", Locale.ENGLISH);

        if(isViewing && !isEditing){
            dialogTitle.setText("VIEW EVENT");

            editTextEventTitle.setText(event.get_title());
            editTextEventDescription.setText(event.get_description());
            editTextDate.setText(dateOnly.format(this.event.get_date()));
            editTextTime.setText(timeOnly.format(this.event.get_date()));
            switchNotify.setChecked(this.event.isNotify());

            editTextEventTitle.setEnabled(false);
            editTextEventDescription.setEnabled(false);
            editTextDate.setEnabled(false);
            editTextTime.setEnabled(false);
            switchNotify.setEnabled(false);

            builder.setView(viewEvent)
                    .setNegativeButton(R.string.action_cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DialogEvent.this.getDialog().cancel();
                        }
                    });

        }
        else if(isEditing && !isViewing){

        }

        //return super.onCreateDialog(savedInstanceState);

        return builder.create();
    }
}