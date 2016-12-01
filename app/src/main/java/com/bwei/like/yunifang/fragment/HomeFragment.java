package com.bwei.like.yunifang.fragment;

import android.view.View;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bwei.like.yunifang.R;
import com.bwei.like.yunifang.base.BaseDataxUtils;
import com.bwei.like.yunifang.base.BaseFragment;
import com.bwei.like.yunifang.bean.HomeRoot;
import com.bwei.like.yunifang.utils.CommonUtils;
import com.bwei.like.yunifang.utils.LogUtils;
import com.bwei.like.yunifang.utils.UrlUtils;
import com.bwei.like.yunifang.view.MyRoolViewPager;
import com.bwei.like.yunifang.view.ShowingPage;
import com.google.gson.Gson;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiKe on 2016/11/28.
 */
public class HomeFragment extends BaseFragment implements SpringView.OnFreshListener {

    private String data;
    private View view;
    private SpringView home_springView;
    private MyRoolViewPager home_MyRoolViewPager;
    private AutoLinearLayout home_dots_linearLayout;
    int[] dotArray = new int[]{R.mipmap.page_indicator_focused, R.mipmap.page_indicator_unfocused};
    ArrayList<String> imgUrlList = new ArrayList<>();
    ArrayList<ImageView> dotList = new ArrayList<>();
    private HomeRoot homeRoot;

    @Override
    protected View createSuccessView() {
        view = CommonUtils.inflate(R.layout.homefragment_item);
        //初始化控件
        initView();
        initRoolViewPager();
        return view;
    }

    /**
     * 初始化轮播图
     */
    private void initRoolViewPager() {
        List<HomeRoot.DataBean.Ad1Bean> ad1 = homeRoot.data.ad1;
        for (int i = 0; i < ad1.size(); i++) {
            imgUrlList.add(ad1.get(i).image);
        }
        //初始化小圆点
        initDots(ad1);

        home_MyRoolViewPager.initData(imgUrlList,dotArray,dotList);
        home_MyRoolViewPager.startViewPager();
        home_MyRoolViewPager.setOnPageClickListener(new MyRoolViewPager.OnPageClickListener() {
            @Override
            public void setOnPage(int position) {
                Toast.makeText(getActivity(), "我点击了"+position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * //初始化小圆点
     * @param ad1
     */
    private void initDots(List<HomeRoot.DataBean.Ad1Bean> ad1) {
        for (int i = 0; i < ad1.size(); i++) {
            ImageView imageView = new ImageView(getActivity());
            if (i==0){
                imageView.setImageResource(dotArray[0]);
            }else {
                imageView.setImageResource(dotArray[1]);
            }
            dotList.add(imageView);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(CommonUtils.dip2px(10),CommonUtils.dip2px(5),CommonUtils.dip2px(10),CommonUtils.dip2px(5));
            home_dots_linearLayout.addView(imageView,params);
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
        home_MyRoolViewPager = (MyRoolViewPager) view.findViewById(R.id.home_MyRoolViewPager);
        home_dots_linearLayout = (AutoLinearLayout) view.findViewById(R.id.home_dots_linearLayout);
    }

    @Override
    protected void onLoad() {
        MyBaseData myBaseData = new MyBaseData();
        myBaseData.getData(UrlUtils.HOME_URL , UrlUtils.HOME_ARGS, 1, BaseDataxUtils.NORMALTIME);
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
            }else {
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
}
