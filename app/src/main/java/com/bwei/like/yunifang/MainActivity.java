package com.bwei.like.yunifang;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RadioGroup;

import com.bwei.like.yunifang.base.BaseActivity;
import com.bwei.like.yunifang.factory.FragmentFactory;
import com.bwei.like.yunifang.utils.CommonUtils;
import com.bwei.like.yunifang.view.NoScrollViewPager;
import com.bwei.like.yunifang.view.ShowingPage;
import com.zhy.autolayout.AutoLayoutActivity;

public class MainActivity extends BaseActivity {

    private NoScrollViewPager main_viewPager;
    private RadioGroup main_radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            initView();

    }

    private void initView() {
        main_viewPager = (NoScrollViewPager) findViewById(R.id.main_viewPager);
        main_radioGroup = (RadioGroup) findViewById(R.id.main_radioGroup);

        //设置viewPager设配器
        main_viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return FragmentFactory.getFragment(position);
            }

            @Override
            public int getCount() {
                return 4;
            }
        });

        main_radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home_page:
                        main_viewPager.setCurrentItem(0);
                        break;

                    case R.id.rb_category_page:
                        main_viewPager.setCurrentItem(1);
                        break;

                    case R.id.rb_cart_page:
                        main_viewPager.setCurrentItem(2);
                        break;

                    case R.id.rb_mine_page:
                        main_viewPager.setCurrentItem(3);
                        break;
                }
            }
        });
    }
}
