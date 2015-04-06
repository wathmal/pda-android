package com.awesome.wathmal.awesomeapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by wathmal on 3/19/15.
 * all CURD operations
 */
public class DatabaseHandler extends SQLiteOpenHelper {


    // all static variables
    // database version
    private static final int DATABASE_VERSION = 4;

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





        String CREATE_EVENT_TABLE=  "CREATE TABLE "+TABLE_EVENT +"(" +
                EVENT_KEY_ID+ " INTEGER PRIMARY KEY NOT NULL," +
                EVENT_KEY_TITLE+ " VARCHAR(30) NOT NULL," +
                EVENT_KEY_DUE_DATE+" DATE," +
                EVENT_KEY_DESC+" VARCHAR(200)," +
                EVENT_KEY_REPEATED+ " BOOLEAN,"+
                EVENT_KEY_LOCATION_ID+ " INTEGER REFERENCES "+ TABLE_LOCATION+"("+LOCATION_KEY_ID+") ON UPDATE CASCADE,"+
                EVENT_KEY_RECURRENCE_TYPE+ " VARCHAR(20),"+
                EVENT_KEY_EVENT_TYPE+ " VARCHAR(20),"+
                EVENT_KEY_RESOURCE_ID+ " INTEGER,"+
                EVENT_KEY_NOTIFY+ " BOOLEAN"
                +")";

        String CREATE_MEDIA_TABLE= "CREATE TABLE "+ TABLE_MEDIA +"("+
                MEDIA_KEY_ID+ " INTEGER PRIMARY KEY NOT NULL," +
                MEDIA_KEY_TYPE+ " VARCHAR(30) NOT NULL," +
                MEDIA_KEY_RESOURCE_ID+ " INTEGER,"+
                MEDIA_KEY_EVENT_ID+ " INTEGER REFERENCES "+ TABLE_EVENT+"("+EVENT_KEY_ID+") ON UPDATE CASCADE"
                +")";


        String CREATE_BOOK_TABLE= "CREATE TABLE "+ TABLE_BOOK+ "("+
                BOOK_KEY_ID+ " INTEGER PRIMARY KEY NOT NULL,"+
                BOOK_KEY_TITLE+ " VARCHAR(100),"+
                BOOK_KEY_PAGES+ " INTEGER,"+
                BOOK_KEY_CURRENT_PAGE+ " INTEGER,"+
                BOOK_KEY_EVENT_ID+ " INTEGER REFERENCES "+ TABLE_EVENT+"("+EVENT_KEY_ID+") ON UPDATE CASCADE"
                +")";


        String CREATE_MOVIE_TABLE= "CREATE TABLE "+ TABLE_MOVIE+ "("+
                MOVIE_KEY_IMDB_ID+ " VARCHAR(11) PRIMARY KEY NOT NULL,"+
                MOVIE_KEY_TITLE+ " VARCHAR(200),"+
                MOVIE_KEY_DURATION+ " INTEGER,"+
                MOVIE_KEY_CURRENT_TIME+ " INTEGER,"+
                MOVIE_KEY_MEDIA_ID+ " INTEGER REFERENCES "+TABLE_MEDIA+"("+MEDIA_KEY_ID+") ON UPDATE CASCADE"
                +")";

        String CREATE_AUDIO_BOOK_TABLE= "CREATE TABLE "+ TABLE_AUDIO_BOOK+ "("+
                AUDIO_BOOK_ID+ " INTEGER PRIMARY KEY NOT NULL,"+
                AUDIO_BOOK_TITLE+ " VARCHAR(100),"+
                AUDIO_BOOK_DURATION+ " INTEGER,"+
                AUDIO_BOOK_CURR_TIME+ " INTEGER,"+
                AUDIO_BOOK_MEDIA_ID+ " INTEGER REFERENCES "+TABLE_MEDIA+"("+MEDIA_KEY_ID+") ON UPDATE CASCADE"
                +")";

