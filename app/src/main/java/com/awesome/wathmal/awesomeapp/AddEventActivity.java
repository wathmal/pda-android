package com.awesome.wathmal.awesomeapp;

import android.app.AlarmManager;
import android.app.DialogFragment;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class AddEventActivity extends FragmentActivity implements AdapterView.OnItemSelectedListener{

    String []eventTypes;
    String []recurrenceTypes;

    /*event attributes*/
    private String eventTitle= null;
    private String eventDescription= null;
    private Date eventDate= null;
    private String dateString= "";
    private String timeString= "";
    private boolean eventRepeated= false;
    private long eventResourceId = 0;
    private String selectedEventType= "event";
    private String selectedRecurrenceType= "todo";
    // not the actural row id
    private int selectedLocationId= 0;
    private boolean eventNotify= false;
    /*event attributes*/

    private DatabaseHandler dh;
    String locationArray[];
    List<Location> locations;


    Spinner spinnerLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);



        this.dh= new DatabaseHandler(this);
        eventTypes= getResources().getStringArray(R.array.event_types);
        recurrenceTypes= getResources().getStringArray(R.array.recurrence_types);



        // get all locations.
        this.locations= Collections.emptyList();
        this.locations= this.dh.getAllLocations();


        // set current date
        Calendar c= Calendar.getInstance();
        // this give month number starting from 1
        int month= c.get(Calendar.MONTH)+1;
        this.dateString= String.valueOf(c.get(Calendar.YEAR)+"-"+month+"-"+c.get(Calendar.DAY_OF_MONTH));
        this.timeString= String.valueOf(c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE)+":00");
        updateDateAndTime();



        /*
        * initialise spinners
        * */

        // event type spinner
        final Spinner spinnerEventType= (Spinner) findViewById(R.id.spinnerEventType);

        ArrayAdapter<CharSequence> eventTypeAdapter = ArrayAdapter.createFromResource(this,
                R.array.event_types, R.layout.my_spinner_item);
        eventTypeAdapter.setDropDownViewResource(R.layout.my_spinner_dropdown_item);
        spinnerEventType.setAdapter(eventTypeAdapter);
        spinnerEventType.setOnItemSelectedListener(this);
        // recurrence type spinner
        Spinner spinnerRecurrenceType= (Spinner) findViewById(R.id.spinnerRecurrenceType);
        ArrayAdapter<CharSequence> recurrenceTypeAdapter = ArrayAdapter.createFromResource(this,
                R.array.recurrence_types, R.layout.my_spinner_item);
        recurrenceTypeAdapter.setDropDownViewResource(R.layout.my_spinner_dropdown_item);
        spinnerRecurrenceType.setAdapter(recurrenceTypeAdapter);
        spinnerRecurrenceType.setOnItemSelectedListener(this);


        // location spinner
        this.spinnerLocation= (Spinner) findViewById(R.id.spinnerLocation);

        /*if(locations.size() ==0){
            this.dh.addLocation(new Location((float)6.0440405, (float)80.722632, "my home"));
            locations= this.dh.getAllLocations();
        }*/

        this.locationArray = new String[locations.size()+1];
        for(int i=0; i<locations.size(); i++){
            this.locationArray[i]= locations.get(i).getAddress();
        }
        this.locationArray[locations.size()]= "add new";
        ArrayAdapter<String> locationAdapter= new ArrayAdapter<String>(this, R.layout.my_spinner_item, this.locationArray);
        locationAdapter.setDropDownViewResource(R.layout.my_spinner_dropdown_item);
        spinnerLocation.setAdapter(locationAdapter);
        spinnerLocation.setOnItemSelectedListener(this);


        // date button action
        Button dateButton= (Button) findViewById(R.id.buttonAddDate);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment fragment= new DatePickerFragment();
                fragment.show(getFragmentManager(),"choose date");
            }
        });

        // time button action
        Button timeButton= (Button) findViewById(R.id.buttonAddTime);
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment fragment= new TimePickerFragment();
                fragment.show(getFragmentManager(),"choose time");
            }
        });



        Intent currentIntent= getIntent();
        final int eventTypeFromIntent;
        if((eventTypeFromIntent = currentIntent.getIntExtra("eventType", 0)) != 0){
            this.selectedEventType= this.eventTypes[eventTypeFromIntent];


            spinnerEventType.setSelection(eventTypeFromIntent);
            spinnerEventType.setEnabled(false);

        }



    }

    // update date and time which is set to global dateString and timeString
    public void updateDateAndTime(){
        DateFormat df= new SimpleDateFormat("yy-MM-dd HH:mm", Locale.ENGLISH);
        try {
            this.eventDate= df.parse(this.dateString+" "+this.timeString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        TextView textViewDateAndTime= (TextView) findViewById(R.id.textViewDateAndTime);
        textViewDateAndTime.setText(df.format(this.eventDate));
    }

    /*
    get Date from the DatePickerFragment and handle inside the AddEventActivity
     */
    public void setDate(int year, int monthOfYear, int dayOfMonth){

        monthOfYear= monthOfYear+1;
        this.dateString = String.valueOf(year +"-"+ monthOfYear+"-"+ dayOfMonth);
        updateDateAndTime();


    }

    public void setTime(int hourOfDay, int minutes){
        this.timeString= String.valueOf(hourOfDay+":"+minutes+":00");
        updateDateAndTime();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_event, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
    handle save button click event
     */
    public void saveButtonHandler(View view){

        EditText textTitle= (EditText) findViewById(R.id.editTextEventTitle);
        EditText textDescription= (EditText) findViewById(R.id.editTextEventDescription);

        Switch switchRepeated= (Switch) findViewById(R.id.switchRepeated);
        Switch switchNotify= (Switch)findViewById(R.id.switchNotify);

        this.eventTitle= textTitle.getText().toString();
        this.eventDescription= textDescription.getText().toString();
        this.eventRepeated= switchRepeated.isChecked();
        this.eventNotify= switchNotify.isChecked();

        Event newEvent= new Event(this.eventTitle, this.eventDescription, this.eventDate, this.eventRepeated,
                this.locations.get(this.selectedLocationId).getId(),
                this.selectedRecurrenceType,
                this.selectedEventType,
                (int)this.eventResourceId,
                this.eventNotify
                );
        DatabaseHandler dh= new DatabaseHandler(this);

        long eventRowId= dh.addEvent(newEvent);


        int noOfRowsAffected= 0;
        // book
        if(this.selectedEventType.equals(this.eventTypes[1])) {
            noOfRowsAffected= dh.updateEventIdOfATable(DatabaseHandler.TABLE_BOOK,
                    DatabaseHandler.BOOK_KEY_ID,
                    DatabaseHandler.BOOK_KEY_EVENT_ID,
                    (int)this.eventResourceId, (int)eventRowId);


        }
        // medicine
        else if(this.selectedEventType.equals(this.eventTypes[2])){
            // update this to common updateEventIdOfATable() d.
            noOfRowsAffected= dh.updateMedicineEventId((int)this.eventResourceId, (int)eventRowId);

        }
        // movie
        else if(this.selectedEventType.equals(this.eventTypes[3])){
            Media media= new Media(this.selectedEventType, (int)this.eventResourceId, (int)eventRowId);
            long mediaId= dh.addMedia(media);
            Log.d("database", "added new media, id= "+mediaId);

            noOfRowsAffected= dh.updateEventIdOfATable(DatabaseHandler.TABLE_MOVIE,
                    DatabaseHandler.MOVIE_KEY_ID,
                    DatabaseHandler.MOVIE_KEY_MEDIA_ID,
                    (int)this.eventResourceId, (int)mediaId);

        }
        // audio book
        else if(this.selectedEventType.equals(this.eventTypes[4])){
            Media media= new Media(this.selectedEventType, (int)this.eventResourceId, (int)eventRowId);
            long mediaId= dh.addMedia(media);
            Log.d("database", "added new media, id= "+mediaId);

            noOfRowsAffected= dh.updateEventIdOfATable(DatabaseHandler.TABLE_AUDIO_BOOK,
                    DatabaseHandler.AUDIO_BOOK_ID,
                    DatabaseHandler.AUDIO_BOOK_MEDIA_ID,
                    (int)this.eventResourceId, (int)mediaId);
        }
        Log.d("database", this.selectedEventType + ", table updated " + noOfRowsAffected + " rows");
        /*update eventId of the resource*/


        /*add to recurrence type table*/
        long recurrenceTypeRowId= 0;
        // todo
        if(this.selectedRecurrenceType.equals(this.recurrenceTypes[0])){
            Todo todo= new Todo();
            todo.setEventId((int)eventRowId);
            recurrenceTypeRowId = dh.addTodo(todo);
        }
        // daily
        else if(this.selectedRecurrenceType.equals(this.recurrenceTypes[1])){
            Daily daily= new Daily();
            daily.setEventId((int)eventRowId);
            recurrenceTypeRowId= dh.addDaily(daily);
        }
        // weekly
        else if(this.selectedRecurrenceType.equals(this.recurrenceTypes[2])){
            Weekly weekly= new Weekly();
            weekly.setEventId((int)eventRowId);
            recurrenceTypeRowId= dh.addWeekly(weekly);
        }
        // monthly
        else if(this.selectedRecurrenceType.equals(this.recurrenceTypes[3])){
            Monthly monthly= new Monthly();
            monthly.setEventId((int)eventRowId);
            recurrenceTypeRowId= dh.addMonthly(monthly);
        }
        // yearly
        else if(this.selectedRecurrenceType.equals(this.recurrenceTypes[4])){
            Yearly yearly= new Yearly();
            yearly.setEventId((int)eventRowId);
            recurrenceTypeRowId= dh.addYearly(yearly);
        }
        Log.d("database", "new event added to "+this.selectedRecurrenceType+" table, id= "+recurrenceTypeRowId);
        /*add to recurrence type table*/


        /*add notification if set*/
        if(this.eventNotify){
            long a[]= {10, 33, 12, 212,21 ,12};
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.drawable.ic_event_white_24dp)
                            .setContentTitle(this.eventTitle)
                            .setContentText(this.eventDescription)
                            .setVibrate(a);
            scheduleNotification(mBuilder.build(), this.eventDate.getTime(), (int)eventRowId);


        }
        /*add notification if set*/
        Toast.makeText(this, "new event added, id= "+eventRowId, Toast.LENGTH_SHORT).show();
        finish();

    }

    public void setEventResourceId(long eventResourceId) {
        this.eventResourceId = eventResourceId;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //Toast.makeText(this, "item "+parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();

        /*
        * string comparison is happening
        * optimize it to int comparison, i don't like this! :D
        * */

        // eventType spinner
        if(parent.getId() == R.id.spinnerEventType){
            this.selectedEventType = this.eventTypes[position];

            switch (position){
                case 0: {
                    // just event
                    break;
                }
                case 1: {
                    android.support.v4.app.DialogFragment dialogAddBook =  new DialogAddBook(getBaseContext());
                    dialogAddBook.show(getSupportFragmentManager(), "book");
                    break;
                }
                case 2: {
                    android.support.v4.app.DialogFragment dialogAddMedicine =  new DialogAddMedicine(getBaseContext());
                    dialogAddMedicine.show(getSupportFragmentManager(), "medicine");
                    break;
                }
                case 3: {
                    android.support.v4.app.DialogFragment dialogAddMovie =  new DialogAddMovie(getBaseContext());
                    dialogAddMovie.show(getSupportFragmentManager(), "movie");
                    break;
                }
                case 4: {
                    android.support.v4.app.DialogFragment dialogAddAudioBook =  new DialogAddAudioBook(getBaseContext());
                    dialogAddAudioBook.show(getSupportFragmentManager(), "audio book");
                    break;
                }

            }

        }

        // recurrenceType spinner
        else if(parent.getId() == R.id.spinnerRecurrenceType){
            this.selectedRecurrenceType = this.recurrenceTypes[position];
        }

        // location spinner
        else if(parent.getId() == R.id.spinnerLocation){
            if(position <= this.locations.size()-1){

                this.selectedLocationId= position;

            }
            // add new loacation selected
//            if(parent.getSelectedItem().toString().equals("add new")){
            else{
                android.support.v4.app.DialogFragment dialogAddLocation= new DialogAddLocation(getBaseContext());
                dialogAddLocation.show(getSupportFragmentManager(), "add location");
            }

        }



    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    void refreshLocationSpinner(){
        this.locations= this.dh.getAllLocations();
        // location spinner


        /*if(locations.size() ==0){
            this.dh.addLocation(new Location((float)6.0440405, (float)80.722632, "my home"));
            locations= this.dh.getAllLocations();
        }*/

        this.locationArray = new String[locations.size()+1];
        for(int i=0; i<locations.size(); i++){
            this.locationArray[i]= locations.get(i).getAddress();
        }
        this.locationArray[locations.size()]= "add new";
        ArrayAdapter<String> locationAdapter= new ArrayAdapter<String>(this, R.layout.my_spinner_item, this.locationArray);
        locationAdapter.setDropDownViewResource(R.layout.my_spinner_dropdown_item);
        this.spinnerLocation.setAdapter(locationAdapter);
        this.spinnerLocation.setOnItemSelectedListener(this);
    }

    /*
    * https://gist.github.com/BrandonSmith/6679223
    * schedule notifications ..
    * notification id is the event id itself, no need of another table.
    * */
    private void scheduleNotification(Notification notification, long time, int notificationId) {

        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, notificationId);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

        if(this.selectedRecurrenceType.equals(this.recurrenceTypes[0])) {
            alarmManager.set(AlarmManager.RTC, time, pendingIntent);
            Log.i("notification", "new notification set to "+ time);
        }
        // daily event
        else if(this.selectedRecurrenceType.equals(this.recurrenceTypes[1])){
            alarmManager.setRepeating(AlarmManager.RTC, time, AlarmManager.INTERVAL_DAY, pendingIntent);
            Log.i("notification", "new "+this.selectedRecurrenceType+" repeating notification set to "+ time);
        }
    }
}
