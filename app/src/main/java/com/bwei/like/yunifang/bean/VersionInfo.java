package com.bwei.like.yunifang.bean;

/**
 * Created by zhiyuan on 16/8/8.
 */

public class VersionInfo {
    private String downloadUrl;
    private String versionCode;
    private String versionDes;
    private String versionName;

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionDes() {
        return versionDes;
    }

    public void setVersionDes(String versionDes) {
        this.versionDes = versionDes;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public VersionInfo(String downloadUrl, String versionCode, String versionDes, String versionName) {
        this.downloadUrl = downloadUrl;
        this.versionCode = versionCode;
        this.versionDes = versionDes;
        this.versionName = versionName;
    }

    public VersionInfo() {
    }
}
