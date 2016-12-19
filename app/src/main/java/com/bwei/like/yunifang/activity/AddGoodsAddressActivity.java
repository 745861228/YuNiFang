package com.bwei.like.yunifang.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bwei.like.yunifang.R;
import com.bwei.like.yunifang.base.BaseActivity;
import com.bwei.like.yunifang.bean.AddressBean;
import com.bwei.like.yunifang.dao.AddressDao;
import com.bwei.like.yunifang.utils.CommonUtils;

import java.util.ArrayList;

public class AddGoodsAddressActivity extends BaseActivity implements View.OnClickListener {

    private ImageView back_image;
    private TextView include_middle_tv;
    private TextView include_right_tv;
    private AddressDao addressDao;
    private EditText userName_ed;
    private EditText userPhone_ed;
    private EditText userAddres_ed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goods_address);

        initView();
        initViewListener();
        addressDao = new AddressDao(this);

    }

    /**
     * 初始化控件监听事件
     */
    private void initViewListener() {
        back_image.setOnClickListener(this);
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


        include_middle_tv.setText("新建收货地址");
        include_middle_tv.setTextColor(Color.BLACK);
        include_right_tv.setText("保存");
        include_right_tv.setTextColor(getResources().getColor(R.color.YuniFangZhangHao_textColor));

        userName_ed = (EditText) findViewById(R.id.userName_ed);
        userPhone_ed = (EditText) findViewById(R.id.userPhone_ed);
        userAddres_ed = (EditText) findViewById(R.id.userAddres_ed);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //图片返回按钮
            case R.id.back_image:
                finish();
                AddGoodsAddressActivity.this.overridePendingTransition(R.anim.login_in0, R.anim.login_out);
                break;
            //保存监听事件
            case R.id.include_right_tv:
                setAddressDao();
                Toast.makeText(AddGoodsAddressActivity.this, "添加成功！", Toast.LENGTH_SHORT).show();
                finish();
                AddGoodsAddressActivity.this.overridePendingTransition(R.anim.login_in0, R.anim.login_out);
                break;
        }
    }


    public void setAddressDao() {
        addressDao.addAddress(new AddressBean(userAddres_ed.getText().toString(), userName_ed.getText().toString(), userPhone_ed.getText().toString()));
//        ArrayList<AddressBean> addressBeen = addressDao.selectId(new AddressBean(userAddres_ed.getText().toString(), userName_ed.getText().toString(), userPhone_ed.getText().toString()));
//        CommonUtils.saveString("addressId",addressBeen.get(0).getId()+"");
    }
}
