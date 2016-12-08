package com.bwei.like.yunifang.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bwei.like.yunifang.R;
import com.bwei.like.yunifang.adapater.CommonAdapter;
import com.bwei.like.yunifang.adapater.ViewHolder;
import com.bwei.like.yunifang.base.BaseActivity;
import com.bwei.like.yunifang.utils.CommonUtils;
import com.bwei.like.yunifang.utils.DataClearManager;
import com.bwei.like.yunifang.utils.LogUtils;

import java.io.File;

public class UserSeting_Activity extends BaseActivity implements View.OnClickListener {

    private ImageView back_image;
    private ListView user_Setting_listview;
    private Button out_login;
    private TextView include_meddim_tv;
    private String[] itemStr = {"购物须知", "意见反馈", "清除缓存", "关于我们", "拨打电话", "检查更新"};
    private File cacheDir;
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

            case R.id.out_login:

                break;
        }
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
                    user_setting_right_tv.setText("已缓存"+DataClearManager.getFormatSize( folderSize));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            if (itemStr[position].equals("关于我们")){
                next_icon.setImageResource(R.mipmap.about_us_code);
            }

            if (itemStr[position].equals("拨打电话")){
                next_icon.setVisibility(View.GONE);
                user_setting_right_tv.setText("400-688-0900");
                user_setting_right_tv.setTextColor(getResources().getColor(R.color.YuniFangZhangHao_textColor));
            }

            if (itemStr[position].equals("关于我们")){

            }


            user_Setting_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (itemStr[position].equals("清除缓存")){
                        DataClearManager.deleteCache(cacheDir);
                        long folderSize = 0;
                        try {
                            folderSize = DataClearManager.getFolderSize(cacheDir);
                            user_setting_right_tv.setText("已缓存"+DataClearManager.getFormatSize( folderSize));
                            LogUtils.i("TAG=======","点击了+"+itemStr[position]);
                            MyAdapater.this.notifyDataSetChanged();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }
            });


            return convertView;
        }
    }


    /**
     * 获取某个文件夹中所有文件及子文件的大小
     *
     * @param dir
     * @return
     */
    private long getCacheSize(File dir) {
        long size = 0;
        File[] listFiles = dir.listFiles();
        for (int i = 0; i < listFiles.length; i++) {
            // 是个文件夹
            if (listFiles[i].isDirectory()) {
                size = size + getCacheSize(listFiles[i]);
            }
            // 当前是个文件
            else {
                size = size + listFiles[i].length();
            }
        }
        return size;

    }
}
