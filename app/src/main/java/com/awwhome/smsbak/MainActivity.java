package com.awwhome.smsbak;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.util.Xml;
import android.view.View;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 获取系统短信内容
     *
     * @param view
     */
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


    /**
     * 将获取到的短信内容备份为xml文件
     *
     * @param view
     */
    public void backsms(View view) {
        Log.d(TAG, "click: 备份短信");

        try {
            // 1.获取序列化器XmlSerializer对象
            XmlSerializer xmlSerializer = Xml.newSerializer();

            File file = new File(Environment.getExternalStorageDirectory().getPath(), "backupsms.xml");

            FileOutputStream fos = new FileOutputStream(file);

            // 2.设置参数
            xmlSerializer.setOutput(fos, "utf-8");

            // 3.写xml文件开头
            // 第一个参数，字符集
            // 第二个参数，是否是一个独立的xml
            xmlSerializer.startDocument("utf-8", true);

            // 4.写根节点smss
            xmlSerializer.startTag(null, "smss");

            // 5.写sms节点
            xmlSerializer.startTag(null, "sms");

            Uri uri = Uri.parse("content://sms/");

            Cursor cursor = getContentResolver().query(uri, new String[]{"address", "date", "body"}, null, null, null);

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String address = cursor.getString(0);
                    String date = cursor.getString(1);
                    String body = cursor.getString(2);
                    // 6.写address节点
                    xmlSerializer.startTag(null, "address");

                    xmlSerializer.text(address);

                    xmlSerializer.endTag(null, "address");

                    // 7.写date节点
                    xmlSerializer.startTag(null, "date");
                    xmlSerializer.text(date);
                    xmlSerializer.endTag(null, "date");

                    // 8.写body节点
                    xmlSerializer.startTag(null, "body");
                    xmlSerializer.text(body);
                    xmlSerializer.endTag(null, "body");

                }

            }

            xmlSerializer.endTag(null, "sms");
            xmlSerializer.endTag(null, "smss");
            xmlSerializer.endDocument();

            Log.d(TAG, "backsms: 备份成功");

            fos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
