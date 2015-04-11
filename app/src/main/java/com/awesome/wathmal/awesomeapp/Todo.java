package com.awesome.wathmal.awesomeapp;

/**
 * Created by wathmal on 3/28/15.
 */
public class Todo {
    /*
    private static final String TODO_ID= "todo_id";
    private static final String TODO_EVENT_ID= "event_id";*/

    private int id;
    private int eventId;

    public Todo() {
    }

    public Todo(int eventId) {
        this.eventId = eventId;
    }

    public Todo(int id, int eventId) {
        this.id = id;
        this.eventId = eventId;
    }

    public int getId() {
        return id;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public void setId(int id) {
        this.id = id;
    }
}
