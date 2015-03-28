package com.awesome.wathmal.awesomeapp;

/**
 * Created by wathmal on 3/28/15.
 */
public class Book {
    /*
    private static final String BOOK_KEY_ID= "book_id";
    private static final String BOOK_KEY_TITLE= "title";
    private static final String BOOK_KEY_PAGES= "pages";
    private static final String BOOK_KEY_CURRENT_PAGE= "current_page";
    private static final String BOOK_KEY_EVENT_ID= "event_id";
    */
    private int id;
    private String title;
    private int pages;
    private int currentPage;
    private int eventId;

    public Book() {
    }

    public Book(String title, int pages, int currentPage, int eventId) {
        this.title = title;
        this.pages = pages;
        this.currentPage = currentPage;
        this.eventId = eventId;
    }

    public Book(int id, String title, int pages, int currentPage, int eventId) {
        this.id = id;
        this.title = title;
        this.pages = pages;
        this.currentPage = currentPage;
        this.eventId = eventId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getPages() {
        return pages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getEventId() {
        return eventId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }
}
