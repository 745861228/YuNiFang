package com.bwei.like.yunifang.fragment;

import android.view.View;

import com.bwei.like.yunifang.base.BaseFragment;
import com.bwei.like.yunifang.view.ShowingPage;

/**
 * Created by LiKe on 2016/11/28.
 */
public class CategoryFragment extends BaseFragment {
    @Override
    protected View createSuccessView() {
        return null;
    }

    @Override
    protected void onLoad() {
        showingPage(ShowingPage.StateType.STATE_LOAD_ERROR);
    }
}
