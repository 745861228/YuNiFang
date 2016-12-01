package com.bwei.like.yunifang.activity;

import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bwei.like.yunifang.MainActivity;
import com.bwei.like.yunifang.R;
import com.bwei.like.yunifang.base.BaseActivity;
import com.bwei.like.yunifang.view.MyPopupWindow;

public class Login_Activity extends BaseActivity implements View.OnClickListener {

    private TextView login_regest_tv;
    private ImageView login_back_image;
    private TextView login_YuniFangZhanghao_tv;
    private TextView login_phoneNumber_tv;
    private EditText login_phone_ed;
    private EditText login_password_ed;
    private TextView login_remember_password_tv;
    private Button login_but;
    private TextView login_othrt_tv;
    private LinearLayout login_LinearLayout_getCode;
    private MyPopupWindow myPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);

        initView();
    }

    private void initView() {
        login_regest_tv = (TextView) findViewById(R.id.tv_includ);
        login_regest_tv.setOnClickListener(this);
        login_regest_tv.setTextColor(getResources().getColor(R.color.YuniFangZhangHao_textColor));
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
        login_but.setBackgroundColor(getResources().getColor(R.color.YuniFangZhangHao_textColor));
        login_othrt_tv = (TextView) findViewById(R.id.login_other_tv);
        login_othrt_tv.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        login_othrt_tv.setTextColor(getResources().getColor(R.color.YuniFangZhangHao_textColor));
        login_othrt_tv.setOnClickListener(this);
        login_LinearLayout_getCode = (LinearLayout) findViewById(R.id.login_linearlayout_getCode);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //注册
            case R.id.tv_includ:

                break;

            //返回图片
            case R.id.back_image:
                finish();
                overridePendingTransition(R.anim.login_in0, R.anim.login_out);
                break;

            //御泥坊账号登陆
            case R.id.login_YuniFang_ZhangHao_tv:
                login_YuniFangZhanghao_tv.setTextColor(getResources().getColor(R.color.YuniFangZhangHao_textColor));
                login_YuniFangZhanghao_tv.setBackgroundColor(Color.WHITE);
                login_phoneNumber_tv.setTextColor(Color.BLACK);
                login_phoneNumber_tv.setBackgroundColor(getResources().getColor(R.color.YuniFangZhangHao_Bg));
                login_remember_password_tv.setVisibility(View.VISIBLE);
                login_LinearLayout_getCode.setVisibility(View.INVISIBLE);
                login_password_ed.setVisibility(View.VISIBLE);
                break;

            case R.id.login_phoneNumber_tv:
                login_YuniFangZhanghao_tv.setBackgroundResource(R.color.YuniFangZhangHao_Bg);
                login_phoneNumber_tv.setTextColor(getResources().getColor(R.color.YuniFangZhangHao_textColor));
                login_phoneNumber_tv.setBackgroundColor(Color.WHITE);
                login_YuniFangZhanghao_tv.setTextColor(Color.BLACK);
                login_password_ed.setVisibility(View.INVISIBLE);
                login_remember_password_tv.setVisibility(View.INVISIBLE);
                login_LinearLayout_getCode.setVisibility(View.VISIBLE);
                break;

            //忘记密码
            case R.id.login_remeber_password:

                break;

            //登陆按钮
            case R.id.login_but:

                break;

            //第三方登陆
            case R.id.login_other_tv:
                myPopupWindow = new MyPopupWindow(Login_Activity.this,itemsOnClick);
                myPopupWindow.showAtLocation(Login_Activity.this.findViewById(R.id.login_other_tv), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
                break;

        }
    }


    //为弹出窗口实现监听类
    private View.OnClickListener itemsOnClick = new View.OnClickListener(){

        public void onClick(View v) {
            myPopupWindow.dismiss();
            switch (v.getId()) {
                case R.id.qq_land:
                    break;
                case R.id.sina_land:
                    break;
                case R.id.wechat_land:
                    break;
                default:
                    break;
            }


        }

    };
}
