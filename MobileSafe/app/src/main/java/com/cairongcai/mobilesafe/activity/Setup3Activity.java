package com.cairongcai.mobilesafe.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cairongcai.mobilesafe.R;
import com.cairongcai.mobilesafe.utils.ConstantValues;
import com.cairongcai.mobilesafe.utils.SPutil;

public class Setup3Activity extends AppCompatActivity {

    private Button bt_select_number;
    private EditText et_phone_number;
    private String phone="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup3);

        initUI();
    }

    private void initUI() {
        bt_select_number = (Button) findViewById(R.id.bt_select_number);
        et_phone_number = (EditText) findViewById(R.id.et_phone_number);
         phone= SPutil.getString(getApplicationContext(), ConstantValues.CONTACT_NUMBER,"");
        et_phone_number.setText(phone);
        bt_select_number.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getApplicationContext(),ContactListActivity.class);
                    startActivityForResult(intent,0);
                }
            });
        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       if(data!=null)
       {
           phone =  data.getStringExtra("phone");
           et_phone_number.setText(phone);
       }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void nextPage(View view)
    {
        phone= et_phone_number.getText().toString();
        SPutil.putString(getApplicationContext(),ConstantValues.CONTACT_NUMBER,phone);
        Intent intent=new Intent(getApplicationContext(),Setup4Activity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.next_in_anim, R.anim.next_out_anim);
    }
    public void prePage(View view)
    {
        Intent intent=new Intent(getApplicationContext(),Setup2Activity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.pre_in_anim, R.anim.pre_out_anim);
    }
}
