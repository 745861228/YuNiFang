package com.bwei.like.yunifang.adapater;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwei.like.yunifang.R;
import com.bwei.like.yunifang.bean.CartDbBean;
import com.bwei.like.yunifang.utils.CommonUtils;
import com.bwei.like.yunifang.utils.ImageLoaderUtils;
import com.bwei.like.yunifang.utils.LogUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;

/**
 * Created by LiKe on 2016/12/13.
 */
public class CartlvAdapater extends BaseAdapter implements View.OnClickListener {

    private Context context;
    private ArrayList<CartDbBean> cartDbBeenList;
    private CheckBox all_select_cb;
    private TextView cart_sumMoney_tv;
    private Button pay_button;
    private TextView include_right_tv;
    private int tag = 0;
    private float sumMoney = 0;
    private boolean isFlag = true;
    private AutoLinearLayout cartlvitemlinearLayout;


    public CartlvAdapater(CheckBox all_select_cb, TextView cart_sumMoney_tv, ArrayList<CartDbBean> cartDbBeenList, Context context, TextView include_right_tv, Button pay_button) {
        this.all_select_cb = all_select_cb;
        this.cart_sumMoney_tv = cart_sumMoney_tv;
        this.cartDbBeenList = cartDbBeenList;
        this.context = context;
        this.include_right_tv = include_right_tv;
        this.pay_button = pay_button;
        all_select_cb.setOnClickListener(this);
        include_right_tv.setOnClickListener(this);
    }

    @Override
    public int getCount() {
        return cartDbBeenList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = CommonUtils.inflate(R.layout.fragment_car_lv_item);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ImageLoader.getInstance().displayImage(cartDbBeenList.get(position).getGoods_img(), viewHolder.cartlvitemimg, ImageLoaderUtils.initOptionsCircle());
        viewHolder.cartlvitemgoodsname.setText(cartDbBeenList.get(position).getGoods_name());
        viewHolder.cartlvitemshowprice.setText("￥ " + cartDbBeenList.get(position).getShow_price());
        viewHolder.cartlvitemgoodsnum.setText("数量:" + cartDbBeenList.get(position).getNumber());
        viewHolder.cartallcheckitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewHolder.cartallcheckitem.isChecked()) {
                    cartDbBeenList.get(position).setChecked(true);
                    sumMoney += (Float.parseFloat(cartDbBeenList.get(position).getShow_price()))*Integer.parseInt(cartDbBeenList.get(position).getNumber());
                    tag++;
                } else {
                    cartDbBeenList.get(position).setChecked(false);
                    tag--;
                    sumMoney -= (Float.parseFloat(cartDbBeenList.get(position).getShow_price()))*Integer.parseInt(cartDbBeenList.get(position).getNumber());
                }

                if (tag == cartDbBeenList.size()) {
                    all_select_cb.setText("取消全选");
                    all_select_cb.setChecked(true);
                } else {
                    all_select_cb.setText("全选");
                    all_select_cb.setChecked(false);
                }
                LogUtils.i("tag......", tag + "");
                cart_sumMoney_tv.setText("总计:￥" + sumMoney);
            }
        });
        viewHolder.cartallcheckitem.setChecked(cartDbBeenList.get(position).isChecked());
        if (isFlag) {
            viewHolder.cartlvitemlinearLayout.setVisibility(View.GONE);
        } else {
            viewHolder.cartlvitemlinearLayout.setVisibility(View.VISIBLE);
        }

        return convertView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //全选按钮
            case R.id.all_select_cb:
                if (all_select_cb.isChecked()) {
                    for (int i = 0; i < cartDbBeenList.size(); i++) {
                        cartDbBeenList.get(i).setChecked(true);
                        sumMoney += (Float.parseFloat(cartDbBeenList.get(i).getShow_price()))*Integer.parseInt(cartDbBeenList.get(i).getNumber());
                    }
                    tag = cartDbBeenList.size();
                    all_select_cb.setText("取消全选");
                } else {
                    for (int i = 0; i < cartDbBeenList.size(); i++) {
                        cartDbBeenList.get(i).setChecked(false);
                        sumMoney -= (Float.parseFloat(cartDbBeenList.get(i).getShow_price()))*Integer.parseInt(cartDbBeenList.get(i).getNumber());
                    }
                    tag = 0;
                    sumMoney = 0;
                    all_select_cb.setText("全选");
                }
                cart_sumMoney_tv.setText("总计:￥"+sumMoney);
                break;

            //编辑的操作
            case R.id.include_right_tv:
                if (isFlag) {
                    include_right_tv.setText("完成");
                    cart_sumMoney_tv.setVisibility(View.GONE);
                    pay_button.setText("删除");
                } else {
                    include_right_tv.setText("编辑");
                    cart_sumMoney_tv.setVisibility(View.VISIBLE);
                    pay_button.setText("结算");
                }
                isFlag = !isFlag;
                break;
        }
        notifyDataSetChanged();

    }

    public class ViewHolder {
        public final CheckBox cartallcheckitem;
        public final ImageView cartlvitemimg;
        public final TextView cartlvitemgoodsname;
        public final TextView cartlvitemshowprice;
        public final TextView cartlvitemgoodsnum;
        public final Button reducenumber;
        public final Button shownumber;
        public final Button addnumber;
        public AutoLinearLayout cartlvitemlinearLayout;
        public final View root;

        public ViewHolder(View root) {
            cartallcheckitem = (CheckBox) root.findViewById(R.id.cart_all_check_item);
            cartlvitemimg = (ImageView) root.findViewById(R.id.cart_lv_item_img);
            cartlvitemgoodsname = (TextView) root.findViewById(R.id.cart_lv_item_goods_name);
            cartlvitemshowprice = (TextView) root.findViewById(R.id.cart_lv_item_show_price);
            cartlvitemgoodsnum = (TextView) root.findViewById(R.id.cart_lv_item_goods_num);
            reducenumber = (Button) root.findViewById(R.id.reduce_number);
            shownumber = (Button) root.findViewById(R.id.show_number);
            addnumber = (Button) root.findViewById(R.id.add_number);
            cartlvitemlinearLayout = (AutoLinearLayout) root.findViewById(R.id.cart_lv_item_linearLayout);
            this.root = root;
        }
    }
}
