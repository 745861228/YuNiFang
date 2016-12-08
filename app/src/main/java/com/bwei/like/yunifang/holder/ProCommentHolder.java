package com.bwei.like.yunifang.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bwei.like.yunifang.R;
import com.bwei.like.yunifang.application.MyApplication;
import com.bwei.like.yunifang.bean.ParticularsRoot;
import com.bwei.like.yunifang.utils.CommonUtils;
import com.bwei.like.yunifang.utils.ImageLoaderUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.autolayout.AutoLinearLayout;

/**
 * Created by LiKe on 2016/12/8.
 */
public class ProCommentHolder extends BaseHolder<ParticularsRoot.DataBean.CommentsBean> {

    private final ImageView user_icon;
    private final TextView nick_name;
    private final TextView content;
    private final TextView createtime;
    private final AutoLinearLayout uset_linearLayout;

    public ProCommentHolder(View itemView) {
        super(itemView);
        user_icon = (ImageView) itemView.findViewById(R.id.user_icon);
        nick_name = (TextView) itemView.findViewById(R.id.nick_name);
        content = (TextView) itemView.findViewById(R.id.content);
        createtime = (TextView) itemView.findViewById(R.id.createtime);
        uset_linearLayout = (AutoLinearLayout) itemView.findViewById(R.id.uset_linearLayout);
    }

    @Override
    public void setData(Context context, ParticularsRoot.DataBean.CommentsBean commentsBean) {
        ImageLoader.getInstance().displayImage(commentsBean.user.icon,user_icon, ImageLoaderUtils.initOptions());
        nick_name.setText(commentsBean.user.nick_name);
        content.setText(commentsBean.content);
        createtime.setText(commentsBean.createtime);

        if (commentsBean.pictures!=null&&commentsBean.pictures.size()!=0){
            uset_linearLayout.setVisibility(View.VISIBLE);
            for (int i = 0; i < commentsBean.pictures.size(); i++) {
                ImageView imageView = new ImageView(context);
                ImageLoader.getInstance().displayImage(commentsBean.user.icon,user_icon, CommonUtils.getinitOptionsCircle());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(200,200);
                uset_linearLayout.addView(imageView,params);
            }
        }
    }
}
