package com.example.takahiro_tsuno.rssreader;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ContentAdapter extends ArrayAdapter<RssContent> {

    private final Context context;
    public List<RssContent> rssContentList;

    public ContentAdapter(Context context, List<RssContent> rssContentList){
        super(context, 0, rssContentList);
        this.context = context;
        this.rssContentList = rssContentList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RssContent content = getItem(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.main_content_row, parent, false);
        TextView titleView = (TextView) contentView.findViewById(R.id.main_content_row_title);
        titleView.setText(content.title);
        TextView descriptionView = (TextView) contentView.findViewById(R.id.main_content_row_description);
        descriptionView.setText(content.description);
        return contentView;
    }
}
