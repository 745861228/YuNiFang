package com.bwei.like.yunifang.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwei.like.yunifang.Itemdecoration.DividerGridItemDecoration;
import com.bwei.like.yunifang.R;
import com.bwei.like.yunifang.base.BaseActivity;
import com.bwei.like.yunifang.base.BaseDataxUtils;
import com.bwei.like.yunifang.base.BaseFragment;
import com.bwei.like.yunifang.bean.AllGoodsRoot;
import com.bwei.like.yunifang.bean.CategoryRoot;
import com.bwei.like.yunifang.fragment.CategoryFragment;
import com.bwei.like.yunifang.interfaces.OnItemClickListener;
import com.bwei.like.yunifang.recyclerview_adapater.AllGoodsAdapater;
import com.bwei.like.yunifang.utils.CommonUtils;
import com.bwei.like.yunifang.utils.LogUtils;
import com.bwei.like.yunifang.utils.UrlUtils;
import com.bwei.like.yunifang.view.ShowingPage;
import com.google.gson.Gson;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.ArrayList;

public class CateGoryOtherActivity extends BaseActivity implements SpringView.OnFreshListener, View.OnClickListener {

    private SpringView mSpringView;
    private RecyclerView mRecyclerView;
    private CategoryRoot.DataBean.CategoryBean.ChildrenBean childrenBean;
    private AllGoodsAdapater allGoodsAdapater;
    private AllGoodsRoot allGoodsRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_other);
        //获取数据
        Intent intent = getIntent();
        childrenBean = (CategoryRoot.DataBean.CategoryBean.ChildrenBean) intent.getSerializableExtra("childrenBean");

        initView();
        initDatas();
    }

    private void initDatas() {
        MyBaseData myBaseData = new MyBaseData();
        myBaseData.getData(UrlUtils.CATEGORY_ID + childrenBean.id, null, 0, BaseDataxUtils.NOTIME);
    }

    private void initView() {
        ImageView back_image = (ImageView) findViewById(R.id.back_image);
        back_image.setOnClickListener(this);
        TextView include_meddim_tv = (TextView) findViewById(R.id.include_meddim_tv);
        include_meddim_tv.setText(childrenBean.cat_name);
        mSpringView = (SpringView) findViewById(R.id.mSpringView);
        mRecyclerView = (RecyclerView) findViewById(R.id.mRecyclerview);

        //设置下拉刷新，上拉加载
        mSpringView.setListener(this);
        //设置springView默认头和尾
        mSpringView.setHeader(new DefaultHeader(this));
        // home_springView.setFooter(new DefaultFooter(getActivity()));
        //设置springView头部隐藏
        mSpringView.setType(SpringView.Type.FOLLOW);
    }


    /**
     * 请求数据
     */
    class MyBaseData extends BaseDataxUtils {

        @Override
        protected void setResulttError(ShowingPage.StateType stateLoadError) {

        }

        @Override
        public void setResultData(String resultData) {
            //解析数据
            Gson gson = new Gson();
            allGoodsRoot = gson.fromJson(resultData, AllGoodsRoot.class);
            mRecyclerView.addItemDecoration(new DividerGridItemDecoration(CommonUtils.dip2px(5)));
            mRecyclerView.setLayoutManager(new GridLayoutManager(CateGoryOtherActivity.this, 2, GridLayoutManager.VERTICAL, false));
            allGoodsAdapater = new AllGoodsAdapater((ArrayList<AllGoodsRoot.DataBean>) allGoodsRoot.data, CateGoryOtherActivity.this);
            mRecyclerView.setAdapter(allGoodsAdapater);

            /**
             * 条目监听事件
             *
             */

            allGoodsAdapater.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Intent intent = new Intent(CateGoryOtherActivity.this,Particulars_Activity.class);
                    intent.putExtra("id",allGoodsRoot.data.get(position).id);
                    startActivity(intent);
                    CateGoryOtherActivity.this.overridePendingTransition(R.anim.login_in,R.anim.login_in0);
                }
            });
        }
    }


    @Override
    public void onRefresh() {
        initDatas();
        lode();
    }

    @Override
    public void onLoadmore() {

    }

    //停止刷新
    public void lode() {
        mSpringView.scrollTo(0, 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_image:
                finish();
                CateGoryOtherActivity.this.overridePendingTransition(R.anim.login_in0, R.anim.login_out);
                break;
        }
    }
}
