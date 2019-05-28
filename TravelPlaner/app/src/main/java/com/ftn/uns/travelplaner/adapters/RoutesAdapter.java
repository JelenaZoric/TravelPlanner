package com.ftn.uns.travelplaner.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ftn.uns.travelplaner.R;
import com.ftn.uns.travelplaner.model.Route;
import com.ftn.uns.travelplaner.util.DateTimeFormatter;

import java.util.List;

public class RoutesAdapter extends ArrayAdapter<Route> {

    private final Context context;
    private final List<Route> data;

    public RoutesAdapter(Context context, List<Route> data) {
        super(context, -1, data);
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Route getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.route_list_item, parent, false);

        TextView nameView = rowView.findViewById(R.id.route_name);
        TextView dateView = rowView.findViewById(R.id.route_date);

        Route route = getItem(position);
        nameView.setText(route.name);
        dateView.setText(DateTimeFormatter.formatDate(route.date));

        return rowView;
    }
}
