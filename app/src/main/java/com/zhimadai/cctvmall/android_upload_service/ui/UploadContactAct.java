package com.zhimadai.cctvmall.android_upload_service.ui;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import com.zhimadai.cctvmall.android_upload_service.MyApp;
import com.zhimadai.cctvmall.android_upload_service.R;
import com.zhimadai.cctvmall.android_upload_service.permission.PermissionListener;
import com.zhimadai.cctvmall.android_upload_service.permission.PermissionManager;
import com.zhimadai.cctvmall.android_upload_service.service.ContactIntentService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UploadContactAct extends AppCompatActivity {

    @BindView(R.id.start_intent)
    Button startIntent;
    @BindView(R.id.stop_intent)
    Button stopIntent;

    private static final int REQUEST_CODE_CONTACT = 0x00;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_contact);
        ButterKnife.bind(this);
        MyApp.getInstance().addActivity(this);
    }

    @OnClick(R.id.start_intent)
    public void onStartIntentClicked() {
        permission();
    }

    private void permission() {
        PermissionManager helper;
        helper = PermissionManager.with(UploadContactAct.this)
                //添加权限请求码
                .addRequestCode(UploadContactAct.REQUEST_CODE_CONTACT)
                //设置权限，可以添加多个权限
                .permissions(Manifest.permission.READ_CONTACTS)
                //设置权限监听器
                .setPermissionsListener(new PermissionListener() {

                    @Override
                    public void onGranted() {
                        //当权限被授予时调用
                        getIntents();
                        Toast.makeText(UploadContactAct.this, "Storage Permission granted", Toast
                                .LENGTH_LONG).show();
                    }

                    @Override
                    public void onDenied() {
                        //用户拒绝该权限时调用
                        Toast.makeText(UploadContactAct.this, "Storage Permission denied", Toast
                                .LENGTH_LONG).show();
                    }

                    @Override
                    public void onShowRationale(String[] permissions) {
                        //当用户拒绝某权限时并点击`不再提醒`的按钮时，下次应用再请求该权限时，需要给出合适的响应（比如,给个展示对话框来解释应用为什么需要该权限）

                    }
                })
                //请求权限
                .request();
    }

    private void getIntents() {
        Intent intent = new Intent(this, ContactIntentService.class);
        startService(intent);
    }


    @OnClick(R.id.stop_intent)
    public void onStopIntentClicked() {
        Intent intent = new Intent(this,ContactIntentService.class);
        stopService(intent);
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        //捕获返回键按下的事件
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            MyApp.getInstance().exit();
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
}