        String CREATE_MEDICINE_TABLE= "CREATE TABLE "+ TABLE_MEDICINE +"("+
                MEDICINE_KEY_ID+ "INTEGER PRIMARY KEY NOT NULL,"+
                MEDICINE_KEY_NAME+ " VARCHAR(100),"+
                MEDICINE_KEY_DOSAGE+ " VARCHAR(50),"+
                MEDICINE_KEY_EVENT_ID+ " INTEGER REFERENCES "+ TABLE_EVENT+"("+EVENT_KEY_ID+") ON UPDATE CASCADE"
                +")";


        String CREATE_TODO_TABLE= "CREATE TABLE "+ TABLE_TODO +"("+
                TODO_ID+ " INTEGER PRIMARY KEY NOT NULL,"+
                TODO_EVENT_ID+ " INTEGER REFERENCES "+ TABLE_EVENT+"("+EVENT_KEY_ID+") ON UPDATE CASCADE"
                +")";

        String CREATE_DAILY_TABLE= "CREATE TABLE "+ TABLE_DAILY +"("+
                DAILY_ID+ " INTEGER PRIMARY KEY NOT NULL,"+
                DAILY_EVENT_ID+ " INTEGER REFERENCES "+ TABLE_EVENT+"("+EVENT_KEY_ID+") ON UPDATE CASCADE"
                +")";

        String CREATE_WEEKLY_TABLE= "CREATE TABLE "+ TABLE_WEEKLY +"("+
                WEEKLY_ID+ " INTEGER PRIMARY KEY NOT NULL,"+
                WEEKLY_EVENT_ID+ " INTEGER REFERENCES "+ TABLE_EVENT+"("+EVENT_KEY_ID+") ON UPDATE CASCADE"
                +")";

        String CREATE_MONTHLY_TABLE= "CREATE TABLE "+ TABLE_MONTHLY +"("+
                MONTHLY_ID+ " INTEGER PRIMARY KEY NOT NULL,"+
                MONTHLY_EVENT_ID+ " INTEGER REFERENCES "+ TABLE_EVENT+"("+EVENT_KEY_ID+") ON UPDATE CASCADE"
                +")";

        String CREATE_YEARLY_TABLE= "CREATE TABLE "+ TABLE_YEARLY +"("+
                YEARLY_ID+ " INTEGER PRIMARY KEY NOT NULL,"+
                YEARLY_EVENT_ID+ " INTEGER REFERENCES "+ TABLE_EVENT+"("+EVENT_KEY_ID+") ON UPDATE CASCADE"
                +")";

