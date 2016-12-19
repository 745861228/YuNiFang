package com.bwei.like.yunifang.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bwei.like.yunifang.bean.AddressBean;
import com.bwei.like.yunifang.db.MySqliteOpenHelper;

import java.util.ArrayList;

/**
 * Created by LiKe on 2016/12/18.
 */
public class AddressDao {

    private final MySqliteOpenHelper mySqliteOpenHelper;

    public AddressDao(Context context) {
        mySqliteOpenHelper = new MySqliteOpenHelper(context);
    }


    /**
     * 添加收货地址的方法
     */

    public void addAddress(AddressBean addressBean){
        SQLiteDatabase db = mySqliteOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("userName",addressBean.getUserName());
        values.put("userPhone",addressBean.getUserPhone());
        values.put("userAddress",addressBean.getUserAddress());
        db.insert("address",null,values);
        db.close();
    }


    /**
     * 根据收货人姓名查询id
     */

    ArrayList<AddressBean> addressBeanArrayList = new ArrayList<>();
    public ArrayList<AddressBean> selectId(AddressBean addressBean){
        SQLiteDatabase db = mySqliteOpenHelper.getReadableDatabase();
        Cursor cursor = db.query("address", new String[]{"id"}, "userName=?", new String[]{addressBean.getUserName()}, null, null, null);
        addressBeanArrayList.clear();
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String userName = cursor.getString(cursor.getColumnIndex("userName"));
            String userAddress = cursor.getString(cursor.getColumnIndex("userAddress"));
            String userPhone = cursor.getString(cursor.getColumnIndex("userPhone"));
            addressBeanArrayList.add(new AddressBean(id,userAddress,userName,userPhone));
        }
        return addressBeanArrayList;
    }


    /**
     * 查询全部
     * @param
     * @return
     */
    public ArrayList<AddressBean> selectAll(){
        SQLiteDatabase db = mySqliteOpenHelper.getReadableDatabase();
        Cursor cursor = db.query("address", null, null, null, null, null, null);
        addressBeanArrayList.clear();
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String userName = cursor.getString(cursor.getColumnIndex("userName"));
            String userAddress = cursor.getString(cursor.getColumnIndex("userAddress"));
            String userPhone = cursor.getString(cursor.getColumnIndex("userPhone"));
            addressBeanArrayList.add(new AddressBean(id,userAddress,userName,userPhone));
        }
        return addressBeanArrayList;
    }

    /**
     * 根据id查询出对应的用户
     */

    public ArrayList<AddressBean> selectIdUser(String _id){
        SQLiteDatabase db = mySqliteOpenHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from address where id=?",new String[]{_id});
        addressBeanArrayList.clear();
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String userName = cursor.getString(cursor.getColumnIndex("userName"));
            String userAddress = cursor.getString(cursor.getColumnIndex("userAddress"));
            String userPhone = cursor.getString(cursor.getColumnIndex("userPhone"));
            addressBeanArrayList.add(new AddressBean(id,userAddress,userName,userPhone));
        }
        return addressBeanArrayList;
    }

}
