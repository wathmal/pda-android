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
public class DialogAddMedicine extends DialogFragment {
    Context context;
    EditText editTextMedicineName;
    EditText editTextMedicineDosage;

    public DialogAddMedicine(){

    }

    @SuppressLint("ValidFragment")
    public DialogAddMedicine(Context context){
        this.context= context;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //    return super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View viewAddMedicine= inflater.inflate(R.layout.dialog_add_medicine, null);

        this.editTextMedicineName = (EditText)viewAddMedicine.findViewById(R.id.editTextMedicineName);
        this.editTextMedicineDosage= (EditText)viewAddMedicine.findViewById(R.id.editTextMedicineDosage);



        builder.setView(viewAddMedicine)
                .setPositiveButton("add medicine", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // add to db
                        DatabaseHandler dh = new DatabaseHandler(context);

                        long rowId = dh.addMedicine(new Medicine(editTextMedicineName.getText().toString(),
                                editTextMedicineDosage.getText().toString(), 0
                        ));
                        if (rowId != -1) {

                            ((AddEventActivity) getActivity()).setEventResourceId(rowId);
                            Toast.makeText(getActivity(), "added new medicine, id= " + rowId, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "error occurred", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        DialogAddMedicine.this.getDialog().cancel();
                    }
                });
        return builder.create();

    }
}
