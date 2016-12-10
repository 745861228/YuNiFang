package com.bwei.like.yunifang.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bwei.like.yunifang.R;
import com.bwei.like.yunifang.adapater.CommonAdapter;
import com.bwei.like.yunifang.adapater.ViewHolder;
import com.bwei.like.yunifang.base.BaseActivity;
import com.bwei.like.yunifang.base.BaseDataxUtils;
import com.bwei.like.yunifang.bean.GoodsDesc;
import com.bwei.like.yunifang.bean.HomeRoot;
import com.bwei.like.yunifang.bean.ParticularsRoot;
import com.bwei.like.yunifang.recyclerview_adapater.ProCommentAdapater;
import com.bwei.like.yunifang.recyclerview_adapater.ProDetailAdapater;
import com.bwei.like.yunifang.recyclerview_adapater.ProDetailsAdapater;
import com.bwei.like.yunifang.utils.CommonUtils;
import com.bwei.like.yunifang.utils.LogUtils;
import com.bwei.like.yunifang.utils.UrlUtils;
import com.bwei.like.yunifang.view.Home_ListView;
import com.bwei.like.yunifang.view.MyRoolViewPager;
import com.bwei.like.yunifang.view.MyScrollView;
import com.bwei.like.yunifang.view.ShowingPage;
import com.google.gson.Gson;
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
    private AutoLinearLayout myAutoLinearLayout;
    private ListView list;
    private int searchLayoutTop;
    private RecyclerView particulars_recyclerView;
    private TextView pro_details_tv;
    private TextView pro_detail_tv;
    private TextView pro_comment_tv;
    private GoodsDesc[] goodsBeen;
    private ProDetailsAdapater proDetailsAdapater;
    private ProDetailAdapater proDetailAdapater;

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
    }

    /**
     * /头部轮播图
     */
    private void particularsMyRoolViewPager() {

        List<ParticularsRoot.DataBean.GoodsBean.GalleryBean> gallery = particularsRoot.data.goods.gallery;
        Log.i("kkkkk", "particularsMyRoolViewPager: ....." + gallery.size());
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
            searchLayoutTop = search2_LinearLayout.getBottom();//获取searchLayout的顶部位置
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

                break;

            //购物车图标
            case R.id.include_image_goods_shopping_cart:

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


                particulars_recyclerView.setAdapter(new ProCommentAdapater(comments, Particulars_Activity.this));


                break;
        }
    }
}
