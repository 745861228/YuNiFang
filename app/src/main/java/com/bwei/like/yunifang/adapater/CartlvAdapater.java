package com.bwei.like.yunifang.adapater;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bwei.like.yunifang.R;
import com.bwei.like.yunifang.activity.OrderParticularsActivity;
import com.bwei.like.yunifang.bean.CartDbBean;
import com.bwei.like.yunifang.dao.CartDao;
import com.bwei.like.yunifang.utils.CommonUtils;
import com.bwei.like.yunifang.utils.ImageLoaderUtils;
import com.bwei.like.yunifang.utils.LogUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.ArrayList;

/**
 * Created by LiKe on 2016/12/13.
 */
public class CartlvAdapater extends BaseAdapter implements View.OnClickListener{

    private  AutoRelativeLayout car_rela_noShop;
    private  AutoRelativeLayout cart_lv_relattiveLayout;
    private TextView include_middle_tv;
    private Context context;
    private ArrayList<CartDbBean> cartDbBeenList;
    private CheckBox all_select_cb;
    private TextView cart_sumMoney_tv;
    private Button pay_button;
    private TextView include_right_tv;
    private int tag = 0;
    private float sumMoney = 0;
    private boolean isFlag = true;
    private final CartDao cartDao;


    public CartlvAdapater(CheckBox all_select_cb, TextView cart_sumMoney_tv, ArrayList<CartDbBean> cartDbBeenList,
                          Context context, TextView include_right_tv, Button pay_button, TextView include_middle_tv,
                          AutoRelativeLayout cart_lv_relattiveLayout, AutoRelativeLayout car_rela_noShop) {
        this.all_select_cb = all_select_cb;
        this.cart_sumMoney_tv = cart_sumMoney_tv;
        this.cartDbBeenList = cartDbBeenList;
        this.context = context;
        this.include_right_tv = include_right_tv;
        this.pay_button = pay_button;
        this.include_middle_tv = include_middle_tv;
        this.cart_lv_relattiveLayout = cart_lv_relattiveLayout;
        this.car_rela_noShop = car_rela_noShop;
        all_select_cb.setOnClickListener(this);
        include_right_tv.setOnClickListener(this);
        pay_button.setOnClickListener(this);
        cartDao = new CartDao(context);
    }

    @Override
    public int getCount() {
        return cartDbBeenList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = CommonUtils.inflate(R.layout.fragment_car_lv_item);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ImageLoader.getInstance().displayImage(cartDbBeenList.get(position).getGoods_img(), viewHolder.cartlvitemimg, ImageLoaderUtils.initOptionsCircle());
        viewHolder.cartlvitemgoodsname.setText(cartDbBeenList.get(position).getGoods_name());
        viewHolder.cartlvitemshowprice.setText("￥ " + cartDbBeenList.get(position).getShow_price());
        viewHolder.cartlvitemgoodsnum.setText("数量:" + cartDbBeenList.get(position).getNumber());
        viewHolder.cartallcheckitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewHolder.cartallcheckitem.isChecked()) {
                    cartDbBeenList.get(position).setChecked(true);
                    sumMoney += (Float.parseFloat(cartDbBeenList.get(position).getShow_price())) * Integer.parseInt(cartDbBeenList.get(position).getNumber());
                    tag++;
                } else {
                    cartDbBeenList.get(position).setChecked(false);
                    sumMoney -= (Float.parseFloat(cartDbBeenList.get(position).getShow_price())) * Integer.parseInt(cartDbBeenList.get(position).getNumber());
                    tag--;
                }

