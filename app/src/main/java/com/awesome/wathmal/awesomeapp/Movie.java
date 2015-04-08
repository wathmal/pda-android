package com.awesome.wathmal.awesomeapp;

/**
 * Created by wathmal on 3/28/15.
 */
public class Movie {

    /*private static final String MOVIE_KEY_IMDB_ID= "imdb_id";
    private static final String MOVIE_KEY_TITLE= "title";
    private static final String MOVIE_KEY_DURATION= "duration";
    private static final String MOVIE_KEY_CURRENT_TIME= "current_time";
    private static final String MOVIE_KEY_MEDIA_ID= "media_id";
    */
    private int id;
    private String imdbId;
    private String title;
    private int duration;
    private int currentTime;
    private int mediaId;

    public Movie() {
    }

    public Movie(String title, int duration, int currentTime, int mediaId) {
        this.title = title;
        this.duration = duration;
        this.currentTime = currentTime;
        this.mediaId = mediaId;
    }

    public Movie(String imdbId, String title, int duration, int currentTime, int mediaId) {
        this.imdbId = imdbId;
        this.title = title;
        this.duration = duration;
        this.currentTime = currentTime;
        this.mediaId = mediaId;
    }

    public String getImdbId() {
        return imdbId;
    }

    public String getTitle() {
        return title;
    }

    public int getDuration() {
        return duration;
    }

    public int getCurrentTime() {
        return currentTime;
    }

    public int getMediaId() {
        return mediaId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setCurrentTime(int currentTime) {
        this.currentTime = currentTime;
    }

    public void setMediaId(int mediaId) {
        this.mediaId = mediaId;
    }

    public Movie(int id, String imdbId, String title, int duration, int currentTime, int mediaId) {
        this.id = id;
        this.imdbId = imdbId;
        this.title = title;
        this.duration = duration;
        this.currentTime = currentTime;
        this.mediaId = mediaId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
