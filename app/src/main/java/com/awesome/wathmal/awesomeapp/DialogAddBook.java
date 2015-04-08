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
public class DialogAddBook extends DialogFragment {
    Context context;

    public DialogAddBook(){}

    @SuppressLint("ValidFragment")
    public DialogAddBook(Context context) {
        this.context = context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
    //    return super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View viewAddBook= inflater.inflate(R.layout.dialog_add_book, null);

        final EditText editTextBookTitle= (EditText)viewAddBook.findViewById(R.id.editTextBookTitle);
        final EditText editTextTotalPages= (EditText)viewAddBook.findViewById(R.id.editTextTotalPages);
        final EditText editTextCurrentPage= (EditText)viewAddBook.findViewById(R.id.editTextCurrentPage);
        final DatabaseHandler dh= new DatabaseHandler(this.context);

        builder.setView(viewAddBook)
                .setPositiveButton("add book", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // add to db
                        Book book= new Book(editTextBookTitle.getText().toString(),
                                Integer.parseInt(editTextTotalPages.getText().toString()),
                                Integer.parseInt(editTextCurrentPage.getText().toString()),
                                0);

                        long rowId = dh.addBook(book);
                        if (rowId != -1) {

                            ((AddEventActivity) getActivity()).setEventResourceId(rowId);
                            Toast.makeText(getActivity(), "added new book, id= " + rowId, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "error occurred", Toast.LENGTH_SHORT).show();
                        }

                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        DialogAddBook.this.getDialog().cancel();
                    }
                });
        return builder.create();

    }
}
