package com.bwei.like.yunifang.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bwei.like.yunifang.R;
import com.bwei.like.yunifang.bean.GoodsDesc;
import com.bwei.like.yunifang.utils.CommonUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by LiKe on 2016/12/8.
 */
public class ProDetailHolder extends BaseHolder<GoodsDesc> {

    private final ImageView imageView;

    public ProDetailHolder(View itemView) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.proDetail_item_image);
    }

    @Override
    public void setData(Context context, GoodsDesc goodsDesc) {
        ImageLoader.getInstance().displayImage(goodsDesc.url,imageView, CommonUtils.getinitOptionsCircle());
    }
}
