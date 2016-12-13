package com.bwei.like.yunifang.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bwei.like.yunifang.R;
import com.bwei.like.yunifang.adapater.CartlvAdapater;
import com.bwei.like.yunifang.adapater.CommonAdapter;
import com.bwei.like.yunifang.adapater.ViewHolder;
import com.bwei.like.yunifang.application.MyApplication;
import com.bwei.like.yunifang.base.BaseFragment;
import com.bwei.like.yunifang.bean.CartDbBean;
import com.bwei.like.yunifang.dao.CartDao;
import com.bwei.like.yunifang.utils.CommonUtils;
import com.bwei.like.yunifang.view.ShowingPage;

import java.util.ArrayList;

/**
 * Created by LiKe on 2016/11/28.
 */
public class CartFragment extends BaseFragment {

    private View inflate;
    private ImageView back_image;
    private TextView include_middle_tv;
    private TextView include_right_tv;
    private ListView cart_listView;
    private CartDao cartDao;
    private CheckBox all_select_cb;
    private TextView cart_sumMoney_tv;
    private Button pay_button;

    @Override
    protected View createSuccessView() {
        inflate = CommonUtils.inflate(R.layout.cartfragment_item);
        cartDao = new CartDao(getActivity());
        initView();
        return inflate;
    }

    /**
     * 初始化控件
     */
    private void initView() {
        back_image = (ImageView) inflate.findViewById(R.id.back_image);
        back_image.setVisibility(View.GONE);
        include_middle_tv = (TextView) inflate.findViewById(R.id.include_meddim_tv);
        include_right_tv = (TextView) inflate.findViewById(R.id.include_right_tv);
        include_right_tv.setVisibility(View.VISIBLE);
        include_right_tv.setText("编辑");
        cart_listView = (ListView) inflate.findViewById(R.id.cart_listView);
        all_select_cb = (CheckBox) inflate.findViewById(R.id.all_select_cb);
        cart_sumMoney_tv = (TextView) inflate.findViewById(R.id.cart_sumMoney_tv);
        pay_button = (Button) inflate.findViewById(R.id.pay_button);


        ArrayList<CartDbBean> cartDbBeenList =  cartDao.selectGoods();
        if (cartDbBeenList.size() == 0){
            include_middle_tv.setText("购物车");
        }else {
            include_middle_tv.setText("购物车 ("+cartDbBeenList.size()+")");
            cart_listView.setAdapter(new CartlvAdapater(all_select_cb,cart_sumMoney_tv,cartDbBeenList,getActivity(),include_right_tv,pay_button));
        }

    }


    @Override
    protected void onLoad() {
        CartFragment.this.showingPage(ShowingPage.StateType.STATE_LOAD_SUCCESS);
    }
}
