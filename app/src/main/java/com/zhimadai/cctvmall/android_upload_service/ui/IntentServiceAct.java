package com.zhimadai.cctvmall.android_upload_service.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

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
    }

    @OnClick(R.id.start_intent)
    public void onStartIntent() {
        Intent startIntent = new Intent(this, MyIntentService.class);
        startService(startIntent);
    }

    @OnClick(R.id.stop_intent)
    public void onStopIntent() {
        Intent stopIntent = new Intent(this, MyIntentService.class);
        startService(stopIntent);
    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.tv_start:
//                Intent startIntent = new Intent(getActivity(), MyIntentService.class);
//                getActivity().startService(startIntent);
//                break;
//            case R.id.tv_cancel:
//                Intent stopIntent = new Intent(getActivity(), MyIntentService.class);
//                getActivity().stopService(stopIntent);
//                break;
//        }
//    }
}