package com.bwei.like.yunifang.navigation;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.bwei.like.yunifang.MainActivity;
import com.bwei.like.yunifang.R;
import com.bwei.like.yunifang.base.BaseActivity;

import java.util.ArrayList;

public class NavigationActivity extends BaseActivity implements ViewPager.OnPageChangeListener, View.OnClickListener, Animation.AnimationListener {

    private ViewPager navigation_viewPager;
    private ImageView ico_next_guidance;
    private ArrayList<View> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        navigation_viewPager = (ViewPager) findViewById(R.id.navigation_viewPager);
        initView();

    }

    private void initView() {
        arrayList = new ArrayList<>();
        View view1 = View.inflate(NavigationActivity.this, R.layout.navigation_page1, null);
        View view2 = View.inflate(NavigationActivity.this, R.layout.navigation_page2, null);
        View view3 = View.inflate(NavigationActivity.this, R.layout.navigation_page3, null);
        View view4 = View.inflate(NavigationActivity.this, R.layout.navigation_page4, null);

        ico_next_guidance = (ImageView) view4.findViewById(R.id.ico_next_guidance);

        arrayList.add(view1);
        arrayList.add(view2);
        arrayList.add(view3);
        arrayList.add(view4);

        navigation_viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return arrayList.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(arrayList.get(position));
                return arrayList.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });
        navigation_viewPager.setOnPageChangeListener(this);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == arrayList.size()-1){
            ico_next_guidance.setOnClickListener(this);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        TranslateAnimation translateAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT,0,
                Animation.RELATIVE_TO_PARENT,-1,
                Animation.RELATIVE_TO_PARENT,0,
                Animation.RELATIVE_TO_PARENT,0);

        translateAnimation.setDuration(5000);
        translateAnimation.setFillAfter(true);
        ico_next_guidance.startAnimation(translateAnimation);

        translateAnimation.setAnimationListener(this);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        startActivity(new Intent(NavigationActivity.this,MainActivity.class));
        overridePendingTransition(R.anim.logo_in,R.anim.logo_out);
        finish();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
