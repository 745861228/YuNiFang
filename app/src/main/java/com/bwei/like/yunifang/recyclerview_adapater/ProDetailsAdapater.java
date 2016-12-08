package com.bwei.like.yunifang.recyclerview_adapater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bwei.like.yunifang.R;
import com.bwei.like.yunifang.bean.ParticularsRoot;
import com.bwei.like.yunifang.holder.ProDetailsHolder;
import com.bwei.like.yunifang.utils.CommonUtils;

import java.util.ArrayList;

/**
 * Created by LiKe on 2016/12/8.
 */
public class ProDetailsAdapater extends RecyclerView.Adapter<ProDetailsHolder> {

    private Context context;
    private ArrayList<ParticularsRoot.DataBean.GoodsBean.AttributesBean> attributes;

    public ProDetailsAdapater(ArrayList<ParticularsRoot.DataBean.GoodsBean.AttributesBean> attributes, Context context) {
        this.attributes = attributes;
        this.context = context;
    }

    @Override
    public ProDetailsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = CommonUtils.inflate(R.layout.prodetails_item);
        ProDetailsHolder proDetailsHolder = new ProDetailsHolder(view);
        return proDetailsHolder;
    }

    @Override
    public void onBindViewHolder(ProDetailsHolder holder, int position) {
        holder.setData(context,attributes.get(position));
    }

    @Override
    public int getItemCount() {
        return attributes.size();
    }
}
