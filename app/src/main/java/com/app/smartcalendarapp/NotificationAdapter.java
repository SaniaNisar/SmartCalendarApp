package com.app.smartcalendarapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class NotificationAdapter extends BaseAdapter {

    Context context;
    ArrayList<Notification> notificationList;
    LayoutInflater inflater;

    public NotificationAdapter(Context context, ArrayList<Notification> notificationList) {
        this.context = context;
        this.notificationList = notificationList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return notificationList.size();
    }

    @Override
    public Object getItem(int position) {
        return notificationList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return notificationList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_notification, null);

        TextView messageText = view.findViewById(R.id.textMessage);
        TextView datetimeText = view.findViewById(R.id.textDatetime);

        Notification item = notificationList.get(position);
        messageText.setText(item.getMessage());
        datetimeText.setText(item.getDatetime());

        return view;
    }
}
