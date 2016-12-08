package com.bwei.like.yunifang.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bwei.like.yunifang.R;
import com.bwei.like.yunifang.activity.Ad5WebView_Activity;
import com.bwei.like.yunifang.activity.Home_MoreActivity;
import com.bwei.like.yunifang.activity.Login_Activity;
import com.bwei.like.yunifang.activity.Particulars_Activity;
import com.bwei.like.yunifang.activity.SelectAllActivity;
import com.bwei.like.yunifang.adapater.CommonAdapter;
import com.bwei.like.yunifang.adapater.ViewHolder;
import com.bwei.like.yunifang.application.MyApplication;
import com.bwei.like.yunifang.base.BaseDataxUtils;
import com.bwei.like.yunifang.base.BaseFragment;
import com.bwei.like.yunifang.bean.HomeRoot;
import com.bwei.like.yunifang.utils.CommonUtils;
import com.bwei.like.yunifang.utils.UrlUtils;
import com.bwei.like.yunifang.view.Home_GridView;
import com.bwei.like.yunifang.view.Home_ListView;
import com.bwei.like.yunifang.view.MyRoolViewPager;
import com.bwei.like.yunifang.view.RotateDownPageTransformer;
import com.bwei.like.yunifang.view.ShowingPage;
import com.google.gson.Gson;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.autolayout.AutoLinearLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiKe on 2016/11/28.
 */
public class HomeFragment extends BaseFragment implements SpringView.OnFreshListener ,View.OnClickListener{