                if (tag == cartDbBeenList.size()) {
                    all_select_cb.setText("取消全选");
                    all_select_cb.setChecked(true);
                } else {
                    all_select_cb.setText("全选");
                    all_select_cb.setChecked(false);
                }
                LogUtils.i("tag......", tag + "");
                cart_sumMoney_tv.setText("总计:￥" + sumMoney);
            }
        });
        viewHolder.cartallcheckitem.setChecked(cartDbBeenList.get(position).isChecked());
        if (isFlag) {
            viewHolder.cartlvitemlinearLayout.setVisibility(View.GONE);
            viewHolder.cartlvitemgoodsnum.setVisibility(View.VISIBLE);
            cartDao.updateNumber(cartDbBeenList.get(position).getGoods_name(), cartDbBeenList.get(position).getNumber());
            updateCartGoodsNumber(viewHolder.reducenumber, viewHolder.shownumber, viewHolder.addnumber, viewHolder.cartlvitemgoodsnum, position);
        } else {
            viewHolder.cartlvitemlinearLayout.setVisibility(View.VISIBLE);
            viewHolder.cartlvitemgoodsnum.setVisibility(View.INVISIBLE);
            viewHolder.cartlvitemgoodsnum.setText(cartDbBeenList.get(position).getNumber());
        }
        if (Integer.parseInt(cartDbBeenList.get(position).getNumber()) == 1) {
            viewHolder.reducenumber.setTextColor(Color.GRAY);
        }

        return convertView;
    }

    /***
     * 购物车中操作商品的数量
     *
     * @param reducenumber
     * @param shownumber
     * @param addnumber
     * @param cartlvitemgoodsnum
     */
    private void updateCartGoodsNumber(final Button reducenumber, final Button shownumber, final Button addnumber, TextView cartlvitemgoodsnum, final int position) {
        final CartDbBean bean = cartDbBeenList.get(position);
        shownumber.setText(bean.getNumber());

        reducenumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Integer.parseInt(bean.getNumber()) > 1) {
                    bean.setNumber("" + (Integer.parseInt(bean.getNumber()) - 1));
                    shownumber.setText(Integer.parseInt(bean.getNumber()) + "");
                    addnumber.setTextColor(Color.BLACK);
//                    sumMoney -= (Float.parseFloat(cartDbBeenList.get(position).getShow_price())) * Integer.parseInt(cartDbBeenList.get(position).getNumber());
//                    cart_sumMoney_tv.setText("总计:￥" + sumMoney);
                }
                if (Integer.parseInt(bean.getNumber()) == 1) {
                    Toast.makeText(context, "亲，不可以在点击了！", Toast.LENGTH_SHORT).show();
                    reducenumber.setTextColor(Color.GRAY);
                }
            }
        });

        addnumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(bean.getNumber()) < bean.getRestrict_purchase_num()) {
                    bean.setNumber("" + (Integer.parseInt(bean.getNumber()) + 1));
                    shownumber.setText(Integer.parseInt(bean.getNumber()) + "");
                    reducenumber.setTextColor(Color.BLACK);
                    float lastMoney = sumMoney;
//                    sumMoney += (Float.parseFloat(cartDbBeenList.get(position).getShow_price())) * Integer.parseInt(cartDbBeenList.get(position).getNumber());
//                    cart_sumMoney_tv.setText("总计:￥" + sumMoney);
                }

                if (Integer.parseInt(bean.getNumber()) == bean.getRestrict_purchase_num()) {
                    addnumber.setTextColor(Color.GRAY);
                    Toast.makeText(context, "本商品限购" + bean.getRestrict_purchase_num() + "件", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //全选按钮
            case R.id.all_select_cb:
                if (all_select_cb.isChecked()) {
                    for (int i = 0; i < cartDbBeenList.size(); i++) {
                        cartDbBeenList.get(i).setChecked(true);
                        sumMoney += (Float.parseFloat(cartDbBeenList.get(i).getShow_price())) * Integer.parseInt(cartDbBeenList.get(i).getNumber());
                    }
                    tag = cartDbBeenList.size();
                    all_select_cb.setText("取消全选");
                } else {
                    for (int i = 0; i < cartDbBeenList.size(); i++) {
                        cartDbBeenList.get(i).setChecked(false);
                        sumMoney -= (Float.parseFloat(cartDbBeenList.get(i).getShow_price())) * Integer.parseInt(cartDbBeenList.get(i).getNumber());
                    }
                    tag = 0;
                    sumMoney = 0;
                    all_select_cb.setText("全选");
                }
                cart_sumMoney_tv.setText("总计:￥" + sumMoney);
                break;

            //编辑的操作
            case R.id.include_right_tv:
                if (isFlag) {
                    include_right_tv.setText("完成");
                    cart_sumMoney_tv.setVisibility(View.GONE);
                    pay_button.setText("删除");
                } else {
                    include_right_tv.setText("编辑");
                    cart_sumMoney_tv.setVisibility(View.VISIBLE);
                    pay_button.setText("结算");
                }
                for (int i = 0; i < cartDbBeenList.size(); i++) {
                    cartDbBeenList.get(i).setChecked(false);
                    sumMoney -= (Float.parseFloat(cartDbBeenList.get(i).getShow_price())) * Integer.parseInt(cartDbBeenList.get(i).getNumber());
                }
                tag = 0;
                sumMoney = 0;
                all_select_cb.setText("全选");
                all_select_cb.setChecked(false);

                isFlag = !isFlag;
                cart_sumMoney_tv.setText("总计:￥" + sumMoney);
                include_middle_tv.setText("购物车(" + cartDbBeenList.size() + ")");
                break;
            //结算删除的操作
            case R.id.pay_button:

                //结算
                if (isFlag) {
                    if (tag == 0){
                        Toast.makeText(context, "您还没有选中任何商品哦", Toast.LENGTH_SHORT).show();
                    }else {
                        Intent intent = new Intent(context, OrderParticularsActivity.class);
                        ArrayList<CartDbBean> arrayList = new ArrayList<>();
                        for (int i = 0; i < cartDbBeenList.size(); i++) {
                            if (cartDbBeenList.get(i).isChecked()){
                                arrayList.add(cartDbBeenList.get(i));
                            }
                        }
                        intent.putExtra("arrayList",arrayList);
                        context.startActivity(intent);
                    }

                } else {     //删除操作
                    for (int i = cartDbBeenList.size()-1; i >=0 ; i--) {
                        if (cartDbBeenList.get(i).isChecked()) {
                            cartDao.deleteGoods(cartDbBeenList.get(i).getId());
                            cartDbBeenList.remove(i);
                        }
                    }
                    if (cartDbBeenList.size() == 0){
                        include_middle_tv.setText("购物车");
                        car_rela_noShop.setVisibility(View.VISIBLE);
                        cart_lv_relattiveLayout.setVisibility(View.GONE);
                    }else {
                        car_rela_noShop.setVisibility(View.GONE);
                        cart_lv_relattiveLayout.setVisibility(View.VISIBLE);
                        include_middle_tv.setText("购物车 ("+ cartDbBeenList.size()+")");
                    }
                }
                break;
        }
        notifyDataSetChanged();

    }



    public class ViewHolder {
        public final CheckBox cartallcheckitem;
        public final ImageView cartlvitemimg;
        public final TextView cartlvitemgoodsname;
        public final TextView cartlvitemshowprice;
        public final TextView cartlvitemgoodsnum;
        public final Button reducenumber;
        public final Button shownumber;
        public final Button addnumber;
        public AutoLinearLayout cartlvitemlinearLayout;
        public final View root;

        public ViewHolder(View root) {
            cartallcheckitem = (CheckBox) root.findViewById(R.id.cart_all_check_item);
            cartlvitemimg = (ImageView) root.findViewById(R.id.cart_lv_item_img);
            cartlvitemgoodsname = (TextView) root.findViewById(R.id.cart_lv_item_goods_name);
            cartlvitemshowprice = (TextView) root.findViewById(R.id.cart_lv_item_show_price);
            cartlvitemgoodsnum = (TextView) root.findViewById(R.id.cart_lv_item_goods_num);
            reducenumber = (Button) root.findViewById(R.id.reduce_number);
            shownumber = (Button) root.findViewById(R.id.show_number);
            addnumber = (Button) root.findViewById(R.id.add_number);
            cartlvitemlinearLayout = (AutoLinearLayout) root.findViewById(R.id.cart_lv_item_linearLayout);
            this.root = root;
        }
    }


}
