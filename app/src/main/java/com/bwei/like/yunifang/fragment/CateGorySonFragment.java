package com.bwei.like.yunifang.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwei.like.yunifang.Itemdecoration.DividerGridItemDecoration;
import com.bwei.like.yunifang.R;
import com.bwei.like.yunifang.activity.Particulars_Activity;
import com.bwei.like.yunifang.activity.SelectAllActivity;
import com.bwei.like.yunifang.base.BaseDataxUtils;
import com.bwei.like.yunifang.base.BaseFragment;
import com.bwei.like.yunifang.bean.CateGorySonFragmentRoot;
import com.bwei.like.yunifang.interfaces.OnItemClickListener;
import com.bwei.like.yunifang.recyclerview_adapater.CateGorySonFragmentAdapater;
import com.bwei.like.yunifang.utils.CommonUtils;
import com.bwei.like.yunifang.utils.LogUtils;
import com.bwei.like.yunifang.utils.UrlUtils;
import com.bwei.like.yunifang.view.ShowingPage;
import com.google.gson.Gson;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiKe on 2016/12/10.
 */
public class CateGorySonFragment extends BaseFragment implements SpringView.OnFreshListener {

    private SpringView mSpringView;
    private RecyclerView mRecyclerView;
    private ArrayList<CateGorySonFragmentRoot.DataBean> data;
    private View view;
    private ImageView back_image;

    @Override
    protected View createSuccessView() {
        view = CommonUtils.inflate(R.layout.category_son_fragment);

        initView();
        return view;
    }

    private void initData() {
        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(CommonUtils.dip2px(5)));
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2,GridLayoutManager.VERTICAL,false));
        CateGorySonFragmentAdapater cateGorySonFragmentAdapater = new CateGorySonFragmentAdapater(data, getActivity());
        mRecyclerView.setAdapter(cateGorySonFragmentAdapater);
        cateGorySonFragmentAdapater.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getActivity(), Particulars_Activity.class);
                intent.putExtra("id",data.get(position).id);
                getActivity().startActivity(intent);
                getActivity().overridePendingTransition(R.anim.login_in,R.anim.login_in0);
            }
        });
    }

    /**
     * 初始化控件
     */
    private void initView() {
        mSpringView = (SpringView) view.findViewById(R.id.mSpringView);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.mRecyclerView);
        //设置下拉刷新，上拉加载
        mSpringView.setListener(this);
        //设置springView默认头和尾
        mSpringView.setHeader(new DefaultHeader(getActivity()));
        // home_springView.setFooter(new DefaultFooter(getActivity()));
        //设置springView头部隐藏
        mSpringView.setType(SpringView.Type.FOLLOW);
        TextView include_meddim_tv = (TextView) mSpringView.findViewById(R.id.include_meddim_tv);

    }

    @Override
    protected void onLoad() {
        //获取id值
        String id = CateGorySonFragment.this.getArguments().getString("id", null);
        MyBaseData myBaseData = new MyBaseData();
        myBaseData.getData(UrlUtils.CATEGORY_ID+id,"",0,BaseDataxUtils.NOTIME);
    }


    public static Fragment getFragment(String id){
        CateGorySonFragment fragment = new CateGorySonFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id",id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadmore() {

    }

    //停止刷新
    public void lode() {
        mSpringView.scrollTo(0, 0);
    }

    class MyBaseData extends BaseDataxUtils{

        @Override
        protected void setResulttError(ShowingPage.StateType stateLoadError) {
            showingPage(ShowingPage.StateType.STATE_LOAD_ERROR);
        }

        @Override
        public void setResultData(String resultData) {

            LogUtils.i("-----resultData",resultData);
            showingPage(ShowingPage.StateType.STATE_LOAD_SUCCESS);
            Gson gson = new Gson();
            CateGorySonFragmentRoot cateGorySonFragmentRoot = gson.fromJson(resultData, CateGorySonFragmentRoot.class);
            LogUtils.i("-----cateGorySonFragmentRoot",cateGorySonFragmentRoot.data.size()+"------------------");

            if (cateGorySonFragmentRoot!=null&&cateGorySonFragmentRoot.data!=null){
                data = (ArrayList<CateGorySonFragmentRoot.DataBean>) cateGorySonFragmentRoot.data;
            }else {
                showingPage(ShowingPage.StateType.STATE_LOAD_ERROR);
            }

           getActivity().runOnUiThread(new Runnable() {
               @Override
               public void run() {
                   // 展示数据
                   initData();
               }
           });
        }
    }
}
