package com.bwei.like.yunifang.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwei.like.yunifang.R;

public class Login_Activity extends AppCompatActivity implements View.OnClickListener {

    private TextView login_regest_tv;
    private ImageView login_back_image;
    private TextView login_YuniFangZhanghao_tv;
    private TextView login_phoneNumber_tv;
    private EditText login_phone_ed;
    private EditText login_password_ed;
    private TextView login_remember_password_tv;
    private Button login_but;
    private TextView login_othrt_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login_);

        initView();
    }

    private void initView() {
        login_regest_tv = (TextView) findViewById(R.id.tv_includ);
        login_regest_tv.setOnClickListener(this);
        login_back_image = (ImageView) findViewById(R.id.back_image);
        login_back_image.setOnClickListener(this);
        login_YuniFangZhanghao_tv = (TextView) findViewById(R.id.login_YuniFang_ZhangHao_tv);
        login_YuniFangZhanghao_tv.setOnClickListener(this);
        login_phoneNumber_tv = (TextView) findViewById(R.id.login_phoneNumber_tv);
        login_phoneNumber_tv.setOnClickListener(this);
        login_phone_ed = (EditText) findViewById(R.id.login_phone_ed);
        login_password_ed = (EditText) findViewById(R.id.login_password_ed);
        login_remember_password_tv = (TextView) findViewById(R.id.login_remeber_password);
        login_remember_password_tv.setOnClickListener(this);
        login_but = (Button) findViewById(R.id.login_but);
        login_but.setOnClickListener(this);
        login_othrt_tv = (TextView) findViewById(R.id.login_other_tv);
        login_othrt_tv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //注册
            case R.id.tv_includ:

                break;

            //返回图片
            case R.id.back_image:

                break;

            //御泥坊账号登陆


        }
    }
}
