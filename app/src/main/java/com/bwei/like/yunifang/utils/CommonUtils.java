package com.bwei.like.yunifang.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.View;

import com.bwei.like.yunifang.application.MyApplication;

/**
 * Created by LiKe on 2016/11/28.
 */
public class CommonUtils {

    private static final String SP_NAME = "YUNIFANG";
    private static SharedPreferences sharedPreferences;

    //上下文
    public static Context getContext() {
        return MyApplication.getContext();
    }

    //handler
    public static Handler getHandler() {
        return MyApplication.getHandler();
    }

    //获取主线程
    public static Thread getMainThread() {
        return MyApplication.getMainThread();
    }

    //主线程id
    public static int getMainThreadId() {
        return MyApplication.getMainThreadId();
    }

    //获取资源文件夹操作
    public static Resources getResources() {
        return getContext().getResources();
    }

    //获取资源文件字符串
    public static String getString(int stringId) {
        return getResources().getString(stringId);
    }

    //返回drawablewe文件
    public static Drawable getDrawable(int drawableId) {
        return getResources().getDrawable(drawableId);
    }

    // dip--->px, 1dp = 2px,定义一个控件的宽高 layoutParams(w,h)
    public static int dip2px(int dip) {
        // 获取dp和px的转换关系的变量
        float density = getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5);
    }

    // px---->dp
    public static int px2dip(int px) {
        float density = getResources().getDisplayMetrics().density;
        return (int) (px / density + 0.5);
    }

    //判断当前线程是否在主线程
    public static boolean isRunInMainThread() {
        //先获取主线程的线程号然后和当前线程号比较
        return getMainThreadId() == android.os.Process.myTid();
    }


    // 区分是否在 主线程中,做UI处理
    public static void runOnMainThread(Runnable runnable) {
        if (isRunInMainThread()) {
            // 如果当前任务就在主线程中,直接运行
            runnable.run();
        } else {
            // 如果当任务在子线程中,则将其传递到主线程中去执行
            getHandler().post(runnable);
        }
    }

    public static View inflate(int layoutId) {
        return View.inflate(getContext(), layoutId, null);
    }

    // 将dimens中的dp转换成像素值
    public static int getDimens(int dimensId) {
        return getResources().getDimensionPixelSize(dimensId);
    }

    public static void postDelayed(Runnable runnable, int delayed) {
        // 延时做任务方法
        getHandler().postDelayed(runnable, delayed);
    }

    // 移除指定任务的操作
    public static void removeCallBack(Runnable runnable) {
        getHandler().removeCallbacks(runnable);
    }

    //保存sp
    public static void saveBolean(String str, boolean flag) {
        if (sharedPreferences == null) {
            sharedPreferences = MyApplication.getContext().getSharedPreferences(SP_NAME, MyApplication.getContext().MODE_PRIVATE);
        }
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putBoolean(str, flag);
        edit.commit();
    }

    //获取sp
    public static boolean getBoolean(String str) {
        if (sharedPreferences == null) {
            sharedPreferences = MyApplication.getContext().getSharedPreferences(SP_NAME, MyApplication.getContext().MODE_PRIVATE);
        }
        return sharedPreferences.getBoolean(str, false);
    }
}
