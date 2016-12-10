package com.bwei.like.yunifang.activity;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bwei.like.yunifang.R;
import com.bwei.like.yunifang.adapater.CommonAdapter;
import com.bwei.like.yunifang.adapater.ViewHolder;
import com.bwei.like.yunifang.base.BaseActivity;
import com.bwei.like.yunifang.bean.HomeRoot;
import com.bwei.like.yunifang.utils.CommonUtils;
import com.bwei.like.yunifang.view.Home_GridView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.autolayout.AutoLinearLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Home_MoreActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private ListView more_ListView;
    private TextView include_middle_tv;
    private ImageView back_image;
    //  private ArrayList<HomeRoot.DataBean.BestSellersBean> bestSellers;
    private ScrollView more_scrollView;
    private HomeRoot.DataBean.SubjectsBean subjectsBean;
    private TextView title_tv;
    private TextView detail_tv;
    private Home_GridView more_gridView;
    private List<HomeRoot.DataBean.SubjectsBean.GoodsListBean> goodsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__more);
        init();

        Intent intent = getIntent();
        /**
         * 获取本周热销数据
         */
        //  bestSellers = (ArrayList<HomeRoot.DataBean.BestSellersBean>) intent.getSerializableExtra("bestSellers");
        /**
         * 获取热门专题更多数据
         */
        subjectsBean = (HomeRoot.DataBean.SubjectsBean) intent.getSerializableExtra("subjectsBean");


//        if (bestSellers!= null ) {
//            /**
//             * 本周热销
//             */
//            initView();
//        }else
        /**
         * 热门专题
         */
        if (subjectsBean != null && subjectsBean.goodsList.size() != 0) {
            initHotMore();
        }

    }

    private void init() {
        include_middle_tv = (TextView) findViewById(R.id.include_meddim_tv);
        back_image = (ImageView) findViewById(R.id.back_image);
        back_image.setOnClickListener(this);
        more_ListView = (ListView) findViewById(R.id.more_ListView);
        more_scrollView = (ScrollView) findViewById(R.id.more_scrollView);
    }

    /**
     * 热门专题
     */
    private void initHotMore() {
        include_middle_tv.setText("专题");
        more_ListView.setVisibility(View.GONE);
        more_scrollView.setVisibility(View.VISIBLE);
        title_tv = (TextView) findViewById(R.id.title_tv);
        detail_tv = (TextView) findViewById(R.id.detail_tv);
        more_gridView = (Home_GridView) findViewById(R.id.more_gridView);
        more_gridView.setOnItemClickListener(this);


        goodsList = subjectsBean.goodsList;
        title_tv.setText(subjectsBean.title);
        detail_tv.setText(subjectsBean.detail);

        more_gridView.setAdapter(new CommonAdapter<HomeRoot.DataBean.SubjectsBean.GoodsListBean>(Home_MoreActivity.this, goodsList, R.layout.home_home_gridview_item) {
            @Override
            public void convert(ViewHolder helper, HomeRoot.DataBean.SubjectsBean.GoodsListBean item) {
                helper.setImageByUrl(R.id.home_default_gridView_image, item.goods_img);
                helper.setText(R.id.efficacy, item.efficacy);
                helper.setText(R.id.goods_name, item.goods_name);
                helper.setText(R.id.shop_price, "￥" + item.shop_price);
                helper.setText(R.id.market_price, "￥" + item.market_price);
            }
        });

    }

    /**
     * 本周热销
     */
//    private void initView() {
//        final List<HomeRoot.DataBean.BestSellersBean.GoodsListBean> goodsList = bestSellers.get(0).goodsList;
//        final int[] picImage = new int[]{R.mipmap.hot_rank_1, R.mipmap.hot_rank_2, R.mipmap.hot_rank_3};
//        more_ListView.setVisibility(View.VISIBLE);
//        more_scrollView.setVisibility(View.GONE);
//        include_middle_tv.setText(bestSellers.get(0).name);
//        more_ListView.setAdapter(new BaseAdapter() {
//            @Override
//            public int getCount() {
//                return goodsList.size();
//            }
//
//            @Override
//            public Object getItem(int position) {
//                return null;
//            }
//
//            @Override
//            public long getItemId(int position) {
//                return 0;
//            }
//
//            @Override
//            public View getView(int position, View convertView, ViewGroup parent) {
//                convertView = CommonUtils.inflate(R.layout.home_more_listview_item);
//                ImageView goods_img = (ImageView) convertView.findViewById(R.id.goods_img);
//                TextView goods_name = (TextView) convertView.findViewById(R.id.goods_name);
//                TextView shop_price = (TextView) convertView.findViewById(R.id.shop_price);
//                TextView market_price = (TextView) convertView.findViewById(R.id.market_price);
//                ImageView order_image = (ImageView) convertView.findViewById(R.id.order_image);
//                TextView order_tv = (TextView) convertView.findViewById(R.id.order_tv);
//
//                ImageLoader.getInstance().displayImage(goodsList.get(position).goods_img, goods_img, CommonUtils.getinitOptionsCircle());
//                goods_name.setText(goodsList.get(position).goods_name);
//                shop_price.setText("￥" + goodsList.get(position).shop_price);
//                market_price.setText("￥" + goodsList.get(position).market_price);
//                if (position <= 2) {
//                    order_image.setVisibility(View.VISIBLE);
//                    order_tv.setVisibility(View.GONE);
//                    order_image.setImageResource(picImage[position]);
//                } else {
//                    order_image.setVisibility(View.GONE);
//                    order_tv.setVisibility(View.VISIBLE);
//                    int i = position + 1;
//                    order_tv.setText("No" + i);
//                }
//
//                return convertView;
//            }
//        });
//
//    }

    /**
     * 监听事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_image:
                finish();
                Home_MoreActivity.this.overridePendingTransition(R.anim.login_in0, R.anim.login_out);
                break;

        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(Home_MoreActivity.this,Particulars_Activity.class);
        intent.putExtra("id",goodsList.get(position).id);
        startActivity(intent);
    }
}
