package com.cairongcai.mobilesafe.engine;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by HY-IT on 2017/5/16.
 */

public class AddressDao {

    /**
     * 根据号码到数据库查询地址的方法
      * @param phone 号码
     * @return 地址
     */
    static private String path="data/data/com.cairongcai.mobilesafe/files/address.db";
    private static String mAddress;

    public static String getAddress(String phone)
    {
        String regularExpression = "^1[3-8]\\d{9}";
        SQLiteDatabase db=SQLiteDatabase.openDatabase(path,null,SQLiteDatabase.OPEN_READONLY);
        if(phone.matches(regularExpression))
        {
            phone=phone.substring(0,7);
            Cursor cursor=db.query("data1",new String[]{"outkey"},"id=?",new String[]{phone},null,null,null);
            if(cursor.moveToNext())
            {
                String outkey=cursor.getString(0);
                Cursor indexCuesor=db.query("data2",new String[]{"location"},"id=?",new String[]{outkey},null,null,null);
                if(indexCuesor.moveToNext())
                {
                    mAddress = indexCuesor.getString(0);
                }
            }else {
                mAddress="未知号码";
            }
        } else
        {
            int lenth=phone.length();
            switch (lenth)
            {
                case 3:
                    mAddress = "报警电话";
                    break;
                case 4://119 110 120 114
                    mAddress = "模拟器";
                    break;
                case 5://10086 99555
                    mAddress = "服务电话";
                    break;
                case 7:
                    mAddress = "本地电话";
                    break;
                case 8:
                    mAddress = "本地电话";
                    break;
                case 11:
                    //(3+8) 区号+座机号码(外地),查询data2
                    String area = phone.substring(1, 3);
                    Cursor cursor = db.query("data2", new String[]{"location"}, "area = ?", new String[]{area}, null, null, null);
                    if(cursor.moveToNext()){
                        mAddress = cursor.getString(0);
                    }else{
                        mAddress = "未知号码";
                    }
                    break;
                case 12:
                    //(4+8) 区号(0791(江西南昌))+座机号码(外地),查询data2
                    String area1 = phone.substring(1, 4);
                    Cursor cursor1 = db.query("data2", new String[]{"location"}, "area = ?", new String[]{area1}, null, null, null);
                    if(cursor1.moveToNext()){
                        mAddress = cursor1.getString(0);
                    }else{
                        mAddress = "未知号码";
                    }
                    break;
            }
        }
        return mAddress;
    }
}
