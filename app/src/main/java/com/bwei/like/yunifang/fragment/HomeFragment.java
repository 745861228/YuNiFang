package com.bwei.like.yunifang.fragment;

import android.view.View;
import android.widget.TextView;

import com.bwei.like.yunifang.base.BaseDataxUtils;
import com.bwei.like.yunifang.base.BaseFragment;
import com.bwei.like.yunifang.utils.LogUtils;
import com.bwei.like.yunifang.view.ShowingPage;

/**
 * Created by LiKe on 2016/11/28.
 */
public class HomeFragment extends BaseFragment {

    private String data;

    @Override
    protected View createSuccessView() {
        TextView textView = new TextView(getContext());
        textView.setText(data);
        return textView;
    }

    @Override
    protected void onLoad() {
        MyBaseData myBaseData = new MyBaseData();
        myBaseData.getData("http://www.sougou.com",null,1,BaseDataxUtils.NORMALTIME);
    }


    class MyBaseData extends BaseDataxUtils {

        @Override
        protected void setResulttError(ShowingPage.StateType stateLoadError) {
            HomeFragment.this.showingPage(ShowingPage.StateType.STATE_LOAD_ERROR);
        }

        @Override
        public void setResultData(String resultData) {
            HomeFragment.this.data = resultData;
            LogUtils.i("TAG-------------",resultData);
            showingPage(ShowingPage.StateType.STATE_LOAD_SUCCESS);
        }
    }
}
