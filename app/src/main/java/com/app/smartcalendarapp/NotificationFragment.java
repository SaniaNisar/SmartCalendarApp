package com.app.smartcalendarapp;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;

public class NotificationFragment extends Fragment {

    ListView listViewNotifications;
    TaskDatabaseHelper dbHelper;
    ArrayList<Notification> notificationList;
    NotificationAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        listViewNotifications = view.findViewById(R.id.listViewNotifications);
        dbHelper = new TaskDatabaseHelper(getContext());
        notificationList = new ArrayList<>();

        addDummyNotificationsIfNeeded();
        loadNotifications();

        return view;
    }

    private void addDummyNotificationsIfNeeded() {
        if (dbHelper.getAllNotifications().getCount() == 0) {
            dbHelper.addNotification("Meeting with Dr. Smith at 5 PM", "2025-05-01 09:00");
            dbHelper.addNotification("Don't forget your blood test", "2025-04-30 14:00");
            dbHelper.addNotification("Report is ready for pickup", "2025-04-29 10:00");
        }
    }

    private void loadNotifications() {
        notificationList.clear();
        Cursor cursor = dbHelper.getAllNotifications();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(TaskDatabaseHelper.COLUMN_NOTIFICATION_ID));
                String message = cursor.getString(cursor.getColumnIndexOrThrow(TaskDatabaseHelper.COLUMN_MESSAGE));
                String dateTime = cursor.getString(cursor.getColumnIndexOrThrow(TaskDatabaseHelper.COLUMN_NOTIFICATION_DATETIME));

                notificationList.add(new Notification(id, message, dateTime));
            } while (cursor.moveToNext());

            cursor.close();
            adapter = new NotificationAdapter(getContext(), notificationList);
            listViewNotifications.setAdapter(adapter);
        } else {
            Toast.makeText(getContext(), "No notifications found", Toast.LENGTH_SHORT).show();
        }
    }
}
