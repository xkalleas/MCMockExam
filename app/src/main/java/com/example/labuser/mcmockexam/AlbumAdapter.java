package com.example.labuser.mcmockexam;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AlbumAdapter extends ArrayAdapter<Album> {

    private Context mContext;

    public AlbumAdapter(@NonNull Context context,
                        int resource) {
        super(context, resource);

        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null) {
            convertView = LayoutInflater
                    .from(mContext)
                    .inflate(R.layout.list_item_album,
                            parent,
                            false);
        }

        // Get title element
        TextView titleTextView =
                (TextView) convertView.findViewById(R.id.album_list_title);

        // Get thumbnail element
        ImageView thumbnailImageView =
                (ImageView) convertView.findViewById(R.id.album_list_thumbnail);

        Album album = (Album) getItem(position);

        titleTextView.setText(album.getTitle());
        thumbnailImageView.setImageResource(R.drawable.ic_lighter);

        return convertView;
    }
}
