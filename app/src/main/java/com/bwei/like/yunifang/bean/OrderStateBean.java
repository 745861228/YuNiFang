package com.bwei.like.yunifang.bean;

import static com.bwei.like.yunifang.R.id.restrict_purchase_num;

/**
 * author by LiKe on 2016/12/26.
 */

public class OrderStateBean {

    private String id;
    private String goods_name;
    private String goods_img;
    private String number;
    private String show_price;
    private String state;
    private String time;

    public OrderStateBean() {
    }

    public OrderStateBean(String goods_img, String goods_name, String id, String number, String show_price, String state, String time) {
        this.goods_img = goods_img;
        this.goods_name = goods_name;
        this.id = id;
        this.number = number;
        this.show_price = show_price;
        this.state = state;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
