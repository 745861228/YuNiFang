package com.bwei.like.yunifang.activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwei.like.yunifang.Itemdecoration.DividerGridItemDecoration;
import com.bwei.like.yunifang.R;
import com.bwei.like.yunifang.base.BaseActivity;
import com.bwei.like.yunifang.base.BaseDataxUtils;
import com.bwei.like.yunifang.bean.AllGoodsRoot;
import com.bwei.like.yunifang.interfaces.OnItemClickListener;
import com.bwei.like.yunifang.recyclerview_adapater.AllGoodsAdapater;
import com.bwei.like.yunifang.utils.CommonUtils;
import com.bwei.like.yunifang.utils.LogUtils;
import com.bwei.like.yunifang.utils.UrlUtils;
import com.bwei.like.yunifang.view.ShowingPage;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SelectAllActivity extends BaseActivity implements SpringView.OnFreshListener, View.OnClickListener {

    private TextView include_middle_tv;
    private Button default_but;
    private Button up_but;
    private Button down_but;
    private SpringView select_springView;
    private RecyclerView select_recyclerView;
    private ImageView back_image;
    private AllGoodsAdapater allGoodsAdapater;
    private ArrayList<AllGoodsRoot.DataBean> dataBeanArrayList = new ArrayList<>();



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
                LogUtils.i("TAG*********", resultData);
                initGsonData(resultData);

            }
        };
        baseDataxUtils.getData(UrlUtils.ALLGOODSURL, null, 0, BaseDataxUtils.NOTIME);
    }

    /**
     * 解析Gson数据
     * @param resultData
     */
    private void initGsonData(String resultData) {
        //解析数据
        Gson gson = new Gson();
        AllGoodsRoot allGoodsRoot = gson.fromJson(resultData, AllGoodsRoot.class);
        if (allGoodsRoot != null && allGoodsRoot.data != null) {
            dataBeanArrayList.addAll(allGoodsRoot.data);
            select_recyclerView.addItemDecoration(new DividerGridItemDecoration(CommonUtils.dip2px(5)));
            select_recyclerView.setLayoutManager(new GridLayoutManager(SelectAllActivity.this, 2, GridLayoutManager.VERTICAL, false));
            for (int i = 0; i < dataBeanArrayList.size(); i++) {
                for (int j = dataBeanArrayList.size() - 1; j > i; j--) {
                    if (dataBeanArrayList.get(i).goods_name.equals(dataBeanArrayList.get(j).goods_name)) {
                        dataBeanArrayList.remove(j);
                    }
                }
            }

            if (allGoodsAdapater == null) {
                allGoodsAdapater = new AllGoodsAdapater(dataBeanArrayList, SelectAllActivity.this);
                select_recyclerView.setAdapter(allGoodsAdapater);
            } else {
                allGoodsAdapater.notifyDataSetChanged();
            }
        }
        allGoodsAdapater.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(SelectAllActivity.this,Particulars_Activity.class);
                intent.putExtra("id",dataBeanArrayList.get(position).id);
                startActivity(intent);
                SelectAllActivity.this.overridePendingTransition(R.anim.login_in,R.anim.login_in0);
            }
        });

    }

    /**
     * 初始化控件
     */
    private void initView() {
        back_image = (ImageView) findViewById(R.id.back_image);
        include_middle_tv = (TextView) findViewById(R.id.include_meddim_tv);
        include_middle_tv.setText("全部商品");
        default_but = (Button) findViewById(R.id.default_but);
        up_but = (Button) findViewById(R.id.up_but);
        down_but = (Button) findViewById(R.id.down_but);
        select_springView = (SpringView) findViewById(R.id.select_springView);
        select_recyclerView = (RecyclerView) findViewById(R.id.select_recyclerView);

        back_image.setOnClickListener(this);
        default_but.setOnClickListener(this);
        up_but.setOnClickListener(this);
        down_but.setOnClickListener(this);


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
        initData();
        lode();
    }

    @Override
    public void onLoadmore() {

    }

    //停止刷新
    public void lode() {
        select_springView.scrollTo(0, 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //点击返回图片
            case R.id.back_image:
                finish();
                SelectAllActivity.this.overridePendingTransition(R.anim.login_in0, R.anim.login_out);
                break;
            //默认排序
            case R.id.default_but:
                default_but.setTextColor(getResources().getColor(R.color.YuniFangZhangHao_textColor));
                default_but.setBackgroundResource(R.drawable.button_underline);
                up_but.setTextColor(Color.BLACK);
                up_but.setBackground(null);
                down_but.setTextColor(Color.BLACK);
                down_but.setBackground(null);
                break;
            //价格升序
            case R.id.up_but:
                default_but.setTextColor(Color.BLACK);
                default_but.setBackground(null);
                up_but.setTextColor(getResources().getColor(R.color.YuniFangZhangHao_textColor));
                up_but.setBackgroundResource(R.drawable.button_underline);
                down_but.setTextColor(Color.BLACK);
                down_but.setBackground(null);
                Collections.sort(dataBeanArrayList);
                allGoodsAdapater.notifyDataSetChanged();
                break;
            //价格降序
            case R.id.down_but:
                default_but.setTextColor(Color.BLACK);
                default_but.setBackground(null);
                up_but.setTextColor(Color.BLACK);
                up_but.setBackground(null);
                down_but.setTextColor(getResources().getColor(R.color.YuniFangZhangHao_textColor));
                down_but.setBackgroundResource(R.drawable.button_underline);
                descPrice();
                allGoodsAdapater.notifyDataSetChanged();
                break;


        }
    }


    /**
     * 降序排序
     */
    public void descPrice(){
        Collections.sort(dataBeanArrayList, new Comparator<AllGoodsRoot.DataBean>() {
            @Override
            public int compare(AllGoodsRoot.DataBean lhs, AllGoodsRoot.DataBean rhs) {
                if (lhs.shop_price>rhs.shop_price){
                    return -1;
                }
                return 1;
            }
        });
    }


}
