package com.bwei.like.yunifang.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bwei.like.yunifang.bean.CartDbBean;
import com.bwei.like.yunifang.bean.OrderStateBean;
import com.bwei.like.yunifang.db.MySqliteOpenHelper;
import com.bwei.like.yunifang.utils.LogUtils;

import java.util.ArrayList;

import static com.alipay.sdk.app.statistic.c.A;

/**
 * author by LiKe on 2016/12/26.
 */

public class MyOrderDao {

    private final MySqliteOpenHelper mySqliteOpenHelper;
    private SQLiteDatabase db;


    public MyOrderDao(Context context) {
        mySqliteOpenHelper = new MySqliteOpenHelper(context);
    }

    /**
     * 添加数据库
     */

    public void addMyOrder(ArrayList<CartDbBean> cartDbBeen, String state) {
        SQLiteDatabase db = mySqliteOpenHelper.getWritableDatabase();
        //获取当前系统时间
        long currentTimeMillis = System.currentTimeMillis();


        for (int i = 0; i < cartDbBeen.size(); i++) {
            ContentValues calues = new ContentValues();
            calues.put("_id", cartDbBeen.get(i).getId());
            calues.put("goods_name", cartDbBeen.get(i).getGoods_name());
            calues.put("goods_img", cartDbBeen.get(i).getGoods_img());
            calues.put("show_price", cartDbBeen.get(i).getShow_price());
            calues.put("number", cartDbBeen.get(i).getNumber());
            calues.put("state", state);
            calues.put("time", currentTimeMillis + "");
            db.insert("myOrder", null, calues);
        }


    }

    /**
     * 修改商品状态信息
     */

    public void updateGoodsState(String _id,String state) {
        SQLiteDatabase db = mySqliteOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("state", state);
        db.update("myOrder", values, "_id = ?", new String[]{_id});
    }

    /**
     * 查询全部
     */

    ArrayList<ArrayList<OrderStateBean>> orderStateList = new ArrayList<>();
    private String currentTime = "0";
    public ArrayList selectAll(String state) {
        SQLiteDatabase db = mySqliteOpenHelper.getReadableDatabase();
        Cursor cursor = null;
        if (state.equals("全部")) {
            cursor = db.query("myOrder", null, null, null, null, null, null);
        } else {
            cursor = db.query("myOrder", null, "state=?", new String[]{state}, null, null, null);
        }
        orderStateList.clear();
        Cursor myOrder;
        while (cursor.moveToNext()) {
            ArrayList<OrderStateBean> orderStateBeenList = new ArrayList<>();
            //获取当前状态下对应的时间
            String currentTimeMillis = cursor.getString(cursor.getColumnIndex("time"));

            if (currentTime.equals(currentTimeMillis)){
                continue;
            }
            currentTime = currentTimeMillis;
            //在根据时间查询同意状态下同一时间的商品
            myOrder = db.query("myOrder", null, "time=?", new String[]{currentTimeMillis}, null, null, null);
            while (myOrder.moveToNext()) {
                String _id = myOrder.getString(myOrder.getColumnIndex("_id"));
                String goods_name = myOrder.getString(myOrder.getColumnIndex("goods_name"));
                String goods_img = myOrder.getString(myOrder.getColumnIndex("goods_img"));
                String show_price = myOrder.getString(myOrder.getColumnIndex("show_price"));
                String stateType = myOrder.getString(myOrder.getColumnIndex("state"));
                String number = myOrder.getString(myOrder.getColumnIndex("number"));
                String time = myOrder.getString(myOrder.getColumnIndex("time"));
                orderStateBeenList.add(new OrderStateBean(goods_img, goods_name, _id, number, show_price, stateType, time));
            }
            orderStateList.add(orderStateBeenList);
        }
        db.close();
        return orderStateList;
    }
}
