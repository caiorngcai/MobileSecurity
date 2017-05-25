package com.cairongcai.mobilesafe.activity;

import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.cairongcai.mobilesafe.R;
import com.cairongcai.mobilesafe.utils.ConstantValues;
import com.cairongcai.mobilesafe.utils.MD5Util;
import com.cairongcai.mobilesafe.utils.SPutil;
import com.cairongcai.mobilesafe.utils.ToastUtil;

public class HomeActivity extends AppCompatActivity {

    private GridView gv_home;
    private String[] mTitleStr;
    private int[] mDrawableID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initUI();
        initData();
    }
    /**
     * 此方法准备好要填充到gridview中的数据，包括资源文件数组，数据适配器。
     *
     */
    private void initData() {
        //准备数据
        mTitleStr = new String[]{
         "手机防盗","通信卫士","软件管理","进程管理","流量统计","手机杀毒","缓存清理","高级工具","设置中心"
        };
        mDrawableID = new int[]{
                R.drawable.home_safe,R.drawable.home_callmsgsafe,
                R.drawable.home_apps,R.drawable.home_taskmanager,
                R.drawable.home_netmanager,R.drawable.home_trojan,
                R.drawable.home_sysoptimize,R.drawable.home_tools,R.drawable.home_settings
        };
        //把adapter填充到GridView中，先自定义一个MyAdapter
        gv_home.setAdapter(new MyAdapter());
        gv_home.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position)
                {
                    case 0:
                       //暂时输入密码对话框
                       showPWD();
                        break;
                    case 1:
                        Intent intent1=new Intent(getApplicationContext(),BlackNumberActivity.class);
                        startActivity(intent1);
                        break;
                    case 7:
                        Intent intent7=new Intent(getApplicationContext(),AtoolActivity.class);
                        startActivity(intent7);
                        break;
                    case 8:
                        Intent intent8=new Intent(getApplicationContext(),SettingActivity.class);
                        startActivity(intent8);
                        break;
                }
            }
        });
    }

    private void showPWD() {
        //判断本地是否有存储密码
        String psd= SPutil.getString(this, ConstantValues.MOBILE_SAFE_PSD,"");
        if(TextUtils.isEmpty(psd))
        {
            showSetPsdDialog();
        }else{
            showComfirmPsdDialog();
        }
    }

    /**
     * 展示设置密码对话框的UI
     */
    private void showSetPsdDialog() {
        final AlertDialog.Builder builder=new AlertDialog.Builder(this);
        final View view=View.inflate(this,R.layout.dialog_set_psd,null);
        final AlertDialog dialog=builder.create();
       dialog.setView(view);
       dialog.show();

        Button bt_submit= (Button) view.findViewById(R.id.bt_submit);
        Button bt_cancel= (Button) view.findViewById(R.id.bt_cancel);
        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView et_set_psd= (TextView) view.findViewById(R.id.et_set_psd);
                TextView et_confirm_psd=(TextView)view.findViewById(R.id.et_confirm_psd);

                String setpsd=et_set_psd.getText().toString();
                String confirmpsd=et_confirm_psd.getText().toString();
                if(!TextUtils.isEmpty(setpsd)&&!TextUtils.isEmpty(confirmpsd))
                {
                    if(setpsd.equals(confirmpsd))
                    {
                        Intent intent=new Intent(getApplicationContext(),SetupoverActivity.class);
                        startActivity(intent);
                        dialog.dismiss();

                        SPutil.putString(getApplicationContext(),ConstantValues.MOBILE_SAFE_PSD, MD5Util.encoder(setpsd));
                    }
                }else {
                    ToastUtil.show(getApplicationContext(),"请输入密码");
                }
            }
        });
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    /**
     * 展示确定密码对话框的UI
     */
    private void showComfirmPsdDialog()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        final AlertDialog dialog=builder.create();
        final View view=View.inflate(getApplicationContext(),R.layout.dialog_confim_psd,null);
        dialog.setView(view);
        dialog.show();

       Button bt_submit= (Button) view.findViewById(R.id.bt_submit);
        Button bt_cancel=(Button)view.findViewById(R.id.bt_cancel);

        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String psd=SPutil.getString(getApplicationContext(),ConstantValues.MOBILE_SAFE_PSD,"");
                EditText et_confirm_psd= (EditText) view.findViewById(R.id.et_confirm_psd);
               String confirmpsd= et_confirm_psd.getText().toString();
                if(!TextUtils.isEmpty(confirmpsd))
                {
                    if(psd.equals(MD5Util.encoder(confirmpsd)))
                    {
                        Intent intent=new Intent(getApplicationContext(),SetupoverActivity.class);
                        startActivity(intent);
                        dialog.dismiss();
                    }else {
                        ToastUtil.show(getApplicationContext(),"输入密码错误");
                    }

                }else {
                    ToastUtil.show(getApplicationContext(),"不能输入空密码");
                }
            }
        });
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
    private void initUI() {
        gv_home = (GridView) findViewById(R.id.gv_home);
    }

    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mTitleStr.length;
        }

        @Override
        public Object getItem(int position) {
            return mTitleStr[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view=null;
            if(convertView!=null)
            {
                view=convertView;
            }else {
                view=View.inflate(getApplicationContext(),R.layout.gridview_item,null);
                //注意这里是view.findViewById 不然会报空指针异常
                TextView tv_title= (TextView)view.findViewById(R.id.tv_title);
                ImageView iv_icon= (ImageView)view.findViewById(R.id.iv_icon);
                tv_title.setText(mTitleStr[position]);
                iv_icon.setBackgroundResource(mDrawableID[position]);
            }

            return view;
        }
    }
}
