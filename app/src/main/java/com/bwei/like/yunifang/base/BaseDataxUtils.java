package com.bwei.like.yunifang.base;

import android.text.TextUtils;

import com.bwei.like.yunifang.utils.CommonUtils;
import com.bwei.like.yunifang.utils.LogUtils;
import com.bwei.like.yunifang.utils.MD5Encoder;
import com.bwei.like.yunifang.view.ShowingPage;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by LiKe on 2016/11/29.
 */
public abstract class BaseDataxUtils {

    private final File fileDir;
    public static final int NOTIME = 0;
    public static final int NORMALTIME = 3 * 24 * 60 * 60 * 1000;
    public BaseDataxUtils() {
        //创建本地路径
        File cacheDir = CommonUtils.getContext().getCacheDir();
        fileDir = new File(cacheDir, "yunifang");
        if (!fileDir.exists()) {
            fileDir.mkdir();
        }
    }

    /**
     * @param path      请求地址
     * @param args      请求参数
     * @param index     页码索引
     * @param validTime 有效时间
     */
    public void getData(String path, String args, int index, int validTime) {
        if (validTime == 0) {
            getDataFromNet(path, args, index, validTime);
        } else {
            //从本地获取数据
            String data = getDataFromLocal(path, index, validTime);
            //判断数据是否为空
            if (TextUtils.isEmpty(data)) {
                getDataFromNet(path, args, index, validTime);
            } else {
                setResultData(data);
            }
        }
    }

    /**
     * 从本地获取数据
     *
     * @param path
     * @param index
     * @param validTime
     * @return
     */
    private String getDataFromLocal(String path, int index, int validTime) {

        //从本地获取数据
        try {
            File file = new File(fileDir, MD5Encoder.encode(path) + index);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            //想将第一行事件读取出来进行比较是否过期
            String s = bufferedReader.readLine();

            long time = Long.parseLong(s);

            if (System.currentTimeMillis() < time) {
                //将数据读取出来并返回
                String line = null;
                StringBuilder stringBuilder = new StringBuilder();
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                bufferedReader.close();
                return stringBuilder.toString();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从网络获取数据
     *
     * @param path
     * @param args
     * @param index
     * @param validTime
     */
    private void getDataFromNet(final String path, String args, final int index, final int validTime) {
        RequestParams requestParams = new RequestParams(path);
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                //创建抽象方法将数据回传
                setResultData(s);
                //将数据写入本地
                writeDataToLocal(s, path, index, validTime);
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                setResulttError(ShowingPage.StateType.STATE_LOAD_ERROR);
            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    /**
     * 请求出错
     *
     * @param stateLoadError
     */
    protected abstract void setResulttError(ShowingPage.StateType stateLoadError);

    private void writeDataToLocal(String s, String path, int index, int validTime) {
        try {
            File file = new File(fileDir, MD5Encoder.encode(path) + index);
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            //先将读取事件写入文件
            bufferedWriter.write(System.currentTimeMillis() + validTime + "\r\n");
            //在将json串写入文件
            bufferedWriter.write(s);
            //将流关闭
            bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public abstract void setResultData(String resultData);


    public void postData(String path, HashMap<String, String> argsMap, int index, int validTime) {
        if (validTime == 0) {
            postDataFromNet(path,argsMap,validTime);
        } else {
            //从本地获取数据
            String data = getDataFromLocal(path, index, validTime);
            //判断数据是否为空
            if (TextUtils.isEmpty(data)) {
                postDataFromNet(path,argsMap,validTime);
            } else {
                setResultData(data);
            }
        }
    }

    public void postDataFromNet(final String path, HashMap<String, String> argsMap, int validTime) {
        RequestParams requestParams = new RequestParams(path);
        //将集合中的参数取出
        Set<Map.Entry<String, String>> entries = argsMap.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            requestParams.addParameter(entry.getKey(),entry.getValue());
        }
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                //将数据回传
                setResultData(s);

            }

            @Override
            public void onError(Throwable throwable, boolean b) {

            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}
