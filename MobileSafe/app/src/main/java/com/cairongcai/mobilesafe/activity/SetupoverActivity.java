package com.cairongcai.mobilesafe.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cairongcai.mobilesafe.R;
import com.cairongcai.mobilesafe.utils.ConstantValues;
import com.cairongcai.mobilesafe.utils.SPutil;

public class SetupoverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean setup_over= SPutil.getBoolean(getApplicationContext(), ConstantValues.SETUP_OVER,false);
        if(setup_over){
            setContentView(R.layout.activity_setupover);
        }else {
            Intent intent=new Intent(getApplicationContext(),Setup1Activity.class);
            startActivity(intent);
            finish();
        }
    }
}
