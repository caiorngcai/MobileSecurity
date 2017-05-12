package com.cairongcai.mobilesafe.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by HY-IT on 2017/5/12.
 */

public class MD5Util {

    public static String encoder(String psd)
    {
        try {
            psd=psd+"cairongcai";
            MessageDigest md5=MessageDigest.getInstance("MD5");
            byte[] bs=md5.digest(psd.getBytes());
            StringBuffer buffer=new StringBuffer();
            for (byte b :
                    bs) {
                int i = b &0xff;
               String hexstring= Integer.toHexString(i);
                if(hexstring.length()<2)
                {
                    hexstring="0"+hexstring;
                }
                buffer.append(hexstring);
            }
            return buffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
