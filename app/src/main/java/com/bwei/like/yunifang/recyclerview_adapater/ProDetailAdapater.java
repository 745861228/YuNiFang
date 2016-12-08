package com.bwei.like.yunifang.recyclerview_adapater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bwei.like.yunifang.R;
import com.bwei.like.yunifang.bean.GoodsDesc;
import com.bwei.like.yunifang.holder.ProDetailHolder;
import com.bwei.like.yunifang.utils.CommonUtils;

/**
 * Created by LiKe on 2016/12/8.
 */
public class ProDetailAdapater extends RecyclerView.Adapter<ProDetailHolder> {

    private Context context;
    private GoodsDesc[] goodsDescs;

    public ProDetailAdapater(Context context, GoodsDesc[] goodsDescs) {
        this.context = context;
        this.goodsDescs = goodsDescs;
    }




    @Override
    public ProDetailHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = CommonUtils.inflate(R.layout.prodetail_item);
        ProDetailHolder holder = new ProDetailHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ProDetailHolder holder, int position) {
        holder.setData(context,goodsDescs[position]);
    }


    @Override
    public int getItemCount() {
        return goodsDescs.length;
    }
}
