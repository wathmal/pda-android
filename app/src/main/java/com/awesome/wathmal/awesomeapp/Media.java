package com.awesome.wathmal.awesomeapp;

/**
 * Created by wathmal on 3/28/15.
 */
public class Media {

    /*private static final String MEDIA_KEY_ID= "media_id";
    private static final String MEDIA_KEY_TYPE= "media_type";
    private static final String MEDIA_KEY_RESOURCE_ID= "resource_id";
    private static final String MEDIA_KEY_EVENT_ID= "event_id";*/

    private int id;
    private String type;
    private int resourceId;
    private int eventId;

    public Media() {
    }

    public Media(String type, int resourceId, int eventId) {
        this.type = type;
        this.resourceId = resourceId;
        this.eventId = eventId;
    }

    public Media(int id, String type, int resourceId, int eventId) {
        this.id = id;
        this.type = type;
        this.resourceId = resourceId;
        this.eventId = eventId;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public int getResourceId() {
        return resourceId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public void setId(int id) {
        this.id = id;
    }
}
