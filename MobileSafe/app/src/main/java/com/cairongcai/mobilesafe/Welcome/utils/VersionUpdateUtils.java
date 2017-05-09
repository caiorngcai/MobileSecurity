package com.cairongcai.mobilesafe.Welcome.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;

import com.cairongcai.mobilesafe.HomeActivity;
import com.cairongcai.mobilesafe.R;
import com.cairongcai.mobilesafe.Welcome.SplashActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by HY-IT on 2017/5/9.
 */

public class VersionUpdateUtils {
    protected static final int UPDATE_VERSION = 100;
    protected static final int ENTER_HOME = 101;
    protected static final int URL_ERROR = 102;
    protected static final int IO_ERROR = 103;
    protected static final int JSON_ERROR = 104;
    private String mVersionDes;
    private String mDownloadUrl;
    private Activity mContext;
    private int mVersion;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case IO_ERROR:
                    ToastUtil.show(mContext, "io错误");
                    enterhome();
                    break;
                case JSON_ERROR:
                    ToastUtil.show(mContext, "json错误");
                    enterhome();
                    break;
                case URL_ERROR:
                    ToastUtil.show(mContext, "网络错误");
                    enterhome();
                    break;
                case ENTER_HOME:
                    enterhome();
                    break;
                case UPDATE_VERSION:
                    showUpdateDialog();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    public VersionUpdateUtils(int Version, Activity activity) {

        mContext = activity;
        mVersion = Version;
    }

    /**
     * 展示更新UI的方法
     */
    private void showUpdateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setIcon(R.drawable.launch_bg);
        builder.setTitle("版本更新");
        builder.setMessage(mVersionDes);
        builder.setPositiveButton("给朕更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                downloadapk();
            }
        });
        builder.setPositiveButton("就不更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                enterhome();
            }
        });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                enterhome();
                dialog.dismiss();
            }
        });
        builder.show();
    }

    /**
     * 下载后新版本安装apk的方法
     */
    private void downloadapk() {

    }

    /**
     * 进入主页面的方法
     */
    private void enterhome() {

        Intent intent = new Intent(mContext, HomeActivity.class);
        mContext.startActivity(intent);
        mContext.finish();
    }

    /**
     * 获得服务器中最新apk的版本号，并作出判断
     */
    public void getCloudVersuion() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg=Message.obtain();
                URL url= null;
                try {
                    url = new URL("http://10.0.2.2:8080/update74.json");
                    HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                    connection.setConnectTimeout(1000 * 2);
                    connection.setReadTimeout(1000 * 2);
                    int code = connection.getResponseCode();
                    if (code == 200) {
                        InputStream is = connection.getInputStream();
                        String json = StreamUtils.stream2string(is);
                        JSONObject jsonObject = new JSONObject(json);
                        String versioname=jsonObject.getString("versionname");
                        mVersionDes = jsonObject.getString("versionDes");
                        String versionCode = jsonObject.getString("versionCode");
                        mDownloadUrl = jsonObject.getString("downloadUrl");

                        if(mVersion<Integer.parseInt(versionCode))
                        {
                            msg.what=UPDATE_VERSION;
                        }else {
                            msg.what=ENTER_HOME;
                        }
                    }
                }
                catch (MalformedURLException e) {
                    e.printStackTrace();
                    msg.what=URL_ERROR;
                }
                catch (IOException e) {
                    e.printStackTrace();
                    msg.what=IO_ERROR;
                }
                catch (JSONException e)
                {
                    msg.what=JSON_ERROR;
                    e.printStackTrace();
                }finally {
                    SystemClock.sleep(2000);
                   mHandler.sendMessage(msg);
                }
            }
        }).start();
    }
}
