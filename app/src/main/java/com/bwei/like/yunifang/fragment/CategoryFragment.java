package com.bwei.like.yunifang.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bwei.like.yunifang.R;
import com.bwei.like.yunifang.adapater.CommonAdapter;
import com.bwei.like.yunifang.adapater.ViewHolder;
import com.bwei.like.yunifang.base.BaseDataxOkHttp;
import com.bwei.like.yunifang.base.BaseDataxUtils;
import com.bwei.like.yunifang.base.BaseFragment;
import com.bwei.like.yunifang.bean.CategoryRoot;
import com.bwei.like.yunifang.utils.CommonUtils;
import com.bwei.like.yunifang.utils.UrlUtils;
import com.bwei.like.yunifang.view.Home_GridView;
import com.bwei.like.yunifang.view.ShowingPage;
import com.google.gson.Gson;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiKe on 2016/11/28.
 */
public class CategoryFragment extends BaseFragment {
    private String data;
    private View inflate;
    private ImageView cateGory_classify_facial_mask;
    private ImageView cataeGory_classify_emollient_water;
    private ImageView cateGory_classify_body_lotion;
    private ImageView cateGory_classify_facial_cleanser;
    private ImageView cateGory_classify_other;
    private ImageView cateGory_classify_kit;
    private Home_GridView cateGory_girdView_last;
    private CategoryRoot categoryRoot;
    private AutoLinearLayout cateGory_childDrem;
    private TextView cateGory_cat_name_gonfxiao_tv;
    private Home_GridView cateGory_gridView_fuzhi;

    @Override
    protected View createSuccessView() {
        inflate = CommonUtils.inflate(R.layout.category_fragment);
        //初始化控件
        initView();
        //按功效
        initChildDrem();
        //按肤质
        initCateGoryGrid();

        //明星产品
        initGridView();
        return inflate;
    }

    /**
     * 按肤质
     */
    private void initCateGoryGrid() {
        final int[] colorArray = new int[]{R.color.position0, R.color.position1, R.color.position2, R.color.position3, R.color.position4, R.color.position5};
        //获取数据
        final List<CategoryRoot.DataBean.CategoryBean.ChildrenBean> children = categoryRoot.data.category.get(2).children;
        cateGory_gridView_fuzhi.setAdapter(new CommonAdapter<CategoryRoot.DataBean.CategoryBean.ChildrenBean>(getActivity(), children, R.layout.category_gridview_fuzhi_item) {
            @Override
            public void convert(ViewHolder helper, CategoryRoot.DataBean.CategoryBean.ChildrenBean item) {
                int position = helper.getPosition();
                View convertView = helper.getConvertView();
                convertView.setBackgroundResource(colorArray[position]);
                helper.setText(R.id.cateGory_fuzhi_cat_name, "#" + item.cat_name + "#");
            }
        });
    }

    /**
     * 按功效
     */
    private void initChildDrem() {
        int[] picArray = new int[]{R.mipmap.classify_hydrating, R.mipmap.classify_soothing, R.mipmap.classify_control_oil, R.mipmap.classify_whitening, R.mipmap.classify_firming};
        CategoryRoot.DataBean.CategoryBean categoryBean = categoryRoot.data.category.get(0);
        cateGory_cat_name_gonfxiao_tv.setText("- " + categoryBean.cat_name + " -");
        for (int i = 0; i < categoryBean.children.size(); i++) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setImageResource(picArray[i]);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
            cateGory_childDrem.addView(imageView, params);
        }
    }

    /**
     * 明星产品
     */
    private void initGridView() {
        //获取当前数据集合
        List<CategoryRoot.DataBean.GoodsBriefBean> goodsBrief = categoryRoot.data.goodsBrief;
        ArrayList<CategoryRoot.DataBean.GoodsBriefBean> goodsBriefBeanArrayList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            goodsBriefBeanArrayList.add(goodsBrief.get(i));
        }
        cateGory_girdView_last.setAdapter(new CommonAdapter<CategoryRoot.DataBean.GoodsBriefBean>(getActivity(), goodsBriefBeanArrayList, R.layout.home_home_gridview_item) {
            @Override
            public void convert(ViewHolder helper, CategoryRoot.DataBean.GoodsBriefBean item) {
                helper.setImageByUrl(R.id.home_default_gridView_image, item.goods_img);
                helper.setText(R.id.efficacy, item.efficacy);
                helper.setText(R.id.goods_name, item.goods_name);
                helper.setText(R.id.shop_price, "￥" + item.shop_price);
                helper.setText(R.id.market_price, "￥" + item.market_price);
            }
        });
    }

    private void initView() {
        cateGory_classify_facial_mask = (ImageView) inflate.findViewById(R.id.cateGory_classify_facial_mask);
        cateGory_classify_facial_mask.setScaleType(ImageView.ScaleType.FIT_XY);
        cataeGory_classify_emollient_water = (ImageView) inflate.findViewById(R.id.cateGory_classify_emollient_water);
        cataeGory_classify_emollient_water.setScaleType(ImageView.ScaleType.FIT_XY);
        cateGory_classify_body_lotion = (ImageView) inflate.findViewById(R.id.cateGory_classify_body_lotion);
        cateGory_classify_body_lotion.setScaleType(ImageView.ScaleType.FIT_XY);
        cateGory_classify_facial_cleanser = (ImageView) inflate.findViewById(R.id.cateGory_classify_facial_cleanser);
        cateGory_classify_facial_cleanser.setScaleType(ImageView.ScaleType.FIT_XY);
        cateGory_classify_other = (ImageView) inflate.findViewById(R.id.cateGory_classify_other);
        cateGory_classify_other.setScaleType(ImageView.ScaleType.FIT_XY);
        cateGory_classify_kit = (ImageView) inflate.findViewById(R.id.cateGory_classify_kit);
        cateGory_classify_kit.setScaleType(ImageView.ScaleType.FIT_XY);


        //明星产品
        cateGory_girdView_last = (Home_GridView) inflate.findViewById(R.id.cateGory_girdView_last);

        //按功效
        cateGory_childDrem = (AutoLinearLayout) inflate.findViewById(R.id.cateGory_childDrem);
        cateGory_cat_name_gonfxiao_tv = (TextView) inflate.findViewById(R.id.cateGory_cat_name_gonfxiao_tv);


        //按肤质
        cateGory_gridView_fuzhi = (Home_GridView) inflate.findViewById(R.id.cateGory_gridView_fuzhi);
    }

    @Override
    protected void onLoad() {
        CategoryBaseData categoryBaseData = new CategoryBaseData();
        categoryBaseData.getData(UrlUtils.CATEGORY_URL, UrlUtils.CATEGORY_ARGS, 0, BaseDataxUtils.NORMALTIME);
    }

    class CategoryBaseData extends BaseDataxOkHttp {

        @Override
        protected void setResulttError(ShowingPage.StateType stateLoadError) {
            CategoryFragment.this.showingPage(ShowingPage.StateType.STATE_LOAD_ERROR);
        }

        @Override
        public void setResultData(String resultData) {
            CategoryFragment.this.data = resultData;
            //解析
            Gson gson = new Gson();
            categoryRoot = gson.fromJson(data, CategoryRoot.class);
            if (categoryRoot != null && categoryRoot.data != null) {
                CategoryFragment.this.showingPage(ShowingPage.StateType.STATE_LOAD_SUCCESS);
            }
        }
    }
}
