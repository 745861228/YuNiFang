package com.bwei.like.yunifang.holder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bwei.like.yunifang.R;
import com.bwei.like.yunifang.bean.OrderStateBean;
import com.bwei.like.yunifang.utils.CommonUtils;
import com.bwei.like.yunifang.utils.ImageLoaderUtils;
import com.bwei.like.yunifang.view.Home_ListView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * author by LiKe on 2016/12/26.
 */

public class MyOrderRecyclerViewHolder extends BaseHolder<ArrayList<OrderStateBean>>{

    private final TextView orderState_tv;
    private final TextView sumNumber_sumMoney_tv;
    private final Button cancelOrder_but;
    private final Button payOrder_but;
    private Home_ListView listView;


    public MyOrderRecyclerViewHolder(View itemView) {
        super(itemView);
        orderState_tv = (TextView) itemView.findViewById(R.id.orderState_tv);
        listView = (Home_ListView) itemView.findViewById(R.id.myOrderFragment_recyclerView_lv);
        sumNumber_sumMoney_tv = (TextView) itemView.findViewById(R.id.sumNumber_sumMoney_tv);
        cancelOrder_but = (Button) itemView.findViewById(R.id.cancelOrder_but);
        payOrder_but = (Button) itemView.findViewById(R.id.payOrder_but);
    }

    @Override
    public void setData(Context context, final ArrayList<OrderStateBean> orderStateBeen) {
        orderState_tv.setText(orderStateBeen.get(0).getState());
        float sumMoney = 0;
        for (int i = 0; i < orderStateBeen.size(); i++) {
            sumMoney+=Float.parseFloat(orderStateBeen.get(i).getShow_price());
        }
        sumNumber_sumMoney_tv.setText("共"+orderStateBeen.size()+"件商品  合计 :￥"+sumMoney);
        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return orderStateBeen.size();
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
            public View getView(int position, View convertView, ViewGroup parent) {
                View inflate = CommonUtils.inflate(R.layout.myorderrecyclerview_item_lv_item);
                ImageView lv_iv_show = (ImageView) inflate.findViewById(R.id.lv_iv_show);
                TextView lv_tv_name = (TextView) inflate.findViewById(R.id.lv_tv_name);
                TextView lv_tv_price = (TextView) inflate.findViewById(R.id.lv_tv_price);
                TextView lv_tv_num = (TextView) inflate.findViewById(R.id.lv_tv_num);

                lv_tv_name.setText(orderStateBeen.get(position).getGoods_name());
                lv_tv_price.setText("￥:"+orderStateBeen.get(position).getShow_price()+"");
                lv_tv_num.setText("数量:"+orderStateBeen.get(position).getNumber()+"");
                ImageLoader.getInstance().displayImage(orderStateBeen.get(position).getGoods_img(),lv_iv_show, ImageLoaderUtils.initOptionsCircle());
                return inflate;
            }
        });
    }


}
