package com.cairongcai.mobilesafe.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.cairongcai.mobilesafe.R;
import com.cairongcai.mobilesafe.view.SettingItemView;

public class SettingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initUpdate();
    }

    /**
     * 设置设置条目的监听事件，并根据CheckBox状态设置描述信息
     */
    private void initUpdate() {
        final SettingItemView siv_update= (SettingItemView) findViewById(R.id.siv_update);
        siv_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isCheck=siv_update.isCheck();
                siv_update.setChecked(!isCheck);
            }
        });
    }
}
