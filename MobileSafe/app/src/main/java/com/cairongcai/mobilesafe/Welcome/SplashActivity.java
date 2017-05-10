package com.cairongcai.mobilesafe.Welcome;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.cairongcai.mobilesafe.HomeActivity;
import com.cairongcai.mobilesafe.R;
import com.cairongcai.mobilesafe.Welcome.utils.BaseUtils;
import com.cairongcai.mobilesafe.Welcome.utils.StreamUtils;
import com.cairongcai.mobilesafe.Welcome.utils.ToastUtil;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class SplashActivity extends AppCompatActivity {
    private int mLocalVersionCode;
    private TextView tv_version;
    private String mVersionDes;
    public static String mDownloadUrl;
    protected static final int UPDATE_VERSION = 100;
    protected static final int ENTER_HOME = 101;
    protected static final int URL_ERROR = 102;
    protected static final int IO_ERROR = 103;
    protected static final int JSON_ERROR = 104;
    protected static final int OTHER_ERROR=105;
    Handler mHandler=new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
           switch (msg.what)
           {
               case UPDATE_VERSION:
                   showUpdateDialog();
                   break;
               case ENTER_HOME:
                   enterhome();
                   break;
               case IO_ERROR:
                   ToastUtil.show(getApplicationContext(),"io错误");
                   enterhome();
                   break;
               case JSON_ERROR:
                   ToastUtil.show(getApplicationContext(),"json解析错误");
                   enterhome();
                   break;
               case URL_ERROR:
                   ToastUtil.show(getApplicationContext(),"网络错误");
                   enterhome();
                   break;
               case OTHER_ERROR:
                   ToastUtil.show(getApplicationContext(),"发生其他错误");
                   enterhome();
                   break;
           }
        }
    };

    /**
     * 跳转到主页面的方法
     */
    private void enterhome() {
       Intent intent=new Intent(this,HomeActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 展示询问是否更新软件的UI，并针对用户选择做出判断
     */
    private void showUpdateDialog() {
        //此处构造函数的参数必须为this 因为dialog必须依赖于activity存在
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.launch_bg);
        builder.setTitle("有版本更新啦");
        builder.setMessage(mVersionDes);
        builder.setPositiveButton("给我更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                downloadApk();
            }
        });
        builder.setNegativeButton("就不更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            enterhome();
            }
        });
        //有可能按下返回键 所以此方法是必要的
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
     * 下载新版本的方法，使用xutil框架中的httputil功能
     */

    private void downloadApk() {
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
        {
            String path=Environment.getExternalStorageDirectory().getAbsolutePath()
                    + File.separator+"mobilesafe2.0.apk";
            HttpUtils httputil=new HttpUtils();
            httputil.download(SplashActivity.mDownloadUrl, path, new RequestCallBack<File>() {
                @Override
                public void onSuccess(ResponseInfo<File> responseInfo) {
                    File file=responseInfo.result;
                    installApk(file);
                }

                @Override
                public void onFailure(HttpException e, String s) {

                }
            });
        }
    }

    /**
     * 安装的程序的方法
     * @param file 要安装的程序
     */
    private void installApk(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");
       startActivityForResult(intent,0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        enterhome();
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        initUI();
        updateVersion();

    }

    private void updateVersion() {
        mLocalVersionCode=BaseUtils.getLocalVersionCode(this);
        checkCloudVersionCode();
    }

    /**
     * 获取服务器版本号的方法，并判断接下来的各种操作
     */
    private void checkCloudVersionCode() {
        new Thread()
        {
            long starttime=System.currentTimeMillis();
            @Override
            public void run() {
                Message msg=Message.obtain();
                URL url= null;
                try {
                    url = new URL("http://10.0.2.2:8080/update74.json");
                    HttpsURLConnection connection=(HttpsURLConnection) url.openConnection();
                    connection.setConnectTimeout(1000*2);
                    connection.setReadTimeout(1000*2);
                    if(connection.getResponseCode()==200) {
                        InputStream is = connection.getInputStream();
                        String json= StreamUtils.stream2string(is);
                        JSONObject jsonObject=new JSONObject(json);
                        String versionName = jsonObject.getString("versionName");
                        mVersionDes = jsonObject.getString("versionDes");
                        String versionCode = jsonObject.getString("versionCode");
                        mDownloadUrl = jsonObject.getString("downloadUrl");
                        if(mLocalVersionCode<Integer.parseInt(versionCode))
                        {
                            msg.what=UPDATE_VERSION;
                        }else {
                            msg.what=ENTER_HOME;
                        }
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    msg.what=URL_ERROR;
                } catch (IOException e) {
                    e.printStackTrace();
                    msg.what=IO_ERROR;
                }catch (JSONException e)
                {
                    e.printStackTrace();
                    msg.what=JSON_ERROR;
                }catch (ClassCastException e)
                {
                    e.printStackTrace();
                    msg.what=OTHER_ERROR;
                }
                finally {
                    long endtime=System.currentTimeMillis();
                    if(endtime-starttime<4000)
                    {
                        SystemClock.sleep(4000-(endtime-starttime));
                    }
                    mHandler.sendMessage(msg);
                }
            }
        }.start();
    }

    private void initUI() {
        tv_version= (TextView) findViewById(R.id.tv_version);
        tv_version.setText("版本名称"+BaseUtils.getLocalVersionName(this));
    }

}

