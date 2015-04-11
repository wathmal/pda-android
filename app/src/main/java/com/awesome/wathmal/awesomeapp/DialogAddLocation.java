package com.awesome.wathmal.awesomeapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by wathmal on 4/7/15.
 */
public class DialogAddLocation extends DialogFragment
{

    private float lat= 0;
    private float lon= 0;
    private EditText editTextLat= null, editTextLon= null, editTextLocationAddress= null;
    private Switch gpsSwitch= null;
    Context context;
    public DialogAddLocation(){

    }

    @SuppressLint("ValidFragment")
    public DialogAddLocation(Context context){
        this.context= context;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //    return super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View addLocationView= inflater.inflate(R.layout.dialog_add_location, null);

        this.editTextLat= (EditText)addLocationView.findViewById(R.id.editTextLocationLat);
        this.editTextLon= (EditText)addLocationView.findViewById(R.id.editTextLocationLon);
        this.editTextLocationAddress= (EditText)addLocationView.findViewById(R.id.editTextLocationAddress);
        this.gpsSwitch= (Switch)addLocationView.findViewById(R.id.gpsSwitch);
        final TextView textViewLocationStatus= (TextView)addLocationView.findViewById(R.id.textViewLocationStatus);


        final DatabaseHandler dh= new DatabaseHandler(this.context);

        builder.setView(addLocationView)
                .setPositiveButton("add location", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // add to db

                        dh.addLocation(new com.awesome.wathmal.awesomeapp.Location(lat, lon, editTextLocationAddress.getText().toString() ));
                        Toast.makeText(getActivity(), "new location added", Toast.LENGTH_SHORT).show();
                        ((AddEventActivity)getActivity()).refreshLocationSpinner();
                    }
                })
                .setNegativeButton(R.string.action_cancel, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        DialogAddLocation.this.getDialog().cancel();
                    }
                });

        /*
        * location listener
        * cant implement this as we have use same listener in an inner class
        * or don't know how to do it :D
        * */
        final LocationListener listener= new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                getLocationFromGPS(location);
                textViewLocationStatus.setText("location is set by " + location.getProvider());
                Log.d("app", "location changed");
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };



        final LocationManager locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);


        /*
        set last known location in dialog
        * */
        try {
            Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            getLocationFromGPS(lastLocation);
        }
        catch (NullPointerException e){
        //    Log.e("location", e.getMessage());
        }

        if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, listener);
        /*
        * set location status animation
        * */
            textViewLocationStatus.setText("getting location from " + LocationManager.NETWORK_PROVIDER);
            textViewLocationStatus.setAnimation(AnimationUtils.loadAnimation(this.context, android.R.anim.fade_in));
        }


        this.gpsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                try {
                    if (isChecked) {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);
                        textViewLocationStatus.setText("getting location from " + LocationManager.GPS_PROVIDER);
                    } else {
                        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, listener);
                        textViewLocationStatus.setText("getting location from " + LocationManager.NETWORK_PROVIDER);
                    }
                }
                catch (Exception e){
                    Toast.makeText(getActivity(), "switch on gps or location", Toast.LENGTH_SHORT).show();

                }

            }
        });

        //this.editTextLat.setText("hellow");

        return builder.create();




    }


    void getLocationFromGPS(Location currentLocation){
        this.lat = (float)currentLocation.getLatitude();
        this.lon = (float)currentLocation.getLongitude();

        this.editTextLat.setText(String.valueOf(this.lat));
        this.editTextLon.setText(String.valueOf(this.lon));

    }


/*
    @Override
    public void onLocationChanged(Location location) {
        getLocationFromGPS(location);
        Log.d("app", "location changed");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }*/
}
