package com.bwei.like.yunifang.activity;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bwei.like.yunifang.R;
import com.bwei.like.yunifang.base.BaseActivity;
import com.bwei.like.yunifang.fragment.MyOrderFragment;
import com.bwei.like.yunifang.utils.CommonUtils;

public class MyOrderActivity extends BaseActivity implements View.OnClickListener {

    private RadioGroup mRadioGroup;
    private ViewPager mViewPager;
    private ImageView back_image;
    private TextView include_middle_tv;
    private TextView include_right_tv;
    private String[] titles = {"全部", "待付款", "待发货", "待收货", "待评价"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        initView();
        initViewListener();
        initDot();
        //设置视图
        initPicView();
    }

    private void initPicView() {


        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return MyOrderFragment.getFragment(titles[position]);
            }

            @Override
            public int getCount() {
                return titles.length;
            }
        });

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < titles.length; i++) {
                    RadioButton childAt = (RadioButton) mRadioGroup.getChildAt(i);
                    if (i == position) {
                        childAt.setBackgroundResource(R.drawable.button_underline);
                        childAt.setTextColor(getResources().getColor(R.color.YuniFangZhangHao_textColor));
                    }else{
                        childAt.setBackground(null); 
                        childAt.setTextColor(Color.BLACK);
                    }
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //获取数据
        int position = getIntent().getIntExtra("position", -1);
        if (position!=-1){
            mViewPager.setCurrentItem(position);
        }
    }

    /**
     * 初始化
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initDot() {
        for (int i = 0; i < titles.length; i++) {

            View view = CommonUtils.inflate(R.layout.radio_button);
            ((RadioButton) view).setText(titles[i]);
            ((RadioButton) view).setTextColor(Color.BLACK);
            ((RadioButton) view).setGravity(Gravity.CENTER);
            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT, 1.0f);
            mRadioGroup.addView(view, params);
        }
    }

    /**
     * 控件监听事件
     */
    private void initViewListener() {
        back_image.setOnClickListener(this);
        include_right_tv.setOnClickListener(this);

    }

    /**
     * 初始化控件
     */
    private void initView() {
        back_image = (ImageView) findViewById(R.id.back_image);
        include_middle_tv = (TextView) findViewById(R.id.include_meddim_tv);
        include_right_tv = (TextView) findViewById(R.id.include_right_tv);

        include_middle_tv.setText("我的订单");
        include_middle_tv.setTextColor(Color.BLACK);
        include_right_tv.setVisibility(View.VISIBLE);
        include_right_tv.setText("退款订单");
        include_right_tv.setTextColor(getResources().getColor(R.color.YuniFangZhangHao_textColor));


        mRadioGroup = (RadioGroup) findViewById(R.id.mRadioGroup);
        mViewPager = (ViewPager) findViewById(R.id.mViewPager);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //图片返回事件
            case R.id.back_image:
                finish();
                MyOrderActivity.this.overridePendingTransition(R.anim.login_in0, R.anim.login_out);
                break;
            //退款订单监听事件
            case R.id.include_right_tv:

                break;

        }
    }
}
