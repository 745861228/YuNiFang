package com.bwei.like.yunifang.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by LiKe on 2016/12/12.
 */
public class MySqliteOpenHelper extends SQLiteOpenHelper {
    public MySqliteOpenHelper(Context context   ) {
        super(context, "myDb", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //创建购物车表
        db.execSQL("create table cart (id integer primary key autoincrement,_id varchar(50),goods_name varchar(100),goods_img varchar(100),show_price varchar(50),number varchar(20),restrict_purchase_num int)");

        //创建收货地址表
        db.execSQL("create table address (id integer primary key autoincrement,userName varchar(50),userPhone varchar(50),userAddress varchar(100))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
