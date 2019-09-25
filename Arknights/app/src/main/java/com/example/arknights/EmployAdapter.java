package com.example.arknights;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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
        name.setText(ep.Name);
        switch (ep.rarity)
        {
            case 6:
                rarity.setText("☆☆☆☆☆☆");
                name.setTextColor(Color.rgb(255,153 ,18));
                rarity.setTextColor(Color.rgb(255,153 ,18));
                break;
            case 5:
                rarity.setText("☆☆☆☆☆");
                name.setTextColor(Color.rgb(255,215 ,0));
                rarity.setTextColor(Color.rgb(255,215 ,0));
                break;
            case 4:
                rarity.setText("☆☆☆☆");
                name.setTextColor(Color.rgb(106,90 ,205));
                rarity.setTextColor(Color.rgb(106,90 ,205));
                break;
            case 3:
                rarity.setText("☆☆☆");
                name.setTextColor(Color.rgb(220,220 ,220));
                rarity.setTextColor(Color.rgb(220,220 ,220));
                break;
            default:
                break;
        }
        return view;
    }
}
