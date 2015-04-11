package com.awesome.wathmal.awesomeapp;

/**
 * Created by wathmal on 3/28/15.
 */
public class AudioBook {

/*    private static final String AUDIO_BOOK_ID= "audio_book_id";
    private static final String AUDIO_BOOK_TITLE= "title";
    private static final String AUDIO_BOOK_DURATION= "duration";
    private static final String AUDIO_BOOK_CURR_TIME= "current_time";
    private static final String AUDIO_BOOK_MEDIA_ID= "media_id";*/

    private int id;
    private String title;
    private int duration;
    private int currentTime;
    private int mediaId;

    public AudioBook() {
    }

    public AudioBook(String title, int duration, int currentTime, int mediaId) {
        this.title = title;
        this.duration = duration;
        this.currentTime = currentTime;
        this.mediaId = mediaId;
    }

    public AudioBook(int id, String title, int duration, int currentTime, int mediaId) {
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.currentTime = currentTime;
        this.mediaId = mediaId;
    }

    public int getId() {
        return id;
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

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setCurrentTime(int currentTime) {
        this.currentTime = currentTime;
    }

    public void setMediaId(int mediaId) {
        this.mediaId = mediaId;
    }

    public void setId(int id) {
        this.id = id;
    }
}
