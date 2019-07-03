package com.ftn.uns.travelplaner.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.ftn.uns.travelplaner.R;
import com.ftn.uns.travelplaner.model.Item;

import java.util.List;

public class TravelItemsAdapter extends ArrayAdapter<Item> {

    private final Context context;
    private final List<Item> data;

    public TravelItemsAdapter(Context context, List<Item> data) {
        super(context, -1, data);
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Item getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.travel_items_list_item, parent, false);

        TextView itemNameView = rowView.findViewById(R.id.item_name);
        CheckBox itemBroughtView = rowView.findViewById(R.id.item_brought);

        Item item = getItem(position);
        itemNameView.setText(item.name);
        itemBroughtView.setChecked(item.brought);

        return rowView;
    }
}
