package com.bwei.like.yunifang.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.TextView;

import com.bwei.like.yunifang.R;
import com.bwei.like.yunifang.base.BaseActivity;
import com.bwei.like.yunifang.base.BaseDataxUtils;
import com.bwei.like.yunifang.bean.AllGoodsRoot;
import com.bwei.like.yunifang.utils.UrlUtils;
import com.bwei.like.yunifang.view.ShowingPage;
import com.google.gson.Gson;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

public class SelectAllActivity extends BaseActivity implements SpringView.OnFreshListener {

    private TextView include_middle_tv;
    private Button default_but;
    private Button up_but;
    private Button down_but;
    private SpringView select_springView;
    private RecyclerView select_recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectall);
        //初始化控件
       initView();
        //初始化数据
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        BaseDataxUtils baseDataxUtils = new BaseDataxUtils() {
            @Override
            protected void setResulttError(ShowingPage.StateType stateLoadError) {

            }

            @Override
            public void setResultData(String resultData) {
                //解析数据
                Gson gson = new Gson();
                AllGoodsRoot allGoodsRoot = gson.fromJson(resultData, AllGoodsRoot.class);
                if (allGoodsRoot!=null&&allGoodsRoot.data!=null){

                }
            }
        };
        baseDataxUtils.getData(UrlUtils.ALLGOODSURL,null,0,BaseDataxUtils.NOTIME);
    }

    /**
     * 初始化控件
     */
    private void initView() {
        include_middle_tv = (TextView) findViewById(R.id.include_meddim_tv);
        default_but = (Button) findViewById(R.id.default_but);
        up_but = (Button) findViewById(R.id.up_but);
        down_but = (Button) findViewById(R.id.down_but);
        select_springView = (SpringView) findViewById(R.id.select_springView);
        select_recyclerView = (RecyclerView) findViewById(R.id.select_recyclerView);

        //设置下拉刷新，上拉加载
        select_springView.setListener(this);
        //设置springView默认头和尾
        select_springView.setHeader(new DefaultHeader(this));
        select_springView.setFooter(new DefaultFooter(this));
        //设置springView头部隐藏
        select_springView.setType(SpringView.Type.FOLLOW);
    }


    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadmore() {

    }

    //停止刷新
    public void lode() {
        select_springView.scrollTo(0, 0);
    }
}
