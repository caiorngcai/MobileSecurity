package com.cairongcai.mobilesafe.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.cairongcai.mobilesafe.R;
import com.cairongcai.mobilesafe.db.BlackNumberDao;
import com.cairongcai.mobilesafe.db.BlackNumberInfo;
import com.cairongcai.mobilesafe.utils.ToastUtil;

import java.util.List;

public class BlackNumberActivity extends AppCompatActivity {
   private List<BlackNumberInfo> mBlackNumberList;
    private int mCount;
    private ListView lv_blacknumber;
    private BlackNumberAdapter mAdapter;
    private BlackNumberDao mDao;
    private int mode;
    private boolean mIsload=false;
    Handler mHandler=new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
           if(mAdapter==null)
           {
               mAdapter=new BlackNumberAdapter();
               lv_blacknumber.setAdapter(mAdapter);
           }else {
               //adapter对象存在，说明只是更新操作
               lv_blacknumber.deferNotifyDataSetChanged();
           }
        }
    };
    class BlackNumberAdapter extends BaseAdapter
    {

        @Override
        public int getCount() {
            return mBlackNumberList.size();
        }

        @Override
        public Object getItem(int position) {
            return mBlackNumberList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
            //两个优化，复用convertview和减少findviewbyid操作
            ViewHolder holder=null;
            if(convertView==null)
            {
                convertView=View.inflate(getApplicationContext(),R.layout.listview_blacknumber_item,null);
                holder=new ViewHolder();
                holder.tv_blackphone= (TextView) convertView.findViewById(R.id.tv_blackphone);
                holder.tv_balckmode= (TextView) convertView.findViewById(R.id.tv_blackmode);
                holder.iv_lajitong= (ImageView) convertView.findViewById(R.id.iv_delete);
                convertView.setTag(holder);
            }else {
                holder= (ViewHolder) convertView.getTag();
            }
            //垃圾桶图标点击事件
            holder.iv_lajitong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDao.delete(mBlackNumberList.get(position).phone);
                    mBlackNumberList.remove(position);
                    if(mAdapter!=null)
                    {
                        mAdapter.notifyDataSetChanged();
                    }
                }
            });
            holder.tv_blackphone.setText(mBlackNumberList.get(position).phone);
            int mode=Integer.parseInt(mBlackNumberList.get(position).mode);
            switch (mode)
            {
                case 1:
                    holder.tv_balckmode.setText("拦截短信");
                    break;
                case 2:
                    holder.tv_balckmode.setText("拦截电话");
                    break;
                case 3:
                    holder.tv_balckmode.setText("拦截所有");
                    break;

            }
            return convertView;
        }
    }
    static class ViewHolder{
        TextView tv_blackphone;
        TextView tv_balckmode;
        ImageView iv_lajitong;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_black_number);

        initUI();
        initData();

    }

    private void initData() {
        new Thread()
        {
            @Override
            public void run() {
                mDao = BlackNumberDao.getInstance(getApplicationContext());
                mBlackNumberList= mDao.find(0);
                mCount= mDao.getCount();
                mHandler.sendEmptyMessage(0);
            }
        }.start();
    }
    /**
     * UI的初始化动作，和UI的点击事件放应
     */
    private void initUI() {
        Button bt_add= (Button) findViewById(R.id.bt_add);
        lv_blacknumber= (ListView) findViewById(R.id.lv_blacknumber);
        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        lv_blacknumber.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(mBlackNumberList!=null)
                {
                    //参数mIsload是为了防止重复加载
                    if(scrollState== AbsListView.OnScrollListener.SCROLL_STATE_IDLE
                            &&lv_blacknumber.getLastVisiblePosition()==mBlackNumberList.size()-1
                           &&!mIsload ){
                        if(mCount>mBlackNumberList.size())
                        {
                            new Thread()
                            {
                                @Override
                                public void run() {
                                    mDao=BlackNumberDao.getInstance(getApplicationContext());
                                    List<BlackNumberInfo> moreData= mDao.find(mBlackNumberList.size());
                                    //添加下一页的数据
                                    mBlackNumberList.addAll(moreData);
                                 mHandler.sendEmptyMessage(0);
                                }
                            }.start();
                        }


                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    private void showDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        final AlertDialog dialog=builder.create();
        View view=View.inflate(getApplicationContext(),R.layout.dialog_add_blacknumber,null);
        dialog.setView(view,0,0,0,0);
        final EditText et_phone = (EditText) view.findViewById(R.id.et_blacknumber);
        RadioGroup rg_group = (RadioGroup) view.findViewById(R.id.rg_group);

        final Button bt_submit = (Button) view.findViewById(R.id.bt_submit);
        Button bt_cancel = (Button)view.findViewById(R.id.bt_cancel);
        rg_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_sms:
                        //拦截短信
                        mode = 1;
                        break;
                    case R.id.rb_phone:
                        //拦截电话
                        mode = 2;
                        break;
                    case R.id.rb_all:
                        //拦截所有
                        mode = 3;
                        break;
                }
            }
        });
        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String blacknumber=et_phone.getText().toString();
                if(!TextUtils.isEmpty(blacknumber))
                {
                    mDao.insert(blacknumber,mode+"");
                    //刷新集合，刷新adapter
                    BlackNumberInfo blackNumberInfo=new BlackNumberInfo();
                    blackNumberInfo.phone=blacknumber;
                    blackNumberInfo.mode=mode+"";
                    mBlackNumberList.add(0,blackNumberInfo);//插在最顶端
                    if(mAdapter!=null)
                    {
                        mAdapter.notifyDataSetChanged();
                    }
                    dialog.dismiss();
                }else {
                    ToastUtil.show(getApplicationContext(),"输入拦截号码才可以哦！");
                }
            }
        });
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }
}
