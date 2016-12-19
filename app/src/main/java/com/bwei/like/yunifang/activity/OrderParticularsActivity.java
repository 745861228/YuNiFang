package com.bwei.like.yunifang.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bwei.like.yunifang.R;
import com.bwei.like.yunifang.adapater.OrderParticularsAdapater;
import com.bwei.like.yunifang.alipay.PayDemoActivity;
import com.bwei.like.yunifang.base.BaseActivity;
import com.bwei.like.yunifang.bean.AddressBean;
import com.bwei.like.yunifang.bean.CartDbBean;
import com.bwei.like.yunifang.dao.AddressDao;
import com.bwei.like.yunifang.interfaces.OnItemClickSumMoneyListener;
import com.bwei.like.yunifang.utils.CommonUtils;
import com.bwei.like.yunifang.view.Home_ListView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class OrderParticularsActivity extends BaseActivity implements View.OnClickListener {

    private ImageView back_image;
    private TextView include_middle_tv;
    private Home_ListView indent_goods_lv;
    private ArrayList<CartDbBean> cartDbBeanArrayList;
    private TextView indent_goods_args_tv;
    private TextView indent_pay_tv;
    private Button pay_button;
    private RadioButton indent_rb_ali;
    private RadioButton indent_rb_wx;
    public double sumPayMoney;
    private LinearLayout addressLinearLayout;
    private TextView userName_tv;
    private TextView userAddress_tv;
    private TextView userPhone_tv;
    private TextView indent_address_tv;
    private AddressDao addressDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_particulars);
        addressDao = new AddressDao(this);

        initView();
        initViewListener();

        //获取数据
        Intent intent = getIntent();
        cartDbBeanArrayList = (ArrayList<CartDbBean>) intent.getSerializableExtra("arrayList");
        initDatas();

        //设置收货地址
        setAddressMonth();

    }

    private void setAddressMonth() {
        int addressId = CommonUtils.getInt("addressId");
        ArrayList<AddressBean> addressBeen = addressDao.selectIdUser(addressId + "");
        if (addressBeen.size()>0){
            indent_address_tv.setVisibility(View.GONE);
            addressLinearLayout.setVisibility(View.VISIBLE);
            userName_tv.setText(addressBeen.get(0).getUserName());
            userAddress_tv.setText(addressBeen.get(0).getUserAddress());
            userPhone_tv.setText(addressBeen.get(0).getUserPhone());
        }else {
            indent_address_tv.setVisibility(View.VISIBLE);
            addressLinearLayout.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setAddressMonth();
    }

    /**
     * 设置数据
     */
    private void initDatas() {

        int sumNumber = 0;
        double sumMoney = 0;
        for (int i = 0; i < cartDbBeanArrayList.size(); i++) {
            sumMoney += Double.parseDouble(cartDbBeanArrayList.get(i).getShow_price()) * Integer.parseInt(cartDbBeanArrayList.get(i).getNumber());
            sumNumber += Integer.parseInt(cartDbBeanArrayList.get(i).getNumber());
        }
        DecimalFormat df = new DecimalFormat("######0.00");
        String format = df.format(sumMoney);
        indent_goods_args_tv.setText("共计" + sumNumber + "件商品  " + "总计:￥" + format);
        indent_pay_tv.setText("实付:" + format);


        OrderParticularsAdapater orderParticularsAdapater = new OrderParticularsAdapater(cartDbBeanArrayList, this);
        indent_goods_lv.setAdapter(orderParticularsAdapater);
        orderParticularsAdapater.setOnItemClickListener(new OnItemClickSumMoneyListener() {
            @Override
            public void onItemClick(double sumMoney, int sumNumber) {
                OrderParticularsActivity.this.sumPayMoney = sumMoney;
                DecimalFormat df = new DecimalFormat("######0.00");
                String format = df.format(sumMoney);
                indent_goods_args_tv.setText("共计" + sumNumber + "件商品  " + "总计:￥" + format);
                indent_pay_tv.setText("实付:" + format);
            }
        });

    }

    private void initViewListener() {
        back_image.setOnClickListener(this);
        pay_button.setOnClickListener(this);
        addressLinearLayout.setOnClickListener(this);
        indent_address_tv.setOnClickListener(this);
    }

    /**
     * 初始化控件
     */
    private void initView() {
        back_image = (ImageView) findViewById(R.id.back_image);
        include_middle_tv = (TextView) findViewById(R.id.include_meddim_tv);
        include_middle_tv.setText("确认订单");

        indent_goods_lv = (Home_ListView) findViewById(R.id.indent_goods_lv);
        indent_goods_args_tv = (TextView) findViewById(R.id.indent_goods_args_tv);
        indent_pay_tv = (TextView) findViewById(R.id.indent_pay_tv);
        pay_button = (Button) findViewById(R.id.pay_button);

        indent_rb_ali = (RadioButton) findViewById(R.id.indent_rb_ali);
        indent_rb_wx = (RadioButton) findViewById(R.id.indent_rb_wx);

        //点击收货地址
        indent_address_tv = (TextView) findViewById(R.id.indent_address_tv);

        addressLinearLayout = (LinearLayout) findViewById(R.id.addressLinearLayout);
        userName_tv = (TextView) findViewById(R.id.userName_tv);
        userAddress_tv = (TextView) findViewById(R.id.userAddress_tv);
        userPhone_tv = (TextView) findViewById(R.id.userPhone_tv);


    }

    /**
     * 控件监听事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //图片返回按钮
            case R.id.back_image:
                finish();
                OrderParticularsActivity.this.overridePendingTransition(R.anim.login_in0, R.anim.login_out);
                break;
            //结算按钮操作
            case R.id.pay_button:
                if (indent_rb_ali.isChecked()) {
                    Toast.makeText(OrderParticularsActivity.this, "您选择支付宝付款", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(OrderParticularsActivity.this, PayDemoActivity.class);
                    intent.putExtra("sumPayMoney", 0.1);
                    startActivity(intent);
                } else if (indent_rb_wx.isChecked()) {
                    Toast.makeText(OrderParticularsActivity.this, "该功能尚未完善！", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(OrderParticularsActivity.this, "请选择付款方式！！", Toast.LENGTH_SHORT).show();
                }
                break;

            //点击添加收货地址监听事件
            case R.id.indent_address_tv:
                Intent intent2 = new Intent(OrderParticularsActivity.this, ListAddressActivity.class);
                startActivity(intent2);
                OrderParticularsActivity.this.overridePendingTransition(R.anim.login_in, R.anim.login_in0);
                break;

            //点击切换收货地址监听
            case R.id.addressLinearLayout:
                Intent intent = new Intent(OrderParticularsActivity.this, ListAddressActivity.class);
                startActivity(intent);
                OrderParticularsActivity.this.overridePendingTransition(R.anim.login_in, R.anim.login_in0);
                break;
        }
    }


}
