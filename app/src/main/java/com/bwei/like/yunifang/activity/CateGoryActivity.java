package com.bwei.like.yunifang.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bwei.like.yunifang.R;
import com.bwei.like.yunifang.base.BaseActivity;
import com.bwei.like.yunifang.bean.CategoryRoot;
import com.bwei.like.yunifang.fragment.CateGorySonFragment;
import com.bwei.like.yunifang.utils.LogUtils;
import com.bwei.like.yunifang.view.MyViewPagerIndicator;

public class CateGoryActivity extends BaseActivity {

    private MyViewPagerIndicator mIndicator;
    private ViewPager mViewPager;
    private CategoryRoot.DataBean.CategoryBean categoryBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        initView();
        //获取数据
        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);
        categoryBean = (CategoryRoot.DataBean.CategoryBean) intent.getSerializableExtra("categoryBean");

        initDatas();

        mIndicator.setViewPagerIndicator(categoryBean.children,mViewPager);
        mViewPager.setCurrentItem(position);
    }

    /**
     *  初始化viewpager
     */
    private void initDatas() {
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return CateGorySonFragment.getFragment(categoryBean.children.get(position).id);
            }

            @Override
            public int getCount() {
                return categoryBean.children.size();
            }
        });
    }

    private void initView() {
        mIndicator = (MyViewPagerIndicator) findViewById(R.id.mIndicator);
        mViewPager = (ViewPager) findViewById(R.id.mViewPager);
    }
}
