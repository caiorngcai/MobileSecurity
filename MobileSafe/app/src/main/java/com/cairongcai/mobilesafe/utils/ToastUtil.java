package com.cairongcai.mobilesafe.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by HY-IT on 2017/5/9.
 */

public class ToastUtil {
    /**
     * 为了防止吐司的时候少写show 把它封装成方法
     * @param context 上下文环境
     * @param message 要土司的信息
     */
    public static void show(Context context,String message)
    {
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }
}
