package com.bwei.like.yunifang.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bwei.like.yunifang.R;
import com.bwei.like.yunifang.adapater.CommonAdapter;
import com.bwei.like.yunifang.adapater.ViewHolder;
import com.bwei.like.yunifang.base.BaseActivity;
import com.bwei.like.yunifang.base.BaseDataxUtils;
import com.bwei.like.yunifang.base.BaseFragment;
import com.bwei.like.yunifang.bean.HomeRoot;
import com.bwei.like.yunifang.bean.ParticularsRoot;
import com.bwei.like.yunifang.utils.CommonUtils;
import com.bwei.like.yunifang.utils.LogUtils;
import com.bwei.like.yunifang.utils.UrlUtils;
import com.bwei.like.yunifang.view.Home_ListView;
import com.bwei.like.yunifang.view.MyRoolViewPager;
import com.bwei.like.yunifang.view.ShowingPage;
import com.google.gson.Gson;
import com.zhy.autolayout.AutoLinearLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Particulars_Activity extends BaseActivity implements View.OnClickListener {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_particulars_);
        //初始化include头部空间
        initView();

        //获取具体商品的id
        HomeRoot.DataBean.SubjectsBean.GoodsListBean goodsListBean = (HomeRoot.DataBean.SubjectsBean.GoodsListBean) getIntent().getSerializableExtra("goodsListBean");
        //请求数据
        MyBaseData myBaseData = new MyBaseData();
        myBaseData.getData(UrlUtils.SHOP_PARTICULARS + goodsListBean.id, null, 0, BaseDataxUtils.NORMALTIME);


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
        }
    }

    /**
     * 初始化活动信息
     */
    private void activityListView() {
        particulars_activity_listView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        List<ParticularsRoot.DataBean.ActivityBean> activity = particularsRoot.data.activity;
        particulars_activity_listView.setAdapter(new CommonAdapter<ParticularsRoot.DataBean.ActivityBean>(this,activity,R.layout.particulars_activity_listview_item) {
            @Override
            public void convert(ViewHolder helper, ParticularsRoot.DataBean.ActivityBean item) {
                helper.setText(R.id.title,item.title);
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
        show_price.setText("￥"+goods.shop_price);
        market_price.setText("￥"+goods.market_price);

    }


    private void initView() {
        include_middle_tv = (TextView) findViewById(R.id.include_meddim_tv);
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

        }
    }
}
