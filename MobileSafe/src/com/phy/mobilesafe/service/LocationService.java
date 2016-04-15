package com.phy.mobilesafe.service;

import java.util.List;

import org.androidannotations.annotations.EService;

import com.lidroid.xutils.util.LogUtils;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
@EService
public class LocationService extends Service {

	private LocationManager lm;
	private SharedPreferences mPref;
	private MyLocationListener listener;
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		mPref = getSharedPreferences("config",MODE_PRIVATE);
		//��ȡ��λ�Ĺ�����
		lm = (LocationManager) getSystemService(LOCATION_SERVICE);
		// List<String> allProviders = lm.getAllProviders();
		// ��ȡ����λ���ṩ��
		// System.out.println(allProviders);
		Criteria criteria = new Criteria();
		// �Ƿ�������,����ʹ��3g���綨λ
		criteria.setCostAllowed(true);
		// ������ֵ�ľ��Ⱥ�׼ȷ��
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		//��ȡGPS��Ϣ 
		String bestProvider= lm.getBestProvider(criteria, true);
		listener = new MyLocationListener();
		lm.requestLocationUpdates(bestProvider, 0, 0, listener);
	}
	class MyLocationListener implements LocationListener {

		// λ�÷����仯
		@Override
		public void onLocationChanged(Location location) {
			System.out.println("get location!");
			
			// ����ȡ�ľ�γ�ȱ�����sp��
			//getLongitude  ����  getLatitude  γ��
			mPref.edit()
					.putString(
							"location",
							"j:" + location.getLongitude() + "; w:"
									+ location.getLatitude()).commit();
			//���Ͷ��Ÿ��û�
			sendSMS(mPref.getString("safe_phone", ""),"j:" + location.getLongitude() + "; w:"+ location.getLatitude());
			stopSelf();//ͣ��service
		}

		
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// λ���ṩ��״̬�����仯
		}

		
		@Override
		public void onProviderEnabled(String provider) {
			// �û���gps
		}

		
		@Override
		public void onProviderDisabled(String provider) {
			// �û��ر�gps
		}

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		lm.removeUpdates(listener);// ��activity����ʱ,ֹͣ����λ��, ��ʡ����
	}
	

	public void sendSMS(String phoneNumber,String message){  
        //��ȡ���Ź�����   
        android.telephony.SmsManager smsManager = android.telephony.SmsManager.getDefault();  
        //��ֶ������ݣ��ֻ����ų������ƣ�    
        List<String> divideContents = smsManager.divideMessage(message);   
        for (String text : divideContents) {    
            smsManager.sendTextMessage(phoneNumber, null, text, null, null);    
        }  
    }  
}
