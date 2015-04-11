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
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by wathmal on 4/7/15.
 */
public class DialogAddAudioBook extends DialogFragment {
    Context context;
    boolean isViewing= false;
    boolean isEditing= false;
    AudioBook audioBook= null;

    public DialogAddAudioBook() {
    }
    @SuppressLint("ValidFragment")
    public DialogAddAudioBook(Context context, boolean isViewing, boolean isEditing, AudioBook audioBook) {
        this.context = context;
        this.isViewing = isViewing;
        this.isEditing = isEditing;
        this.audioBook = audioBook;
    }

    @SuppressLint("ValidFragment")
    public DialogAddAudioBook(Context context) {
        this.context = context;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //    return super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View viewAddAudioBook= inflater.inflate(R.layout.dialog_add_audio_book, null);

        final TextView dialogTitle= (TextView)viewAddAudioBook.findViewById(R.id.textViewDialogTitle);
        final EditText editTextAudioBookTitle= (EditText)viewAddAudioBook.findViewById(R.id.editTextAudioBookTitle);
        final EditText editTextAudioBookDuration= (EditText)viewAddAudioBook.findViewById(R.id.editTextAudioBookDuration);
        final EditText editTextAudioBookCurrentTime= (EditText)viewAddAudioBook.findViewById(R.id.editTextAudioBookCurrentTime);
        final DatabaseHandler dh= new DatabaseHandler(this.context);



        if(isViewing && !isEditing){
            dialogTitle.setText("VIEW AUDIO BOOK");

            editTextAudioBookTitle.setText(this.audioBook.getTitle());
            editTextAudioBookDuration.setText(String.valueOf(this.audioBook.getDuration()));
            editTextAudioBookCurrentTime.setText(String.valueOf(this.audioBook.getCurrentTime()));

            editTextAudioBookTitle.setEnabled(false);
            editTextAudioBookDuration.setEnabled(false);
            editTextAudioBookCurrentTime.setEnabled(false);

            builder.setView(viewAddAudioBook)
                    .setNegativeButton(R.string.action_cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DialogAddAudioBook.this.getDialog().cancel();
                        }
                    });
        }
        else if(isEditing && !isViewing){
            editTextAudioBookTitle.setText(this.audioBook.getTitle());
            editTextAudioBookDuration.setText(String.valueOf(this.audioBook.getDuration()));
            editTextAudioBookCurrentTime.setText(String.valueOf(this.audioBook.getCurrentTime()));

            builder.setView(viewAddAudioBook)
                    .setPositiveButton("update audio book", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int id) {

                            audioBook.setTitle(editTextAudioBookTitle.getText().toString());
                            audioBook.setDuration(Integer.parseInt(editTextAudioBookDuration.getText().toString()));
                            audioBook.setCurrentTime(Integer.parseInt(editTextAudioBookCurrentTime.getText().toString()));

                            int noOfRowsAffected= dh.updateAudioBook(audioBook);
                            Toast.makeText(getActivity(), "updated. " + noOfRowsAffected+" rows affected.", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton(R.string.action_cancel, new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            DialogAddAudioBook.this.getDialog().cancel();
                        }
                    });

        }

        else{
            builder.setView(viewAddAudioBook)
                    .setPositiveButton("add audio book", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int id) {

                            AudioBook audioBook= new AudioBook(editTextAudioBookTitle.getText().toString(),
                                    Integer.parseInt(editTextAudioBookDuration.getText().toString()),
                                    Integer.parseInt(editTextAudioBookCurrentTime.getText().toString()),
                                    1);

                            long rowId = dh.addAudioBook(audioBook);
                            if (rowId != -1) {

                                ((AddEventActivity) getActivity()).setEventResourceId(rowId);
                                Toast.makeText(getActivity(), "added new audio book, id= " + rowId, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "error occurred", Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .setNegativeButton(R.string.action_cancel, new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            DialogAddAudioBook.this.getDialog().cancel();
                        }
                    });
        }
        return builder.create();

    }
}
