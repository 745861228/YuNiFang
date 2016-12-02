package com.bwei.like.yunifang.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by LiKe on 2016/12/2.
 */
public class Home_ListView extends ListView {
    public Home_ListView(Context context) {
        super(context);
    }

    public Home_ListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Home_ListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);

    }
}
