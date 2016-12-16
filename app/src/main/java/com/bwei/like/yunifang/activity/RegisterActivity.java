package com.bwei.like.yunifang.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwei.like.yunifang.R;
import com.bwei.like.yunifang.base.BaseActivity;
import com.bwei.like.yunifang.utils.ImageLoaderUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private ImageView back_image;
    private TextView include_middle_tv;
    private EditText register_phoneNumber_ed;
    private EditText register_verificationCode_ed;
    private TextView register_getCode_tv;
    private EditText register_password_ed;
    private CheckBox register_cb;
    private CheckBox register_btn_normal_selector;
    private TextView register_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        initViewListener();
    }

    /**
     * 控件监听事件
     */
    private void initViewListener() {
        back_image.setOnClickListener(this);
        register_getCode_tv.setOnClickListener(this);
        register_btn_normal_selector.setOnClickListener(this);
        register_tv.setOnClickListener(this);
    }

    /**
     * 初始化控件
     */
    private void initView() {
        back_image = (ImageView) findViewById(R.id.back_image);
        include_middle_tv = (TextView) findViewById(R.id.include_meddim_tv);
        include_middle_tv.setText("注册");
        include_middle_tv.setTextColor(Color.BLACK);

        register_phoneNumber_ed = (EditText) findViewById(R.id.register_phoneNumber_ed);
        register_verificationCode_ed = (EditText) findViewById(R.id.register_verificationCode_ed);
        register_getCode_tv = (TextView) findViewById(R.id.register_getCode_tv);
        register_password_ed = (EditText) findViewById(R.id.register_password_ed);


        //复选框
        register_cb = (CheckBox) findViewById(R.id.register_cb);
        register_cb.setText(Html.fromHtml("<font color=\'#000\'>我已阅读并同意 </font><font color=\'#5BB8DC\'>使用条款和隐私政策</font>"));

        register_btn_normal_selector = (CheckBox) findViewById(R.id.register_btn_normal_selector);
        register_tv = (TextView) findViewById(R.id.register_tv);
        ImageView register_large_image = (ImageView) findViewById(R.id.register_large_image);
        ImageLoader.getInstance().displayImage("http://image.hmeili.com/yunifang/images/goods/ad1//1503311533748344878180.png",register_large_image, ImageLoaderUtils.initOptionsCircle());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_image:
                finish();
                RegisterActivity.this.overridePendingTransition(R.anim.login_in0, R.anim.login_out);
                break;
            //获取验证码
            case R.id.register_getCode_tv:

                break;
            //监听密码是否为可见状态
            case R.id.register_btn_normal_selector:
                if (register_btn_normal_selector.isChecked()) {
                    register_password_ed.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    register_password_ed.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                break;
            //注册按钮
            case R.id.register_tv:

                break;


        }
    }
}
