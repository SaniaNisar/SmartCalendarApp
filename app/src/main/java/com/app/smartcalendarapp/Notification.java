package com.app.smartcalendarapp;

public class Notification {
    int id;
    String message;
    String datetime;

    public Notification(int id, String message, String datetime) {
        this.id = id;
        this.message = message;
        this.datetime = datetime;
    }

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public String getDatetime() {
        return datetime;
    }
}
