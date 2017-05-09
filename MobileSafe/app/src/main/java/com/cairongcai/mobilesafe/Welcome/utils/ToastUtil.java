package com.cairongcai.mobilesafe.Welcome.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by HY-IT on 2017/5/9.
 */

public class ToastUtil {
    public static void show(Context context,String message)
    {
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }
}
