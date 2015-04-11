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
public class DialogAddMedicine extends DialogFragment {
    Context context;
    EditText editTextMedicineName;
    EditText editTextMedicineDosage;

    boolean isViewing= false;
    boolean isEditing= false;
    Medicine medicine= null;

    @SuppressLint("ValidFragment")
    public DialogAddMedicine(Context context, boolean isViewing, boolean isEditing, Medicine medicine) {
        this.context = context;
        this.medicine = medicine;
        this.isEditing = isEditing;
        this.isViewing = isViewing;
    }

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

        final TextView dialogTitle= (TextView)viewAddMedicine.findViewById(R.id.textViewDialogTitle);
        this.editTextMedicineName = (EditText)viewAddMedicine.findViewById(R.id.editTextMedicineName);
        this.editTextMedicineDosage= (EditText)viewAddMedicine.findViewById(R.id.editTextMedicineDosage);
        final DatabaseHandler dh = new DatabaseHandler(context);




        if(isViewing && !isEditing){
            dialogTitle.setText("VIEW MEDICINE");
            this.editTextMedicineName.setText(this.medicine.getName());
            this.editTextMedicineDosage.setText(this.medicine.getDosage());

            this.editTextMedicineName.setEnabled(false);
            this.editTextMedicineDosage.setEnabled(false);

            builder.setView(viewAddMedicine)
                    .setNegativeButton(R.string.action_cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DialogAddMedicine.this.getDialog().cancel();
                        }
                    });

        }

        else if(isEditing && !isViewing){
            dialogTitle.setText("EDIT MEDICINE");
            this.editTextMedicineName.setText(this.medicine.getName());
            this.editTextMedicineDosage.setText(this.medicine.getDosage());

            builder.setView(viewAddMedicine)
                    .setPositiveButton("update medicine", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            medicine.setName(editTextMedicineName.getText().toString());
                            medicine.setDosage(editTextMedicineDosage.getText().toString());

                            int noOfRowsAffected= dh.updateMedicine(medicine);
                            Toast.makeText(getActivity(), "updated. " + noOfRowsAffected+" rows affected.", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton(R.string.action_cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DialogAddMedicine.this.getDialog().cancel();
                        }
                    });

        }

        else{
            builder.setView(viewAddMedicine)
                    .setPositiveButton("add medicine", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            // add to db


                            long rowId = dh.addMedicine(new Medicine(editTextMedicineName.getText().toString(),
                                    editTextMedicineDosage.getText().toString(), 1
                            ));
                            if (rowId != -1) {

                                ((AddEventActivity) getActivity()).setEventResourceId(rowId);
                                Toast.makeText(getActivity(), "added new medicine, id= " + rowId, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "error occurred", Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .setNegativeButton(R.string.action_cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            DialogAddMedicine.this.getDialog().cancel();
                        }
                    });
        }
        return builder.create();

    }
}
