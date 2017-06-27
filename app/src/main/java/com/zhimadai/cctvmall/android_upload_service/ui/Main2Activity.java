package com.zhimadai.cctvmall.android_upload_service.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.zhimadai.cctvmall.android_upload_service.MyApp;
import com.zhimadai.cctvmall.android_upload_service.R;
import com.zhimadai.cctvmall.android_upload_service.service.MyService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Main2Activity extends AppCompatActivity {

    @BindView(R.id.start_service)
    Button startService;
    @BindView(R.id.stop_service)
    Button stopService;
    @BindView(R.id.upload_service)
    Button uploadService;
    @BindView(R.id.next_service)
    Button nextService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);
        MyApp.getInstance().addActivity(this);
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
    public void onUploadServiceClicked() {
    }

    @OnClick(R.id.next_service)
    public void onNextServiceClicked() {
        startActivity(new Intent(Main2Activity.this,Main3Activity.class));
    }
}
