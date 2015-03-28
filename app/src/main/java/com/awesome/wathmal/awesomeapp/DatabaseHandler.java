package com.awesome.wathmal.awesomeapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by wathmal on 3/19/15.
 */
public class DatabaseHandler extends SQLiteOpenHelper {


    // all static variables
    // database version
    private static final int DATABASE_VERSION = 2;

    // db name
    private static final String DATABASE_NAME = "pdaproject";

    // event table name
    private static final String TABLE_EVENT = "event";

    // todo events table name
    private static final String TABLE_TODO = "todo_events";
    // daily events table name
    private static final String TABLE_DAILY = "daily_events";

    // weekly events table name
    private static final String TABLE_WEEKLY = "weekly_events";
    // monthly events table name
    private static final String TABLE_MONTHLY = "monthly_events";
    // yearly events table name
    private static final String TABLE_YEARLY = "yearly_events";

    // location table name
    private static final String TABLE_LOCATION= "location";
    // media table name
    private static final String TABLE_MEDIA= "media";
    // medicine table name
    private static final String TABLE_MEDICINE= "medicine";
    // book table name
    private static final String TABLE_BOOK= "book";

    // movie table name
    private static final String TABLE_MOVIE= "movie";
    // audio book table name
    private static final String TABLE_AUDIO_BOOK= "audio_book";





    // event table columns names
    private static final String EVENT_KEY_ID = "id";
    private static final String EVENT_KEY_TITLE = "title";
    private static final String EVENT_KEY_DESC = "description";
    private static final String EVENT_KEY_DUE_DATE = "due_date";
    private static final String EVENT_KEY_REPEATED = "repeated";
    private static final String EVENT_KEY_LOCATION_ID= "location_id";
    private static final String EVENT_KEY_RECURRENCE_TYPE= "recurrence_type";
    private static final String EVENT_KEY_EVENT_TYPE= "event_type";
    private static final String EVENT_KEY_RESOURCE_ID= "resource_id";
    private static final String EVENT_KEY_NOTIFY= "notify";

    // location table column names
    private static final String LOCATION_KEY_ID= "loc_id";
    private static final String LOCATION_KEY_LAT= "lat";
    private static final String LOCATION_KEY_LONG= "long";
    private static final String LOCATION_KEY_ADDRESS= "address";


    // media table column names
    private static final String MEDIA_KEY_ID= "media_id";
//    private static final String MEDIA_KEY_TITLE= "title";
//    private static final String MEDIA_KEY_DURATION= "duration";
    private static final String MEDIA_KEY_TYPE= "media_type";
    private static final String MEDIA_KEY_RESOURCE_ID= "resource_id";
    private static final String MEDIA_KEY_EVENT_ID= "event_id";

    // medicine table column names
    private static final String MEDICINE_KEY_ID= "medicine_id";
    private static final String MEDICINE_KEY_NAME= "name";
    private static final String MEDICINE_KEY_DOSAGE= "dosage";
    private static final String MEDICINE_KEY_EVENT_ID= "event_id";


    // book table column names
    private static final String BOOK_KEY_ID= "book_id";
    private static final String BOOK_KEY_TITLE= "title";
    private static final String BOOK_KEY_PAGES= "pages";
    private static final String BOOK_KEY_CURRENT_PAGE= "current_page";
    private static final String BOOK_KEY_EVENT_ID= "event_id";

    // movie table columns
    private static final String MOVIE_KEY_IMDB_ID= "imdb_id";
    private static final String MOVIE_KEY_TITLE= "title";
    private static final String MOVIE_KEY_DURATION= "duration";
    private static final String MOVIE_KEY_CURRENT_TIME= "current_time";
    private static final String MOVIE_KEY_MEDIA_ID= "media_id";


    // audio book table columns
    private static final String AUDIO_BOOK_ID= "audio_book_id";
    private static final String AUDIO_BOOK_TITLE= "title";
    private static final String AUDIO_BOOK_DURATION= "duration";
    private static final String AUDIO_BOOK_CURR_TIME= "current_time";
    private static final String AUDIO_BOOK_MEDIA_ID= "media_id";


    // todo table columns
    private static final String TODO_ID= "todo_id";
    private static final String TODO_EVENT_ID= "event_id";

    // daily table columns
    private static final String DAILY_ID= "daily_id";
    private static final String DAILY_EVENT_ID= "event_id";

    // weekly table columns
    private static final String WEEKLY_ID= "monthly_id";
    private static final String WEEKLY_EVENT_ID= "event_id";

    // monthly table columns
    private static final String MONTHLY_ID= "monthly_id";
    private static final String MONTHLY_EVENT_ID= "event_id";

    // yearly table columns
    private static final String YEARLY_ID= "yearly_id";
    private static final String YEARLY_EVENT_ID= "event_id";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_LOCATION_TABLE= "CREATE TABLE "+ TABLE_LOCATION +"("+
                LOCATION_KEY_ID+ " INTEGER PRIMARY KEY NOT NULL," +
                LOCATION_KEY_LAT+ " FLOAT," +
                LOCATION_KEY_LONG+ " FLOAT," +
                LOCATION_KEY_ADDRESS + "TEXT"
                +")";

        String CREATE_MEDIA_TABLE= "CREATE TABLE "+ TABLE_MEDIA +"("+
                MEDIA_KEY_ID+ " INTEGER PRIMARY KEY NOT NULL," +
                MEDIA_KEY_TYPE+ " VARCHAR(30) NOT NULL," +
                MEDIA_KEY_RESOURCE_ID+ " INTEGER,"+
                MEDIA_KEY_EVENT_ID+ " INTEGER"
                +")";



