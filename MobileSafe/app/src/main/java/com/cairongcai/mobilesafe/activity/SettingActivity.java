package com.cairongcai.mobilesafe.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cairongcai.mobilesafe.R;
import com.cairongcai.mobilesafe.service.BlacknumberService;
import com.cairongcai.mobilesafe.utils.ConstantValues;
import com.cairongcai.mobilesafe.utils.SPutil;
import com.cairongcai.mobilesafe.utils.ServiceUtil;
import com.cairongcai.mobilesafe.view.SettingItemView;

public class SettingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initUpdate();
        initBlackNumber();
    }

    private void initBlackNumber() {
        final SettingItemView siv_blacknumber= (SettingItemView) findViewById(R.id.siv_balcknumber);
        //回显过程
        boolean isRunning= ServiceUtil.isRunning(getApplicationContext(),"com.cairongcai.mobilesafe.service.BlacknumberService");
        siv_blacknumber.setCheck(isRunning);
       //点击过程
        siv_blacknumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isCheck=siv_blacknumber.isCheck();
                siv_blacknumber.setCheck(!isCheck);
                if(!isCheck)
                {
                    //开启服务
                    startService(new Intent(getApplicationContext(), BlacknumberService.class));
                }else {
                    stopService(new Intent(getApplicationContext(),BlacknumberService.class));
                }
            }
        });

    }

    /**
     * 设置设置条目的监听事件，并根据CheckBox状态设置描述信息
     */
    private void initUpdate() {
        final SettingItemView siv_update= (SettingItemView) findViewById(R.id.siv_update);
        boolean open_update= SPutil.getBoolean(this, ConstantValues.OPEN_UPDATE,false);
        siv_update.setCheck(open_update);
        siv_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isCheck=siv_update.isCheck();
                siv_update.setCheck(!isCheck);
                SPutil.putBoolean(getApplicationContext(), ConstantValues.OPEN_UPDATE,!isCheck);
            }
        });
    }
}
