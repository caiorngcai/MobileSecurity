package com.cairongcai.mobilesafe.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.os.RemoteException;
import android.telephony.PhoneStateListener;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;

import com.android.internal.telephony.ITelephony;
import com.cairongcai.mobilesafe.db.BlackNumberDao;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BlacknumberService extends Service {

    private BlackNumberDao mDao;

    public BlacknumberService() {
    }

    @Override
    public void onCreate() {
        //拦截短信的操作
        mDao = BlackNumberDao.getInstance(getApplicationContext());
        //服务一创建就可以监听短信，要两个关键对象--意图过滤器和广播解收者
        IntentFilter intentFilter=new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        intentFilter.setPriority(1000);
       InnerSmsReceiver innerSmsReceiver= new InnerSmsReceiver();
        registerReceiver(innerSmsReceiver,intentFilter);
        //拦截电话的操作
        TelephonyManager tm= (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        MyPhoneStateListener myPhoneStateListener=new MyPhoneStateListener();
        tm.listen(myPhoneStateListener,PhoneStateListener.LISTEN_CALL_STATE);

        super.onCreate();
    }
    class MyPhoneStateListener extends PhoneStateListener
    {
        //拦截电话的具体操作，在响铃事件来临的时候自动挂断电话
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            switch (state)
            {
                case TelephonyManager.CALL_STATE_IDLE:
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    break;
                case TelephonyManager.CALL_STATE_RINGING:
                    endCall(incomingNumber);
                    break;
            }
        }
    }
        //通过aidl文件自动断电话
    private void endCall(String phone) {
        int mode=mDao.getMode(phone);
        if(mode==2||mode==3)
        {
           // 以下只为实现这句代码
            //			ITelephony.Stub.asInterface(ServiceManager.getService(Context.TELEPHONY_SERVICE));
            try {
                //获取servicemanager字节码文件
               Class clazz=Class.forName("android.os.ServiceManager");
                //获取servicemanager中的方法对象
                Method method=clazz.getMethod("getService",String.class);
                //静态方法，传入null
                IBinder ibinder= (IBinder) method.invoke(null,Context.TELECOM_SERVICE);
                ITelephony itelephony= ITelephony.Stub.asInterface(ibinder);
                itelephony.endCall();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
             catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                }
                         catch (NoSuchMethodException e) {
                            e.printStackTrace();
                }
                         catch (ClassNotFoundException e) {
                             e.printStackTrace();
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
   class InnerSmsReceiver extends BroadcastReceiver
   {

       @Override
       public void onReceive(Context context, Intent intent) {
           //获取所有短信数据
           Object[] objects= (Object[]) intent.getExtras().get("pdus");
           for (Object objest:objects
                ) {
                    //获取短信对象
               SmsMessage sm=SmsMessage.createFromPdu((byte[]) objest);
                String incomenumber=sm.getOriginatingAddress();
               int mode=mDao.getMode(incomenumber);
               if(mode==1||mode==3)
               {    //没收掉此次广播，注意只能使用在有序广播上
                   abortBroadcast();
               }
           }
       }
   }
}
