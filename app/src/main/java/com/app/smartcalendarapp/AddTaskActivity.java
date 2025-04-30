package com.app.smartcalendarapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddTaskActivity extends AppCompatActivity {

    EditText editTitle, editDescription;
    TextView textDateTime;
    Button btnPickDateTime, btnSave;
    Calendar calendar;
    TaskDatabaseHelper dbHelper;
    String formattedDateTime = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        editTitle = findViewById(R.id.edit_title);
        editDescription = findViewById(R.id.edit_description);
        textDateTime = findViewById(R.id.text_date_time);
        btnPickDateTime = findViewById(R.id.btn_pick_datetime);
        btnSave = findViewById(R.id.btn_save);

        dbHelper = new TaskDatabaseHelper(this);
        calendar = Calendar.getInstance();

        btnPickDateTime.setOnClickListener(v -> showDateTimePicker());

        btnSave.setOnClickListener(v -> {
            String title = editTitle.getText().toString().trim();
            String description = editDescription.getText().toString().trim();

            if (title.isEmpty() || description.isEmpty() || formattedDateTime.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean inserted = dbHelper.addTask(title, description, formattedDateTime, "upcoming");

            if (inserted) {
                Toast.makeText(this, "Task added successfully", Toast.LENGTH_SHORT).show();
                finish(); // return to previous activity
            } else {
                Toast.makeText(this, "Error adding task", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDateTimePicker() {
        final Calendar now = Calendar.getInstance();

        new DatePickerDialog(this, (view, year, month, day) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, day);

            new TimePickerDialog(this, (view1, hour, minute) -> {
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minute);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
                formattedDateTime = sdf.format(calendar.getTime());
                textDateTime.setText("Selected: " + formattedDateTime);

            }, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), false).show();

        }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH)).show();
    }
}