        String CREATE_EVENT_TABLE=  "CREATE TABLE "+TABLE_EVENT +"(" +
                EVENT_KEY_ID+ " INTEGER PRIMARY KEY NOT NULL," +
                EVENT_KEY_TITLE+ " VARCHAR(30) NOT NULL," +
                EVENT_KEY_DUE_DATE+" DATE," +
                EVENT_KEY_DESC+" VARCHAR(200)," +
                EVENT_KEY_REPEATED+ " BOOLEAN"+
                EVENT_KEY_LOCATION_ID+ " INTEGER"+
                EVENT_KEY_RECURRENCE_TYPE+ " VARCHAR(20)"+
                EVENT_KEY_EVENT_TYPE+ " VARCHAR(20)"+
                EVENT_KEY_RESOURCE_ID+ " INTEGER"+
                EVENT_KEY_NOTIFY+ " BOOLEAN"
                +")";


        String CREATE_BOOK_TABLE= "CREATE TABLE "+ TABLE_BOOK+ "("+
                BOOK_KEY_ID+ " INTEGER PRIMARY KEY NOT NULL,"+
                BOOK_KEY_TITLE+ " VARCHAR(100),"+
                BOOK_KEY_PAGES+ " INTEGER,"+
                BOOK_KEY_CURRENT_PAGE+ " INTEGER,"+
                BOOK_KEY_EVENT_ID+ " INTEGER"
                +")";


        String CREATE_MOVIE_TABLE= "CREATE TABLE "+ TABLE_MOVIE+ "("+
                MOVIE_KEY_IMDB_ID+ " VARCHAR(11) PRIMARY KEY NOT NULL,"+
                MOVIE_KEY_TITLE+ " VARCHAR(200),"+
                MOVIE_KEY_DURATION+ " INTEGER,"+
                MOVIE_KEY_CURRENT_TIME+ " INTEGER,"+
                MOVIE_KEY_MEDIA_ID+ " INTEGER"
                +")";

        String CREATE_AUDIO_BOOK_TABLE= "CREATE TABLE "+ TABLE_AUDIO_BOOK+ "("+
                AUDIO_BOOK_ID+ " INTEGER PRIMARY KEY NOT NULL,"+
                AUDIO_BOOK_TITLE+ " VARCHAR(100),"+
                AUDIO_BOOK_DURATION+ " INTEGER,"+
                AUDIO_BOOK_CURR_TIME+ " INTEGER,"+
                AUDIO_BOOK_MEDIA_ID+ " INTEGER"
                +")";

        String CREATE_MEDICINE_TABLE= "CREATE TABLE "+ TABLE_MEDICINE +"("+
                MEDICINE_KEY_ID+ "INTEGER PRIMARY KEY NOT NULL,"+
                MEDICINE_KEY_NAME+ "VARCHAR(100),"+
                MEDICINE_KEY_DOSAGE+ " VARCHAR(50),"+
                MEDICINE_KEY_EVENT_ID+ " INTEGER"
                +")";


        String CREATE_TODO_TABLE= "CREATE TABLE "+ TABLE_TODO +"("+
                TODO_ID+ " INTEGER PRIMARY KEY NOT NULL,"+
                TODO_EVENT_ID+ " INTEGER"
                +")";

        String CREATE_DAILY_TABLE= "CREATE TABLE "+ TABLE_DAILY +"("+
                DAILY_ID+ " INTEGER PRIMARY KEY NOT NULL,"+
                DAILY_EVENT_ID+ " INTEGER"
                +")";

        String CREATE_WEEKLY_TABLE= "CREATE TABLE "+ TABLE_WEEKLY +"("+
                WEEKLY_ID+ " INTEGER PRIMARY KEY NOT NULL,"+
                WEEKLY_EVENT_ID+ " INTEGER"
                +")";

        String CREATE_MONTHLY_TABLE= "CREATE TABLE "+ TABLE_MONTHLY +"("+
                MONTHLY_ID+ " INTEGER PRIMARY KEY NOT NULL,"+
                MONTHLY_EVENT_ID+ " INTEGER"
                +")";

        String CREATE_YEARLY_TABLE= "CREATE TABLE "+ TABLE_YEARLY +"("+
                YEARLY_ID+ " INTEGER PRIMARY KEY NOT NULL,"+
                YEARLY_EVENT_ID+ " INTEGER"
                +")";


        db.execSQL(CREATE_EVENT_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_EVENT);
        onCreate(db);
    }


    public List<Event> getAllEvents(){
        List<Event> eventList= new ArrayList<Event>();
        String selectQuery= "SELECT * FROM "+ TABLE_EVENT;

        SQLiteDatabase db= this.getWritableDatabase();
        Cursor cursor= db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do{
                Event event= new Event();
                event.set_id(Integer.parseInt(cursor.getString(0)));
                event.set_title(cursor.getString(1));
                event.set_date(cursor.getString(2));
                event.set_description(cursor.getString(3));
                event.set_repeated(Boolean.parseBoolean(cursor.getString(4)));

                eventList.add(event);
            }
            while (cursor.moveToNext());
        }



        return eventList;
    }


    public void addEvent(Event event){
        SQLiteDatabase db= this.getWritableDatabase();

        DateFormat df= new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        ContentValues values= new ContentValues();
        values.put(EVENT_KEY_TITLE, event.get_title());
        values.put(EVENT_KEY_DESC, event.get_description());
        values.put(EVENT_KEY_DUE_DATE, df.format(event.get_date()));
        values.put(EVENT_KEY_REPEATED, String.valueOf(event.is_repeated()));

        db.insert(TABLE_EVENT, null, values);
        db.close();

    }
}
