package com.cairongcai.mobilesafe.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cairongcai.mobilesafe.R;
import com.cairongcai.mobilesafe.engine.AddressDao;

public class QueryAddressActivity extends Activity {

    private EditText et_query_address;
    private TextView tv_query_result;
    private Button bt_query;
    String mAddress;

    private Handler mHandelr=new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            tv_query_result.setText(mAddress);
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_address);
        
        initUI();
    }

    private void initUI() {
        bt_query = (Button) findViewById(R.id.bt_query);
        et_query_address = (EditText) findViewById(R.id.et_query_address);
        tv_query_result = (TextView) findViewById(R.id.tv_query_result);
        bt_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String query_phone= et_query_address.getText().toString();
                if(!TextUtils.isEmpty(query_phone))
                {
                    //不空，子线程中查询
                    new Thread()
                    {
                        @Override
                        public void run() {
                          mAddress=AddressDao.getAddress(query_phone);
                          mHandelr.sendEmptyMessage(0);
                        }
                    }.start();
                }else {
                    //空号,抖动

                }
            }
        });

    }
}
