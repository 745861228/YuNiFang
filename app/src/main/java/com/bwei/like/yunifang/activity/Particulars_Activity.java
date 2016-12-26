package com.bwei.like.yunifang.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bwei.like.yunifang.MainActivity;
import com.bwei.like.yunifang.R;
import com.bwei.like.yunifang.adapater.CommonAdapter;
import com.bwei.like.yunifang.adapater.ViewHolder;
import com.bwei.like.yunifang.application.MyApplication;
import com.bwei.like.yunifang.base.BaseActivity;
import com.bwei.like.yunifang.base.BaseDataxUtils;
import com.bwei.like.yunifang.bean.CartDbBean;
import com.bwei.like.yunifang.bean.GoodsDesc;
import com.bwei.like.yunifang.bean.HomeRoot;
import com.bwei.like.yunifang.bean.ParticularsRoot;
import com.bwei.like.yunifang.dao.CartDao;
import com.bwei.like.yunifang.recyclerview_adapater.ProCommentAdapater;
import com.bwei.like.yunifang.recyclerview_adapater.ProDetailAdapater;
import com.bwei.like.yunifang.recyclerview_adapater.ProDetailsAdapater;
import com.bwei.like.yunifang.utils.CommonUtils;
import com.bwei.like.yunifang.utils.ImageLoaderUtils;
import com.bwei.like.yunifang.utils.LogUtils;
import com.bwei.like.yunifang.utils.UrlUtils;
import com.bwei.like.yunifang.view.Home_ListView;
import com.bwei.like.yunifang.view.MyRoolViewPager;
import com.bwei.like.yunifang.view.MyScrollView;
import com.bwei.like.yunifang.view.ShowingPage;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zhy.autolayout.AutoLinearLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Particulars_Activity extends BaseActivity implements View.OnClickListener, MyScrollView.OnScrollListener {

    private TextView include_middle_tv;
    private ImageView include_back_image;
    private ImageView include_share_image;
    private ImageView include_image_goods_shopping_cart;
    private String data;
    private ParticularsRoot particularsRoot;
    private MyRoolViewPager particulars_myRoolViewPager;
    int[] dotArray = new int[]{R.mipmap.page_indicator_focused, R.mipmap.page_indicator_unfocused};
    ArrayList<String> imageUrlList = new ArrayList<>();
    ArrayList<ImageView> dotimageList = new ArrayList<>();
    private AutoLinearLayout particulars_docs_linearLayout;
    private TextView goods_name;
    private TextView show_price;
    private TextView market_price;
    private CheckBox collection_cb;
    private TextView carriage;
    private TextView sales_volume;
    private TextView stock_number;
    private Home_ListView particulars_activity_listView;

    private String[] mTitles = new String[]{"产品详情", "产品参数", "评论"};
    private AutoLinearLayout search1_LinearLayout, search2_LinearLayout;
    private MyScrollView myScrollView;
    private int searchLayoutTop;
    private RecyclerView particulars_recyclerView;
    private TextView pro_details_tv, pro_detail_top_tv, pro_details_top_tv, pro_comment_top_tv;
    private TextView pro_detail_tv;
    private TextView pro_comment_tv;
    private GoodsDesc[] goodsBeen;
    private ProDetailsAdapater proDetailsAdapater;
    private ProDetailAdapater proDetailAdapater;
    private Button but_add_shopCart;
    private Button but_buy;
    private PopupWindow popupWindow;
    private int defaultShowNumber = 1;
    private boolean isFlag = true;
    private boolean showPop;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    backgroundAlpha((float) msg.obj);
                    break;
            }
        }
    };
    private Button reduce_number;
    private Button show_number;
    private Button add_number;
    private Button cart_pop_sure_button;
    private ImageView pop_cart_pop_image;
    private TextView pop_shop_price;
    private TextView pop_stock_number;
    private TextView pop_restrict_purchase_num;
    private CartDao cartDao;
    private Button share_wxchat;
    private Button share_wxsession;
    private Button share_qzone;
    private Button share_weibo;
    private Button share_qq;
    private Button but_cancel;
    private ProCommentAdapater proCommentAdapater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_particulars_);
        //初始化include头部空间
        initView();
        Intent intent = getIntent();

        MyBaseData myBaseData = new MyBaseData();
        //获取具体商品的id
        String id = intent.getStringExtra("id");
        myBaseData.getData(UrlUtils.SHOP_PARTICULARS + id, null, 0, BaseDataxUtils.NORMALTIME);

        cartDao = new CartDao(this);
    }

    /**
     * /头部轮播图
     */
    private void particularsMyRoolViewPager() {

        List<ParticularsRoot.DataBean.GoodsBean.GalleryBean> gallery = particularsRoot.data.goods.gallery;
        for (int i = 0; i < gallery.size(); i++) {
            imageUrlList.add(gallery.get(i).normal_url);
        }

        //初始化小圆点
        initDots();
        particulars_myRoolViewPager.initData(imageUrlList, dotArray, dotimageList);
        particulars_myRoolViewPager.startViewPager();
    }

    /**
     * 初始化小圆点
     */
    private void initDots() {
        for (int i = 0; i < imageUrlList.size(); i++) {
            ImageView imageView = new ImageView(this);
            if (i == 0) {
                imageView.setImageResource(dotArray[0]);
            } else {
                imageView.setImageResource(dotArray[1]);
            }
            dotimageList.add(imageView);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(CommonUtils.dip2px(10), CommonUtils.dip2px(5), CommonUtils.dip2px(10), CommonUtils.dip2px(5));
            particulars_docs_linearLayout.addView(imageView, params);
        }
    }


    //请求数据
    class MyBaseData extends BaseDataxUtils {

        @Override
        protected void setResulttError(ShowingPage.StateType stateLoadError) {

        }

        @Override
        public void setResultData(String resultData) {
            LogUtils.i("resultData------------", resultData);
            Particulars_Activity.this.data = resultData;
            //解析
            Gson gson = new Gson();
            particularsRoot = gson.fromJson(data, ParticularsRoot.class);

            //初始化轮播图
            particularsMyRoolViewPager();
            //初始化详细信息
            goodsMessage();
            //初始化活动信息
            activityListView();
            //商品详情，产品参数，评论
            particularsMessage();

        }
    }


    /**
     * 初始化活动信息
     */
    private void activityListView() {
        particulars_activity_listView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        List<ParticularsRoot.DataBean.ActivityBean> activity = particularsRoot.data.activity;
        particulars_activity_listView.setAdapter(new CommonAdapter<ParticularsRoot.DataBean.ActivityBean>(this, activity, R.layout.particulars_activity_listview_item) {
            @Override
            public void convert(ViewHolder helper, ParticularsRoot.DataBean.ActivityBean item) {
                helper.setText(R.id.title, item.title);
            }
        });
    }

    //

    /**
     * 初始化详细信息
     */
    private void goodsMessage() {
        ParticularsRoot.DataBean.GoodsBean goods = particularsRoot.data.goods;
        goods_name.setText(goods.goods_name);
        show_price.setText("￥" + goods.shop_price);
        market_price.setText("￥" + goods.market_price);
        sales_volume.setText(goods.sales_volume + "");
        stock_number.setText(goods.collect_count + "");
        carriage.setText(Html.fromHtml("<font color=\'#000\'>运费 </font><font color=\'#FC6B87\'>包邮</font>"));
        sales_volume.setText(Html.fromHtml("<font color=\'#000\'>销量 </font><font color=\'#FC6B87\'>" + sales_volume.getText() + "万</font>"));
        stock_number.setText(Html.fromHtml("<font color=\'#000\'>收藏 </font><font color=\'#FC6B87\'>" + stock_number.getText() + "</font>"));


    }


    private void initView() {
        include_middle_tv = (TextView) findViewById(R.id.include_meddim_tv);
        include_middle_tv.setText("商品详情");
        include_back_image = (ImageView) findViewById(R.id.back_image);
        include_share_image = (ImageView) findViewById(R.id.include_share_image);
        include_share_image.setVisibility(View.VISIBLE);
        include_image_goods_shopping_cart = (ImageView) findViewById(R.id.include_image_goods_shopping_cart);
        include_image_goods_shopping_cart.setVisibility(View.VISIBLE);
        include_image_goods_shopping_cart.setOnClickListener(this);
        include_back_image.setOnClickListener(this);
        include_share_image.setOnClickListener(this);
        include_image_goods_shopping_cart.setOnClickListener(this);

        //头部viewpager轮播
        particulars_myRoolViewPager = (MyRoolViewPager) findViewById(R.id.particulars_myRoolViewPager);
        particulars_docs_linearLayout = (AutoLinearLayout) findViewById(R.id.particulars_docs_linearLayout);
        //中部详细信息
        goods_name = (TextView) findViewById(R.id.goods_name);
        show_price = (TextView) findViewById(R.id.show_price);
        market_price = (TextView) findViewById(R.id.market_price);
        collection_cb = (CheckBox) findViewById(R.id.collection_cb);
        carriage = (TextView) findViewById(R.id.carriage);
        sales_volume = (TextView) findViewById(R.id.sales_volume);
        stock_number = (TextView) findViewById(R.id.stock_number);
        //活动信息
        particulars_activity_listView = (Home_ListView) findViewById(R.id.particulars_activity_listView);


        //底部详细介绍
        myScrollView = (MyScrollView) findViewById(R.id.myScrollView);
        myScrollView.setOnScrollListener(this);
        search1_LinearLayout = (AutoLinearLayout) findViewById(R.id.search01);
        search2_LinearLayout = (AutoLinearLayout) findViewById(R.id.search02);
        particulars_recyclerView = (RecyclerView) findViewById(R.id.particulars_recyclerView);

        //三个按钮控件
        pro_detail_tv = (TextView) findViewById(R.id.pro_detail_tv);//产品详情
        pro_detail_tv.setOnClickListener(this);
        pro_details_tv = (TextView) findViewById(R.id.pro_details_tv);         //产品参数
        pro_details_tv.setOnClickListener(this);
        pro_comment_tv = (TextView) findViewById(R.id.pro_comment_tv);//评论
        pro_comment_tv.setOnClickListener(this);


        //顶部三个按钮控件
        pro_detail_top_tv = (TextView) findViewById(R.id.pro_detail_top_tv);//产品详情
        pro_detail_top_tv.setOnClickListener(this);
        pro_details_top_tv = (TextView) findViewById(R.id.pro_details_top_tv);         //产品参数
        pro_details_top_tv.setOnClickListener(this);
        pro_comment_top_tv = (TextView) findViewById(R.id.pro_comment_top_tv);//评论
        pro_comment_top_tv.setOnClickListener(this);


        //加入购物车
        but_add_shopCart = (Button) findViewById(R.id.but_add_shopCart);
        but_add_shopCart.setOnClickListener(this);
        but_buy = (Button) findViewById(R.id.but_buy);
        but_buy.setOnClickListener(this);
    }

    private void particularsMessage() {
        pro_detail_tv.setTextColor(getResources().getColor(R.color.YuniFangZhangHao_textColor));
        //默认展示的数据
        particulars_recyclerView.setLayoutManager(new LinearLayoutManager(Particulars_Activity.this, LinearLayoutManager.VERTICAL, false));
        String goods_desc = particularsRoot.data.goods.goods_desc;
        //解析
        Gson gson = new Gson();
        goodsBeen = gson.fromJson(goods_desc, GoodsDesc[].class);
        particulars_recyclerView.setAdapter(new ProDetailAdapater(Particulars_Activity.this, goodsBeen));
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            searchLayoutTop = search2_LinearLayout.getTop();//获取searchLayout的顶部位置
        }
    }

    //监听滚动Y值变化，通过addView和removeView来实现悬停效果
    @Override
    public void onScroll(int scrollY) {
        if (scrollY >= searchLayoutTop) {
            search1_LinearLayout.setVisibility(View.VISIBLE);
        } else {
            search1_LinearLayout.setVisibility(View.GONE);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //返回小图标
            case R.id.back_image:
                finish();
                Particulars_Activity.this.overridePendingTransition(R.anim.login_in0, R.anim.login_out);
                break;

            //分享图标
            case R.id.include_share_image:
                showPop = true;
                bottomwindow(v);
                showPopupWidow();
                break;

            //购物车图标
            case R.id.include_image_goods_shopping_cart:
                if (MyApplication.loginFlag) {
                    startActivity(new Intent(Particulars_Activity.this, CartActivity.class));
                    Particulars_Activity.this.overridePendingTransition(R.anim.login_in, R.anim.login_in0);
                } else {
                    startActivity(new Intent(Particulars_Activity.this, Login_Activity.class));
                    Particulars_Activity.this.overridePendingTransition(R.anim.login_in, R.anim.login_in0);
                }
                break;

            //产品详情
            case R.id.pro_detail_tv:
                pro_detail_tv.setTextColor(getResources().getColor(R.color.YuniFangZhangHao_textColor));
                pro_details_tv.setTextColor(Color.BLACK);
                pro_comment_tv.setTextColor(Color.BLACK);

                if (proDetailAdapater == null) {
                    proDetailAdapater = new ProDetailAdapater(Particulars_Activity.this, goodsBeen);
                }
                particulars_recyclerView.setAdapter(proDetailAdapater);
                break;
            //产品参数
            case R.id.pro_details_tv:
                ArrayList<ParticularsRoot.DataBean.GoodsBean.AttributesBean> attributes = (ArrayList<ParticularsRoot.DataBean.GoodsBean.AttributesBean>) particularsRoot.data.goods.attributes;
                pro_details_tv.setTextColor(getResources().getColor(R.color.YuniFangZhangHao_textColor));
                pro_detail_tv.setTextColor(Color.BLACK);
                pro_comment_tv.setTextColor(Color.BLACK);
                if (proDetailsAdapater == null) {
                    proDetailsAdapater = new ProDetailsAdapater(attributes, Particulars_Activity.this);
                }
                particulars_recyclerView.setAdapter(proDetailsAdapater);
                break;
            //评论
            case R.id.pro_comment_tv:
                pro_details_tv.setTextColor(Color.BLACK);
                pro_detail_tv.setTextColor(Color.BLACK);
                pro_comment_tv.setTextColor(getResources().getColor(R.color.YuniFangZhangHao_textColor));
                List<ParticularsRoot.DataBean.CommentsBean> comments = particularsRoot.data.comments;
                if (proCommentAdapater == null) {
                    proCommentAdapater = new ProCommentAdapater(comments, Particulars_Activity.this);
                }
                particulars_recyclerView.setAdapter(proCommentAdapater);
                break;

            //产品详情
            case R.id.pro_detail_top_tv:
                pro_detail_top_tv.setTextColor(getResources().getColor(R.color.YuniFangZhangHao_textColor));
                pro_details_top_tv.setTextColor(Color.BLACK);
                pro_comment_top_tv.setTextColor(Color.BLACK);

                if (proDetailAdapater == null) {
                    proDetailAdapater = new ProDetailAdapater(Particulars_Activity.this, goodsBeen);
                }
                particulars_recyclerView.setAdapter(proDetailAdapater);
                break;
            //产品参数
            case R.id.pro_details_top_tv:
                ArrayList<ParticularsRoot.DataBean.GoodsBean.AttributesBean> attributesTop = (ArrayList<ParticularsRoot.DataBean.GoodsBean.AttributesBean>) particularsRoot.data.goods.attributes;
                pro_details_top_tv.setTextColor(getResources().getColor(R.color.YuniFangZhangHao_textColor));
                pro_detail_top_tv.setTextColor(Color.BLACK);
                pro_comment_top_tv.setTextColor(Color.BLACK);
                if (proDetailsAdapater == null) {
                    proDetailsAdapater = new ProDetailsAdapater(attributesTop, Particulars_Activity.this);
                }
                particulars_recyclerView.setAdapter(proDetailsAdapater);
                break;
            //评论
            case R.id.pro_comment_top_tv:
                pro_details_top_tv.setTextColor(Color.BLACK);
                pro_detail_top_tv.setTextColor(Color.BLACK);
                pro_comment_top_tv.setTextColor(getResources().getColor(R.color.YuniFangZhangHao_textColor));
                List<ParticularsRoot.DataBean.CommentsBean> commentsTop = particularsRoot.data.comments;
                if (proCommentAdapater == null) {
                    proCommentAdapater = new ProCommentAdapater(commentsTop, Particulars_Activity.this);
                }
                particulars_recyclerView.setAdapter(proCommentAdapater);
                break;


            //加入购物车
            case R.id.but_add_shopCart:
                isFlag = true;
                if (MyApplication.loginFlag) {
                    showPop = false;
                    bottomwindow(v);
                    showPopupWidow();
                } else {
                    startActivity(new Intent(Particulars_Activity.this, Login_Activity.class));
                    Particulars_Activity.this.overridePendingTransition(R.anim.login_in, R.anim.login_in0);
                }
                break;
            //立即购买
            case R.id.but_buy:
                isFlag = false;
                if (MyApplication.loginFlag) {
                    showPop = false;
                    bottomwindow(v);
                    showPopupWidow();
                } else {
                    startActivity(new Intent(Particulars_Activity.this, Login_Activity.class));
                    Particulars_Activity.this.overridePendingTransition(R.anim.login_in, R.anim.login_in0);
                }
                break;

            //减少数量
            case R.id.reduce_number:
                if (defaultShowNumber > 1) {
                    defaultShowNumber--;
                    show_number.setText(defaultShowNumber + "");
                    add_number.setTextColor(Color.BLACK);
                }

                if (defaultShowNumber == 1) {
                    reduce_number.setTextColor(Color.GRAY);
                }

                break;
            //增加数量
            case R.id.add_number:
                if (defaultShowNumber < particularsRoot.data.goods.restrict_purchase_num) {
                    defaultShowNumber++;
                    show_number.setText(defaultShowNumber + "");
                    reduce_number.setTextColor(Color.BLACK);
                }
                if (defaultShowNumber == particularsRoot.data.goods.restrict_purchase_num) {
                    add_number.setTextColor(Color.GRAY);
                }

                break;
            //确定按钮
            case R.id.cart_pop_sure_button:
                //表示加入购物车操作
                if (isFlag) {
                    ParticularsRoot.DataBean.GoodsBean goods = particularsRoot.data.goods;
                    cartDao.addGoods(goods, defaultShowNumber);
                    popupWindow.dismiss();
                    Toast.makeText(Particulars_Activity.this, "恭喜添加购物车成功！", Toast.LENGTH_SHORT).show();
                } else {//表示立即购买操作
                    ParticularsRoot.DataBean.GoodsBean goods = particularsRoot.data.goods;
                    Intent intent = new Intent(Particulars_Activity.this, OrderParticularsActivity.class);
                    ArrayList<CartDbBean> arrayList = new ArrayList<>();
                    arrayList.add(new CartDbBean(goods.goods_img, goods.goods_name, goods.id, defaultShowNumber + "", goods.shop_price + "", goods.restrict_purchase_num));
                    intent.putExtra("arrayList", arrayList);
                    Particulars_Activity.this.startActivity(intent);
                    Particulars_Activity.this.overridePendingTransition(R.anim.login_in, R.anim.login_in0);
                }
                break;

            //QQ分享监听
            case R.id.share_qq:
                new ShareAction(Particulars_Activity.this).setPlatform(SHARE_MEDIA.QQ)
                        .withText("hello")
                        .setCallback(umShareListener)
                        .share();
                break;

            //分享弹出框中的取消按钮监听事件
            case R.id.but_cancel:
                popupWindow.dismiss();
                break;
        }
    }


    private void showPopupWidow() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                float alpha = 1f;
                while (alpha > 0.5f) {
                    try {
                        //4是根据弹出动画时间和减少的透明度计算
                        Thread.sleep(4);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Message msg = mHandler.obtainMessage();
                    msg.what = 1;
                    //每次减少0.01，精度越高，变暗的效果越流畅
                    alpha -= 0.01f;
                    msg.obj = alpha;
                    mHandler.sendMessage(msg);
                }
            }

        }).start();
    }


    public void bottomwindow(View view) {
        if (popupWindow != null && popupWindow.isShowing()) {
            return;
        }
        View inflate = null;
        if (showPop) {
            inflate = CommonUtils.inflate(R.layout.pop_share);
            initSharePopupWindowView(inflate);
        } else {
            inflate = CommonUtils.inflate(R.layout.cart_pop);
            initPopupWindowView(inflate);
        }
        showPop = !showPop;
        popupWindow = new PopupWindow(inflate,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        //点击空白处时，隐藏掉pop窗口
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //添加弹出、弹入的动画
        popupWindow.setAnimationStyle(R.style.Popupwindow);
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        popupWindow.showAtLocation(view, Gravity.LEFT | Gravity.BOTTOM, 0, -location[1]);
        //添加pop窗口关闭事件，主要是实现关闭时改变背景的透明度
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                hidePopupWindow();
            }
        });
        backgroundAlpha(1f);
    }

    /**
     * 分享的popup控件初始化
     *
     * @param inflate
     */
    private void initSharePopupWindowView(View inflate) {
        share_wxchat = (Button) inflate.findViewById(R.id.share_wxchat);
        share_wxchat.setOnClickListener(this);

        share_wxsession = (Button) inflate.findViewById(R.id.share_wxsession);
        share_wxsession.setOnClickListener(this);

        share_qzone = (Button) inflate.findViewById(R.id.share_qzone);
        share_qzone.setOnClickListener(this);

        share_weibo = (Button) inflate.findViewById(R.id.share_weibo);
        share_weibo.setOnClickListener(this);

        share_qq = (Button) inflate.findViewById(R.id.share_qq);
        share_qq.setOnClickListener(this);

        but_cancel = (Button) inflate.findViewById(R.id.but_cancel);
        but_cancel.setOnClickListener(this);

    }

    /**
     * 查找popup中的控件
     *
     * @param inflate
     */
    private void initPopupWindowView(View inflate) {
        pop_cart_pop_image = (ImageView) inflate.findViewById(R.id.cart_pop_image);
        pop_shop_price = (TextView) inflate.findViewById(R.id.pop_show_price);
        pop_stock_number = (TextView) inflate.findViewById(R.id.stock_number);
        pop_restrict_purchase_num = (TextView) inflate.findViewById(R.id.restrict_purchase_num);

        ImageLoader.getInstance().displayImage(particularsRoot.data.goods.goods_img, pop_cart_pop_image, ImageLoaderUtils.initOptionsCircle());
        pop_shop_price.setText("￥" + particularsRoot.data.goods.shop_price);
        pop_stock_number.setText("库存" + particularsRoot.data.goods.stock_number + "件");
        pop_restrict_purchase_num.setText("限购" + particularsRoot.data.goods.restrict_purchase_num + "件");

        reduce_number = (Button) inflate.findViewById(R.id.reduce_number);
        show_number = (Button) inflate.findViewById(R.id.show_number);
        add_number = (Button) inflate.findViewById(R.id.add_number);
        cart_pop_sure_button = (Button) inflate.findViewById(R.id.cart_pop_sure_button);
        show_number.setText(defaultShowNumber + "");


        if (defaultShowNumber == 1) {
            reduce_number.setTextColor(Color.GRAY);
        }

        if (defaultShowNumber == 5) {
            add_number.setTextColor(Color.GRAY);
        }

        reduce_number.setOnClickListener(this);
        add_number.setOnClickListener(this);
        cart_pop_sure_button.setOnClickListener(this);
    }


    private void hidePopupWindow() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //此处while的条件alpha不能<= 否则会出现黑屏
                float alpha = 0.5f;
                while (alpha < 0.9f) {
                    try {
                        Thread.sleep(4);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.d("HeadPortrait", "alpha:" + alpha);
                    Message msg = mHandler.obtainMessage();
                    msg.what = 1;
                    alpha += 0.01f;
                    msg.obj = alpha;
                    mHandler.sendMessage(msg);
                }
            }

        }).start();
    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    /**
     *
     */
    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat", "platform" + platform);

            Toast.makeText(Particulars_Activity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(Particulars_Activity.this, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if (t != null) {
                Log.d("throw", "throw:" + t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(Particulars_Activity.this, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };


}

