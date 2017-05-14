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
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.cairongcai.mobilesafe.R;
import com.cairongcai.mobilesafe.utils.Contact;
import com.cairongcai.mobilesafe.utils.ContactQueryUtil;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.cairongcai.mobilesafe.R.layout.contact_item_view;

public class ContactListActivity extends AppCompatActivity {

    protected static final String tag = "ContactListActivity";
    private ListView lv_contact;
    private MyAdapter mAdapter;
    private List<Contact> contacts ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        //气死了，我的华为手机读不到联系人具体数据，只有ID,害我调了一下午
        contacts = ContactQueryUtil.querycontact(getApplicationContext());
        initUI();

    }
    class MyAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return contacts.size();
        }

        @Override
        public Object getItem(int position) {
            return contacts.get(position);
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
                tv_name.setText(contacts.get(position).getName());
                tv_phone.setText(contacts.get(position).getPhone());
            }
            return view;
        }
    }

    private void initUI() {
        lv_contact = (ListView) findViewById(R.id.lv_contact);
        mAdapter=new MyAdapter();
        lv_contact.setAdapter(mAdapter);
        lv_contact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(mAdapter!=null)
                {
                    String phone=contacts.get(position).getPhone();
                    Intent intent=new Intent();
                    intent.putExtra("phone",phone);
                    setResult(0,intent);
                    finish();
                }
            }
        });
    }
}
