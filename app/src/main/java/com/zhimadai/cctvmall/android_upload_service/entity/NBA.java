package com.zhimadai.cctvmall.android_upload_service.entity;

import cn.bmob.v3.BmobObject;

/**
 * Author ： zhangyang
 * Date   ： 2017/6/23
 * Email  :  18610942105@163.com
 * Description  :
 */

public class NBA extends BmobObject {
    private String name;
    private Integer score;
    private String star;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }
}
