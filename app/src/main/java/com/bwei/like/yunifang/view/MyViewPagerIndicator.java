package com.bwei.like.yunifang.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.bwei.like.yunifang.R;
import com.bwei.like.yunifang.bean.CategoryRoot;
import com.bwei.like.yunifang.utils.NetUtils;

import java.util.List;

/**
 * Created by LiKe on 2016/12/9.
 */
public class MyViewPagerIndicator extends LinearLayout implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private List<CategoryRoot.DataBean.CategoryBean.ChildrenBean> children;
    private ViewPager mViewPager;

    public MyViewPagerIndicator(Context context) {
        super(context);
    }

    public MyViewPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyViewPagerIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setViewPagerIndicator(List<CategoryRoot.DataBean.CategoryBean.ChildrenBean> children, ViewPager mViewPager){
        this.children = children;
        this.mViewPager = mViewPager;
        mViewPager.setOnPageChangeListener(this);

        for (int i = 0; i < children.size(); i++) {
            Button button = new Button(getContext());
            button.setText(children.get(i).cat_name);
            button.setOnClickListener(this);
            button.setBackground(null);
            LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT,1.0f);
            this.addView(button,params);
        }
    }

    @Override
    public void onClick(View v) {

        for (int i = 0; i < this.getChildCount(); i++) {
            Button button = (Button) this.getChildAt(i);
            if (v == button){
                mViewPager.setCurrentItem(i);
            }
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        for (int i = 0; i < this.getChildCount(); i++) {
            Button button = (Button) this.getChildAt(i);
            if (i == position){
                button.setTextColor(getResources().getColor(R.color.YuniFangZhangHao_textColor));
                button.setBackgroundResource(R.drawable.button_underline);
            }else {
                button.setTextColor(Color.BLACK);
                button.setBackground(null);
            }
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
