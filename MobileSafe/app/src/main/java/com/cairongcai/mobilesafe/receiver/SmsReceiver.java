package com.cairongcai.mobilesafe.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.telephony.SmsMessage;

import com.cairongcai.mobilesafe.R;
import com.cairongcai.mobilesafe.utils.ConstantValues;
import com.cairongcai.mobilesafe.utils.SPutil;

import static com.cairongcai.mobilesafe.R.*;

public class SmsReceiver extends BroadcastReceiver {
    public SmsReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
       boolean open_security= SPutil.getBoolean(context, ConstantValues.IS_OPEN_SECURITY,false);
        if(open_security)
        {
           Object[] objects= (Object[]) intent.getExtras().get("pdus");
            for (Object object :objects
                 ) {
                SmsMessage sms=SmsMessage.createFromPdu((byte[]) object);
                String address=sms.getOriginatingAddress();
                String messagebody=sms.getMessageBody();
                if(messagebody.contains("#*alarm*#"))
                {
                    MediaPlayer mediaplayer=new MediaPlayer();
                }
            }
        }
    }
}
