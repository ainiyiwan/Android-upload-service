package com.zhimadai.cctvmall.android_upload_service.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.Toast;

import com.zhimadai.cctvmall.android_upload_service.MyApp;
import com.zhimadai.cctvmall.android_upload_service.R;
import com.zhimadai.cctvmall.android_upload_service.entity.NBA;
import com.zhimadai.cctvmall.android_upload_service.service.MyService;
import com.zhimadai.cctvmall.android_upload_service.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListListener;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MyAct";

    @BindView(R.id.start_service)
    Button startService;
    @BindView(R.id.stop_service)
    Button stopService;
    @BindView(R.id.upload_service)
    Button uploadService;
    @BindView(R.id.next_service)
    Button nextService;

    private List<BmobObject> nbas;

    //上次按下返回键的系统时间
    private long lastBackTime = 0;
    //当前按下返回键的系统时间
    private long currentBackTime = 0;

    private MyThread thread = new MyThread();

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //捕获返回键按下的事件
        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            //获取当前系统时间的毫秒数
//            currentBackTime = System.currentTimeMillis();
//
//            if (nbas.size() > 50) {
//                thread.stop();
//                upload();
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }


//            Intent stopIntent = new Intent(this, MyService.class);
//            stopService(stopIntent);


            MyApp.getInstance().exit();
            //比较上次按下返回键和当前按下返回键的时间差，如果大于2秒，则提示再按一次退出
//            if (currentBackTime - lastBackTime > 100) {
//                Toast.makeText(this, "再按一次返回键退出", Toast.LENGTH_SHORT).show();
//                lastBackTime = currentBackTime;
//            } else { //如果两次按下的时间差小于2秒，则退出程序
//            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        MyApp.getInstance().addActivity(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
    }

    private void Task() {
        Log.i(TAG, "onStartCommand() executed");
        NBA nba = new NBA();
        nba.setName("勇士队");
        nba.setScore(150);
        nba.setStar("史蒂芬库里");


        nbas = new ArrayList<BmobObject>();
        for (int i = 0; i < 50000; i++) {
            NBA nba1 = new NBA();
            nba1.setName("史蒂芬库里 " + i);
            if (i < 50) {
                nbas.add(nba1);
            }
            Log.i(TAG, "已存" + i + "个");
            Log.i(TAG, DateUtil.getNow());

        }

        upload();
    }

    private void upload() {
        new BmobBatch().insertBatch(nbas).doBatch(new QueryListListener<BatchResult>() {

            @Override
            public void done(List<BatchResult> o, BmobException e) {
                if (e == null) {
                    for (int i = 0; i < o.size(); i++) {
                        BatchResult result = o.get(i);
                        BmobException ex = result.getError();
                        if (ex == null) {
                            Toast.makeText(MainActivity.this, "第" + i + "个数据批量添加成功：" + result
                                    .getCreatedAt() + "," + result.getObjectId() + "," + result
                                    .getUpdatedAt(), Toast.LENGTH_SHORT).show();
                            Log.i(TAG, "第" + i + "个数据批量添加成功：" + result.getCreatedAt() + "," +
                                    result.getObjectId() + "," + result.getUpdatedAt());
                        } else {
                            Toast.makeText(MainActivity.this, "第" + i + "个数据批量添加失败：" + ex
                                    .getMessage() + "," + ex.getErrorCode(), Toast.LENGTH_SHORT)
                                    .show();
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


    @OnClick(R.id.start_service)
    public void onStartServiceClicked() {
        Intent startIntent = new Intent(this, MyService.class);
        startService(startIntent);
    }

    @OnClick(R.id.stop_service)
    public void onStopServiceClicked() {
        Intent stopIntent = new Intent(this, MyService.class);
        stopService(stopIntent);
    }

    @OnClick(R.id.upload_service)
    public void onViewClicked() {

        thread.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }

    @OnClick(R.id.next_service)
    public void onNextClicked() {
        startActivity(new Intent(MainActivity.this,Main2Activity.class));
    }

    class MyThread extends Thread {
        @Override
        public void run() {
            super.run();
            Task();
        }
    }
}
