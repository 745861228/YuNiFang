package com.bwei.like.yunifang.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.bwei.like.yunifang.R;
import com.bwei.like.yunifang.adapater.OrderParticularsAdapater;
import com.bwei.like.yunifang.alipay2.PayResult;
import com.bwei.like.yunifang.alipay2.SignUtils;
import com.bwei.like.yunifang.base.BaseActivity;
import com.bwei.like.yunifang.bean.AddressBean;
import com.bwei.like.yunifang.bean.CartDbBean;
import com.bwei.like.yunifang.bean.OrderStateBean;
import com.bwei.like.yunifang.dao.AddressDao;
import com.bwei.like.yunifang.dao.CartDao;
import com.bwei.like.yunifang.dao.MyOrderDao;
import com.bwei.like.yunifang.interfaces.OnItemClickSumMoneyListener;
import com.bwei.like.yunifang.utils.CommonUtils;
import com.bwei.like.yunifang.view.Home_ListView;
import com.ta.utdid2.android.utils.SystemUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

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
    private String goodsName = "";
    private String goodsPrice;


    public static final String PARTNER = "2088901305856832";

    // 商户收款账号
    public static final String SELLER = "8@qdbaiu.com";

    // 商户私钥，pkcs8格式
    public static final String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAM/KCxg/OIj6er2GEig0DOkHqBSzEPHGigMbTXP1k2nrxEHeE59xOOuyovQH/A1LgbuVKyOac3uAN4GXIBEoozRVzDhu5dobeNm48BPcpYSAfvN3K/5GLacvJeENqsiBx8KufM/9inlHaDRQV7WhX1Oe2ckat1EkdHwxxQgc36NhAgMBAAECgYEAwn3sWpq6cUR65LD8h9MIjopTImTlpFjgz72bhsHDZK6A+eJDXcddrwh7DI34t/0IBqu+QEoOc/f0fIEXS9hMwTvFY59XG7M8M6SdeaAbemrGmZ1IdD6YDmpbQFHn2ishaYF0YDZIkBS3WLDFrtk/efaarBCpGAVXeEiVQE4LewECQQD5W1rpkq+dHDRzzdtdi1bJ479wun5CfmVDVb2CJH7Iz0t8zyp/iEVV2QELnxZMphmoSzKaLXutTTj2OImpzCtRAkEA1VMxG6nQq9NkU51H1+I3mlUXRZ0XeFA1GFJ7xWpNRAVhEWlDz2zy9v/gX+RFpNC3f5uznycas70Xp78ew43TEQJAZFFqi9mlqTF1sLk67bFnIyXrGPEOZrXvC13tNfR0xVkQZ4/46wHp0xXQo9pG4GNaoyhNnVV7EkelCPnJ+HPZYQJAQh6T9QgQZoGR8hyovPAf3dUL7oa/VIo/urcuJ8VIB5JHQNdIrk0NjaNHj1E4iNosVgATj3vWWel9IIArb99QkQJAKvfm78lwnImtg5IM604hdn/Wu1XF8tpxsKLWcnfchMr0bM9rCmKmhAY+wdmqSyPZRiNb1QaaaDTqJxLy6AnQ+Q==";

    private static final int SDK_PAY_FLAG = 1;


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(OrderParticularsActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        for (int i = 0; i < cartDbBeanArrayList.size(); i++) {
                            myOrderDao.updateGoodsState(cartDbBeanArrayList.get(i).getId(),"待收货");
                        }
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(OrderParticularsActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(OrderParticularsActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }

    };
    private MyOrderDao myOrderDao;
    private CartDao cartDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_particulars);
        addressDao = new AddressDao(this);
        myOrderDao = new MyOrderDao(this);
        cartDao = new CartDao(this);


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
        if (addressBeen.size() > 0) {
            indent_address_tv.setVisibility(View.GONE);
            addressLinearLayout.setVisibility(View.VISIBLE);
            userName_tv.setText(addressBeen.get(0).getUserName());
            userAddress_tv.setText(addressBeen.get(0).getUserAddress());
            userPhone_tv.setText(addressBeen.get(0).getUserPhone());
        } else {
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
            goodsName += cartDbBeanArrayList.get(i).getGoods_name() + "";
        }
        DecimalFormat df = new DecimalFormat("######0.00");
        String format = df.format(sumMoney);
        goodsPrice = format;
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
                    //当我点击结算按钮的时候我把对用的数据保存进数据库，默认状态为代付款，同时把购物车数据库中的数据删除
                    for (int i = 0; i < cartDbBeanArrayList.size(); i++) {
                        //删除购物车数据
                        cartDao.deleteGoods(cartDbBeanArrayList.get(i).getId());
                    }
                    myOrderDao.addMyOrder(cartDbBeanArrayList,"待付款");

                    aliPay();

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

    private void aliPay() {
        if (TextUtils.isEmpty(PARTNER) || TextUtils.isEmpty(RSA_PRIVATE) || TextUtils.isEmpty(SELLER)) {
            new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置PARTNER | RSA_PRIVATE| SELLER")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            //
                            finish();
                        }
                    }).show();
            return;
        }


        String orderInfo = getOrderInfo(goodsName, "该测试商品的详细描述", goodsPrice);

/**
 * 特别注意，这里的签名逻辑需要放在服务端，切勿将私钥泄露在代码中！
 */
        String sign = sign(orderInfo);
        try {
            /**
             * 仅需对sign 做URL编码
             */
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

/**
 * 完整的符合支付宝参数规范的订单信息
 */
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(OrderParticularsActivity.this);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo, true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

// 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }


    /**
     * create the order info. 创建订单信息
     */
    private String getOrderInfo(String subject, String body, String price) {

        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm" + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content 待签名订单信息
     */
    private String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE);
    }

    /**
     * get the sign type we use. 获取签名方式
     */
    private String getSignType() {
        return "sign_type=\"RSA\"";
    }

    /**
     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
     */
    private String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }


}
