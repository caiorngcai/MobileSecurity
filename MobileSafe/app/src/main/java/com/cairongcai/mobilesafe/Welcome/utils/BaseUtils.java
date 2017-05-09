package com.cairongcai.mobilesafe.Welcome.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by HY-IT on 2017/5/9.
 */

public class BaseUtils {
    /**
     * 获得本地软件版本号的方法，用来比对是否有新软件和展示到欢迎页面
     * @param context 上下文环境
     * @return 返回版本号
     */
    public static int getLocalVersion(Context context)
    {
        PackageManager pgmanger=context.getPackageManager();
        try {
            PackageInfo packageInfo=pgmanger.getPackageInfo(context.getPackageName(),0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
