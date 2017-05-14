package com.cairongcai.mobilesafe.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
            initUI();
        }else {
            Intent intent=new Intent(getApplicationContext(),Setup1Activity.class);
            startActivity(intent);
            finish();
        }
    }

    private void initUI() {
        TextView tv_phone= (TextView) findViewById(R.id.tv_phone);
        Button bt_setup_over= (Button) findViewById(R.id.bt_setup_over);
        String contact_number=SPutil.getString(getApplicationContext(),ConstantValues.CONTACT_NUMBER,"");
        tv_phone.setText(contact_number);
        TextView tv_reset_setup= (TextView) findViewById(R.id.tv_reset_setup);
        tv_reset_setup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Setup1Activity.class);
                startActivity(intent);
                finish();
            }
        });

        bt_setup_over.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
