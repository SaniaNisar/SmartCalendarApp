package com.app.smartcalendarapp;

import android.database.Cursor;
import android.os.Bundle;
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

public class PastFragment extends Fragment {

    ListView listViewPastTasks;
    TaskDatabaseHelper dbHelper;
    ArrayList<Task> pastTaskList;
    TaskAdapter taskAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_past, container, false);

        listViewPastTasks = view.findViewById(R.id.listViewPastTasks);
        dbHelper = new TaskDatabaseHelper(getContext());
        pastTaskList = new ArrayList<>();

        loadPastTasks();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadPastTasks();  // Refresh every time the fragment becomes visible
    }

    private void loadPastTasks() {
        pastTaskList.clear();

        // Get current datetime in format used in DB
        String currentDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(new Date());
        Cursor cursor = dbHelper.getPastTasks(currentDateTime);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(TaskDatabaseHelper.COLUMN_TASK_ID));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(TaskDatabaseHelper.COLUMN_TITLE));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(TaskDatabaseHelper.COLUMN_DESCRIPTION));
                String dateTime = cursor.getString(cursor.getColumnIndexOrThrow(TaskDatabaseHelper.COLUMN_DATETIME));

                Task task = new Task(id, title, description, dateTime);
                pastTaskList.add(task);
            } while (cursor.moveToNext());

            cursor.close();

            taskAdapter = new TaskAdapter(getContext(), pastTaskList);
            listViewPastTasks.setAdapter(taskAdapter);

        } else {
            Toast.makeText(getContext(), "No past tasks found", Toast.LENGTH_SHORT).show();
        }
    }
}
