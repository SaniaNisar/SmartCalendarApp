package com.app.smartcalendarapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TaskDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "SmartCalendarDB.db";
    private static final int DATABASE_VERSION = 1;

    // Tasks Table
    public static final String TABLE_TASKS = "tasks";
    public static final String COLUMN_TASK_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_DATETIME = "datetime";
    public static final String COLUMN_STATUS = "status";

    // Notifications Table
    public static final String TABLE_NOTIFICATIONS = "notifications";
    public static final String COLUMN_NOTIFICATION_ID = "id";
    public static final String COLUMN_MESSAGE = "message";
    public static final String COLUMN_NOTIFICATION_DATETIME = "datetime";

    public TaskDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TASKS_TABLE = "CREATE TABLE " + TABLE_TASKS + "("
                + COLUMN_TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TITLE + " TEXT, "
                + COLUMN_DESCRIPTION + " TEXT, "
                + COLUMN_DATETIME + " TEXT, "
                + COLUMN_STATUS + " TEXT"
                + ")";
        db.execSQL(CREATE_TASKS_TABLE);

        String CREATE_NOTIFICATIONS_TABLE = "CREATE TABLE " + TABLE_NOTIFICATIONS + "("
                + COLUMN_NOTIFICATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_MESSAGE + " TEXT, "
                + COLUMN_NOTIFICATION_DATETIME + " TEXT"
                + ")";
        db.execSQL(CREATE_NOTIFICATIONS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFICATIONS);
        onCreate(db);
    }

    // Add a task
    public boolean addTask(String title, String description, String datetime, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_DATETIME, datetime);
        values.put(COLUMN_STATUS, status);
        long result = db.insert(TABLE_TASKS, null, values);
        return result != -1;
    }

    // Add a notification
    public boolean addNotification(String message, String datetime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MESSAGE, message);
        values.put(COLUMN_NOTIFICATION_DATETIME, datetime);
        long result = db.insert(TABLE_NOTIFICATIONS, null, values);
        return result != -1;
    }

    // Get upcoming tasks
    public Cursor getUpcomingTasks(String currentDateTime) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_TASKS +
                " WHERE datetime(datetime) > datetime(?) ORDER BY datetime ASC", new String[]{currentDateTime});
    }

    // Get past tasks
    public Cursor getPastTasks(String currentDateTime) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_TASKS +
                " WHERE datetime(datetime) <= datetime(?) ORDER BY datetime DESC", new String[]{currentDateTime});
    }

    // Get all notifications
    public Cursor getAllNotifications() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NOTIFICATIONS + " ORDER BY datetime DESC", null);
    }
}
