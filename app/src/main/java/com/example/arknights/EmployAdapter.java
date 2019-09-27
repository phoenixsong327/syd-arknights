package com.example.arknights;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.List;

public class EmployAdapter extends ArrayAdapter<employee> {

    private int resourseID;

    public EmployAdapter(Context context, int textViewResourceID, List<employee> objects){
        super(context,textViewResourceID,objects);
        resourseID=textViewResourceID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        employee ep=getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourseID,parent,false);
        TextView name=(TextView)view.findViewById(R.id.item1);
        TextView rarity=(TextView)view.findViewById(R.id.item2);
        ImageView location = (ImageView)view.findViewById(R.id.imageView3);
        name.setText(ep.Name);
        String lo = ep.getLocation();
        Uri uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://com.example.arknights/drawable/" + lo);
        location.setImageURI(uri);
        switch (ep.rarity)
        {
            case 6:
                rarity.setText("★★★★★★");
                view.setBackgroundColor(Color.rgb(255,153 ,18));
                break;
            case 5:
                rarity.setText("★★★★★");
                view.setBackgroundColor(Color.rgb(255,248 ,163));
                rarity.setTextColor(Color.BLACK);
                break;
            case 4:
                rarity.setText("★★★★");
                view.setBackgroundColor(Color.rgb(106,90 ,205));
                break;
            case 3:
                rarity.setText("★★★");
                view.setBackgroundColor(Color.rgb(220,220 ,220));
                break;
            default:
                break;
        }
        return view;
    }
}
