package com.bwei.like.yunifang;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bwei.like.yunifang.activity.Ad5WebView_Activity;
import com.bwei.like.yunifang.activity.Login_Activity;
import com.bwei.like.yunifang.activity.UserSeting_Activity;
import com.bwei.like.yunifang.application.MyApplication;
import com.bwei.like.yunifang.base.BaseActivity;
import com.bwei.like.yunifang.factory.FragmentFactory;
import com.bwei.like.yunifang.fragment.CartFragment;
import com.bwei.like.yunifang.fragment.CategoryFragment;
import com.bwei.like.yunifang.fragment.HomeFragment;
import com.bwei.like.yunifang.fragment.MineFragment;
import com.bwei.like.yunifang.utils.CommonUtils;
import com.bwei.like.yunifang.utils.LogUtils;
import com.bwei.like.yunifang.view.NoScrollViewPager;
import com.bwei.like.yunifang.view.ShowingPage;
import com.zhy.autolayout.AutoLayoutActivity;

import java.io.Serializable;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private NoScrollViewPager main_viewPager;
    private RadioGroup main_radioGroup;
    private FrameLayout main_frameLayout;
    private RadioButton rb_home_page, rb_category_page, rb_cart_page, rb_mine_page;
    private FragmentManager supportFragmentManager;
    private long mExitTime = 0;
    private int position = 0;

    public RadioGroup getMain_radioGroup() {
        return main_radioGroup;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        supportFragmentManager = getSupportFragmentManager();
        addFragment(new HomeFragment(), "homeFragment");
    }

    private void initView() {
        //  main_viewPager = (NoScrollViewPager) findViewById(R.id.main_viewPager);
        main_radioGroup = (RadioGroup) findViewById(R.id.main_radioGroup);
        main_frameLayout = (FrameLayout) findViewById(R.id.main_frameLayout);

        rb_home_page = (RadioButton) findViewById(R.id.rb_home_page);
        rb_category_page = (RadioButton) findViewById(R.id.rb_category_page);
        rb_cart_page = (RadioButton) findViewById(R.id.rb_cart_page);
        rb_mine_page = (RadioButton) findViewById(R.id.rb_mine_page);

        rb_home_page.setOnClickListener(this);
        rb_category_page.setOnClickListener(this);
        rb_cart_page.setOnClickListener(this);
        rb_mine_page.setOnClickListener(this);


//        //设置viewPager设配器
//        main_viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
//            @Override
//            public Fragment getItem(int position) {
//                return FragmentFactory.getFragment(position);
//            }
//
//            @Override
//            public int getCount() {
//                return 4;
//            }
        //       });

//        main_radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                switch (checkedId) {
//                    case R.id.rb_home_page:
//                        main_viewPager.setCurrentItem(0);
//                        break;
//
//                    case R.id.rb_category_page:
//                        main_viewPager.setCurrentItem(1);
//                        break;
//
//                    case R.id.rb_cart_page:
//                        main_viewPager.setCurrentItem(2);
//                        break;
//
//                    case R.id.rb_mine_page:
//                        main_viewPager.setCurrentItem(3);
//                        break;
//                }
//            }
//        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rb_home_page:
                position = 0;
                addFragment(new HomeFragment(), "homeFragment");
                break;

            case R.id.rb_category_page:
                position = 1;
                addFragment(new CategoryFragment(), "categoryFragment");
                break;

            case R.id.rb_cart_page:
                if (MyApplication.loginFlag) {
                    addFragment(new CartFragment(), "cartFragment");
                } else {
                    Intent intent = new Intent(MainActivity.this, Login_Activity.class);
                    startActivityForResult(intent,100);
                    MainActivity.this.overridePendingTransition(R.anim.login_in, R.anim.login_in0);
                    if (position == 0){
                        addFragment(new HomeFragment(), "homeFragment");
                        rb_home_page.setChecked(true);
                    }else if (position == 1){
                        addFragment(new CategoryFragment(), "categoryFragment");
                        rb_category_page.setChecked(true);
                    }else if (position == 3){
                        addFragment(new MineFragment(), "mineFragment");
                        rb_mine_page.setChecked(true);
                    }
                }

                break;

            case R.id.rb_mine_page:
                position = 3;
                addFragment(new MineFragment(), "mineFragment");
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == 101){
            addFragment(new CartFragment(), "cartFragment");
            rb_cart_page.setChecked(true);
        }
    }

    public void addFragment(Fragment fragment, String tag) {
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_frameLayout, fragment, tag);
        //添加到回退栈，并定义标记
        fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }

//        //判断当前按下的是否为返回键
//        if (keyCode == KeyEvent.KEYCODE_BACK&& event.getAction() == KeyEvent.ACTION_DOWN) {
//            //获取当前回退栈中的fragment个数
//            int backStackEntryCount = supportFragmentManager.getBackStackEntryCount();
//            if (backStackEntryCount > 1) {
//                //立刻回退
//                supportFragmentManager.popBackStackImmediate();
//                //获取当前回退到哪一个fragment上
//                FragmentManager.BackStackEntry backStack = supportFragmentManager.getBackStackEntryAt(supportFragmentManager.getBackStackEntryCount() - 1);
//                //获取当前栈顶fragment的标记值
//                String tag = backStack.getName();
//                if ("homeFragment".equals(tag)) {
//                    rb_home_page.setChecked(true);
//                } else if ("categoryFragment".equals(tag)) {
//                    rb_category_page.setChecked(true);
//                } else if ("cartFragment".equals(tag)) {
//                    rb_cart_page.setChecked(true);
//                } else if ("mineFragment".equals(tag)) {
//                    rb_mine_page.setChecked(true);
//                }
//            } else {
//                finish();
//            }
//        }
        return true;
    }
}
