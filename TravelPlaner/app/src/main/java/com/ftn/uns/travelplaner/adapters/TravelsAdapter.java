package com.ftn.uns.travelplaner.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ftn.uns.travelplaner.R;
import com.ftn.uns.travelplaner.model.Travel;
import com.ftn.uns.travelplaner.util.DateTimeFormatter;

import java.util.List;

public class TravelsAdapter extends ArrayAdapter<Travel> {

    private final Context context;
    private final List<Travel> data;

    public TravelsAdapter(Context context, List<Travel> data) {
        super(context, -1, data);
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Travel getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.travels_list_item, parent, false);

        TextView destinationView = rowView.findViewById(R.id.travel_listitem_destination);
        TextView durationView = rowView.findViewById(R.id.travel_listitem_duration);

        Travel travel = getItem(position);
        destinationView.setText(travel.destination.location.toString());
        durationView.setText(DateTimeFormatter.formatDurationView(travel.origin, travel.destination));

        return rowView;
    }
}