    private String data;
    private View view;
    private SpringView home_springView;
    private MyRoolViewPager home_MyRoolViewPager;
    private AutoLinearLayout home_dots_linearLayout;
    int[] dotArray = new int[]{R.mipmap.page_indicator_focused, R.mipmap.page_indicator_unfocused};
    ArrayList<String> imgUrlList = new ArrayList<>();
    ArrayList<ImageView> dotList = new ArrayList<>();
    private HomeRoot homeRoot;
    private Home_GridView home_gridView;
    private HorizontalScrollView my_HomeHorizontalScrollView;
    private TextView home_benWeek_tv;
    private AutoLinearLayout home_Week_linearLayout;
    private Home_ListView home_homeListView;
    private Home_GridView home_defaule_gridView;
    private ViewPager home_youhui_viewPager;
    private PopupWindow popupWindow;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    backgroundAlpha((float) msg.obj);
                    break;
            }
        }
    };
    private TextView selectAll_goods;

    @Override
    protected View createSuccessView() {
        view = CommonUtils.inflate(R.layout.home_fragment_item);
        //初始化控件
        initView();
        //轮播图
        initRoolViewPager();
        //功能专区
        homeFunction();
        //优惠活动
        youHuiActivity();
        //本周热销
       // currentWeek();
        //热门专题
        hotSubject();
        //默认商品展示
        defaultGoodsShow();
        return view;
    }

    /**
     * 优惠活动
     */
    private void youHuiActivity() {
        //获取数据
        final List<HomeRoot.DataBean.ActivityInfoBean.ActivityInfoListBean> activityInfoList = homeRoot.data.activityInfo.activityInfoList;

        //设置Page间间距
        home_youhui_viewPager.setPageMargin(10);
        //设置缓存的页面数量
        home_youhui_viewPager.setOffscreenPageLimit(3);
        home_youhui_viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return Integer.MAX_VALUE;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, final int position) {
                View inflate = CommonUtils.inflate(R.layout.home_youhuiviewpager_item);
                ImageView imageView = (ImageView) inflate.findViewById(R.id.home_youhuiViwePager_image);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setBackgroundResource(R.drawable.share_square);
                container.addView(inflate);
                ImageLoader.getInstance().displayImage(activityInfoList.get(position % activityInfoList.size()).activityImg, imageView, CommonUtils.getinitOptionsCircle());

                //优惠活动监听事件跳转事件
                inflate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       // intentJump("activityInfoListBean",activityInfoList.get(position % activityInfoList.size()));
                    }
                });
                return inflate;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });

        home_youhui_viewPager.setPageTransformer(true, new RotateDownPageTransformer());
    }

    /**
     * 默认商品展示
     */
    private void defaultGoodsShow() {
        home_defaule_gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        List<HomeRoot.DataBean.DefaultGoodsListBean> defaultGoodsList = homeRoot.data.defaultGoodsList;
        home_defaule_gridView.setAdapter(new CommonAdapter<HomeRoot.DataBean.DefaultGoodsListBean>(getActivity(), defaultGoodsList, R.layout.home_home_gridview_item) {
            @Override
            public void convert(ViewHolder helper, HomeRoot.DataBean.DefaultGoodsListBean item) {
                helper.setImageByUrl(R.id.home_default_gridView_image, item.goods_img);
                helper.setText(R.id.efficacy, item.efficacy);
                helper.setText(R.id.goods_name, item.goods_name);
                helper.setText(R.id.shop_price, "￥" + item.shop_price);
                helper.setText(R.id.market_price, "￥" + item.market_price);
            }
        });
    }

    /**
     * 热门专题
     */
    private void hotSubject() {
        final List<HomeRoot.DataBean.SubjectsBean> subjects = homeRoot.data.subjects;
        home_homeListView.setAdapter(new CommonAdapter<HomeRoot.DataBean.SubjectsBean>(getActivity(), subjects, R.layout.home_home_listview_item) {
            @Override
            public void convert(ViewHolder viewHolder, HomeRoot.DataBean.SubjectsBean item) {
                int position = viewHolder.getPosition();
                ImageView home_hot_LargeImage = viewHolder.getView(R.id.home_hot_LargeImage);
                home_hot_LargeImage.setScaleType(ImageView.ScaleType.FIT_XY);
                viewHolder.setImageByUrl(R.id.home_hot_LargeImage, item.image);
                List<HomeRoot.DataBean.SubjectsBean.GoodsListBean> goodsList = item.goodsList;
                LinearLayout home_hot_linearLayout = viewHolder.getView(R.id.home_hot_linearLayout);
                home_hot_linearLayout.removeAllViews();
                for (int i = 0; i < 7; i++) {
                    View inflate = CommonUtils.inflate(R.layout.home_gallery_item);
                    TextView home_gallery_name = (TextView) inflate.findViewById(R.id.home_gallery_name);
                    ImageView home_gallery_image = (ImageView) inflate.findViewById(R.id.home_gallery_image);
                    TextView shop_price = (TextView) inflate.findViewById(R.id.shop_price);
                    TextView market_price = (TextView) inflate.findViewById(R.id.market_price);
                    shop_price.setText("￥" + goodsList.get(i).shop_price);
                    market_price.setText(goodsList.get(i).market_price + "");
                    home_gallery_name.setText(goodsList.get(i).goods_name);
                    shop_price.setTextColor(getResources().getColor(R.color.YuniFangZhangHao_textColor));
                    ImageLoader.getInstance().displayImage(goodsList.get(i).goods_img, home_gallery_image, CommonUtils.getinitOptionsCircle());
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(5, 5, 5, 5);
                    home_hot_linearLayout.addView(inflate, params);

                    //跳转商品详情页
                    setHotMoreOnClickListener(inflate,goodsList.get(i),"goodsListBean", Particulars_Activity.class);
                }
                //监听大图跳转界面
                setHotMoreOnClickListener(home_hot_LargeImage,subjects.get(position),"subjectsBean",Home_MoreActivity.class);
                ImageView imageView = new ImageView(getActivity());
                imageView.setImageResource(R.mipmap.home_more);
                home_hot_linearLayout.addView(imageView);
                setHotMoreOnClickListener(imageView,subjects.get(position),"subjectsBean",Home_MoreActivity.class);
            }
        });
    }

    /**
     * //监听大图跳转界面
     * 展示更多数据
     * @param
     * @param
     * @param
     */
    private void setHotMoreOnClickListener(View imageView, final Object o, final String str, final Class c) {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), c);
                intent.putExtra(str, (Serializable) o);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.login_in,R.anim.login_in0);
            }
        });
    }

    /**
     * 本周热销
     */
