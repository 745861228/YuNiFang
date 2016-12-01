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
import com.bwei.like.yunifang.base.BaseFragment;
import com.bwei.like.yunifang.utils.CommonUtils;
import com.bwei.like.yunifang.view.ShowingPage;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by LiKe on 2016/11/28.
 */
public class MineFragment extends BaseFragment {

    private ListView mine_listView;

    @Override
    protected View createSuccessView() {
        View view = CommonUtils.inflate(R.layout.minefragment_item);
        TextView mine_login_tv = (TextView) view.findViewById(R.id.mine_login_tv);
        mine_login_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Login_Activity.class));
            }
        });
        return view;
    }


    @Override
    protected void onLoad() {
        MineFragment.this.showingPage(ShowingPage.StateType.STATE_LOAD_SUCCESS);
    }
}
