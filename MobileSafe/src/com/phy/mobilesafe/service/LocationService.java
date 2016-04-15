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
		//获取定位的管理者
		lm = (LocationManager) getSystemService(LOCATION_SERVICE);
		// List<String> allProviders = lm.getAllProviders();
		// 获取所有位置提供者
		// System.out.println(allProviders);
		Criteria criteria = new Criteria();
		// 是否允许付费,比如使用3g网络定位
		criteria.setCostAllowed(true);
		// 重设数值的精度和准确度
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		//获取GPS信息 
		String bestProvider= lm.getBestProvider(criteria, true);
		listener = new MyLocationListener();
		lm.requestLocationUpdates(bestProvider, 0, 0, listener);
	}
	class MyLocationListener implements LocationListener {

		// 位置发生变化
		@Override
		public void onLocationChanged(Location location) {
			System.out.println("get location!");
			
			// 将获取的经纬度保存在sp中
			//getLongitude  经度  getLatitude  纬度
			mPref.edit()
					.putString(
							"location",
							"j:" + location.getLongitude() + "; w:"
									+ location.getLatitude()).commit();
			//发送短信给用户
			sendSMS(mPref.getString("safe_phone", ""),"j:" + location.getLongitude() + "; w:"+ location.getLatitude());
			stopSelf();//停掉service
		}

		
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// 位置提供者状态发生变化
		}

		
		@Override
		public void onProviderEnabled(String provider) {
			// 用户打开gps
		}

		
		@Override
		public void onProviderDisabled(String provider) {
			// 用户关闭gps
		}

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		lm.removeUpdates(listener);// 当activity销毁时,停止更新位置, 节省电量
	}
	

	public void sendSMS(String phoneNumber,String message){  
        //获取短信管理器   
        android.telephony.SmsManager smsManager = android.telephony.SmsManager.getDefault();  
        //拆分短信内容（手机短信长度限制）    
        List<String> divideContents = smsManager.divideMessage(message);   
        for (String text : divideContents) {    
            smsManager.sendTextMessage(phoneNumber, null, text, null, null);    
        }  
    }  
}
