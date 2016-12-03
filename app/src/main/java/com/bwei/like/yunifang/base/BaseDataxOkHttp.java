package com.bwei.like.yunifang.base;

import android.text.TextUtils;

import com.bwei.like.yunifang.utils.CommonUtils;
import com.bwei.like.yunifang.utils.MD5Encoder;
import com.bwei.like.yunifang.view.ShowingPage;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.xutils.http.RequestParams;

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
public abstract class BaseDataxOkHttp {

    private final File fileDir;

    public BaseDataxOkHttp() {
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
    private void getDataFromNet(final String path, final String args, final int index, final int validTime) {
        //创建okHttpClient对象
        OkHttpClient mOkHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(path+"?"+args)
                .build();
        //new call
        Call call = mOkHttpClient.newCall(request);
        //请求加入调度
        call.enqueue(new com.squareup.okhttp.Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                setResulttError(ShowingPage.StateType.STATE_LOAD_ERROR);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String data = response.body().string();
                //okHttp默认子线程，必须保证在主线程中运行
                CommonUtils.runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        setResultData(data);
                    }
                });
                //写到本地
                writeDataToLocal(data,path,args,index,validTime);
            }
        });
    }

    /**
     * 请求出错
     *
     * @param stateLoadError
     */
    protected abstract void setResulttError(ShowingPage.StateType stateLoadError);

    private void writeDataToLocal(String s, String path,String args, int index, int validTime) {
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
            postDataFromNet(path,argsMap,index,validTime);
        } else {
            //从本地获取数据
            String data = getDataFromLocal(path, index, validTime);
            //判断数据是否为空
            if (TextUtils.isEmpty(data)) {
                postDataFromNet(path,argsMap,index,validTime);
            } else {
                setResultData(data);
            }
        }
    }

    public void postDataFromNet(final String path, HashMap<String, String> argsMap, final int index, final int validTime) {
        //将Map中的参数取出
        Set<String> strings = argsMap.keySet();
        FormEncodingBuilder formEncodingBuilder = new FormEncodingBuilder();
        for (String key : strings) {
            formEncodingBuilder.add(key, argsMap.get(key));
        }
        //创建client对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //创建body
        RequestBody requestBody = formEncodingBuilder.build();

        Request request = new Request.Builder()
                .url(path)
                .post(requestBody)
                .build();

        //new call
        Call call = okHttpClient.newCall(request);
        //请求加入调度
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                //设置error
                setResulttError(ShowingPage.StateType.STATE_LOAD_ERROR);
            }
            @Override
            public void onResponse(final Response response) throws IOException {
                final String data = response.body().string();
                CommonUtils.runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        //设置数据
                        setResultData(data);
                    }
                });

                //写入本地
                writeDataToLocal(data,path,null,index,validTime);
            }
        });
    }
}
