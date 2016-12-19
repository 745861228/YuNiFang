package com.bwei.like.yunifang.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bwei.like.yunifang.R;
import com.bwei.like.yunifang.adapater.CommonAdapter;
import com.bwei.like.yunifang.adapater.ViewHolder;
import com.bwei.like.yunifang.bean.AddressBean;
import com.bwei.like.yunifang.dao.AddressDao;
import com.bwei.like.yunifang.utils.CommonUtils;

import java.util.ArrayList;

public class ListAddressActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView back_image;
    private TextView include_middle_tv;
    private TextView include_right_tv;
    private Button addAddress_but;
    private ListView listView_listview;
    private ArrayList<AddressBean> addressBeenList;
    private AddressDao addressDao;
    private CommonAdapter<AddressBean> commonAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_address);
        initView();
        initViewListener();

        addressDao = new AddressDao(this);
        addressBeenList = addressDao.selectAll();
        commonAdapter = new CommonAdapter<AddressBean>(this, addressBeenList, R.layout.address_listview_item) {
            @Override
            public void convert(ViewHolder helper, final AddressBean item) {
                helper.setText(R.id.name, item.getUserName());
                helper.setText(R.id.phone, item.getUserPhone());
                helper.setText(R.id.address, item.getUserAddress());
                final RadioButton radioButton = helper.getView(R.id.radioButton);
                radioButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (radioButton.isChecked()){
                            item.setChecked(true);
                        }
                    }
                });
                radioButton.setChecked(item.isChecked());
            }
        };
        listView_listview.setAdapter(commonAdapter);

        listView_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CommonUtils.saveInt("addressId",addressBeenList.get(position).getId());
                finish();
                ListAddressActivity.this.overridePendingTransition(R.anim.login_in0, R.anim.login_out);
            }
        });
    }

    private void initViewListener() {
        back_image.setOnClickListener(this);
        addAddress_but.setOnClickListener(this);
        include_right_tv.setOnClickListener(this);
    }

    /**
     * 初始化控件
     */
    private void initView() {
        back_image = (ImageView) findViewById(R.id.back_image);
        include_middle_tv = (TextView) findViewById(R.id.include_meddim_tv);
        include_right_tv = (TextView) findViewById(R.id.include_right_tv);
        include_right_tv.setVisibility(View.VISIBLE);
        addAddress_but = (Button) findViewById(R.id.addAddress_but);
        listView_listview = (ListView) findViewById(R.id.listView_listview);


        include_middle_tv.setText("选择收货地址");
        include_middle_tv.setTextColor(Color.BLACK);
        include_right_tv.setText("管理");
        include_right_tv.setTextColor(getResources().getColor(R.color.YuniFangZhangHao_textColor));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //返回
            case R.id.back_image:

                break;

            //添加收货地址
            case R.id.addAddress_but:
                Intent intent = new Intent(ListAddressActivity.this, AddGoodsAddressActivity.class);
                startActivityForResult(intent, 100);
                ListAddressActivity.this.overridePendingTransition(R.anim.login_in, R.anim.login_in0);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        addressBeenList = addressDao.selectAll();
        commonAdapter.notifyDataSetChanged();
    }
}
