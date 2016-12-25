package com.bwei.like.yunifang.navigation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bwei.like.yunifang.MainActivity;
import com.bwei.like.yunifang.R;
import com.bwei.like.yunifang.base.BaseActivity;
import com.bwei.like.yunifang.utils.CommonUtils;

public class LogoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);

        startIntent();

    }


    private void startIntent() {

        boolean navigation = CommonUtils.getBoolean("navigation");

        if (navigation) {
            CommonUtils.getHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(LogoActivity.this, MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.logo_in,R.anim.logo_out);
                    finish();
                }
            }, 3000);

        } else {
            Intent intent = new Intent(LogoActivity.this, NavigationActivity.class);
            CommonUtils.saveBolean("navigation", true);
            startActivity(intent);
            overridePendingTransition(R.anim.translate_in, R.anim.translate_out);
            finish();
        }

    }


}


//new Thread() {
//@Override
//public void run() {
//        try {
//        sleep(2000);
//
//        } catch (InterruptedException e) {
//        e.printStackTrace();
//        }
//        }
//        }.start();