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
public class DialogAddMovie extends DialogFragment {
    Context context;

    public DialogAddMovie() {
    }

    @SuppressLint("ValidFragment")
    public DialogAddMovie(Context context) {
        this.context = context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View viewAddMovie= inflater.inflate(R.layout.dialog_add_movie, null);

        final EditText editTextImdbId= (EditText)viewAddMovie.findViewById(R.id.editTextImdbId);
        final EditText editTextMovieTitle= (EditText)viewAddMovie.findViewById(R.id.editTextMovieTitle);
        final EditText editTextDuration= (EditText)viewAddMovie.findViewById(R.id.editTextDuration);
        final EditText editTextCurrentTime= (EditText)viewAddMovie.findViewById(R.id.editTextCurrentTime);
        final DatabaseHandler dh= new DatabaseHandler(this.context);


        builder.setView(viewAddMovie)
                .setPositiveButton("add movie", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // add to db
                        Movie movie= new Movie(editTextImdbId.getText().toString(),
                                editTextMovieTitle.getText().toString(),
                                Integer.parseInt(editTextDuration.getText().toString()),
                                Integer.parseInt(editTextCurrentTime.getText().toString()),
                                0);

                        long rowId = dh.addMovie(movie);
                        if (rowId != -1) {

                            ((AddEventActivity) getActivity()).setEventResourceId(rowId);
                            Toast.makeText(getActivity(), "added new movie, id= " + rowId, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "error occurred", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        DialogAddMovie.this.getDialog().cancel();
                    }
                });

        return builder.create();

    }
}
