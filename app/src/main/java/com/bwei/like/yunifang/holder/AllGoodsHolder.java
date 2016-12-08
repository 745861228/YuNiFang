package com.bwei.like.yunifang.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwei.like.yunifang.R;
import com.bwei.like.yunifang.bean.AllGoodsRoot;
import com.bwei.like.yunifang.utils.CommonUtils;
import com.bwei.like.yunifang.utils.ImageLoaderUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by LiKe on 2016/12/6.
 */
public  class AllGoodsHolder extends BaseHolder<AllGoodsRoot.DataBean>{

    private final ImageView home_default_gridView_image;
    private final TextView efficacy;
    private final TextView goods_name;
    private final TextView shop_price;
    private final TextView market_price;

    public AllGoodsHolder(View itemView) {
        super(itemView);
        home_default_gridView_image = (ImageView) itemView.findViewById(R.id.home_default_gridView_image);
        efficacy = (TextView) itemView.findViewById(R.id.efficacy);
        goods_name = (TextView) itemView.findViewById(R.id.goods_name);
        shop_price = (TextView) itemView.findViewById(R.id.shop_price);
        market_price = (TextView) itemView.findViewById(R.id.market_price);
    }

    @Override
    public void setData(Context context, AllGoodsRoot.DataBean dataBean) {
        ImageLoader.getInstance().displayImage(dataBean.goods_img,home_default_gridView_image, CommonUtils.getinitOptionsCircle());
        efficacy.setText(dataBean.efficacy);
        goods_name.setText(dataBean.goods_name);
        shop_price.setText("￥"+dataBean.shop_price);
        market_price.setText("￥"+dataBean.market_price);
    }


}
