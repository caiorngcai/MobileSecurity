package com.cairongcai.mobilesafe.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.SmsManager;

public class LocationService extends Service {
    public LocationService() {
    }

    @Override
    public void onCreate() {
        LocationManager lm= (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria=new Criteria();
        criteria.isCostAllowed();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
       String bestProvider=lm.getBestProvider(criteria,true);
        MyLocationListener myLocationListener=new MyLocationListener();
        lm.requestLocationUpdates(bestProvider,0,0,myLocationListener);

    }
    class MyLocationListener implements LocationListener{

        @Override
        public void onLocationChanged(Location location) {
            double longitude=location.getLongitude();
            double latitude=location.getLatitude();
            SmsManager sm=SmsManager.getDefault();
            //TODO
            sm.sendTextMessage("",null,"hello",null,null);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
