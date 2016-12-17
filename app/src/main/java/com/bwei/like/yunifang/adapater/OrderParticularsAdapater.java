package com.bwei.like.yunifang.adapater;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bwei.like.yunifang.R;
import com.bwei.like.yunifang.bean.CartDbBean;
import com.bwei.like.yunifang.interfaces.OnItemClickSumMoneyListener;
import com.bwei.like.yunifang.utils.CommonUtils;
import com.bwei.like.yunifang.utils.ImageLoaderUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by LiKe on 2016/12/17.
 */
public class OrderParticularsAdapater extends BaseAdapter {

    private Context context;
    private ArrayList<CartDbBean> cartDbBeanArrayList;
    private OnItemClickSumMoneyListener onItemClickSumMoneyListener;

    public OrderParticularsAdapater(ArrayList<CartDbBean> cartDbBeanArrayList, Context context) {
        this.cartDbBeanArrayList = cartDbBeanArrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return cartDbBeanArrayList.size();
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
        ViewHolder viewHolder;
        if (convertView == null){
            convertView = CommonUtils.inflate(R.layout.order_particulars_goods_lv_item);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ImageLoader.getInstance().displayImage(cartDbBeanArrayList.get(position).getGoods_img(),viewHolder.goodsimage, ImageLoaderUtils.initOptionsCircle());
        viewHolder.goodsname.setText(cartDbBeanArrayList.get(position).getGoods_name());
        viewHolder.showprice.setText("￥"+cartDbBeanArrayList.get(position).getShow_price());
        viewHolder.shownumber.setText(cartDbBeanArrayList.get(position).getNumber());

        if (Integer.parseInt(cartDbBeanArrayList.get(position).getNumber()) == 1){
            viewHolder.reducenumber.setTextColor(Color.GRAY);
        }

        updateNumber(viewHolder.reducenumber,viewHolder.shownumber,viewHolder.addnumber,position);

        return convertView;
    }

    private void updateNumber(final Button reducenumber, final Button shownumber, final Button addnumber, final int position) {
        //减的操作
        reducenumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int goodsNumber = Integer.parseInt(cartDbBeanArrayList.get(position).getNumber());
                if (goodsNumber >1){
                    goodsNumber--;
                    shownumber.setText(goodsNumber+"");
                    cartDbBeanArrayList.get(position).setNumber(goodsNumber+"");
                    setSumMoney();
                }

                if (Integer.parseInt(cartDbBeanArrayList.get(position).getNumber()) == 1){
                    reducenumber.setTextColor(Color.GRAY);
                    Toast.makeText(context, "亲不可以在点击了！", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //加的操作
        addnumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int goodsNumber = Integer.parseInt(cartDbBeanArrayList.get(position).getNumber());
                if (goodsNumber < cartDbBeanArrayList.get(position).getRestrict_purchase_num()){
                    goodsNumber++;
                    shownumber.setText(goodsNumber+"");
                    cartDbBeanArrayList.get(position).setNumber(goodsNumber+"");
                    reducenumber.setTextColor(Color.BLACK);
                    setSumMoney();
                }

                if (Integer.parseInt(cartDbBeanArrayList.get(position).getNumber()) == cartDbBeanArrayList.get(position).getRestrict_purchase_num()){
                    addnumber.setTextColor(Color.GRAY);
                    Toast.makeText(context, "亲不可以在点击了！", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public class ViewHolder {
        public final ImageView goodsimage;
        public final TextView goodsname;
        public final TextView showprice;
        public final Button reducenumber;
        public final Button shownumber;
        public final Button addnumber;
        public final View root;

        public ViewHolder(View root) {
            goodsimage = (ImageView) root.findViewById(R.id.goods_image);
            goodsname = (TextView) root.findViewById(R.id.goods_name);
            showprice = (TextView) root.findViewById(R.id.show_price);
            reducenumber = (Button) root.findViewById(R.id.reduce_number);
            shownumber = (Button) root.findViewById(R.id.show_number);
            addnumber = (Button) root.findViewById(R.id.add_number);
            this.root = root;
        }
    }

    public void setOnItemClickListener(OnItemClickSumMoneyListener onItemClickSumMoneyListener){
        this.onItemClickSumMoneyListener = onItemClickSumMoneyListener;
    }

    public void setSumMoney(){
        double sumMoney = 0;
        int sumNumber = 0;
        for (int i = 0; i < cartDbBeanArrayList.size(); i++) {
            sumMoney += Double.parseDouble(cartDbBeanArrayList.get(i).getShow_price())*Integer.parseInt(cartDbBeanArrayList.get(i).getNumber());
            sumNumber+=Integer.parseInt(cartDbBeanArrayList.get(i).getNumber());
        }
        onItemClickSumMoneyListener.onItemClick(sumMoney,sumNumber);
    }
}
