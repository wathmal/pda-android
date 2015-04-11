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
public class DialogAddMovie extends DialogFragment {
    Context context;
    boolean isViewing= false;
    boolean isEditing= false;
    Movie movie= null;

    public DialogAddMovie() {
    }

    @SuppressLint("ValidFragment")
    public DialogAddMovie(Context context, boolean isViewing, boolean isEditing, Movie movie) {
        this.context = context;
        this.isViewing = isViewing;
        this.isEditing = isEditing;
        this.movie = movie;
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

        final TextView dialogTitle= (TextView)viewAddMovie.findViewById(R.id.textViewDialogTitle);
        final EditText editTextImdbId= (EditText)viewAddMovie.findViewById(R.id.editTextImdbId);
        final EditText editTextMovieTitle= (EditText)viewAddMovie.findViewById(R.id.editTextMovieTitle);
        final EditText editTextDuration= (EditText)viewAddMovie.findViewById(R.id.editTextDuration);
        final EditText editTextCurrentTime= (EditText)viewAddMovie.findViewById(R.id.editTextCurrentTime);
        final DatabaseHandler dh= new DatabaseHandler(this.context);



        if(isViewing && !isEditing){
            dialogTitle.setText("VIEW MOVIE");
            editTextMovieTitle.setText(this.movie.getTitle());
            editTextDuration.setText(String.valueOf(this.movie.getDuration()));
            editTextCurrentTime.setText(String.valueOf(this.movie.getCurrentTime()));

            editTextImdbId.setVisibility(View.GONE);
            editTextMovieTitle.setEnabled(false);
            editTextDuration.setEnabled(false);
            editTextCurrentTime.setEnabled(false);

            builder.setView(viewAddMovie)
                    .setNegativeButton(R.string.action_cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DialogAddMovie.this.getDialog().cancel();
                        }
                    });

        }

        else if(isEditing && !isViewing){
            dialogTitle.setText("EDIT MOVIE");
            editTextImdbId.setText(this.movie.getImdbId());
            editTextMovieTitle.setText(this.movie.getTitle());
            editTextDuration.setText(String.valueOf(this.movie.getDuration()));
            editTextCurrentTime.setText(String.valueOf(this.movie.getCurrentTime()));

            editTextImdbId.setEnabled(false);
            builder.setView(viewAddMovie)
                    .setPositiveButton("update movie", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            // update movie
                            movie.setTitle(editTextMovieTitle.getText().toString());
                            movie.setDuration(Integer.parseInt(editTextDuration.getText().toString()));
                            movie.setCurrentTime(Integer.parseInt(editTextCurrentTime.getText().toString()));

                            int noOfRowsAffected= dh.updateMovie(movie);
                            Toast.makeText(getActivity(), "updated. " + noOfRowsAffected+" rows affected.", Toast.LENGTH_SHORT).show();

                        }
                    })
                    .setNegativeButton(R.string.action_cancel, new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            DialogAddMovie.this.getDialog().cancel();
                        }
                    });


        }

        else{
            builder.setView(viewAddMovie)
                    .setPositiveButton("add movie", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            // add to db
                            Movie movie= new Movie(editTextImdbId.getText().toString(),
                                    editTextMovieTitle.getText().toString(),
                                    Integer.parseInt(editTextDuration.getText().toString()),
                                    Integer.parseInt(editTextCurrentTime.getText().toString()),
                                    1);

                            long rowId = dh.addMovie(movie);
                            if (rowId != -1) {

                                ((AddEventActivity) getActivity()).setEventResourceId(rowId);
                                Toast.makeText(getActivity(), "added new movie, id= " + rowId, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "error occurred", Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .setNegativeButton(R.string.action_cancel, new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            DialogAddMovie.this.getDialog().cancel();
                        }
                    });
        }

        return builder.create();

    }
}
