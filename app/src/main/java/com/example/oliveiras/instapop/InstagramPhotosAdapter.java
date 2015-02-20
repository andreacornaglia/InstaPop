package com.example.oliveiras.instapop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;

public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {
    /*What is a constructor??*/
    //whatb date do we need from the activity?
    //This allows to map the data into the listview
    public InstagramPhotosAdapter(Context context, List<InstagramPhoto> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }
    //what our item looks like
    //use the template to display each photo

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the data item
        InstagramPhoto photo = getItem(position);
        //check if using recycling view, if not we need to inflate
        if (convertView == null) {
            //create a new view by template
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
        }
        //look up the views for populating the data (image, caption)
        TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
        TextView tvUsername = (TextView) convertView.findViewById(R.id.tvUsername);
        TextView tvLikes = (TextView) convertView.findViewById(R.id.tvLikes);
        ImageView ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);
        ImageView ivUserPhoto = (ImageView) convertView.findViewById(R.id.ivUserPhoto);
        //Insert the model data into each of the views
        tvCaption.setText(photo.caption);
        tvUsername.setText(photo.username);
        String likesCountF = NumberFormat.getInstance().format(photo.likesCount);
        tvLikes.setText(likesCountF+" Likes");
        //Clear out the imageView
        ivPhoto.setImageResource(0);
        // Insert the image using Picasso
        Picasso.with(getContext()).load(photo.imageURL).into(ivPhoto);
        Picasso.with(getContext()).load(photo.userURL).into(ivUserPhoto);
        //Return the created items as a view
        return convertView;
    }
}
