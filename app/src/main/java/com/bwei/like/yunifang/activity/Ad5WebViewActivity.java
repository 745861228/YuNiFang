package com.bwei.like.yunifang.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bwei.like.yunifang.R;
import com.bwei.like.yunifang.base.BaseActivity;
import com.bwei.like.yunifang.bean.HomeRoot;

public class Ad5WebViewActivity extends BaseActivity implements View.OnClickListener {

    private ImageView include_image;
    private WebView ad5_webView;
    private TextView include_middle_tv;
    private HomeRoot.DataBean.Ad5Bean ad5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad5_web_view);
        ad5 = (HomeRoot.DataBean.Ad5Bean) getIntent().getSerializableExtra("ad5");
        initView();
        //获取数据
        ad5_webView.loadUrl(ad5.ad_type_dynamic_data);
        ad5_webView.setWebViewClient(new WebViewClient());
    }

    private void initView() {
        include_middle_tv = (TextView) findViewById(R.id.include_meddim_tv);
        include_middle_tv.setText(ad5.title);
        ImageView back_image = (ImageView)findViewById(R.id.back_image);
        back_image.setOnClickListener(this);
        include_image = (ImageView) findViewById(R.id.include_image);
        include_image.setVisibility(View.VISIBLE);
        ViewGroup.LayoutParams params = include_image.getLayoutParams();
        params.height = 30;
        params.width = 30;
        include_image.setLayoutParams(params);
        include_image.setOnClickListener(this);
        include_image.setImageResource(R.drawable.shear_selector);

        ad5_webView = (WebView) findViewById(R.id.ad5_webView);
    }

    private void initSettings() {
        WebSettings settings = ad5_webView.getSettings();
        //是否自动打开窗口
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        //是否识别jsp
        settings.setJavaScriptEnabled(true);
        //设置如何缓存
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        //是否展示缩放按钮
        settings.setBuiltInZoomControls(true);
        //设置默认的缩放比例
        settings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //返回按钮
            case R.id.back_image:
                finish();
                Ad5WebViewActivity.this.overridePendingTransition(R.anim.login_in0, R.anim.login_out);
                break;
            //分享图片按钮监听
            case R.id.include_image:

                break;
        }
    }
}
