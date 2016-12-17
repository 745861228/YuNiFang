package com.bwei.like.yunifang.bean;

import java.io.Serializable;

/**
 * Created by LiKe on 2016/12/12.
 */
public class CartDbBean implements Serializable{

    private String id;
    private String goods_name;
    private String goods_img;
    private String number;
    private String show_price;
    private boolean isChecked;
    private int restrict_purchase_num;
    private int defaultGoodsNumber = 1;



    public CartDbBean() {
    }

    public CartDbBean(String goods_img, String goods_name, String id, String number, String show_price,int restrict_purchase_num) {
        this.goods_img = goods_img;
        this.goods_name = goods_name;
        this.id = id;
        this.number = number;
        this.show_price = show_price;
        this.restrict_purchase_num = restrict_purchase_num;
    }


    public int getDefaultGoodsNumber() {
        return defaultGoodsNumber;
    }

    public void setDefaultGoodsNumber(int defaultGoodsNumber) {
        this.defaultGoodsNumber = defaultGoodsNumber;
    }

    public int getRestrict_purchase_num() {
        return restrict_purchase_num;
    }

    public void setRestrict_purchase_num(int restrict_purchase_num) {
        this.restrict_purchase_num = restrict_purchase_num;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getGoods_img() {
        return goods_img;
    }

    public void setGoods_img(String goods_img) {
        this.goods_img = goods_img;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getShow_price() {
        return show_price;
    }

    public void setShow_price(String show_price) {
        this.show_price = show_price;
    }
}
