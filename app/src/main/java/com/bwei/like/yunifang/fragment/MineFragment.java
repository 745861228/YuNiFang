package com.bwei.like.yunifang.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bwei.like.yunifang.R;
import com.bwei.like.yunifang.activity.Login_Activity;
import com.bwei.like.yunifang.activity.MyOrderActivity;
import com.bwei.like.yunifang.activity.UserSeting_Activity;
import com.bwei.like.yunifang.application.MyApplication;
import com.bwei.like.yunifang.base.BaseFragment;
import com.bwei.like.yunifang.utils.CommonUtils;
import com.bwei.like.yunifang.utils.ImageLoaderUtils;
import com.bwei.like.yunifang.view.ShowingPage;
import com.bwei.like.yunifang.view.UserImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by LiKe on 2016/11/28.
 */
public class MineFragment extends BaseFragment implements View.OnClickListener {

    private ListView mine_listView;
    private View view;
    private ImageView user_setting;
    private TextView mine_login_tv;
    private ImageView user_icon_image;
    private TextView user_name_tv;
    private ImageView default_uset_image;
    private RadioButton wait_pay;
    private RadioButton wait_send_good;
    private RadioButton wait_receive_good;
    private RadioButton wait_evaluate;
    private RadioButton wait_refund;

    @Override
    protected View createSuccessView() {
        view = CommonUtils.inflate(R.layout.minefragment_item);
        initView();

        initViewListener();
        return view;
    }

    /**
     * 控件设置监听事件
     */
    private void initViewListener() {
        wait_pay.setOnClickListener(this);
        wait_send_good.setOnClickListener(this);
        wait_receive_good.setOnClickListener(this);
        wait_evaluate.setOnClickListener(this);
        wait_refund.setOnClickListener(this);
    }

    /**
     * 初始化控件
     */
    private void initView() {
        user_setting = (ImageView) view.findViewById(R.id.user_setting);
        user_setting.setOnClickListener(this);
        mine_login_tv = (TextView) view.findViewById(R.id.mine_login_tv);
        mine_login_tv.setOnClickListener(this);

        user_icon_image = (ImageView) view.findViewById(R.id.user_icon_image);
        user_name_tv = (TextView) view.findViewById(R.id.user_name_tv);
        default_uset_image = (ImageView) view.findViewById(R.id.default_uset_image);


        //收货信息监听事件
        wait_pay = (RadioButton) view.findViewById(R.id.wait_pay);
        wait_send_good = (RadioButton) view.findViewById(R.id.wait_send_good);
        wait_receive_good = (RadioButton) view.findViewById(R.id.wait_receive_good);
        wait_evaluate = (RadioButton) view.findViewById(R.id.wait_evaluate);
        wait_refund = (RadioButton) view.findViewById(R.id.wait_refund);
    }


    @Override
    protected void onLoad() {
        MineFragment.this.showingPage(ShowingPage.StateType.STATE_LOAD_SUCCESS);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            //用户设置界面
            case R.id.user_setting:
                intentActivity(UserSeting_Activity.class);
                break;

            //用户登录按钮
            case R.id.mine_login_tv:
                intentActivity(Login_Activity.class);
                break;
            //待付款
            case R.id.wait_pay:
                intent = new Intent(getActivity(), MyOrderActivity.class);
                intent.putExtra("position", 1);
                getActivity().startActivity(intent);
                break;
            //代发货
            case R.id.wait_send_good:
                intent = new Intent(getActivity(), MyOrderActivity.class);
                intent.putExtra("position", 2);
                getActivity().startActivity(intent);
                break;
            //待收货
            case R.id.wait_receive_good:
                intent = new Intent(getActivity(), MyOrderActivity.class);
                intent.putExtra("position", 3);
                getActivity().startActivity(intent);
                break;
            //带评价
            case R.id.wait_evaluate:
                intent = new Intent(getActivity(), MyOrderActivity.class);
                intent.putExtra("position", 4);
                getActivity().startActivity(intent);
                break;
            //待退款
            case R.id.wait_refund:

                break;
        }

    }

    private void intentActivity(Class c) {
        startActivity(new Intent(getActivity(), c));
        getActivity().overridePendingTransition(R.anim.login_in, R.anim.login_in0);
    }

    @Override
    public void onResume() {
        super.onResume();
        MineFragment.this.showingPage(ShowingPage.StateType.STATE_LOAD_SUCCESS);
        initUserMessage();
    }

    /**
     * 初始化用户信息
     */
    private void initUserMessage() {
        if (MyApplication.loginFlag) {
            String screen_name = CommonUtils.getString("screen_name");
            String profile_image_url = CommonUtils.getString("profile_image_url");
            user_name_tv.setVisibility(View.VISIBLE);
            user_icon_image.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(profile_image_url, user_icon_image, ImageLoaderUtils.initOptions());
            user_name_tv.setText(screen_name);
            default_uset_image.setVisibility(View.GONE);
        } else {
            user_name_tv.setVisibility(View.GONE);
            user_icon_image.setVisibility(View.GONE);
            default_uset_image.setVisibility(View.VISIBLE);
            default_uset_image.setImageResource(R.mipmap.user_icon_no_set);
        }
    }
}
