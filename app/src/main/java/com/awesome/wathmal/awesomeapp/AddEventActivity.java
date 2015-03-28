package com.awesome.wathmal.awesomeapp;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class AddEventActivity extends Activity {

    private String eventTitle= null;
    private String eventDescription= null;
    private Date eventDate= null;
    private boolean eventRepeated= false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        Button dateButton= (Button) findViewById(R.id.buttonAddDate);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment fragment= new DatePickerFragment();
                fragment.show(getFragmentManager(),"choose date");
            }
        });
    }

    /*
    get Date from the DatePickerFragment and handle inside the AddEventActivity
     */
    public void setDate(int year, int monthOfYear, int dayOfMonth){
        DateFormat df= new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        try {
            this.eventDate= df.parse(String.valueOf(year)+"-"+String.valueOf(monthOfYear)+"-"+String.valueOf(dayOfMonth));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        EditText date= (EditText) findViewById(R.id.editTextDate);
        date.setText(df.format(this.eventDate));

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
        CheckBox checkBoxRepeated= (CheckBox) findViewById(R.id.checkBoxRepeated);

        this.eventTitle= textTitle.getText().toString();
        this.eventDescription= textDescription.getText().toString();
        this.eventRepeated= checkBoxRepeated.isChecked();

        Event newEvent= new Event(this.eventTitle, this.eventDescription, this.eventDate, this.eventRepeated);
        DatabaseHandler dh= new DatabaseHandler(this);

        dh.addEvent(newEvent);
        Toast.makeText(this, "new item added", Toast.LENGTH_SHORT).show();

//        Intent mainActivityIntent= new Intent(AddEventActivity.this, MainActivity.class);
//        AddEventActivity.this.startActivity(mainActivityIntent);
        finish();

    }


}
