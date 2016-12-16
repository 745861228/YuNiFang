package com.bwei.like.yunifang.application;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Process;

import com.bwei.like.yunifang.utils.CommonUtils;
import com.bwei.like.yunifang.utils.ImageLoaderUtils;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.zhy.autolayout.config.AutoLayoutConifg;

import org.xutils.x;

/**
 * Created by LiKe on 2016/11/28.
 */
public class MyApplication extends Application {

    private static Context context;
    private static Handler handler;
    private static int mainThreadId;

    public static boolean loginFlag = false;

    @Override
    public void onCreate() {
        super.onCreate();

        //imageLoader初始化
        ImageLoaderUtils.initConfiguration(getApplicationContext());

        AutoLayoutConifg.getInstance().useDeviceSize();
        //获取上下文
        context = getApplicationContext();
        //创建handler
        handler = new Handler();
        //获取主线程号
        mainThreadId = Process.myTid();
        //初始化xUtils3
        x.Ext.init(this);
        //设置degbug模式
        x.Ext.setDebug(true);
        //umeng
        UMShareAPI.get(this);
        PlatformConfig.setWeixin("wx967daebe835fbeac", "5bb696d9ccd75a38c8a0bfe0675559b3");
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");

        //获取当前是否为登陆状态
        loginFlag = CommonUtils.getBoolean("loginFlag");
    }



    public static int getMainThreadId(){
        return mainThreadId;
    }

    /**
     * 获取整个应用的上下文
     */
    public static Context getContext(){
        return context;
    }

    public static Handler getHandler(){
        return handler;
    }

    /**
     * 获取主线程
     * @return
     */
    public static Thread getMainThread(){
        return Thread.currentThread();
    }


}
