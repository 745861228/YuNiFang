package com.bwei.like.yunifang.recyclerview_adapater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bwei.like.yunifang.R;
import com.bwei.like.yunifang.bean.CateGorySonFragmentRoot;
import com.bwei.like.yunifang.holder.CateGorySonFragmentHolder;
import com.bwei.like.yunifang.utils.CommonUtils;

import java.util.ArrayList;

/**
 * Created by LiKe on 2016/12/10.
 */
public class CateGorySonFragmentAdapater extends RecyclerView.Adapter<CateGorySonFragmentHolder> {

    private Context context;
    private ArrayList<CateGorySonFragmentRoot.DataBean> arrayList;

    public CateGorySonFragmentAdapater(ArrayList<CateGorySonFragmentRoot.DataBean> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public CateGorySonFragmentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = CommonUtils.inflate(R.layout.home_home_gridview_item);
        CateGorySonFragmentHolder holder = new CateGorySonFragmentHolder(inflate);
        return holder;
    }

    @Override
    public void onBindViewHolder(CateGorySonFragmentHolder holder, int position) {
        holder.setData(context,arrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
