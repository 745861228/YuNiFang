package com.bwei.like.yunifang.bean;

import java.io.Serializable;

/**
 * Created by LiKe on 2016/12/18.
 */
public class AddressBean implements Serializable {


    private int id;
    private String userName;
    private String userPhone;
    private String userAddress;
    private boolean isChecked;


    public AddressBean( String userAddress, String userName, String userPhone) {

        this.userAddress = userAddress;
        this.userName = userName;
        this.userPhone = userPhone;
    }


    public AddressBean(int id, String userAddress, String userName, String userPhone) {
        this.id = id;
        this.userAddress = userAddress;
        this.userName = userName;
        this.userPhone = userPhone;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
