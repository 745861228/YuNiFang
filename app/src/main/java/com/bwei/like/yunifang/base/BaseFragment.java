package com.bwei.like.yunifang.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bwei.like.yunifang.application.MyApplication;
import com.bwei.like.yunifang.utils.NetUtils;
import com.bwei.like.yunifang.view.ShowingPage;

/**
 * Created by LiKe on 2016/11/28.
 */
public abstract class BaseFragment extends Fragment {

    private ShowingPage showingPage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        showingPage = new ShowingPage(getContext()) {
            @Override
            public void onLoad() {
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            sleep(500);
                            BaseFragment.this.onLoad();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }

            @Override
            public View createSuccessView() {
                return BaseFragment.this.createSuccessView();
            }
        };
        return showingPage;
    }

    /**
     * 加载成功视图
     *
     * @return
     */
    protected abstract View createSuccessView();

    /**
     * 加载数据
     */
    protected abstract void onLoad();

    /**
     * 设置当前状态
     *
     * @param stateType
     */
    public void showingPage(ShowingPage.StateType stateType) {
        if (showingPage != null) {
            showingPage.showCurrentPage(stateType);
        }
    }


}
