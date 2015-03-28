package com.awesome.wathmal.awesomeapp;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by wathmal on 3/19/15.
 */
public class Event {
    private int _id;
    private String _title;
    private String _description;
    private Date _date;
    private boolean _repeated;

    /*
    later modifications
     */
    private int locationId;
    private String recurrenceType;
    private String eventType;
    /*
    foreign key to hold the resource id connecting to event
    ex: book_id, media_id
     */
    private int resourceId;
    private boolean notify;

    public Event(){

    }

    public Event(String _title, String _description, Date _date, boolean _repeated) {
        this._title = _title;
        this._description = _description;
        this._date = _date;
        this._repeated = _repeated;
    }

    public Event(int _id, String _title, String _description, Date _date, boolean _repeated) {
        this._id = _id;
        this._title = _title;
        this._description = _description;
        this._date = _date;
        this._repeated = _repeated;
    }

    public Event(String _title, String _description, Date _date, boolean _repeated, int locationId, String recurrenceType, String eventType, int resourceId, boolean notify) {
        this._title = _title;
        this._description = _description;
        this._date = _date;
        this._repeated = _repeated;
        this.locationId = locationId;
        this.recurrenceType = recurrenceType;
        this.eventType = eventType;
        this.resourceId = resourceId;
        this.notify = notify;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_title() {
        return _title;
    }

    public void set_title(String _title) {
        this._title = _title;
    }

    public String get_description() {
        return _description;
    }

    public void set_description(String _description) {
        this._description = _description;
    }

    /*
    can use this to retrieve both time and date
     */
    public Date get_date() {
        return _date;
    }

    public void set_date(String date) {
        DateFormat df= new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date fdate= null;
        try {
            fdate = df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this._date = fdate;
    }

    public boolean is_repeated() {
        return _repeated;
    }

    public void set_repeated(boolean _repeated) {
        this._repeated = _repeated;
    }

    /*
    time should be formatted as hh:mm:ss
     */
    public void setTime(String time){
        DateFormat dateOnly= new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        DateFormat dateAndTime= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);

        String dateString= dateOnly.format(this._date);
        Date updatedDateAndTime= this._date;
        try {
            updatedDateAndTime= dateAndTime.parse(dateString+" "+ time);
        } catch (ParseException e) {
            e.printStackTrace();
            // if an exception occurs the date won't change
        }
        this._date= updatedDateAndTime;
    }

    public int getLocationId() {
        return locationId;
    }

    public String getRecurrenceType() {
        return recurrenceType;
    }

    public String getEventType() {
        return eventType;
    }

    public int getResourceId() {
        return resourceId;
    }

    public boolean isNotify() {
        return notify;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    /*
    daily
    monthly
    yearly
    todo
     */
    public void setRecurrenceType(String recurrenceType) {
        this.recurrenceType = recurrenceType;
    }

    /*
    medicine
    book
    media
    event
     */
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    /*
    if the user want to receive a notification
     */
    public void setNotify(boolean notify) {
        this.notify = notify;
    }
}
