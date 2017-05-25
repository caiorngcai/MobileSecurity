package com.cairongcai.mobilesafe.engine;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Xml;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by HY-IT on 2017/5/25.
 */
public class SmsBackup {
    static int  index=0;
    private static Cursor cursor;
    private static FileOutputStream fos;

    public static void backup(Context context,String path,CallBack callback) {
        cursor = context.getContentResolver().query(Uri.parse("content://sms/"),
                 new String[]{"address","date","type","body"},null,null,null);
        try {
            fos = new FileOutputStream(new File(path));
            XmlSerializer serializer= Xml.newSerializer();
            serializer.setOutput(fos,"utf-8");
            serializer.startDocument("utf-8",true);
            serializer.startTag(null,"smss");
            if(callback!=null)
            {
               int max= cursor.getCount();
                callback.setMax(max);
            }
            while (cursor.moveToNext())
            {
                serializer.startTag(null,"sms");

                serializer.startTag(null,"address");
                serializer.text(cursor.getString(0));
                serializer.endTag(null,"address");

                serializer.startTag(null,"date");
                serializer.text(cursor.getString(1));
                serializer.endTag(null,"date");

                serializer.startTag(null,"type");
                serializer.text(cursor.getString(2));
                serializer.endTag(null,"type");

                serializer.startTag(null,"body");
                String text3=cursor.getString(3);
               serializer.text(text3);
                serializer.endTag(null,"body");

                serializer.endTag(null,"sms");
                index++;
                Thread.sleep(500);
                if(callback!=null)
                    //方法的调用
                callback.setProgress(index);
            }
            serializer.endTag(null,"smss");
            serializer.endDocument();
        }catch (Exception e)
        {
            e.printStackTrace();
        }finally {
            if(cursor!=null&&fos!=null)
            {
                cursor.close();
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    //回调写法
    //1.定义一个接口
    //2,定义接口中未实现的业务逻辑方法(短信总数设置,备份过程中短信百分比更新)
    //3.传递一个实现了此接口的类的对象(至备份短信的工具类中),接口的实现类,一定实现了上诉两个为实现方法(就决定了使用对话框,还是进度条)
    //4.获取传递进来的对象,在合适的地方(设置总数,设置百分比的地方)做方法的调用
    public interface CallBack
    {
        public void setMax(int count);
        public void setProgress(int index);
    }

}
