package com.awesome.wathmal.awesomeapp;

/**
 * Created by wathmal on 3/28/15.
 */
public class Location {
    private int id;
    private float lat;
    private float lon;
    private String address;

    public Location() {
    }

    public Location(float lat, float lon, String address) {
        this.lat = lat;
        this.lon = lon;
        this.address = address;
    }

    public Location(int id, float lat, float lon, String address) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public float getLat() {
        return lat;
    }

    public float getLon() {
        return lon;
    }

    public String getAddress() {
        return address;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setId(int id) {
        this.id = id;
    }
}
