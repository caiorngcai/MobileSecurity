package com.cairongcai.mobilesafe.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;

import com.cairongcai.mobilesafe.R;
import com.cairongcai.mobilesafe.utils.ConstantValues;
import com.cairongcai.mobilesafe.utils.SPutil;
import com.cairongcai.mobilesafe.utils.ToastUtil;
import com.cairongcai.mobilesafe.view.SettingItemView;

public class Setup2Activity extends AppCompatActivity {

    private SettingItemView siv_sim_bound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup2);

        initUI();
    }
    /**
     * 绑定sim卡改变UI的操作方法
     */
    private void initUI() {
        siv_sim_bound = (SettingItemView) findViewById(R.id.siv_sim_bound);

        //回显过程，从sp中拿数据
        String sim_number=SPutil.getString(getApplicationContext(),ConstantValues.SIM_NUMBER_BOUND,"");
        if(TextUtils.isEmpty(sim_number))
        {
            siv_sim_bound.setCheck(false);
        }else {
            siv_sim_bound.setCheck(true);
        }
        //自定义事件的监听事件
        siv_sim_bound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean ischeck=siv_sim_bound.isCheck();
                siv_sim_bound.setCheck(!ischeck);
                if(!ischeck)
                {
                    TelephonyManager manager= (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                    String SimSerialNumber=manager.getSimSerialNumber();
                    SPutil.putString(getApplicationContext(),ConstantValues.SIM_NUMBER_BOUND,SimSerialNumber);
                }else {
                    SPutil.remove(getApplicationContext(),ConstantValues.SIM_NUMBER_BOUND);
                }
            }
        });
    }

    public void nextPage(View view)
    {
        String sim_number= SPutil.getString(getApplicationContext(), ConstantValues.SIM_NUMBER_BOUND,"");
        if(!TextUtils.isEmpty(sim_number)) {
            Intent intent = new Intent(getApplicationContext(), Setup3Activity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.next_in_anim, R.anim.next_out_anim);
        }else {
            ToastUtil.show(getApplicationContext(),"请先绑定sim卡哦。。");
        }
    }
    public void prePage(View view)
    {
        Intent intent=new Intent(getApplicationContext(),Setup1Activity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.pre_in_anim, R.anim.pre_out_anim);
    }
}
