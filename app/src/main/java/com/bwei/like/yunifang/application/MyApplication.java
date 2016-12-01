package com.bwei.like.yunifang.application;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Process;

import com.bwei.like.yunifang.utils.ImageLoaderUtils;
import com.zhy.autolayout.config.AutoLayoutConifg;

import org.xutils.x;

/**
 * Created by LiKe on 2016/11/28.
 */
public class MyApplication extends Application {

    private static Context context;
    private static Handler handler;
    private static int mainThreadId;

    @Override
    public void onCreate() {
        super.onCreate();


        AutoLayoutConifg.getInstance().useDeviceSize();
        //获取上下文
        context = getApplicationContext();
        //创建handler
        handler = new Handler();
        //获取主线程号
        mainThreadId = Process.myTid();
        //imageLoader初始化
        ImageLoaderUtils.initConfiguration(this);
        //初始化xUtils3
        x.Ext.init(this);
        //设置degbug模式
        x.Ext.setDebug(true);
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
