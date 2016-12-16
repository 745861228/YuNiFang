package com.bwei.like.yunifang.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bwei.like.yunifang.MainActivity;
import com.bwei.like.yunifang.R;
import com.bwei.like.yunifang.adapater.CommonAdapter;
import com.bwei.like.yunifang.adapater.ViewHolder;
import com.bwei.like.yunifang.application.MyApplication;
import com.bwei.like.yunifang.base.BaseActivity;
import com.bwei.like.yunifang.bean.VersionInfo;
import com.bwei.like.yunifang.utils.CommonUtils;
import com.bwei.like.yunifang.utils.DataClearManager;
import com.bwei.like.yunifang.utils.LogUtils;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

public class UserSeting_Activity extends BaseActivity implements View.OnClickListener {

    private ImageView back_image;
    private ListView user_Setting_listview;
    private Button out_login;
    private TextView include_meddim_tv;
    private String[] itemStr = {"购物须知", "意见反馈", "清除缓存", "关于我们", "拨打电话", "检查更新"};
    private File cacheDir;
    private boolean isLaster = false;
    private VersionInfo versionInfo;
    private AlertDialog alertDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_seting);
        // 缓存路径
        cacheDir = getCacheDir();

        initView();
        initDatas();
    }

    /**
     * 设置适配器展示数据
     */
    private void initDatas() {
        user_Setting_listview.setAdapter(new MyAdapater());
    }


    private void initView() {

        back_image = (ImageView) findViewById(R.id.back_image);
        back_image.setOnClickListener(this);
        include_meddim_tv = (TextView) findViewById(R.id.include_meddim_tv);
        include_meddim_tv.setText("设置");
        user_Setting_listview = (ListView) findViewById(R.id.user_setting_listview);
        out_login = (Button) findViewById(R.id.out_login);
        out_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_image:
                finish();
                UserSeting_Activity.this.overridePendingTransition(R.anim.login_in0, R.anim.login_out);
                break;

            /**
             * 退出的操作
             */
            case R.id.out_login:
                userOutAppButton();
                break;
        }
    }

    /**
     * 用户退出程序，并清除数据
     */
    private void userOutAppButton() {
        AlertDialog.Builder builder = new AlertDialog.Builder(UserSeting_Activity.this);
        builder.setMessage("确认退出吗？");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //清除sp
                CommonUtils.clearSp();
                alertDialog.dismiss();
                MyApplication.loginFlag = false;
                CommonUtils.saveBolean("loginFlag",MyApplication.loginFlag);
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        alertDialog = builder.create();
        alertDialog.show();
    }


    class MyAdapater extends BaseAdapter {

        @Override
        public int getCount() {
            return itemStr.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = CommonUtils.inflate(R.layout.user_setting_item);
            TextView user_settng_left_tv = (TextView) convertView.findViewById(R.id.user_settng_left_tv);
            ImageView next_icon = (ImageView) convertView.findViewById(R.id.next_icon);
            final TextView user_setting_right_tv = (TextView) convertView.findViewById(R.id.user_setting_right_tv);
            user_settng_left_tv.setText(itemStr[position]);

            if (itemStr[position].equals("清除缓存")) {
                next_icon.setVisibility(View.GONE);
                try {
                    long folderSize = DataClearManager.getFolderSize(cacheDir);
                    user_setting_right_tv.setText("已缓存" + DataClearManager.getFormatSize(folderSize));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            if (itemStr[position].equals("关于我们")) {
                next_icon.setImageResource(R.mipmap.about_us_code);
            }

            if (itemStr[position].equals("拨打电话")) {
                next_icon.setVisibility(View.GONE);
                user_setting_right_tv.setText("400-688-0900");
                user_setting_right_tv.setTextColor(getResources().getColor(R.color.YuniFangZhangHao_textColor));
            }

            if (itemStr[position].equals("关于我们")) {

            }

            /**
             * 检查更新
             */
            if (itemStr[position].equals("检查更新")) {
                next_icon.setVisibility(View.GONE);
                user_setting_right_tv.setVisibility(View.VISIBLE);
                user_setting_right_tv.setText("您当前已经是最新版本");
                //获取服务器版本信息
                getVersionCode(user_setting_right_tv);
            }


            /**
             * 条目点击事件
             */
            user_Setting_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (itemStr[position].equals("清除缓存")) {
                        DataClearManager.deleteCache(cacheDir);
                        long folderSize = 0;
                        try {
                            folderSize = DataClearManager.getFolderSize(cacheDir);
                            user_setting_right_tv.setText("已缓存" + DataClearManager.getFormatSize(folderSize));
                            LogUtils.i("TAG=======", "点击了+" + itemStr[position]);
                            MyAdapater.this.notifyDataSetChanged();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                    /**+
                     * 检查更新
                     */
                    if (itemStr[position].equals("检查更新")) {
                        if (!isLaster) {
                            //获取最新版本
                            String downloadUrl = versionInfo.getDownloadUrl();
                            getNetDownLoad(downloadUrl);
                        }
                    }

                    if (itemStr[position].equals("关于我们")) {
                        startActivity(new Intent(UserSeting_Activity.this, ZXingActivity.class));
                        UserSeting_Activity.this.overridePendingTransition(R.anim.login_in, R.anim.login_in0);
                        Toast.makeText(UserSeting_Activity.this, "点击了关于我们", Toast.LENGTH_SHORT).show();
                    }

                    if (itemStr[position].equals("拨打电话")) {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:" + "400-688-0900"));
                        startActivity(intent);
                    }
                }
            });

            return convertView;
        }
    }

    /**
     * 下载最新版本信息
     *
     * @param downloadUrl
     */
    private void getNetDownLoad(String downloadUrl) {
        RequestParams params = new RequestParams(downloadUrl);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();
        x.http().get(params, new Callback.ProgressCallback<File>() {

            @Override
            public void onSuccess(File file) {
                Toast.makeText(UserSeting_Activity.this, "下载成功！", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                //安装apk界面打开
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                intent.addCategory("android.intent.category.DEFAULT");

                Log.i("AAAA", "-----" + file.getAbsolutePath());
                intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                startActivity(intent);
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

            @Override
            public void onWaiting() {

            }

            @Override
            public void onStarted() {

            }

            @Override
            public void onLoading(long l, long l1, boolean b) {
                progressDialog.setMax((int) l);
                progressDialog.setProgress((int) l1);
            }
        });


    }

    /**
     * 获取当前服务器版本信息
     *
     * @param
     * @param user_setting_right_tv
     * @return
     */
    private void getVersionCode(final TextView user_setting_right_tv) {

        RequestParams params = new RequestParams("http://169.254.239.3:8080/versioninfo.txt");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                Gson gson = new Gson();
                versionInfo = gson.fromJson(s, VersionInfo.class);
                //这是获取服务器上的版本信息
                String versionName = versionInfo.getVersionName();
                //获取当前应用的版本信息
                String versionName1 = getVersionName();
                if (versionName.equals(versionName1)) {
                    isLaster = true;
                    user_setting_right_tv.setText("您当前已经是最新版本！");
                } else {
                    isLaster = false;
                    user_setting_right_tv.setText("当前最新版本是" + versionName1);
                }


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

    //获取本应用的版本信息
    public String getVersionName() {
        //获取包信息
        PackageManager packageManager = getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            //获取当前应用的版本信息
            String versionName = packageInfo.versionName;
            return versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
