package com.bwei.like.yunifang.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by LiKe on 2016/12/6.
 */
public abstract class BaseHolder<T>  extends RecyclerView.ViewHolder{
    public BaseHolder(View itemView) {
        super(itemView);
    }

    public abstract void setData(Context context, T t);
}
