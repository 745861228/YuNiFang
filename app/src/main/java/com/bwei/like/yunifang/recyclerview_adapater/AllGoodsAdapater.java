package com.bwei.like.yunifang.recyclerview_adapater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bwei.like.yunifang.R;
import com.bwei.like.yunifang.bean.AllGoodsRoot;
import com.bwei.like.yunifang.holder.AllGoodsHolder;
import com.bwei.like.yunifang.holder.BaseHolder;
import com.bwei.like.yunifang.interfaces.OnItemClickListener;
import com.bwei.like.yunifang.utils.CommonUtils;

import java.util.ArrayList;

/**
 * Created by LiKe on 2016/12/6.
 */
public class AllGoodsAdapater extends RecyclerView.Adapter<AllGoodsHolder> {

    private Context context;
    private ArrayList<AllGoodsRoot.DataBean> arrayList;
    private OnItemClickListener onItmeClickListener;
    private View view;


    public AllGoodsAdapater(ArrayList<AllGoodsRoot.DataBean> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }


    @Override
    public AllGoodsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = CommonUtils.inflate(R.layout.home_home_gridview_item);
        AllGoodsHolder holder = new AllGoodsHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(AllGoodsHolder holder, final int position) {
        holder.setData(context,arrayList.get(position));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItmeClickListener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItmeClickListener = onItemClickListener;
    }
}
