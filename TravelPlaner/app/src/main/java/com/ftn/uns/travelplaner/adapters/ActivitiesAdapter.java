package com.ftn.uns.travelplaner.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ftn.uns.travelplaner.R;
import com.ftn.uns.travelplaner.model.Activity;
import com.ftn.uns.travelplaner.util.DateTimeFormatter;

import java.util.List;

public class ActivitiesAdapter extends ArrayAdapter<Activity> {

    private final Context context;
    private final List<Activity> data;

    public ActivitiesAdapter(Context context, List<Activity> data) {
        super(context, -1, data);
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Activity getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activities_list_item, parent, false);

        TextView objectView = rowView.findViewById(R.id.activity_object);
        TextView typeView = rowView.findViewById(R.id.activity_type);
        TextView timeView = rowView.findViewById(R.id.activity_time);

        Activity activity = getItem(position);
        objectView.setText(activity.object.name);
        typeView.setText(activity.type.name().toLowerCase());
        timeView.setText(DateTimeFormatter.formatTime(activity.time));

        return rowView;
    }
}