        db.execSQL(CREATE_LOCATION_TABLE);
        db.execSQL(CREATE_EVENT_TABLE);
        db.execSQL(CREATE_MEDIA_TABLE);
        db.execSQL(CREATE_BOOK_TABLE);
        db.execSQL(CREATE_MOVIE_TABLE);
        db.execSQL(CREATE_AUDIO_BOOK_TABLE);
        db.execSQL(CREATE_MEDICINE_TABLE);
        db.execSQL(CREATE_TODO_TABLE);
        db.execSQL(CREATE_DAILY_TABLE);
        db.execSQL(CREATE_WEEKLY_TABLE);
        db.execSQL(CREATE_MONTHLY_TABLE);
        db.execSQL(CREATE_YEARLY_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("awesomeapp", "old db version is"+ oldVersion);
        Log.d("awesomeapp", "new db version is"+ newVersion);

        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_YEARLY);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_MONTHLY);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_WEEKLY);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_DAILY);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_TODO);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_MEDICINE);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_AUDIO_BOOK);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_MOVIE);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_BOOK);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_MEDIA);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_EVENT);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_LOCATION);
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
                event.setLocationId(Integer.parseInt(cursor.getString(5)));
                event.setRecurrenceType(cursor.getString(6));
                event.setEventType(cursor.getString(7));
                event.setResourceId(cursor.getInt(8));
                event.setNotify(Boolean.parseBoolean(cursor.getString(9)));

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
        values.put(EVENT_KEY_REPEATED, event.is_repeated());        // can pu boolean
        // add newly updated values
        values.put(EVENT_KEY_LOCATION_ID, event.getLocationId());
        values.put(EVENT_KEY_RECURRENCE_TYPE, event.getRecurrenceType());
        values.put(EVENT_KEY_EVENT_TYPE, event.getEventType());
        values.put(EVENT_KEY_RESOURCE_ID, event.getResourceId());
        values.put(EVENT_KEY_NOTIFY, event.isNotify());

        db.insert(TABLE_EVENT, null, values);
        db.close();

    }


    public int updateEvent(Event event){
        /*
        all columns will be updated
        make sure when updating all the columns are set.
         */

        SQLiteDatabase db= this.getWritableDatabase();

        DateFormat df= new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        ContentValues values= new ContentValues();
        values.put(EVENT_KEY_TITLE, event.get_title());
        values.put(EVENT_KEY_DESC, event.get_description());
        values.put(EVENT_KEY_DUE_DATE, df.format(event.get_date()));
        values.put(EVENT_KEY_REPEATED, event.is_repeated());        // can pu boolean
        // add newly updated values
        values.put(EVENT_KEY_LOCATION_ID, event.getLocationId());
        values.put(EVENT_KEY_RECURRENCE_TYPE, event.getRecurrenceType());
        values.put(EVENT_KEY_EVENT_TYPE, event.getEventType());
        values.put(EVENT_KEY_RESOURCE_ID, event.getEventType());
        values.put(EVENT_KEY_NOTIFY, event.isNotify());

        // returns number of rows affected
        int noOfRows= db.update(TABLE_EVENT, values, EVENT_KEY_ID+ " = ?", new String[]{String.valueOf(event.get_id())});
        db.close();

        return noOfRows;
    }

    // deletes a row
    public int deleteEvent(Event event){
        SQLiteDatabase db= this.getWritableDatabase();

        int noOfRows= db.delete(TABLE_EVENT, EVENT_KEY_ID+ " = ?", new String[]{String.valueOf(event.get_date())});
        db.close();
        return noOfRows;
    }


    public void addLocation(Location location){
        SQLiteDatabase db= this.getWritableDatabase();

        ContentValues values= new ContentValues();
        values.put(LOCATION_KEY_LAT, location.getLat());
        values.put(LOCATION_KEY_LONG, location.getLon());
        values.put(LOCATION_KEY_ADDRESS, location.getAddress());

        db.insert(TABLE_LOCATION, null, values);
        db.close();
    }

    public void addMedia(Media media){
        SQLiteDatabase db= this.getWritableDatabase();

        ContentValues values= new ContentValues();
        values.put(MEDIA_KEY_TYPE, media.getType());
        values.put(MEDIA_KEY_RESOURCE_ID, media.getResourceId());
        values.put(MEDIA_KEY_EVENT_ID, media.getEventId());

        db.insert(TABLE_MEDIA, null, values);
        db.close();

    }


    public void addMedicine(Medicine medicine){
        SQLiteDatabase db= this.getWritableDatabase();

        ContentValues values= new ContentValues();
        values.put(MEDICINE_KEY_NAME, medicine.getName());
        values.put(MEDICINE_KEY_DOSAGE, medicine.getDosage());
        values.put(MEDICINE_KEY_EVENT_ID, medicine.getEventId());

        db.insert(TABLE_MEDICINE, null, values);
        db.close();
    }

    public void addBook(Book book){
        SQLiteDatabase db= this.getWritableDatabase();

        ContentValues values= new ContentValues();
        values.put(BOOK_KEY_TITLE, book.getTitle());
        values.put(BOOK_KEY_PAGES, book.getPages());
        values.put(BOOK_KEY_CURRENT_PAGE, book.getCurrentPage());
        values.put(BOOK_KEY_EVENT_ID, book.getEventId());

        db.insert(TABLE_BOOK, null, values);
        db.close();
    }

    public void addMovie(Movie movie){
        SQLiteDatabase db= this.getWritableDatabase();

        ContentValues values= new ContentValues();
        values.put(MOVIE_KEY_IMDB_ID, movie.getImdbId());
        values.put(MOVIE_KEY_TITLE, movie.getTitle());
        values.put(MOVIE_KEY_DURATION, movie.getDuration());
        values.put(MOVIE_KEY_CURRENT_TIME, movie.getCurrentTime());
        values.put(MOVIE_KEY_MEDIA_ID, movie.getMediaId());

        db.insert(TABLE_MOVIE, null, values);
        db.close();
    }

    public void addAudioBook(AudioBook audioBook){
        SQLiteDatabase db= this.getWritableDatabase();

        ContentValues values= new ContentValues();
        values.put(AUDIO_BOOK_TITLE, audioBook.getTitle());
        values.put(AUDIO_BOOK_DURATION, audioBook.getDuration());
        values.put(AUDIO_BOOK_CURR_TIME, audioBook.getCurrentTime());
        values.put(AUDIO_BOOK_MEDIA_ID, audioBook.getMediaId());

        db.insert(TABLE_AUDIO_BOOK, null, values);
        db.close();

    }

    public void addTodo(Todo todo){
        SQLiteDatabase db= this.getWritableDatabase();

        ContentValues values= new ContentValues();
        values.put(TODO_EVENT_ID, todo.getEventId());

        db.insert(TABLE_TODO, null, values);
        db.close();
    }

    public void addDaily(Daily daily){
        SQLiteDatabase db= this.getWritableDatabase();

        ContentValues values= new ContentValues();
        values.put(DAILY_EVENT_ID, daily.getEventId());

        db.insert(TABLE_DAILY, null, values);
        db.close();
    }

    public void addWeekly(Weekly weekly){
        SQLiteDatabase db= this.getWritableDatabase();

        ContentValues values= new ContentValues();
        values.put(WEEKLY_EVENT_ID, weekly.getEventId());

        db.insert(TABLE_WEEKLY, null, values);
        db.close();
    }

    public void addMonthly(Monthly monthly){
        SQLiteDatabase db= this.getWritableDatabase();

        ContentValues values= new ContentValues();
        values.put(MONTHLY_EVENT_ID, monthly.getEventId());

        db.insert(TABLE_MONTHLY, null, values);
        db.close();
    }

    public void addYearly(Yearly yearly){
        SQLiteDatabase db= this.getWritableDatabase();

        ContentValues values= new ContentValues();
        values.put(YEARLY_EVENT_ID, yearly.getEventId());

        db.insert(TABLE_YEARLY, null, values);
        db.close();
    }



    public Location getLocation(int id){

        String selectQuery= "SELECT * FROM "+ TABLE_LOCATION+" WHERE "+LOCATION_KEY_ID+"="+id;
        SQLiteDatabase db= this.getWritableDatabase();

        Cursor cursor= db.query(TABLE_LOCATION, new String[]{LOCATION_KEY_ID},LOCATION_KEY_ID+"=?", new String[]{String.valueOf(id)}, null, null, null, "1");

        if(cursor != null){
            cursor.moveToFirst();
        }
        Location location= new Location(cursor.getInt(0), cursor.getFloat(1), cursor.getFloat(2), cursor.getString(3));
        db.close();
        return location;
    }

    public List getAllLocations(){
        List<Location> locationList= new ArrayList<Location>();
        String selectQuery= "SELECT * FROM "+ TABLE_LOCATION;

        SQLiteDatabase db= this.getWritableDatabase();
        Cursor cursor= db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do{
                Location location= new Location();

                location.setLat(cursor.getFloat(1));
                location.setLon(cursor.getFloat(2));
                location.setAddress(cursor.getString(3));

                locationList.add(location);
            }
            while (cursor.moveToNext());
        }

        db.close();
        return locationList;
    }


    /*
    *
    * public List<Event> getAllEvents(){
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
                event.setLocationId(Integer.parseInt(cursor.getString(5)));
                event.setRecurrenceType(cursor.getString(6));
                event.setEventType(cursor.getString(7));
                event.setResourceId(Integer.parseInt(cursor.getString(8)));
                event.setNotify(Boolean.parseBoolean(cursor.getString(9)));

                eventList.add(event);
            }
            while (cursor.moveToNext());
        }



        return eventList;
    }
    * */


    public List getAllMedicines(){
        List<Medicine> medicineList= new ArrayList<Medicine>();
        String selectQuery= "SELECT * FROM "+ TABLE_MEDICINE;

        SQLiteDatabase db= this.getWritableDatabase();
        Cursor cursor= db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                Medicine medicine= new Medicine();
                medicine.setName(cursor.getString(1));
                medicine.setDosage(cursor.getString(2));
                medicine.setEventId(cursor.getInt(3));

                medicineList.add(medicine);
            }
            while (cursor.moveToNext());

        }
        db.close();

        return medicineList;
    }

    public List getAllMovies(){

        List<Movie> movieList= new ArrayList<Movie>();
        String selectQuery= "SELECT * FROM "+ TABLE_MOVIE;

        SQLiteDatabase db= this.getWritableDatabase();
        Cursor cursor= db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do{
                Movie movie= new Movie();
                movie.setImdbId(cursor.getString(0));
                movie.setTitle(cursor.getString(1));
                movie.setDuration(cursor.getInt(2));
                movie.setCurrentTime(cursor.getInt(3));
                movie.setMediaId(cursor.getInt(4));

                movieList.add(movie);
            }
            while (cursor.moveToNext());

        }
        db.close();
        return movieList;
    }

    public List getAllAudioBooks(){

        List<AudioBook> audioBookList= new ArrayList<AudioBook>();
        String selectQuery= "SELECT * FROM "+ TABLE_AUDIO_BOOK;

        SQLiteDatabase db= this.getWritableDatabase();
        Cursor cursor= db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do{
                AudioBook audioBook= new AudioBook();
                audioBook.setTitle(cursor.getString(1));
                audioBook.setDuration(cursor.getInt(2));
                audioBook.setCurrentTime(cursor.getInt(3));
                audioBook.setMediaId(cursor.getInt(4));

                audioBookList.add(audioBook);
            }
            while (cursor.moveToNext());
        }
        db.close();
        return audioBookList;
    }

    public List getAllBooks(){

        List<Book> bookList= new ArrayList<Book>();
        String selectQuery= "SELECT * FROM "+ TABLE_BOOK;

        SQLiteDatabase db= this.getWritableDatabase();
        Cursor cursor= db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do{
                Book book= new Book();
                book.setTitle(cursor.getString(1));
                book.setPages(cursor.getInt(2));
                book.setCurrentPage(cursor.getInt(3));
                book.setEventId(cursor.getInt(4));
            }
            while(cursor.moveToNext());
        }
        db.close();
        return bookList;
    }

    public List getAllTodos(){
        List<Todo> todoList= new ArrayList<Todo>();
        String selectQuery= "SELECT * FROM "+ TABLE_TODO;

        SQLiteDatabase db= this.getWritableDatabase();
        Cursor cursor= db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                Todo todo= new Todo(cursor.getInt(1));
            }
            while (cursor.moveToNext());
        }
        db.close();
        return todoList;

    }

    public List getAllDaily(){
        List<Daily> dailyList= new ArrayList<Daily>();
        String selectQuery= "SELECT * FROM "+ TABLE_DAILY;

        SQLiteDatabase db= this.getWritableDatabase();
        Cursor cursor= db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                Daily daily= new Daily(cursor.getInt(1));
            }
            while (cursor.moveToNext());
        }
        db.close();
        return dailyList;

    }

    public List getAllWeekly(){
        List<Weekly> weeklyList= new ArrayList<Weekly>();
        String selectQuery= "SELECT * FROM "+ TABLE_WEEKLY;

        SQLiteDatabase db= this.getWritableDatabase();
        Cursor cursor= db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                Weekly weekly= new Weekly(cursor.getInt(1));
            }
            while (cursor.moveToNext());
        }
        db.close();
        return weeklyList;

    }

    public List getAllMonthly(){
        List<Monthly> monthlyList= new ArrayList<Monthly>();
        String selectQuery= "SELECT * FROM "+ TABLE_MONTHLY;

        SQLiteDatabase db= this.getWritableDatabase();
        Cursor cursor= db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                Monthly monthly= new Monthly(cursor.getInt(1));
            }
            while (cursor.moveToNext());
        }
        db.close();
        return monthlyList;

    }

    public List getAllYearly(){
        List<Yearly> yearlyList= new ArrayList<Yearly>();
        String selectQuery= "SELECT * FROM "+ TABLE_YEARLY;

        SQLiteDatabase db= this.getWritableDatabase();
        Cursor cursor= db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                Yearly yearly= new Yearly(cursor.getInt(1));
            }
            while (cursor.moveToNext());
        }
        db.close();
        return yearlyList;

    }

}