//    private void currentWeek() {
//        LinearLayout.LayoutParams params;
//        final List<HomeRoot.DataBean.BestSellersBean> bestSellers = homeRoot.data.bestSellers;
//        home_benWeek_tv.setText("~~" + bestSellers.get(0).name + "~~");
//        home_benWeek_tv.setTextColor(Color.BLACK);
//        home_Week_linearLayout.removeAllViews();
//        for (int i = 0; i < 7; i++) {
//            View inflate = CommonUtils.inflate(R.layout.home_gallery_item);
//            TextView home_gallery_name = (TextView) inflate.findViewById(R.id.home_gallery_name);
//            ImageView home_gallery_image = (ImageView) inflate.findViewById(R.id.home_gallery_image);
//            TextView shop_price = (TextView) inflate.findViewById(R.id.shop_price);
//            TextView market_price = (TextView) inflate.findViewById(R.id.market_price);
//            shop_price.setText("￥" + bestSellers.get(0).goodsList.get(i).shop_price);
//            market_price.setText(bestSellers.get(0).goodsList.get(i).market_price + "");
//            shop_price.setTextColor(getResources().getColor(R.color.YuniFangZhangHao_textColor));
//            params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//            home_gallery_name.setText(bestSellers.get(0).goodsList.get(i).goods_name);
//            ImageLoader.getInstance().displayImage(bestSellers.get(0).goodsList.get(i).goods_img, home_gallery_image, CommonUtils.getinitOptionsCircle());
//            params.setMargins(5, 5, 5, 5);
//            home_Week_linearLayout.addView(inflate, params);
//
//            //点击商品跳转详情界面
//            inflate.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                }
//            });
//        }
//        ImageView imageView = new ImageView(getActivity());
//        imageView.setImageResource(R.mipmap.home_more);
//        home_Week_linearLayout.addView(imageView);
//
//        //更多监听事件
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), Home_MoreActivity.class);
//                intent.putExtra("bestSellers", (Serializable) homeRoot.data.bestSellers);
//                startActivity(intent);
//            }
//        });
//    }


    /**
     * 功能专区
     */
    private void homeFunction() {
        //设置点击时没有背景颜色
        home_gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        home_gridView.setAdapter(new CommonAdapter<HomeRoot.DataBean.Ad5Bean>(getActivity(), homeRoot.data.ad5, R.layout.home_function_item) {
            @Override
            public void convert(ViewHolder viewHolder, HomeRoot.DataBean.Ad5Bean item) {
                viewHolder.setText(R.id.home_function_tv, item.title);
                viewHolder.setImageByUrl(R.id.home_function_image, item.image);
            }
        });

        //监听事件
        home_gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    //每日签到
                    case 0:
                        if (MyApplication.loginFlag) {

                        } else {
                            bottomwindow(view);
                            showPopupWidow();
                        }
                        break;

                    //积分商城
                    case 1:
                        intentJump("ad5",homeRoot.data.ad5.get(position));
                        break;

                    //兑换专区
                    case 2:
                        if (MyApplication.loginFlag) {
                            intentJump("ad5",homeRoot.data.ad5.get(position));
                        } else {
                            bottomwindow(view);
                            showPopupWidow();
                        }
                        break;

                    //真伪查询
                    case 3:
                        intentJump("ad5",homeRoot.data.ad5.get(position));
                        break;

                    case 4:
                        intentJump("ad5",homeRoot.data.ad5.get(position));
                        break;

                    case 5:
                        intentJump("ad5",homeRoot.data.ad5.get(position));
                        break;

                    case 6:
                        intentJump("ad5",homeRoot.data.ad5.get(position));
                        break;

                    case 7:
                   //     intentJump("ad5",homeRoot.data.ad5.get(position));
                        break;
                }
            }
        });
    }

    /**
     * 积分商城和真伪查询页面跳转
     * @param string
     * @param object
     */
    private void intentJump(String string,Object object) {
        Intent intent = new Intent(getActivity(), Ad5WebView_Activity.class);
        intent.putExtra(string, (Serializable) object);
        getActivity().startActivity(intent);
        getActivity().overridePendingTransition(R.anim.login_in,R.anim.login_in0);
    }

    /**
     * 初始化轮播图
     */
    private void initRoolViewPager() {
        final List<HomeRoot.DataBean.Ad1Bean> ad1 = homeRoot.data.ad1;
        for (int i = 0; i < ad1.size(); i++) {
            imgUrlList.add(ad1.get(i).image);
        }
        //初始化小圆点
        initDots(ad1);

        home_MyRoolViewPager.initData(imgUrlList, dotArray, dotList);
        home_MyRoolViewPager.startViewPager();
        home_MyRoolViewPager.setOnPageClickListener(new MyRoolViewPager.OnPageClickListener() {
            @Override
            public void setOnPage(int position) {
                Toast.makeText(getActivity(), "我点击了" + position, Toast.LENGTH_SHORT).show();
                intentJump("adl",ad1.get(position));
            }
        });
    }

    /**
     * //初始化小圆点
     *
     * @param ad1
     */
    private void initDots(List<HomeRoot.DataBean.Ad1Bean> ad1) {
        for (int i = 0; i < ad1.size(); i++) {
            ImageView imageView = new ImageView(getActivity());
            if (i == 0) {
                imageView.setImageResource(dotArray[0]);
            } else {
                imageView.setImageResource(dotArray[1]);
            }
            dotList.add(imageView);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(CommonUtils.dip2px(10), CommonUtils.dip2px(5), CommonUtils.dip2px(10), CommonUtils.dip2px(5));
            home_dots_linearLayout.addView(imageView, params);
        }
    }

    /**
     * 初始化控件
     */
    private void initView() {
        home_springView = (SpringView) view.findViewById(R.id.home_springView);
        //设置下拉刷新，上拉加载
        home_springView.setListener(this);
        //设置springView默认头和尾
        home_springView.setHeader(new DefaultHeader(getActivity()));
        // home_springView.setFooter(new DefaultFooter(getActivity()));
        //设置springView头部隐藏
        home_springView.setType(SpringView.Type.FOLLOW);

        //查找控件
        //轮播图
        home_MyRoolViewPager = (MyRoolViewPager) view.findViewById(R.id.home_MyRoolViewPager);
        home_dots_linearLayout = (AutoLinearLayout) view.findViewById(R.id.home_dots_linearLayout);
        //多功能菜单
        home_gridView = (Home_GridView) view.findViewById(R.id.home_gridView);

        //优惠活动
        home_youhui_viewPager = (ViewPager) view.findViewById(R.id.home_Youhui_viewPager);

        //本周热销
        my_HomeHorizontalScrollView = (HorizontalScrollView) view.findViewById(R.id.home_MyHorizontalScrollView);
        home_benWeek_tv = (TextView) view.findViewById(R.id.home_benWeek_tv);
        home_Week_linearLayout = (AutoLinearLayout) view.findViewById(R.id.home_linear_HorizontalScrollView);

        //热门专题
        home_homeListView = (Home_ListView) view.findViewById(R.id.home_homeListView);


        //默认的商品
        home_defaule_gridView = (Home_GridView) view.findViewById(R.id.home_defau_gridView);

        //查看全部商品
        selectAll_goods = (TextView) view.findViewById(R.id.selectAll_goods);
        selectAll_goods.setOnClickListener(this);
    }

    @Override
    protected void onLoad() {
        MyBaseData myBaseData = new MyBaseData();
        myBaseData.getData(UrlUtils.HOME_URL, UrlUtils.HOME_ARGS, 1, BaseDataxUtils.NORMALTIME);
    }

    /**
     * 查看全部商品
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.selectAll_goods:
                startActivity(new Intent(getActivity(), SelectAllActivity.class));
                getActivity().overridePendingTransition(R.anim.login_in,R.anim.login_in0);
                break;
        }
    }


    class MyBaseData extends BaseDataxUtils {

        @Override
        protected void setResulttError(ShowingPage.StateType stateLoadError) {
            HomeFragment.this.showingPage(ShowingPage.StateType.STATE_LOAD_ERROR);
        }

        @Override
        public void setResultData(String resultData) {
            HomeFragment.this.data = resultData;
            //解析gson
            Gson gson = new Gson();
            homeRoot = gson.fromJson(data, HomeRoot.class);
            if (homeRoot != null && homeRoot.code == 200) {
                showingPage(ShowingPage.StateType.STATE_LOAD_SUCCESS);
            } else {
                showingPage(ShowingPage.StateType.STATE_LOAD_EMPTY);
            }
        }

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadmore() {

    }

    //停止刷新
    public void lode() {
        home_springView.scrollTo(0, 0);
    }

    /**
     * 点击每日签到弹出popupwindow
     */

    public void bottomwindow(View view) {
        if (popupWindow != null && popupWindow.isShowing()) {
            return;
        }
        View layout = CommonUtils.inflate(R.layout.home_popupwindow_item);
        Button back_button = (Button) layout.findViewById(R.id.back_button);
        Button goto_Login_button = (Button) layout.findViewById(R.id.goto_Login_button);
        popupWindow = new PopupWindow(layout,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        //点击空白处时，隐藏掉pop窗口
//        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
//        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //添加弹出、弹入的动画
        popupWindow.setAnimationStyle(R.style.Popupwindow);
        int[] location = new int[2];

        view.getLocationOnScreen(location);
        popupWindow.showAtLocation(view, Gravity.CENTER | Gravity.CENTER, 0, 0);
        //添加pop窗口关闭事件，主要是实现关闭时改变背景的透明度
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                hidePopupWindow();
            }
        });
        backgroundAlpha(1f);

        /**
         * 按钮点击事件
         */
        back_buttonAndGotoLogin(back_button,goto_Login_button);
    }

    /**
     * 两个按钮点击事件
     * @param back_button
     * @param goto_Login_button
     */
    private void back_buttonAndGotoLogin(Button back_button, Button goto_Login_button) {
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        //
        goto_Login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                getActivity().startActivity(new Intent(getActivity(), Login_Activity.class));
            }
        });
    }

    /**
     * popupwindow动画
     * @param
     */
    private void showPopupWidow() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                float alpha = 1f;
                while (alpha > 0.5f) {
                    try {
                        //4是根据弹出动画时间和减少的透明度计算
                        Thread.sleep(4);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Message msg = mHandler.obtainMessage();
                    msg.what = 1;
                    //每次减少0.01，精度越高，变暗的效果越流畅
                    alpha -= 0.01f;
                    msg.obj = alpha;
                    mHandler.sendMessage(msg);
                }
            }

        }).start();
    }

    /**
     * popupwindow
     * @param
     */
    private void hidePopupWindow() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //此处while的条件alpha不能<= 否则会出现黑屏
                float alpha = 0.5f;
                while (alpha < 0.9f) {
                    try {
                        Thread.sleep(4);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.d("HeadPortrait", "alpha:" + alpha);
                    Message msg = mHandler.obtainMessage();
                    msg.what = 1;
                    alpha += 0.01f;
                    msg.obj = alpha;
                    mHandler.sendMessage(msg);
                }
            }

        }).start();
    }

    /**
     * popupwindow动画
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getActivity().getWindow().setAttributes(lp);
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

}
