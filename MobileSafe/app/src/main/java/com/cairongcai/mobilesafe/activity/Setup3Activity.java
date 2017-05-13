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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup3);

        initUI();
    }

    private void initUI() {
        bt_select_number = (Button) findViewById(R.id.bt_select_number);
        et_phone_number = (EditText) findViewById(R.id.et_phone_number);
        String contact_number= SPutil.getString(getApplicationContext(), ConstantValues.CONTACT_NUMBER,"");
        if(!TextUtils.isEmpty(contact_number)){
            et_phone_number.setText(contact_number);
        }else {
            bt_select_number.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getApplicationContext(),ContactListActivity.class);
                    startActivityForResult(intent,0);
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       if(data!=null)
       {
          String phone= data.getStringExtra("phone");
           SPutil.putString(getApplicationContext(),ConstantValues.CONTACT_NUMBER,phone);
       }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void nextPage(View view)
    {

    }
    public void prePage(View view)
    {
        Intent intent=new Intent(getApplicationContext(),Setup2Activity.class);
        startActivity(intent);
        finish();
    }
}
