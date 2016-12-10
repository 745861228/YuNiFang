package com.bwei.like.yunifang.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwei.like.yunifang.R;
import com.bwei.like.yunifang.bean.CateGorySonFragmentRoot;
import com.bwei.like.yunifang.utils.CommonUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by LiKe on 2016/12/10.
 */
public class CateGorySonFragmentHolder extends BaseHolder<CateGorySonFragmentRoot.DataBean> {

    private final ImageView imageView;
    private final TextView efficacy;
    private final TextView shop_price;
    private final TextView market_price;
    private final TextView goods_name;

    public CateGorySonFragmentHolder(View itemView) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.home_default_gridView_image);
        efficacy = (TextView) itemView.findViewById(R.id.efficacy);
        shop_price = (TextView) itemView.findViewById(R.id.shop_price);
        market_price = (TextView) itemView.findViewById(R.id.market_price);
        goods_name = (TextView) itemView.findViewById(R.id.goods_name);
    }

    @Override
    public void setData(Context context, CateGorySonFragmentRoot.DataBean dataBean) {
        ImageLoader.getInstance().displayImage(dataBean.goods_img,imageView, CommonUtils.getinitOptionsCircle());
        efficacy.setText(dataBean.efficacy);
        shop_price.setText("￥"+dataBean.shop_price);
        market_price.setText("￥"+dataBean.market_price);
        goods_name.setText(dataBean.goods_name);
    }
}
