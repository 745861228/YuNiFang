package com.bwei.like.yunifang.fragment;

import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bwei.like.yunifang.R;
import com.bwei.like.yunifang.adapater.CommonAdapter;
import com.bwei.like.yunifang.adapater.ViewHolder;
import com.bwei.like.yunifang.application.MyApplication;
import com.bwei.like.yunifang.base.BaseFragment;
import com.bwei.like.yunifang.utils.CommonUtils;
import com.bwei.like.yunifang.view.ShowingPage;

import java.util.ArrayList;

/**
 * Created by LiKe on 2016/11/28.
 */
public class CartFragment extends BaseFragment {




    @Override
    protected View createSuccessView() {

        return null;
    }



    @Override
    protected void onLoad() {
      //  CartFragment.this.showingPage(ShowingPage.StateType.STATE_LOAD_SUCCESS);
    }
}
