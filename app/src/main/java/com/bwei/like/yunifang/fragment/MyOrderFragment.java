package com.bwei.like.yunifang.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bwei.like.yunifang.R;
import com.bwei.like.yunifang.bean.OrderStateBean;
import com.bwei.like.yunifang.dao.MyOrderDao;
import com.bwei.like.yunifang.recyclerview_adapater.MyOrderRecyclerViewAdapater;
import com.bwei.like.yunifang.utils.CommonUtils;
import com.bwei.like.yunifang.utils.LogUtils;

import java.util.ArrayList;

/**
 * author by LiKe on 2016/12/26.
 */

public class MyOrderFragment extends Fragment {

    private View view;
    private MyOrderDao myOrderDao;
    private RecyclerView myOrderFragment_recyclerView;
    private ArrayList<ArrayList<OrderStateBean>> orderStateBeenList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = CommonUtils.inflate(R.layout.myorderfragment_item);
        myOrderFragment_recyclerView = (RecyclerView) view.findViewById(R.id.myOrderFragment_recyclerView);
        myOrderDao = new MyOrderDao(getActivity());
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //获取对应的状态
        String state = this.getArguments().getString("state");


        myOrderFragment_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myOrderFragment_recyclerView.setAdapter(new MyOrderRecyclerViewAdapater(myOrderDao.selectAll(state), getActivity()));
    }


    public static Fragment getFragment(String state) {
        MyOrderFragment fragment = new MyOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putString("state", state);
        fragment.setArguments(bundle);
        return fragment;
    }
}
