package com.cairongcai.mobilesafe.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HY-IT on 2017/5/23.
 */

public class BlackNumberDao {

    private final BlackNumberOpenHelper blackNumberOpenHelper;
    private SQLiteDatabase db;
    private int mode;

    private BlackNumberDao(Context context)
    {
        blackNumberOpenHelper = new BlackNumberOpenHelper(context);
    }
    private static BlackNumberDao blackNumberDao=null;
    public static BlackNumberDao getInstance(Context context)
    {
        if(blackNumberDao==null)
        {
            blackNumberDao=new BlackNumberDao(context);
        }
        return blackNumberDao;
    }

    /**
     * 网黑名单数据库插入一条数据的方法
     * @param phone 号码
     * @param mode 模式
     */
    public void insert(String phone,String mode)
    {
        db = blackNumberOpenHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("phone",phone);
        values.put("mode",mode);
        db.insert("blacknumber",null,values);
        db.close();
    }

    /**
     * 从黑名单数据库删除一条数据的方法
     * @param phone
     */
    public void delete(String phone)
    {
        db=blackNumberOpenHelper.getWritableDatabase();
        db.delete("blacknumber","phone=?",new String[]{phone});
        db.close();
    }

    /**
     * 从黑名单数据库输出数据的方法
     * @param phone 根据电话号码
     * @param mode 改变的模式
     */
    public void update(String phone,String mode)
    {
        db=blackNumberOpenHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("mode",mode);
        db.update("blacknumber",values,"phone=?",new String[]{phone});
        db.close();
    }

    /**
     * 从数据库查找所有数据的方法
     * @return
     */
    public List<BlackNumberInfo> findAll()
    {
        List<BlackNumberInfo> blackNumberList=new ArrayList<BlackNumberInfo>();
        db=blackNumberOpenHelper.getWritableDatabase();
        Cursor cursor = db.query("blacknumber", new String[]{"phone","mode"}, null, null, null, null, "_id desc");
        while (cursor.moveToNext())
        {
            BlackNumberInfo blackNumberInfo=new BlackNumberInfo();
            blackNumberInfo.phone=cursor.getString(0);
            blackNumberInfo.mode=cursor.getString(1);
            blackNumberList.add(blackNumberInfo);
        }
        cursor.close();
        db.close();
        return blackNumberList;
    }
    public List<BlackNumberInfo> find(int index) {
        db = blackNumberOpenHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select phone,mode from blacknumber order by _id desc limit ?,20;", new String[]{index + ""});
        List<BlackNumberInfo> blackNumberList = new ArrayList<BlackNumberInfo>();
        while (cursor.moveToNext()) {
            BlackNumberInfo blackNumberInfo = new BlackNumberInfo();
            blackNumberInfo.phone = cursor.getString(0);
            blackNumberInfo.mode = cursor.getString(1);
            blackNumberList.add(blackNumberInfo);
        }
        cursor.close();
        db.close();
        return blackNumberList;
    }

        /**
         * @return	数据库中数据的总条目个数,返回0代表没有数据或异常
         */
        public int getCount(){
        SQLiteDatabase db = blackNumberOpenHelper.getWritableDatabase();
        int count = 0;
        Cursor cursor = db.rawQuery("select count(*) from blacknumber;", null);
        if(cursor.moveToNext()){
            count = cursor.getInt(0);
        }

        cursor.close();
        db.close();
        return count;
    }

    /**
     * 根据黑名单电话找拦截模式的方法
     * @param incomenumber 黑名单电话
     * @return 拦截模式
     */
    public int getMode(String incomenumber) {
        SQLiteDatabase sb=blackNumberOpenHelper.getWritableDatabase();
        Cursor cursor=db.query("blacknumber", new String[]{"mode"}, "phone = ?", new String[]{incomenumber}, null, null,null);
        if(cursor.moveToNext())
        {
            mode = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return mode;
    }
}

