package com.bwei.like.yunifang.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bwei.like.yunifang.R;
import com.bwei.like.yunifang.activity.Login_Activity;
import com.bwei.like.yunifang.activity.UserSeting_Activity;
import com.bwei.like.yunifang.base.BaseFragment;
import com.bwei.like.yunifang.utils.CommonUtils;
import com.bwei.like.yunifang.view.ShowingPage;

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

    @Override
    protected View createSuccessView() {
        view = CommonUtils.inflate(R.layout.minefragment_item);
        initView();

        return view;
    }

    /**
     * 初始化控件
     */
    private void initView() {
        user_setting = (ImageView) view.findViewById(R.id.user_setting);
        user_setting.setOnClickListener(this);
        mine_login_tv = (TextView) view.findViewById(R.id.mine_login_tv);
        mine_login_tv.setOnClickListener(this);
    }


    @Override
    protected void onLoad() {
        MineFragment.this.showingPage(ShowingPage.StateType.STATE_LOAD_SUCCESS);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //用户设置界面
            case R.id.user_setting:
                intentActivity(UserSeting_Activity.class);
                break;

            //用户登录按钮
            case R.id.mine_login_tv:
                intentActivity(Login_Activity.class);
                break;
        }
    }

    private void intentActivity(Class c) {
        startActivity(new Intent(getActivity(),c));
        getActivity().overridePendingTransition(R.anim.login_in,R.anim.login_in0);
    }
}
