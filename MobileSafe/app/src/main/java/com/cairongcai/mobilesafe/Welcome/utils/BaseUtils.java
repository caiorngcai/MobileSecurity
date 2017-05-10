package com.cairongcai.mobilesafe.Welcome.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;

import com.cairongcai.mobilesafe.Welcome.SplashActivity;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import java.io.File;

/**
 * Created by HY-IT on 2017/5/9.
 */

public class BaseUtils {
    /**
     * 获得本地软件版本名称的方法，用来比对是否有新软件和展示到欢迎页面
     * @param context 上下文环境
     * @return 返回版本号
     */
    public static String getLocalVersionName(Context context)
    {
        PackageManager pgmanger=context.getPackageManager();
        try {
            PackageInfo packageInfo=pgmanger.getPackageInfo(context.getPackageName(),0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取版本号的方法
     * @param context 上下文环境
     * @return 返回应用的版本号
     */
    public static int getLocalVersionCode(Context context)
    {
        PackageManager packageManager=context.getPackageManager();
        try{
            PackageInfo packageInfo=packageManager.getPackageInfo(context.getPackageName(),0);
            return packageInfo.versionCode;
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return 0;
    }

}
