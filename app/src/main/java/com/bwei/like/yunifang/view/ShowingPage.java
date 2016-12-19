package com.bwei.like.yunifang.view;

import android.content.Context;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.bwei.like.yunifang.R;
import com.bwei.like.yunifang.utils.CommonUtils;

/**
 * Created by LiKe on 2016/11/28.
 */
public abstract class ShowingPage extends FrameLayout implements View.OnClickListener {

    /**
     * 定义5种状态
     *
     * @param context
     */

    public static final int STATE_UNLOAD = 1;
    public static final int STATE_LOADING = 2;
    public static final int STATE_LOAD_ERROR = 3;
    public static final int STATE_LOAD_EMPTY = 4;
    public static final int STATE_LOAD_SUCCESS = 5;


    //定义一个初始状态---- 默认当前为未加载状态
    public int currentState = STATE_LOADING;
    private View showingpage_unload;
    private View showingpage_load_error;
    private View showingpage_load_empty;
    private View showingpage_loading;
    private View showingpage_success;
    private final LayoutParams layoutParams;

    public ShowingPage(Context context) {
        super(context);
        //初始化界面，创建视图

        layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        //初始化未加载状态布局
        if (showingpage_unload == null) {
            showingpage_unload = CommonUtils.inflate(R.layout.showingpage_unload);
            this.addView(showingpage_unload, layoutParams);
        }
        //初始化加载错误布局
        if (showingpage_load_error == null) {
            showingpage_load_error = CommonUtils.inflate(R.layout.showingpage_load_error);
            Button bt_reload = (Button) showingpage_load_error.findViewById(R.id.bt_reload);
            bt_reload.setOnClickListener(this);
            this.addView(showingpage_load_error, layoutParams);
        }

        //初始化加载为空布局
        if (showingpage_load_empty == null) {
            showingpage_load_empty = CommonUtils.inflate(R.layout.showingpage_load_empty);
            this.addView(showingpage_load_empty, layoutParams);
        }
        //初始化正在加载状态布局
        if (showingpage_loading == null) {
            showingpage_loading = CommonUtils.inflate(R.layout.showingpage_loading);
            this.addView(showingpage_loading, layoutParams);
        }

        //添加展示不同状态的布局
        showPage();
        //数据加载
        onLoad();
    }

    //请求结束之后，设置当前状态，展示界面
    public void showCurrentPage(StateType stateType) {
        this.currentState = stateType.getCurrentState();
        showPage();
    }

    /**
     * 数据加载
     */
    protected abstract void onLoad();

    /**
     * 控制运行在主线程中
     */
    private void showPage() {
        CommonUtils.runOnMainThread(new Runnable() {
            @Override
            public void run() {
                showUIPage();
            }
        });
    }

    private void showUIPage() {
        //判断是否为为未加载状态
        if (showingpage_unload != null) {
            showingpage_unload.setVisibility(currentState == STATE_UNLOAD ? View.VISIBLE : View.GONE);
        }
        //判断是否为正在加载状态
        if (showingpage_loading != null) {
            showingpage_loading.setVisibility(currentState == STATE_LOADING ? View.VISIBLE : View.GONE);
        }
        //判断是否为加载错误
        if (showingpage_load_error != null) {
            showingpage_load_error.setVisibility(currentState == STATE_LOAD_ERROR ? View.VISIBLE : View.GONE);
        }
        //判断是否为加载为空
        if (showingpage_load_empty != null) {
            showingpage_load_empty.setVisibility(currentState == STATE_LOAD_EMPTY ? View.VISIBLE : View.GONE);
        }

        //成功的状态，加载成功界面
        if (showingpage_success == null && currentState == STATE_LOAD_SUCCESS) {
            //加载成功的界面，添加到当前的showingPage
            showingpage_success = createSuccessView();
            this.addView(showingpage_success, layoutParams);
        }

        if (showingpage_success != null) {
            showingpage_success.setVisibility(currentState == STATE_LOAD_SUCCESS ? View.VISIBLE : View.GONE);
        }
    }

    /**
     * 创建成功界面
     *
     * @return
     */
    public abstract View createSuccessView();

    /**
     * 重新加载按钮
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_reload:
                resetView();
                break;
        }
    }

    /**
     * 重置
     */
    private void resetView() {
        if (currentState != STATE_UNLOAD) {
            currentState = STATE_UNLOAD;
        }
        //展示界面
        showPage();
        //重新加载
        onLoad();
    }

    /**
     * 创建枚举类
     *STATE_LOAD_ERROR = 3;
     public static final int STATE_LOAD_EMPTY = 4;
     public static final int STATE_LOAD_SUCCESS = 5;
     */

    public enum StateType{
        STATE_LOADING(2),STATE_LOAD_ERROR(3),STATE_LOAD_EMPTY(4),STATE_LOAD_SUCCESS(5);
        private final int currentState;

        StateType(int currentState){
            this.currentState = currentState;
        }

        public int getCurrentState(){
            return currentState;
        }
    }
}
