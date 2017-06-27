package com.zhimadai.cctvmall.android_upload_service.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
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

public class MyService extends Service {

    public static final String TAG = "MyService";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate() executed");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Task();
            }
        }).start();


//        return super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    private void Task() {
        Log.i(TAG, "onStartCommand() executed");
        NBA nba = new NBA();
        nba.setName("勇士队");
        nba.setScore(150);
        nba.setStar("史蒂芬库里");


        List<BmobObject> nbas = new ArrayList<BmobObject>();
        for (int i = 0; i < 50000; i++) {
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            NBA nba1 = new NBA();
            nba1.setName("史蒂芬库里 " + i);
            if (i < 10) {
                nbas.add(nba1);
            }
            Log.i(TAG, "已存" + i + "个");
            Log.i(TAG, DateUtil.getNow());
            //add
//            nba1.save(new SaveListener<String>() {
//
//                @Override
//                public void done(String objectId, BmobException e) {
//                    if(e==null){
//                        Toast.makeText(MyService.this,"创建数据成功：" + objectId,Toast.LENGTH_SHORT)
// .show();
//                        Log.i(TAG,"创建数据成功：" + objectId);
//                    }else{
//                        Log.i(TAG,"失败："+e.getMessage()+","+e.getErrorCode());
//                    }
//                }
//            });

        }

        new BmobBatch().insertBatch(nbas).doBatch(new QueryListListener<BatchResult>() {

            @Override
            public void done(List<BatchResult> o, BmobException e) {
                if (e == null) {
                    for (int i = 0; i < o.size(); i++) {
                        BatchResult result = o.get(i);
                        BmobException ex = result.getError();
                        if (ex == null) {
//                            Toast.makeText(MyService.this, "第" + i + "个数据批量添加成功：" + result
//                                    .getCreatedAt() + "," + result.getObjectId() + "," + result
//                                    .getUpdatedAt(), Toast.LENGTH_SHORT).show();
                            Log.i(TAG, "第" + i + "个数据批量添加成功：" + result.getCreatedAt() + "," +
                                    result.getObjectId() + "," + result.getUpdatedAt());
                        } else {
                            Toast.makeText(MyService.this, "第" + i + "个数据批量添加失败：" + ex.getMessage
                                    () + "," + ex.getErrorCode(), Toast.LENGTH_SHORT).show();
                            Log.i(TAG, "第" + i + "个数据批量添加失败：" + ex.getMessage() + "," + ex
                                    .getErrorCode());
                        }
//                        if (i == o.size()-1){
//                            stopSelf();
//                        }
                        stopSelf();
                    }
                } else {
                    Log.i(TAG, "失败：" + e.getMessage() + "," + e.getErrorCode());
                    stopSelf();
                }
            }
        });

//        nba.save(new SaveListener<String>() {
//
//            @Override
//            public void done(String objectId, BmobException e) {
//                if(e==null){
//                    Toast.makeText(MyService.this,"创建数据成功：" + objectId,Toast.LENGTH_SHORT).show();
//                    Log.i(TAG,"创建数据成功：" + objectId);
//                }else{
//                    Log.i(TAG,"失败："+e.getMessage()+","+e.getErrorCode());
//                }
//            }
//        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        stopSelf();
        Log.i(TAG, "onDestroy() executed");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
