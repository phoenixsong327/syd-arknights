package com.example.arknights;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class RecordDataBaseHelper extends SQLiteOpenHelper {
    private Context context;//上下文

    //数据库中创建一张Record表
    public static final String Note = "create table Record ("
            + "name text," + "rarity text,"+"location,text)";

    //2.实现构造方法
    public RecordDataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        //int version-当前数据库的版本号，可用于对数据库进行升级操作
        super(context, name, factory, version);
        this.context = context;

    }


    //3.重写onCreate方法
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Note);//执行建表语句，创建数据库
    }

    //4.重写onUpgrade方法
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
