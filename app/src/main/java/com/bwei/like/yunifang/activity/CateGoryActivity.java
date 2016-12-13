package com.bwei.like.yunifang.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwei.like.yunifang.R;
import com.bwei.like.yunifang.base.BaseActivity;
import com.bwei.like.yunifang.bean.CategoryRoot;
import com.bwei.like.yunifang.fragment.CateGorySonFragment;
import com.bwei.like.yunifang.utils.LogUtils;
import com.bwei.like.yunifang.view.MyViewPagerIndicator;

import java.util.ArrayList;
import java.util.List;

public class CateGoryActivity extends BaseActivity implements View.OnClickListener {

    private MyViewPagerIndicator mIndicator;
    private ViewPager mViewPager;
    private CategoryRoot.DataBean.CategoryBean categoryBean;
    private TextView include_meddim_tv;
    private ImageView back_image;
    private ArrayList<CategoryRoot.DataBean.CategoryBean.ChildrenBean> childrenBeenList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        //获取数据
        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);
        categoryBean = (CategoryRoot.DataBean.CategoryBean) intent.getSerializableExtra("categoryBean");
        initView();

        childrenBeenList = new ArrayList<>();
            if (categoryBean.cat_name.equals("按属性")){
                for (int i = 0; i < categoryBean.children.size(); i++) {
                    if (i>5){
                        childrenBeenList.add(categoryBean.children.get(i));
                    }
                }
            }else {
                childrenBeenList.addAll(categoryBean.children);
            }
        initDatas();

        mIndicator.setViewPagerIndicator(childrenBeenList,mViewPager);
        mViewPager.setCurrentItem(position);
    }

    /**
     *  初始化viewpager
     */
    private void initDatas() {
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return CateGorySonFragment.getFragment(childrenBeenList.get(position).id);
            }

            @Override
            public int getCount() {
                return childrenBeenList.size();
            }
        });
    }

    private void initView() {
        mIndicator = (MyViewPagerIndicator) findViewById(R.id.mIndicator);
        mViewPager = (ViewPager) findViewById(R.id.mViewPager);
        include_meddim_tv = (TextView) findViewById(R.id.include_meddim_tv);
        back_image = (ImageView) findViewById(R.id.back_image);


        include_meddim_tv.setText(categoryBean.cat_name);
        back_image = (ImageView)findViewById(R.id.back_image);
        back_image.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_image:
                finish();
                CateGoryActivity.this.overridePendingTransition(R.anim.login_in0, R.anim.login_out);
                break;
        }
    }
}
