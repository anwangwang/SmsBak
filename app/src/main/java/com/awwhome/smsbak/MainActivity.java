package com.awwhome.smsbak;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void getsystemsms(View view) {
        Log.d(TAG, "click: 获取短信");

        Uri uri = Uri.parse("content://sms/");

        Cursor cursor = getContentResolver().query(uri, new String[]{"address", "date", "body"}, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String address = cursor.getString(0);
                String date = cursor.getString(1);
                String body = cursor.getString(2);
                Log.d(TAG, "getsystemsms: 获取到的系统短信内容有：address:" + address + ",date:" + date + ",body:" + body);
            }

        }


    }

    public void backsms(View view) {
        Log.d(TAG, "click: 备份短信");


    }

}
