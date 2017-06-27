package com.zhimadai.cctvmall.android_upload_service.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.zhimadai.cctvmall.android_upload_service.entity.NBA;
import com.zhimadai.cctvmall.android_upload_service.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListListener;


public class MyIntentService extends IntentService {

    public static final String TAG = "IntentService";

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Task();
            }
        }).start();
    }

    private void Task() {
        Log.i(TAG, "onStartCommand() executed");
        NBA nba = new NBA();
        nba.setName("勇士队");
        nba.setScore(150);
        nba.setStar("史蒂芬库里");


        List<BmobObject> nbas = new ArrayList<BmobObject>();
        for (int i = 0; i < 50000; i++) {
            NBA nba1 = new NBA();
            nba1.setName("史蒂芬库里 " + i);
            if (i < 10) {
                nbas.add(nba1);
            }
            Log.i(TAG, "已存" + i + "个");
            Log.i(TAG, DateUtil.getNow());

        }

        new BmobBatch().insertBatch(nbas).doBatch(new QueryListListener<BatchResult>() {

            @Override
            public void done(List<BatchResult> o, BmobException e) {
                if (e == null) {
                    for (int i = 0; i < o.size(); i++) {
                        BatchResult result = o.get(i);
                        BmobException ex = result.getError();
                        if (ex == null) {
                            Log.i(TAG, "第" + i + "个数据批量添加成功：" + result.getCreatedAt() + "," +
                                    result.getObjectId() + "," + result.getUpdatedAt());
                        } else {
                            Toast.makeText(MyIntentService.this, "第" + i + "个数据批量添加失败：" + ex.getMessage
                                    () + "," + ex.getErrorCode(), Toast.LENGTH_SHORT).show();
                            Log.i(TAG, "第" + i + "个数据批量添加失败：" + ex.getMessage() + "," + ex
                                    .getErrorCode());
                        }
                    }
                } else {
                    Log.i(TAG, "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
