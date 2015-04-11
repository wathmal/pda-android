package com.awesome.wathmal.awesomeapp;

/**
 * Created by wathmal on 3/28/15.
 */
public class Medicine {
/*
    private static final String MEDICINE_KEY_ID= "medicine_id";
    private static final String MEDICINE_KEY_NAME= "name";
    private static final String MEDICINE_KEY_DOSAGE= "dosage";
    private static final String MEDICINE_KEY_EVENT_ID= "event_id";*/

    private int id;
    private String name;
    private String dosage;
    private int eventId;

    public Medicine() {
    }

    public Medicine(String name, String dosage, int eventId) {
        this.name = name;
        this.dosage = dosage;
        this.eventId = eventId;
    }

    public Medicine(int id, String name, String dosage, int eventId) {
        this.id = id;
        this.name = name;
        this.dosage = dosage;
        this.eventId = eventId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDosage() {
        return dosage;
    }

    public int getEventId() {
        return eventId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public void setId(int id) {
        this.id = id;
    }
}
