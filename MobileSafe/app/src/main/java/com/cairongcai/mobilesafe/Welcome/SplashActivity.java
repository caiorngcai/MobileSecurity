package com.cairongcai.mobilesafe.Welcome;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.cairongcai.mobilesafe.HomeActivity;
import com.cairongcai.mobilesafe.R;
import com.cairongcai.mobilesafe.Welcome.utils.BaseUtils;
import com.cairongcai.mobilesafe.Welcome.utils.VersionUpdateUtils;

public class SplashActivity extends AppCompatActivity {
    private int mVersion;
    private TextView tv_version;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        mVersion = BaseUtils.getLocalVersion(this);
        initview();
        final VersionUpdateUtils updateversion = new VersionUpdateUtils(mVersion, SplashActivity.this);
        updateversion.getCloudVersuion();
    }

    private void initview() {
        tv_version = (TextView) findViewById(R.id.tv_version);
        tv_version.setText("版本号" + mVersion);
    }
}

