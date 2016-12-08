package com.bwei.like.yunifang.Itemdecoration;


import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by LiKe on 2016/12/7.
 */
public class DividerGridItemDecoration extends RecyclerView.ItemDecoration {
    private int i;

    public DividerGridItemDecoration(int i) {
        this.i = i;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.top = i;
        outRect.bottom = i;
        outRect.left = i;
        outRect.right = i;
    }
}
