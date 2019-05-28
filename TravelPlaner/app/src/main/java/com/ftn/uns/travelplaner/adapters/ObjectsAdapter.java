package com.ftn.uns.travelplaner.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ftn.uns.travelplaner.R;
import com.ftn.uns.travelplaner.model.Object;

import java.util.List;
import java.util.Locale;

public class ObjectsAdapter extends ArrayAdapter<Object> {

    private final Context context;
    private final List<Object> data;

    public ObjectsAdapter(Context context, List<Object> data) {
        super(context, -1, data);
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.objects_list_item, parent, false);

        TextView nameView = rowView.findViewById(R.id.object_title);
        TextView addressView = rowView.findViewById(R.id.object_address);
        TextView ratingView = rowView.findViewById(R.id.object_rating);

        Object object = getItem(position);
        nameView.setText(object.name);
        addressView.setText(object.address);
        ratingView.setText(String.format(Locale.getDefault(), "%.1f", object.rating));

        return rowView;
    }
}
