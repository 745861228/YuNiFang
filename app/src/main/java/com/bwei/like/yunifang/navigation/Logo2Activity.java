package com.bwei.like.yunifang.navigation;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bwei.like.yunifang.MainActivity;
import com.bwei.like.yunifang.R;
import com.bwei.like.yunifang.utils.CommonUtils;

import java.util.Timer;
import java.util.TimerTask;

public class Logo2Activity extends AppCompatActivity implements View.OnClickListener {

    private Button logo2_jump_but;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int i = (int) msg.obj;
            logo2_jump_but.setText("跳过" + i + "s");
            if (i == 0) {
                startIntent();
            }
        }
    };
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo2);
        logo2_jump_but = (Button) findViewById(R.id.logo2_jump_but);
        logo2_jump_but.setOnClickListener(this);
        //跳转到main
        jumpActivity();
    }

    private int i = 5;

    private void jumpActivity() {
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                handler.obtainMessage(0, i).sendToTarget();
                i--;
            }
        };
        timer.schedule(task, 0, 1000);
    }

    private void startIntent() {
        timer.cancel();
        handler.removeCallbacksAndMessages(null);
        boolean navigation = CommonUtils.getBoolean("navigation");
        Intent intent = null;
        if (navigation) {
            intent = new Intent(Logo2Activity.this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.translate_in, R.anim.translate_out);
        } else {
            intent = new Intent(Logo2Activity.this, NavigationActivity.class);
            CommonUtils.saveBolean("navigation", true);
            startActivity(intent);
            overridePendingTransition(R.anim.translate_in, R.anim.translate_out);
        }
        finish();
    }

    @Override
    public void onClick(View v) {
        startIntent();
    }
}
