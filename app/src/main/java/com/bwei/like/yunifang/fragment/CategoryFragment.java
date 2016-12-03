package com.bwei.like.yunifang.fragment;

import android.view.View;
import android.webkit.URLUtil;
import android.widget.GridView;
import android.widget.ImageView;

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
    private Home_GridView cateGory_gridView;
    private CategoryRoot categoryRoot;

    @Override
    protected View createSuccessView() {
        inflate = CommonUtils.inflate(R.layout.category_fragment);
        //初始化控件
        initView();
        //明星产品
        initGridView();
        return inflate;
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
        cateGory_gridView.setAdapter(new CommonAdapter<CategoryRoot.DataBean.GoodsBriefBean>(getActivity(),goodsBriefBeanArrayList,R.layout.home_home_gridview_item) {
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
        cateGory_gridView = (Home_GridView) inflate.findViewById(R.id.cateGory_girdView);

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
            CategoryFragment.this.showingPage(ShowingPage.StateType.STATE_LOAD_SUCCESS);
            CategoryFragment.this.data = resultData;
            //解析
            Gson gson = new Gson();
            categoryRoot = gson.fromJson(data, CategoryRoot.class);
        }
    }
}
