package com.zhimadai.cctvmall.android_upload_service.entity;

import cn.bmob.v3.BmobObject;

/**
 * Author ： zhangyang
 * Date   ： 2017/6/28
 * Email  :  18610942105@163.com
 * Description  :
 */

public class Contact extends BmobObject {
    private String phoneId;
    private String phoneName;
    private String phoneNumber;

    public String getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(String phoneId) {
        this.phoneId = phoneId;
    }

    public String getPhoneName() {
        return phoneName;
    }

    public void setPhoneName(String phoneName) {
        this.phoneName = phoneName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
