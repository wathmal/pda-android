package com.awesome.wathmal.awesomeapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by wathmal on 4/7/15.
 */
public class DialogAddAudioBook extends DialogFragment {
    Context context;

    public DialogAddAudioBook() {
    }

    @SuppressLint("ValidFragment")
    public DialogAddAudioBook(Context context) {
        this.context = context;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //    return super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View viewAddAudioBook= inflater.inflate(R.layout.dialog_new_audio_book, null);

        final EditText editTextAudioBookTitle= (EditText)viewAddAudioBook.findViewById(R.id.editTextAudioBookTitle);
        final EditText editTextAudioBookDuration= (EditText)viewAddAudioBook.findViewById(R.id.editTextAudioBookDuration);
        final EditText editTextAudioBookCurrentTime= (EditText)viewAddAudioBook.findViewById(R.id.editTextAudioBookCurrentTime);
        final DatabaseHandler dh= new DatabaseHandler(this.context);


        builder.setView(viewAddAudioBook)
                .setPositiveButton("add audio book", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        AudioBook audioBook= new AudioBook(editTextAudioBookTitle.getText().toString(),
                                Integer.parseInt(editTextAudioBookDuration.getText().toString()),
                                Integer.parseInt(editTextAudioBookCurrentTime.getText().toString()),
                                0);

                        long rowId = dh.addAudioBook(audioBook);
                        if (rowId != -1) {

                            ((AddEventActivity) getActivity()).setEventResourceId(rowId);
                            Toast.makeText(getActivity(), "added new audio book, id= " + rowId, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "error occurred", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        DialogAddAudioBook.this.getDialog().cancel();
                    }
                });
        return builder.create();

    }
}
