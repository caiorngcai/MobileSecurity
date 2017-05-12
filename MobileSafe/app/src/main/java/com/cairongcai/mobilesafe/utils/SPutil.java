package com.cairongcai.mobilesafe.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by HY-IT on 2017/5/12.
 */

public class SPutil {

    private static SharedPreferences sp;

    /**
     * 存储boolean值的方法
     */
    public static void putBoolean(Context context,String key,boolean value)
    {
        //由于是静态方法，sp可能会随机存储在内存中
        if(sp==null) {
            sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        sp.edit().putBoolean(key,value).commit();
    }

    /**
     * 从存储中获得boolean类型值的方法
     * @param context
     * @param key
     * @param defValue
     * @return
     */
    public static boolean getBoolean(Context context,String key,boolean defValue)
    {
        if(sp==null)
        {
            sp=context.getSharedPreferences("config",Context.MODE_PRIVATE);
        }
        return sp.getBoolean(key,defValue);
    }

    /**
     * 存储string类型的值到内存中
     * @param context
     * @param key
     * @param value
     */
    public  static void putString(Context context,String key,String value)
    {
        if(sp==null)
        {
            sp=context.getSharedPreferences("config",Context.MODE_PRIVATE);
        }
        sp.edit().putString(key,value).commit();
    }

    /**
     * 从内存中获取string类型的值
     * @param context
     * @param key
     * @return
     */
    public static String getString(Context context,String key,String defValue)
    {
        if(sp==null)
        {
            sp=context.getSharedPreferences("config",Context.MODE_PRIVATE);
        }
        return sp.getString(key,defValue);
    }
}
