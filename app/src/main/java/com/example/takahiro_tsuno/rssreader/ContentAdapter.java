package com.example.takahiro_tsuno.rssreader;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.takahiro_tsuno.rssreader.RssFeed.Item;

import java.util.List;

public class ContentAdapter extends ArrayAdapter<Item> {

    private final Context context;
    public List<Item> rssContentList;

    public ContentAdapter(Context context){
        super(context, 0);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Item content = getItem(position);
        View contentView = convertView;
        if(contentView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            contentView = inflater.inflate(R.layout.adapter_content, parent, false);
        }

        TextView titleView = (TextView) contentView.findViewById(R.id.main_content_row_title);
        titleView.setText(content.title);
        TextView descriptionView = (TextView) contentView.findViewById(R.id.main_content_row_description);
        descriptionView.setText(content.description);
        return contentView;
    }
}
