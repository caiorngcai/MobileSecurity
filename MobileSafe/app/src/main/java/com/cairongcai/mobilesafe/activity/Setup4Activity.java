package com.cairongcai.mobilesafe.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.cairongcai.mobilesafe.R;
import com.cairongcai.mobilesafe.utils.ConstantValues;
import com.cairongcai.mobilesafe.utils.SPutil;
import com.cairongcai.mobilesafe.utils.ToastUtil;

public class Setup4Activity extends AppCompatActivity {

    private CheckBox setup4_cb_box;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup4);
        init();
    }

    private void init() {
        setup4_cb_box = (CheckBox) findViewById(R.id.setup4_cb_box);
        //回显状态
        boolean is_open_security= SPutil.getBoolean(getApplicationContext(), ConstantValues.IS_OPEN_SECURITY,false);
        setup4_cb_box.setChecked(is_open_security);//从父类继承的方法
        if(is_open_security)
        {
            setup4_cb_box.setText("防盗功能已经开启");
        }else {
            setup4_cb_box.setText("防盗功能已关闭");
        }
        //点击事件处理
        setup4_cb_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            //isChecked是改变过后的状态
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SPutil.putBoolean(getApplication(),ConstantValues.IS_OPEN_SECURITY,isChecked);
                if(isChecked)
                {
                    setup4_cb_box.setText("防盗功能已经开启");
                }else {
                    setup4_cb_box.setText("防盗功能已关闭");
                }
            }
        });
    }
    public void nextPage(View view)
    {
       if (SPutil.getBoolean(getApplicationContext(),ConstantValues.IS_OPEN_SECURITY,false))
        {
            Intent intent = new Intent(getApplicationContext(), SetupoverActivity.class);
            startActivity(intent);
            finish();
            //能点下一页，说明整个设置防盗功能完成
            SPutil.putBoolean(getApplicationContext(),ConstantValues.SETUP_OVER,true);
        }else{
           ToastUtil.show(getApplicationContext(),"请开启防盗保护");
    }
    }
    public void prePage(View view)
    {
        Intent intent=new Intent(getApplicationContext(),Setup3Activity.class);
        startActivity(intent);
        finish();

        overridePendingTransition(R.anim.pre_in_anim,R.anim.pre_out_anim);
    }
}
