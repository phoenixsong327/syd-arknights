package com.example.arknights;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView counts;
    private TextView rarity;
    private Button clear;
    private Button record;
    private RecordDataBaseHelper helper;
    private SQLiteDatabase db;
    private List<employee> employees = new ArrayList<employee>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        Button button1 = (Button)findViewById(R.id.button);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this,ChouKaActivity.class);
                in.putExtra("Number",1);
                startActivity(in);
            }
        });
        Button button_ten = (Button)findViewById(R.id.button_ten);
        button_ten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this,ChouKaActivity.class);
                in.putExtra("Number",10);
                startActivity(in);
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SPUtils.clear(MainActivity.this);
                db.delete("Record",null,null);
                onResume();
            }
        });
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, listEmployee.class);
                in.putExtra("ANS",(Serializable) employees);
                startActivity(in);
            }
        });
    }

    private void init(){
        helper = new RecordDataBaseHelper(this,"Record",null,1);
        db=helper.getWritableDatabase();
        clear = (Button)findViewById(R.id.clear);
        counts=(TextView)findViewById(R.id.countsText);
        rarity=(TextView)findViewById(R.id.SixRarityText);
        record=(Button)findViewById(R.id.viewRecord);
        if(!SPUtils.contains(this,"Counts"))
            SPUtils.put(this,"Counts",0);
        counts.setText(SPUtils.get(this,"Counts",0).toString());
        if((int)SPUtils.get(this,"Counts",0)-(int)SPUtils.get(this,"LastSix",0)<50)
            rarity.setText("2%");
        else{
            int r = ((int)SPUtils.get(this,"Counts",0)-(int)SPUtils.get(this,"LastSix",0)+1-50)*2+2;
            rarity.setText(""+r+"%");
        }
        Cursor c = db.query("Record",new String[]{"name","rarity","location"},
                null,null,null,null,null);
        c.moveToFirst();
        while(!c.isAfterLast()){
            employees.add(new employee(c.getString(c.getColumnIndex("name")),c.getInt(c.getColumnIndex("rarity")),c.getString(c.getColumnIndex("location"))));
            c.moveToNext();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if(!SPUtils.contains(this,"Counts"))
            SPUtils.put(this,"Counts",0);
        counts.setText(SPUtils.get(this,"Counts",0).toString());
        if((int)SPUtils.get(this,"Counts",0)-(int)SPUtils.get(this,"LastSix",0)<50)
            rarity.setText("2%");
        else{
            int r = ((int)SPUtils.get(this,"Counts",0)-(int)SPUtils.get(this,"LastSix",0)+1-50)*2+2;
            rarity.setText(""+r+"%");
        }
        Cursor c = db.query("Record",new String[]{"name","rarity","location"},
                null,null,null,null,null);
        c.moveToFirst();
        employees.clear();
        while(!c.isAfterLast()){
            employees.add(new employee(c.getString(c.getColumnIndex("name")),c.getInt(c.getColumnIndex("rarity")),c.getString(c.getColumnIndex("location"))));
            c.moveToNext();
        }
    }
}
