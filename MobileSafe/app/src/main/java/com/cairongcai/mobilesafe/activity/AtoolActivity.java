package com.cairongcai.mobilesafe.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;

import com.cairongcai.mobilesafe.R;
import com.cairongcai.mobilesafe.engine.SmsBackup;
import com.cairongcai.mobilesafe.utils.ToastUtil;

import java.io.File;

public class AtoolActivity extends Activity {

    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atool);

        initPhoneAddress();
        initSmssBackup();
    }

    @Override
    protected void onDestroy() {
        if(dialog!=null)
        {
            dialog.dismiss();
            super.onDestroy();
        }
    }

    private void initSmssBackup() {
        TextView tv_smssbackup= (TextView) findViewById(R.id.tv_smssbackup);
        tv_smssbackup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSmssbackupDialog();
            }
        });
    }

    private void showSmssbackupDialog() {
        dialog = new ProgressDialog(this);
        dialog.setIcon(R.drawable.ic_launcher);
        dialog.setTitle("短信备份");
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.show();
        new Thread()
        {
            @Override
            public void run() {
                //在引擎类中实现短信备份的具体操作
                String path= Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator+"smscai.xml";
                SmsBackup.backup(getApplicationContext(), path, new SmsBackup.CallBack() {
                    @Override
                    //方法的实现
                    public void setMax(int count) {
                        dialog.setMax(count);
                    }

                    @Override
                    public void setProgress(int index) {
                        dialog.setProgress(index);
                    }
                });
                dialog.dismiss();
            }
        }.start();
    }

    private void initPhoneAddress() {
        TextView tv_query_phone_address= (TextView) findViewById(R.id.tv_query_phone_address);
        tv_query_phone_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),QueryAddressActivity.class));
            }
        });
    }

}
