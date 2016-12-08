package com.bwei.like.yunifang.holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.bwei.like.yunifang.R;
import com.bwei.like.yunifang.bean.ParticularsRoot;

/**
 * Created by LiKe on 2016/12/8.
 */
public class ProDetailsHolder extends BaseHolder<ParticularsRoot.DataBean.GoodsBean.AttributesBean> {

    private final TextView attr_name;
    private final TextView attr_value;

    public ProDetailsHolder(View itemView) {
        super(itemView);
        attr_name = (TextView) itemView.findViewById(R.id.attr_name);
        attr_value = (TextView) itemView.findViewById(R.id.attr_value);
    }

    @Override
    public void setData(Context context, ParticularsRoot.DataBean.GoodsBean.AttributesBean attributesBean) {
        attr_name.setText(attributesBean.attr_name);
        attr_value.setText(attributesBean.attr_value);
    }
}
