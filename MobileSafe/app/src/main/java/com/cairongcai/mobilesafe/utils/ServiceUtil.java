package com.cairongcai.mobilesafe.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;

import java.util.List;

import static android.R.id.list;

/**
 * Created by HY-IT on 2017/5/24.
 */

public class ServiceUtil {
    public static boolean isRunning(Context context,String servicename)
    {
        //activity管理者对象
        ActivityManager am= (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        //获取正在运行的服务的集合
        List<RunningServiceInfo> runningServicesList=am.getRunningServices(1000);
        for (RunningServiceInfo runningserviceinfo:runningServicesList
             ) {
            if(servicename.equals(runningserviceinfo.service.getClassName()))
            {
                return true;
            }
        }
        return false;
    }
}
