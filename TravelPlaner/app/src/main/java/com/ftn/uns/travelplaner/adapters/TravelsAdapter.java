package com.ftn.uns.travelplaner.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ftn.uns.travelplaner.R;
import com.ftn.uns.travelplaner.model.Transportation;
import com.ftn.uns.travelplaner.model.Travel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

public class TravelsAdapter extends ArrayAdapter<Travel> {

    private static final String DATE_PATTERN = "%d %s %d";
    private static final String DURATION_PATTERN = "%s -> %s";
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
        durationView.setText(formatDurationView(travel.origin, travel.destination));

        return rowView;
    }

    private String formatDurationView(Transportation origin, Transportation destination) {
        LocalDateTime originDepartureTime = origin.departure;
        LocalDateTime destinationDepartureTime = destination.departure;

        String startDate = formatDate(LocalDate.from(originDepartureTime));
        String endDate = formatDate(LocalDate.from(destinationDepartureTime));

        return String.format(Locale.getDefault(), DURATION_PATTERN, startDate, endDate);
    }

    private String formatDate(LocalDate date) {
        return String.format(
                Locale.getDefault(),
                DATE_PATTERN,
                date.getDayOfMonth(),
                date.getMonth().name(),
                date.getYear());
    }
}
