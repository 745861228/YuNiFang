package com.bwei.like.yunifang.bean;

import java.util.Comparator;
import java.util.List;

/**
 * Created by LiKe on 2016/12/6.
 */
public class AllGoodsRoot {

    public int code;
    public String msg;
    public List<DataBean> data;

    public static class DataBean implements Comparable<DataBean>{
        public String efficacy;
        public String goods_img;
        public String goods_name;
        public String id;
        public boolean is_allow_credit;
        public boolean is_coupon_allowed;
        public double market_price;
        public int sales_volume;
        public double shop_price;
        public int sort;
        public String watermarkUrl;

        @Override
        public int compareTo(DataBean another) {
            if (this.shop_price>another.shop_price){
                return 1;
            }
            return -1;
        }
    }

}
