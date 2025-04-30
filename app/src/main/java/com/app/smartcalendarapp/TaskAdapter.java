package com.app.smartcalendarapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.smartcalendarapp.R;
import com.app.smartcalendarapp.Task;

import java.util.List;

public class TaskAdapter extends BaseAdapter {

    private Context context;
    private List<Task> taskList;

    public TaskAdapter(Context context, List<Task> taskList) {
        this.context = context;
        this.taskList = taskList;
    }

    @Override
    public int getCount() {
        return taskList.size();
    }

    @Override
    public Object getItem(int position) {
        return taskList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return taskList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Recycle the view if it already exists
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_task, parent, false);
        }

        // Get the current task at the given position
        Task task = taskList.get(position);

        // Find the text views
        TextView textTitle = convertView.findViewById(R.id.textTitle);
        TextView textDescription = convertView.findViewById(R.id.textDescription);
        TextView textDateTime = convertView.findViewById(R.id.textDateTime);

        // Set data into the views
        textTitle.setText(task.getTitle());
        textDescription.setText(task.getDescription());
        textDateTime.setText(task.getDateTime());

        // Return the populated view
        return convertView;
    }
}
