package com.cairongcai.mobilesafe.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by HY-IT on 2017/5/9.
 */

public class StreamUtil {
    /**
     * 把服务器返回的流转化为字符串的方法
     * @param is 返回的流
     * @return 转化后的字符串
     */
    public static String stream2string(InputStream is)
    {
        ByteArrayOutputStream bos=new ByteArrayOutputStream();
        byte buffer[]=new byte[1024];
        int temp=-1;
        try {
            while((temp=is.read(buffer))!=-1)
            {
                bos.write(buffer,0,temp);
            }
            return bos.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                is.close();
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
