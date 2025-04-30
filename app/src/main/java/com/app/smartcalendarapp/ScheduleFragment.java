package com.app.smartcalendarapp;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ScheduleFragment extends Fragment {

    ListView listViewTasks;
    TaskDatabaseHelper dbHelper;
    ArrayList<Task> taskList;
    TaskAdapter taskAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);

        listViewTasks = view.findViewById(R.id.listViewTasks);
        dbHelper = new TaskDatabaseHelper(getContext());
        taskList = new ArrayList<>();

        loadUpcomingTasks();

        return view;
    }

    private void loadUpcomingTasks() {
        taskList.clear();

        // Get current datetime in format compatible with SQLite (yyyy-MM-dd HH:mm)
        String currentDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(new Date());
        Cursor cursor = dbHelper.getUpcomingTasks(currentDateTime);

        Log.d("ScheduleFragment", "Current time: " + currentDateTime);
        Log.d("ScheduleFragment", "Found " + cursor.getCount() + " upcoming tasks");

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(TaskDatabaseHelper.COLUMN_TASK_ID));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(TaskDatabaseHelper.COLUMN_TITLE));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(TaskDatabaseHelper.COLUMN_DESCRIPTION));
                String dateTime = cursor.getString(cursor.getColumnIndexOrThrow(TaskDatabaseHelper.COLUMN_DATETIME));

                Log.d("ScheduleFragment", "Task: " + title + " - " + dateTime);

                Task task = new Task(id, title, description, dateTime);
                taskList.add(task);

            } while (cursor.moveToNext());

            cursor.close();

            taskAdapter = new TaskAdapter(getContext(), taskList);
            listViewTasks.setAdapter(taskAdapter);

        } else {
            Toast.makeText(getContext(), "No upcoming tasks found", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        loadUpcomingTasks(); // Refresh the list every time fragment becomes visible
    }
}
