package com.bwei.like.yunifang.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bwei.like.yunifang.bean.CartDbBean;
import com.bwei.like.yunifang.bean.ParticularsRoot;
import com.bwei.like.yunifang.db.MySqliteOpenHelper;

import java.util.ArrayList;

/**
 * Created by LiKe on 2016/12/12.
 */
public class CartDao {

    private final MySqliteOpenHelper mySqliteOpenHelper;

    public CartDao(Context context) {
        mySqliteOpenHelper = new MySqliteOpenHelper(context);
    }

    //创建添加的方法
    public void addGoods(ParticularsRoot.DataBean.GoodsBean goodsBean, int number) {
        SQLiteDatabase db = mySqliteOpenHelper.getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from cart where goods_name=?",new String[]{goodsBean.goods_name});
//        Cursor cursor = db.query("cart", new String[]{"goods_name"}, "goods_name=?", new String[]{goodsBean.goods_name}, null, null, null);
        if (cursor.moveToNext()){
            String oldNumber = cursor.getString(cursor.getColumnIndex("number"));
            int newNumber = Integer.parseInt(oldNumber) + number;
            updateNumber(goodsBean.goods_name,newNumber+"");
            return;
        }

        db.beginTransaction();//开启事物
        try {
            ContentValues values = new ContentValues();
            values.put("_id", goodsBean.id);
            values.put("goods_name", goodsBean.goods_name);
            values.put("goods_img", goodsBean.goods_img);
            values.put("show_price", goodsBean.shop_price);
            values.put("number", number + "");
            db.insert("cart", null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction(); // 结束事务
            db.close();
        }
    }

    ArrayList<CartDbBean> arrayList = new ArrayList<>();
    public ArrayList<CartDbBean> selectGoods(){
        SQLiteDatabase db = mySqliteOpenHelper.getReadableDatabase();
      //  Cursor cursor = db.query("cart", new String[]{"goods_name"}, "goods_name=?", new String[]{goods_name}, null, null, null);
        Cursor cursor = db.query("cart", null, null, null, null, null, null);
        arrayList.clear();
        while(cursor.moveToNext()){
            String id = cursor.getString(cursor.getColumnIndex("_id"));
            String name = cursor.getString(cursor.getColumnIndex("goods_name"));
            String goods_img = cursor.getString(cursor.getColumnIndex("goods_img"));
            String number = cursor.getString(cursor.getColumnIndex("number"));
            String show_price = cursor.getString(cursor.getColumnIndex("show_price"));
            arrayList.add(new CartDbBean(goods_img,name,id,number,show_price));
        }
        return arrayList;
    }


    /**
     * 删除数据
     */
    public void deleteGoods(String goods_name){
        SQLiteDatabase db = mySqliteOpenHelper.getReadableDatabase();
        db.delete("cart","goods_name=?",new String[]{goods_name});
        db.close();
    }

    /**
     * 修改商品数量
     */
    public void updateNumber(String goods_name,String number){
        SQLiteDatabase db = mySqliteOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("number",number);
        db.update("cart",values,"goods_name=?",new String[]{goods_name});
    }
}
