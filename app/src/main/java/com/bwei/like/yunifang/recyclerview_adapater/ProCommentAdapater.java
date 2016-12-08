package com.bwei.like.yunifang.recyclerview_adapater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bwei.like.yunifang.R;
import com.bwei.like.yunifang.bean.ParticularsRoot;
import com.bwei.like.yunifang.holder.ProCommentHolder;
import com.bwei.like.yunifang.utils.CommonUtils;

import java.util.List;

/**
 * Created by LiKe on 2016/12/8.
 */
public class ProCommentAdapater extends RecyclerView.Adapter<ProCommentHolder> {

    private Context context;
    private List<ParticularsRoot.DataBean.CommentsBean> comments;


    public ProCommentAdapater(List<ParticularsRoot.DataBean.CommentsBean> comments, Context context) {
        this.comments = comments;
        this.context = context;
    }

    @Override
    public ProCommentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = CommonUtils.inflate(R.layout.procomment_item);
        ProCommentHolder holder = new ProCommentHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ProCommentHolder holder, int position) {
        holder.setData(context,comments.get(position));
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }
}
