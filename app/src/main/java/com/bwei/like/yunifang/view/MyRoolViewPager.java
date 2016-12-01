package com.bwei.like.yunifang.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bwei.like.yunifang.utils.ImageLoaderUtils;
import com.bwei.like.yunifang.utils.LogUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by LiKe on 2016/12/1.
 */
public class MyRoolViewPager extends ViewPager {
    private ArrayList<String> imgUrlList;
    private ArrayList<ImageView> dotList;
    private DisplayImageOptions displayImageOptions;
    private MyPagerAdapater myPagerAdapater;
    public static final int SUCCESS = 0;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == SUCCESS) {
                //获取当前所在的页数
                int currentItem = MyRoolViewPager.this.getCurrentItem();
                currentItem++;
                MyRoolViewPager.this.setCurrentItem(currentItem);
                handler.sendEmptyMessageDelayed(SUCCESS, 2000);
            }
        }
    };
    private OnPageClickListener onPageClickListener;

    public MyRoolViewPager(Context context) {
        super(context);
        init();
    }

    public MyRoolViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        displayImageOptions = ImageLoaderUtils.initOptionsCircle();
    }

    public void initData(final ArrayList<String> imgUrlList, final int[] dotArray, final ArrayList<ImageView> dotList) {
        this.imgUrlList = imgUrlList;
        this.dotList = dotList;
        //设置监听事件
        this.setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < dotList.size(); i++) {
                    if (position % imgUrlList.size() == i) {
                        dotList.get(i).setImageResource(dotArray[0]);
                    } else {
                        dotList.get(i).setImageResource(dotArray[1]);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 设置开始无限自动轮播
     */

    public void startViewPager() {
        if (myPagerAdapater == null) {
            myPagerAdapater = new MyPagerAdapater();
            this.setAdapter(myPagerAdapater);
        }
        handler.sendEmptyMessageDelayed(SUCCESS, 2);
    }


    /**
     * 设置viewpager适配器
     */

    class MyPagerAdapater extends PagerAdapter {

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
            ImageView imageView = new ImageView(getContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            ImageLoader.getInstance().displayImage(imgUrlList.get(position % imgUrlList.size()), imageView, displayImageOptions);
            container.addView(imageView);
            imageView.setOnTouchListener(new OnTouchListener() {

                private boolean flag=false;
                private long downTime;
                private float downX;
                private float downY;


                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            handler.removeCallbacksAndMessages(null);
                            //获取按下的坐标
                            downY = event.getY();
                            downX = event.getX();
                            //获取按下的事件
                            downTime = System.currentTimeMillis();

                            break;

                        case MotionEvent.ACTION_MOVE:
                            handler.removeCallbacksAndMessages(null);
                            LogUtils.i("UPTAG****","哥们移动了了");
                            break;

                        case MotionEvent.ACTION_UP:
                            LogUtils.i("UPTAG****","哥们抬起来了");
                            //获取抬起时的坐标值
                            float upY = event.getY();
                            float upX = event.getX();
                            //判断当前是否是点击了
                            if (downX == upX && downY == upY && System.currentTimeMillis() - downTime < 1000) {
                                //设置监听回调
                                onPageClickListener.setOnPage(position % imgUrlList.size());
                            }

                                handler.sendEmptyMessageDelayed(SUCCESS, 2000);

                            break;

                        case MotionEvent.ACTION_CANCEL:
                            //   handler.sendEmptyMessageDelayed(SUCCESS, 2000);
                            break;
                    }

                    return true;
                }
            });
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            removeView((View) object);
        }
    }

    //准备接口
    public interface OnPageClickListener {
        public void setOnPage(int position);
    }

    //设置接口
    public void setOnPageClickListener(OnPageClickListener onPageClickListener) {
        this.onPageClickListener = onPageClickListener;
    }


    //当前View不可见
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        //移除任务和消息
        handler.removeCallbacksAndMessages(null);
    }
}
