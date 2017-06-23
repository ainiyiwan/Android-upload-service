package com.zhimadai.cctvmall.android_upload_service;

import android.app.Activity;
import android.app.Application;
import android.support.v7.app.AppCompatActivity;

import java.util.LinkedList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;

/**
 * Author ： zhangyang
 * Date   ： 2017/6/23
 * Email  :  18610942105@163.com
 * Description  :
 */

public class MyApp extends Application {


    //对于新增和删除操作add和remove，LinedList比较占优势，因为ArrayList实现了基于动态数组的数据结构，要移动数据。LinkedList基于链表的数据结构,便于增加删除 
    private List<AppCompatActivity> activityList = new LinkedList<>();
    private static MyApp instance;
    public MyApp(){ }
    //单例模式中获取唯一的MyApp实例
    public static MyApp getInstance() {
        if(null == instance) {
            instance = new MyApp();
        }
        return instance;
    }

    //添加Activity到容器中
    public void addActivity(AppCompatActivity activity)  {
        activityList.add(activity);
    }

    //遍历所有Activity并finish
    public void exit(){
        for(Activity activity:activityList) {
            activity.finish();
        }
        System.exit(0);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //第一：默认初始化
        Bmob.initialize(this, "4c182f966d56f233499972091d02eb28");
        // 注:自v3.5.2开始，数据sdk内部缝合了统计sdk，开发者无需额外集成，传渠道参数即可，不传默认没开启数据统计功能
        //Bmob.initialize(this, "Your Application ID","bmob");

        //第二：自v3.4.7版本开始,设置BmobConfig,允许设置请求超时时间、文件分片上传时每片的大小、文件的过期时间(单位为秒)，
        BmobConfig config = new BmobConfig.Builder(this)
                //设置appkey
                .setApplicationId("Your Application ID")
                //请求超时时间（单位为秒）：默认15s
                .setConnectTimeout(30)
                //文件分片上传时每片的大小（单位字节），默认512*1024
                .setUploadBlockSize(1024 * 1024)
                //文件的过期时间(单位为秒)：默认1800s
                .setFileExpiration(2500)
                .build();
        Bmob.initialize(config);
    }
}
