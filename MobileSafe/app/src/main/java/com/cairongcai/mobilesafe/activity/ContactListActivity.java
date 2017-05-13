package com.cairongcai.mobilesafe.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.cairongcai.mobilesafe.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.cairongcai.mobilesafe.R.layout.contact_item_view;

public class ContactListActivity extends AppCompatActivity {

    private ListView lv_contact;
    private List<Map<String,String>> list=new ArrayList<Map<String,String>>();
    private MyAdapter mAdapter;

    private Handler mhander=new Handler()
    {

        private ListView lv_contact;

        @Override
        public void handleMessage(Message msg) {
            mAdapter = new MyAdapter();
            lv_contact.setAdapter(mAdapter);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        initUI();
        initData();
    }
    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
          View view;
            if(convertView!=null)
            {
                view=convertView;
            }else {
                view=View.inflate(getApplicationContext(), R.layout.contact_item_view,null);
               TextView tv_name= (TextView) view.findViewById(R.id.tv_name);
                TextView tv_phone= (TextView) view.findViewById(R.id.tv_phone);
                tv_name.setText(list.get(position).get("name"));
                tv_phone.setText(list.get(position).get("phone"));
            }
            return view;
        }
    }

    private void initUI() {
        lv_contact = (ListView) findViewById(R.id.lv_contact);
        lv_contact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(mAdapter!=null)
                {
                    HashMap<String,String> map= (HashMap<String, String>) mAdapter.getItem(position);
                    String phone=map.get("phone");
                    Intent intent=new Intent();
                    intent.putExtra("phone",phone);
                    setResult(0,intent);
                    finish();
                }
            }
        });
    }

    private void initData() {

        new Thread()
        {
            @Override
            public void run() {

                ContentResolver resolver=getContentResolver();
                Cursor cursor=resolver.query(Uri.parse("content://com.android.contacts/raw_contacts"),
                        new String[]{"contact_id"},null,null,null);
                while(cursor.moveToNext())
                {
                    String id=cursor.getString(0);
                    Cursor indexcursor=resolver.query(Uri.parse("content://com.android.contacts/data"),
                            new String[]{"data1","mimetype"},"raw_contact_id=?",new String[]{id},null);
                    HashMap<String,String> map=new HashMap<String, String>();
                    while (indexcursor.moveToNext())
                    {
                        String data=indexcursor.getString(0);
                        String type=indexcursor.getString(1);
                         if(type.equals("vnd.android.cursor.item/phone_v2"))
                         {
                             if(TextUtils.isEmpty(data))
                             map.put("phone",data);
                         }else if(type.equals("vnd.android.cursor.item/name"))
                         {
                             if(TextUtils.isEmpty(data))
                                 map.put("name",data);
                         }
                    }
                    indexcursor.close();
                    list.add(map);
                }
                cursor.close();
                mhander.sendEmptyMessage(0);
            }
        }.start();
    }
}
