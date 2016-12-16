package com.bwei.like.yunifang.fragment;

import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bwei.like.yunifang.MainActivity;
import com.bwei.like.yunifang.R;
import com.bwei.like.yunifang.adapater.CartlvAdapater;
import com.bwei.like.yunifang.base.BaseFragment;
import com.bwei.like.yunifang.bean.CartDbBean;
import com.bwei.like.yunifang.dao.CartDao;
import com.bwei.like.yunifang.utils.CommonUtils;
import com.bwei.like.yunifang.view.ShowingPage;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.ArrayList;

/**
 * Created by LiKe on 2016/11/28.
 */
public class CartFragment extends BaseFragment implements View.OnClickListener {

    private View inflate;
    private ImageView back_image;
    private TextView include_middle_tv;
    private TextView include_right_tv;
    private ListView cart_listView;
    private CartDao cartDao;
    private CheckBox all_select_cb;
    private TextView cart_sumMoney_tv;
    private Button pay_button;
    private AutoRelativeLayout car_rela_noShop;
    private Button noshop_btn;
    private AutoRelativeLayout cart_lv_relattiveLayout;
    private ArrayList<CartDbBean> cartDbBeenList;
    private CartlvAdapater cartlvAdapater;

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
        include_right_tv.setTextColor(Color.BLACK);

        cart_lv_relattiveLayout = (AutoRelativeLayout) inflate.findViewById(R.id.cart_lv_relattiveLayout);
        cart_listView = (ListView) inflate.findViewById(R.id.cart_listView);
        all_select_cb = (CheckBox) inflate.findViewById(R.id.all_select_cb);
        cart_sumMoney_tv = (TextView) inflate.findViewById(R.id.cart_sumMoney_tv);
        pay_button = (Button) inflate.findViewById(R.id.pay_button);
        car_rela_noShop = (AutoRelativeLayout) inflate.findViewById(R.id.car_rela_noShop);
        noshop_btn = (Button) inflate.findViewById(R.id.noshop_btn);
        noshop_btn.setOnClickListener(this);

        cartDbBeenList = cartDao.selectGoods();
        setCurrentView();

    }

    private void setCurrentView() {
        if (cartDbBeenList.size() == 0){
            include_middle_tv.setText("购物车");
            include_middle_tv.setTextColor(Color.BLACK);
            car_rela_noShop.setVisibility(View.VISIBLE);
            cart_lv_relattiveLayout.setVisibility(View.GONE);
        }else {
            car_rela_noShop.setVisibility(View.GONE);
            cart_lv_relattiveLayout.setVisibility(View.VISIBLE);
            include_middle_tv.setText("购物车 ("+ cartDbBeenList.size()+")");
            include_middle_tv.setTextColor(Color.BLACK);
            cartlvAdapater = new CartlvAdapater(all_select_cb, cart_sumMoney_tv, cartDbBeenList, getActivity(), include_right_tv, pay_button,include_middle_tv,cart_lv_relattiveLayout,car_rela_noShop);
            cart_listView.setAdapter(cartlvAdapater);
        }
    }


    @Override
    protected void onLoad() {
        CartFragment.this.showingPage(ShowingPage.StateType.STATE_LOAD_SUCCESS);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.noshop_btn:
                MainActivity mainActivity = (MainActivity) getActivity();
                RadioButton rb = (RadioButton) mainActivity.getMain_radioGroup().getChildAt(0);
                rb.setChecked(true);
                FragmentManager supportFragmentManager = getActivity().getSupportFragmentManager();
                supportFragmentManager.popBackStack("homeFragment",0);
                break;

//            case R.id.pay_button:
//                setCurrentView();
//                break;
        }
    }
}
