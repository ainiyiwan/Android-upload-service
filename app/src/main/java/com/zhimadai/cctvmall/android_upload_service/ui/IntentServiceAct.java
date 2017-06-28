package com.zhimadai.cctvmall.android_upload_service.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.Button;

import com.zhimadai.cctvmall.android_upload_service.MyApp;
import com.zhimadai.cctvmall.android_upload_service.R;
import com.zhimadai.cctvmall.android_upload_service.service.MyIntentService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IntentServiceAct extends AppCompatActivity {

    @BindView(R.id.start_intent)
    Button startIntent;
    @BindView(R.id.stop_intent)
    Button stopIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_service);
        ButterKnife.bind(this);
        MyApp.getInstance().addActivity(this);
    }

    @OnClick(R.id.start_intent)
    public void onStartIntent() {
        Intent startIntent = new Intent(this, MyIntentService.class);
        startService(startIntent);
    }

    @OnClick(R.id.stop_intent)
    public void onStopIntent() {
        Intent stopIntent = new Intent(this, MyIntentService.class);
        stopService(stopIntent);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //捕获返回键按下的事件
        if (keyCode == KeyEvent.KEYCODE_BACK) {


//            Intent stopIntent = new Intent(this, MyIntentService.class);
//            stopService(stopIntent);


            MyApp.getInstance().exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}