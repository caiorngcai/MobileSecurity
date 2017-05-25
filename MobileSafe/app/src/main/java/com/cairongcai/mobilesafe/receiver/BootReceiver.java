package com.cairongcai.mobilesafe.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;

import com.cairongcai.mobilesafe.utils.ConstantValues;
import com.cairongcai.mobilesafe.utils.SPutil;

public class BootReceiver extends BroadcastReceiver {
    public BootReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        TelephonyManager tm= (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String simSerialNumber=tm.getSimSerialNumber();
        String sim_number= SPutil.getString(context, ConstantValues.SIM_NUMBER_BOUND,"");
        if(!sim_number.equals(simSerialNumber))
        {
            SmsManager sm=SmsManager.getDefault();
            sm.sendTextMessage("",null,"电话卡更改了",null,null);
        }
    }
}
