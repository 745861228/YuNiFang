package com.bwei.like.yunifang.navigation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bwei.like.yunifang.R;
import com.bwei.like.yunifang.utils.CommonUtils;

public class LogoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);

        CommonUtils.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(LogoActivity.this, Logo2Activity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.translate_in, R.anim.translate_out);
                finish();
            }
        }, 2000);
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