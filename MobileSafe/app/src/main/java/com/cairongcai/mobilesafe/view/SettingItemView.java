package com.cairongcai.mobilesafe.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cairongcai.mobilesafe.R;

/**
 * Created by HY-IT on 2017/5/11.
 */

public class SettingItemView extends RelativeLayout {
    private static final String NAMESPACE = "http://schemas.android.com/apk/res/com.cairongcai.mobilesafe";
    private static final String tag = "SettingItemView";
    private String mDestitle;
    private String mDesoff;
    private String mDeson;
    private CheckBox cb_box;
    private TextView tv_des;

    public SettingItemView(Context context) {
       this(context,null);
    }

    public SettingItemView(Context context, AttributeSet attrs) {
       this(context, attrs,0);
    }
    //不管创建这个view对象时调用哪个方法，都让它最终调用最后个方法
    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(context, R.layout.setting_item_view,this);//把setting_item_view挂载到当前类对应的view中

        TextView tv_title= (TextView) findViewById(R.id.tv_title);
        tv_des = (TextView) findViewById(R.id.tv_des);
        cb_box = (CheckBox) findViewById(R.id.cb_box);

        initAttrs(attrs);
        tv_title.setText(mDestitle);

    }

    /**
     * 用于获取自定义view中自定义属性的值的方法
     * @param attrs 自定义属性组成的集合
     */
    private void initAttrs(AttributeSet attrs) {
        mDestitle = attrs.getAttributeValue(NAMESPACE,"destitle");
        mDesoff = attrs.getAttributeValue(NAMESPACE, "desoff");
        mDeson = attrs.getAttributeValue(NAMESPACE, "deson");
    }

    /**
     * 判断CheckBox是否勾选的方法
     * @return CheckBox状态
     */
    public boolean isCheck()
    {
        return cb_box.isChecked();
    }

    /**
     * 根据CheckBox状态设置描述信息的方法
     * @param isCheck CheckBox的状态
     */
    public void setCheck(boolean isCheck)
    {
        cb_box.setChecked(isCheck);
        if(isCheck)
        {
            tv_des.setText(mDeson);
        }else {
            tv_des.setText(mDesoff);
        }

    }
}
