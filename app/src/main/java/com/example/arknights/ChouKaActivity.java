package com.example.arknights;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.SeekBar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ChouKaActivity extends AppCompatActivity {

    //private static SQLiteDatabase database;
    public static final String DATABASE_FILENAME = "employ.db"; // 这个是DB文件名字
    public static final String PACKAGE_NAME = "com.example.arknights"; // 这个是自己项目包路径
    public static final String DATABASE_PATH = "/data"
            + Environment.getDataDirectory().getAbsolutePath() + "/"
            + PACKAGE_NAME; // 获取存储位置地址
    private List<employee> ans=new ArrayList<employee>() ;
    private ImageView bg;
    private SeekBar sb ;
    private SQLiteDatabase db;
    private int num;
    private int maxRarity=3;
    private RecordDataBaseHelper helper;
    private SQLiteDatabase dbR;
    private boolean isIntented=false;

    public static SQLiteDatabase openDatabase(Context context) {
        SQLiteDatabase database;
        try {
            String databaseFilename = DATABASE_PATH + "/" + DATABASE_FILENAME;
            File dir = new File(DATABASE_PATH);
            if (!dir.exists()) {
                dir.mkdir();
            }
            if (!(new File(databaseFilename)).exists()) {
                InputStream is = context.getResources().openRawResource(R.raw.employ);
                FileOutputStream fos = new FileOutputStream(databaseFilename);
                byte[] buffer = new byte[8192];
                int count = 0;
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }

                fos.close();
                is.close();
            }
            database = SQLiteDatabase.openOrCreateDatabase(
                    databaseFilename, null);
            return database;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chou_ka);
        init();
        for(int i=0;i<num;i++){
            double r=0;
            if((int)SPUtils.get(this,"Counts",0)-(int)SPUtils.get(this,"LastSix",0)>=50){
                r = ((int)SPUtils.get(this,"Counts",0)-(int)SPUtils.get(this,"LastSix",0)+1-50)*0.02;
            }
            calAns(0.02+r,0.08,0.5,0.4);
        }
        for (employee e :ans) {
            if(e.getRarity()>maxRarity)
                maxRarity=e.getRarity();
        }
        switch (maxRarity){
            case 6:
                bg.setBackgroundColor(Color.rgb(255,153 ,18));
                break;
            case 5:
                bg.setBackgroundColor(Color.rgb(255,248 ,163));
                break;
            case 4:
                bg.setBackgroundColor(Color.rgb(106,90 ,205));
                break;
            case 3:
                bg.setBackgroundColor(Color.rgb(220,220 ,220));
                break;
                default:
                    break;
        }
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                bg.setAlpha(progress);
                if (progress<20&&!isIntented) {
                    Intent in=new Intent(ChouKaActivity.this, listEmployee.class);
                    in.putExtra("ANS",(Serializable)ans);
                    startActivity(in);
                    isIntented=true;
                    ChouKaActivity.this.finish();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void init(){
        Intent in = getIntent();
        num=in.getIntExtra("Number",1);
        db = openDatabase(this);
        sb=findViewById(R.id.seekBar2);
        helper = new RecordDataBaseHelper(this,"Record",null,1);
        dbR = helper.getWritableDatabase();
        bg = (ImageView)findViewById(R.id.chouKaBg);
    }

    private void calAns(double r6,double r5,double r4,double r3){
        SPUtils.put(this,"Counts",(int)SPUtils.get(this,"Counts",0)+1);
        Cursor c6 = db.rawQuery("select * from employ where rarity == 6", null);
        Cursor c5 = db.rawQuery("select * from employ where rarity == 5", null);
        Cursor c4 = db.rawQuery("select * from employ where rarity == 4", null);
        Cursor c3 = db.rawQuery("select * from employ where rarity == 3", null);
        String name="";
        int rarity;
        String location;
        double m = Math.random();
        if((int)SPUtils.get(this,"Counts",0)==10&&
                (int)SPUtils.get(this,"LastSix",0)==0&&
                (int)SPUtils.get(this,"FiveCounts",0)==0){
            c5.moveToFirst();
            for(int i=0;i<c5.getCount();i++)
            {
                if(m<=(double)(i+1)/c5.getCount())
                {
                    name=c5.getString(c5.getColumnIndex("employee"));
                    rarity=c5.getInt(c5.getColumnIndex("rarity"));
                    location=c5.getString(c5.getColumnIndex("location"));
                    ans.add(new employee(name,rarity,location));
                    ContentValues values = new ContentValues();
                    values.put("name",name);
                    values.put("rarity",rarity);
                    values.put("location",location);
                    dbR.insert("Record",null,values);
                    SPUtils.put(this,"FiveCounts",(int)SPUtils.get(this,"FiveCounts",0)+1);
                    break;
                }
                else
                    c5.moveToNext();
            }
        }
        else if(m<=r6)
        {
            c6.moveToFirst();
            for(int i=0;i<c6.getCount();i++)
            {
                if(m<=(double)(i+1)/c6.getCount()*r6)
                {
                    name=c6.getString(c6.getColumnIndex("employee"));
                    rarity=c6.getInt(c6.getColumnIndex("rarity"));
                    location=c6.getString(c5.getColumnIndex("location"));
                    ans.add(new employee(name,rarity,location));
                    ContentValues values = new ContentValues();
                    values.put("name",name);
                    values.put("rarity",rarity);
                    values.put("location",location);
                    dbR.insert("Record",null,values);
                    SPUtils.put(this,"LastSix",(int)SPUtils.get(this,"Counts",0));
                    break;
                }
                else
                    c6.moveToNext();
            }
        }
        else if(m<=(r6+r5))
        {
            c5.moveToFirst();
            for(int i=0;i<c5.getCount();i++)
            {
                if(m<=(r6+(double)(i+1)/c5.getCount()*r5))
                {
                    name=c5.getString(c5.getColumnIndex("employee"));
                    rarity=c5.getInt(c5.getColumnIndex("rarity"));
                    location=c5.getString(c5.getColumnIndex("location"));
                    ans.add(new employee(name,rarity,location));
                    ContentValues values = new ContentValues();
                    values.put("name",name);
                    values.put("rarity",rarity);
                    values.put("location",location);
                    dbR.insert("Record",null,values);
                    SPUtils.put(this,"FiveCounts",(int)SPUtils.get(this,"FiveCounts",0)+1);
                    break;
                }
                else
                    c5.moveToNext();
            }
        }
        else if(m<=(r6+r5+r4))
        {
            c4.moveToFirst();
            for(int i=0;i<c4.getCount();i++)
            {
                if(m<=(r6+r5+(double)(i+1)/c4.getCount()*r4))
                {
                    name=c4.getString(c4.getColumnIndex("employee"));
                    rarity=c4.getInt(c4.getColumnIndex("rarity"));
                    location=c4.getString(c5.getColumnIndex("location"));
                    ans.add(new employee(name,rarity,location));
                    ContentValues values = new ContentValues();
                    values.put("name",name);
                    values.put("rarity",rarity);
                    values.put("location",location);
                    dbR.insert("Record",null,values);
                    break;
                }
                else
                    c4.moveToNext();
            }
        }
        else {
            c3.moveToFirst();
            for (int i = 0; i < c3.getCount(); i++) {
                if (m <= ( r6 +  r5 + r4 + (double)(i + 1)/c3.getCount() * r3) )
                {
                    name=c3.getString(c3.getColumnIndex("employee"));
                    rarity=c3.getInt(c3.getColumnIndex("rarity"));
                    location=c3.getString(c5.getColumnIndex("location"));
                    ans.add(new employee(name,rarity,location));
                    ContentValues values = new ContentValues();
                    values.put("name",name);
                    values.put("rarity",rarity);
                    values.put("location",location);
                    dbR.insert("Record",null,values);
                    break;
                }
                else
                    c3.moveToNext();
            }
        }
    }
}


