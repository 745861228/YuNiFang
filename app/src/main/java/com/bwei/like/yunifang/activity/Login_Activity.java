package com.bwei.like.yunifang.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bwei.like.yunifang.MainActivity;
import com.bwei.like.yunifang.R;
import com.bwei.like.yunifang.application.MyApplication;
import com.bwei.like.yunifang.base.BaseActivity;
import com.bwei.like.yunifang.utils.CommonUtils;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

public class Login_Activity extends BaseActivity implements View.OnClickListener {

    private TextView login_regest_tv;
    private ImageView login_back_image;
    private TextView login_YuniFangZhanghao_tv;
    private TextView login_phoneNumber_tv;
    private EditText login_phone_ed;
    private EditText login_password_ed;
    private TextView login_remember_password_tv;
    private TextView login_othrt_tv;
    private LinearLayout login_LinearLayout_getCode;
    private PopupWindow popupWindow;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    backgroundAlpha((float) msg.obj);
                    break;
            }
        }
    };
    private TextView login_login_tv;
    private Button login_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);

        initView();
    }

    private void initView() {
        login_regest_tv = (TextView) findViewById(R.id.include_right_tv);
        login_regest_tv.setText("注册");
        login_regest_tv.setVisibility(View.VISIBLE);
        login_regest_tv.setOnClickListener(this);
        login_login_tv = (TextView) findViewById(R.id.include_meddim_tv);
        login_login_tv.setText("登录");
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
        login_button = (Button) findViewById(R.id.login_button);
        login_button.setBackgroundColor(getResources().getColor(R.color.YuniFangZhangHao_textColor));
        login_button.setOnClickListener(this);

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
            case R.id.include_right_tv:
                startActivity(new Intent(Login_Activity.this,RegisterActivity.class));
                Login_Activity.this.overridePendingTransition(R.anim.login_in,R.anim.login_in0);
                break;

            //返回图片
            case R.id.back_image:
                finish();
                Login_Activity.this.overridePendingTransition(R.anim.login_in0, R.anim.login_out);
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
            case R.id.login_button:
                Toast.makeText(Login_Activity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                MyApplication.loginFlag = true;
                CommonUtils.saveBolean("loginFlag",MyApplication.loginFlag);
                Login_Activity.this.overridePendingTransition(R.anim.login_in0, R.anim.login_out);
                Intent intent = getIntent();
                setResult(101,intent);
                finish();
                break;

            //第三方登陆
            case R.id.login_other_tv:
                bottomwindow(v);
                showPopupWidow();
                break;
            //QQ登陆按钮
            case R.id.qq_land:
                UMShareAPI mShareAPI = UMShareAPI.get(Login_Activity.this);
                //  mShareAPI.doOauthVerify(Login_Activity.this, SHARE_MEDIA.QQ, umAuthListener);
                mShareAPI.getPlatformInfo(Login_Activity.this, SHARE_MEDIA.QQ, umAuthListener);
                popupWindow.dismiss();
                break;

        }
    }

    private void showPopupWidow() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                float alpha = 1f;
                while (alpha > 0.5f) {
                    try {
                        //4是根据弹出动画时间和减少的透明度计算
                        Thread.sleep(4);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Message msg = mHandler.obtainMessage();
                    msg.what = 1;
                    //每次减少0.01，精度越高，变暗的效果越流畅
                    alpha -= 0.01f;
                    msg.obj = alpha;
                    mHandler.sendMessage(msg);
                }
            }

        }).start();
    }

    public void bottomwindow(View view) {
        if (popupWindow != null && popupWindow.isShowing()) {
            return;
        }
        LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.pop_layout, null);
        //设置窗口内可点击事件
        windowClickListener(layout);
        popupWindow = new PopupWindow(layout,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        //点击空白处时，隐藏掉pop窗口
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //添加弹出、弹入的动画
        popupWindow.setAnimationStyle(R.style.Popupwindow);
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        popupWindow.showAtLocation(view, Gravity.LEFT | Gravity.BOTTOM, 0, -location[1]);
        //添加pop窗口关闭事件，主要是实现关闭时改变背景的透明度
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                hidePopupWindow();
            }
        });
        backgroundAlpha(1f);
    }


    /**
     * //设置窗口内可点击事件
     *
     * @param layout
     */
    private void windowClickListener(LinearLayout layout) {
        ImageButton qq_land = (ImageButton) layout.findViewById(R.id.qq_land);
        qq_land.setOnClickListener(this);
    }

    private void hidePopupWindow() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //此处while的条件alpha不能<= 否则会出现黑屏
                float alpha = 0.5f;
                while (alpha < 0.9f) {
                    try {
                        Thread.sleep(4);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.d("HeadPortrait", "alpha:" + alpha);
                    Message msg = mHandler.obtainMessage();
                    msg.what = 1;
                    alpha += 0.01f;
                    msg.obj = alpha;
                    mHandler.sendMessage(msg);
                }
            }

        }).start();
    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

    }

    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Toast.makeText(Login_Activity.this, "登陆成功" , Toast.LENGTH_SHORT).show();
            CommonUtils.saveString("screen_name",data.get("screen_name"));
            CommonUtils.saveString("profile_image_url",data.get("profile_image_url"));
            MyApplication.loginFlag = true;
            CommonUtils.saveBolean("loginFlag",MyApplication.loginFlag);
            Login_Activity.this.overridePendingTransition(R.anim.login_in0, R.anim.login_out);
            Intent intent = getIntent();
            setResult(101,intent);
            Login_Activity.this.finish();
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(getApplicationContext(), "Authorize fail", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(getApplicationContext(), "Authorize cancel", Toast.LENGTH_SHORT).show();
        }
    };

}
