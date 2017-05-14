package com.cairongcai.mobilesafe.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HY-IT on 2017/5/2.
 */

public class ContactQueryUtil {
    public static List<Contact> querycontact(Context context)
    {
        List<Contact> contactList=new ArrayList<Contact>();
        //从上层源代码中获取uri
        Uri contactnumuri = Uri.parse("content://com.android.contacts/raw_contacts");
        Uri dataUri = Uri.parse("content://com.android.contacts/data");
        //从联系人数据库中获得游标对象
        Cursor cursor=context.getContentResolver().query(contactnumuri,new String[]{"contact_id"},null,null,null);
        //开始遍历存取数据
        while (cursor.moveToNext())
        {
            String contact_id=cursor.getString(0);
            if(contact_id!=null)
            {
                Contact contact=new Contact();
                contact.setId(contact_id);

                Cursor datacursor = context.getContentResolver().query(dataUri, new String[]{"data1","mimetype"}, "raw_contact_id=?", new String[]{contact_id}, null);
                while (datacursor.moveToNext())
                {
                    String data1=datacursor.getString(0);
                    String mimetype=datacursor.getString(1);
                    if ("vnd.android.cursor.item/name".equals(mimetype)) {
                        contact.setName(data1);
                    }else if ("vnd.android.cursor.item/phone_v2".equals(mimetype)) {
                        contact.setPhone(data1);
                    }
                }
                contactList.add(contact);
            }

        }
        return contactList;
    }
}
