package com.ftn.uns.travelplaner.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ftn.uns.travelplaner.R;
import com.ftn.uns.travelplaner.model.Comment;

import java.util.List;

public class CommentsAdapter extends ArrayAdapter<Comment> {

    private final Context context;
    private final List<Comment> data;

    public CommentsAdapter(Context context, List<Comment> data) {
        super(context, -1, data);
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Comment getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.comments_list_item, parent, false);

        TextView userView = rowView.findViewById(R.id.comment_user);
        TextView descriptionView = rowView.findViewById(R.id.comment_content);
        TextView ratingView = rowView.findViewById(R.id.comment_rating);

        Comment comment = getItem(position);

        userView.setText(String.format("%s %s", comment.user.firstName, comment.user.lastName));
        descriptionView.setText(comment.text);
        ratingView.setText(String.valueOf(comment.rating));

        return rowView;
    }

}
