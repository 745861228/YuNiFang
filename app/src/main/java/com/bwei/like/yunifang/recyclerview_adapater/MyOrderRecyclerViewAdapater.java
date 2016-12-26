package com.bwei.like.yunifang.recyclerview_adapater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bwei.like.yunifang.R;
import com.bwei.like.yunifang.bean.OrderStateBean;
import com.bwei.like.yunifang.holder.MyOrderRecyclerViewHolder;
import com.bwei.like.yunifang.utils.CommonUtils;
import com.bwei.like.yunifang.utils.LogUtils;

import java.util.ArrayList;

/**
 * author by LiKe on 2016/12/26.
 */

public class MyOrderRecyclerViewAdapater extends RecyclerView.Adapter<MyOrderRecyclerViewHolder>{

    private Context context;
    private ArrayList<ArrayList<OrderStateBean>> arrayLists;

    public MyOrderRecyclerViewAdapater(ArrayList<ArrayList<OrderStateBean>> arrayLists, Context context) {
        this.arrayLists = arrayLists;
        this.context = context;
    }

    @Override
    public MyOrderRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = CommonUtils.inflate(R.layout.myorderfragment_recyclerview_item);
        MyOrderRecyclerViewHolder myOrderRecyclerViewHolder = new MyOrderRecyclerViewHolder(inflate);
        return myOrderRecyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(MyOrderRecyclerViewHolder holder, int position) {
        holder.setData(context,arrayLists.get(position));
    }

    @Override
    public int getItemCount() {
        return arrayLists.size();
    }
}
