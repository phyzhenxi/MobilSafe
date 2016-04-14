package com.phy.mobilesafe.receiver;

import com.lidroid.xutils.util.LogUtils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
/**
 * 监听手机开机启动的广播
 * @author phy
 */
public class BootCompleteReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		SharedPreferences sp = context.getSharedPreferences("config", context.MODE_PRIVATE);
		boolean protect = sp.getBoolean("protect", false);
		//只有在防盗保护开启下才能进行sim卡判断
		if(protect){
			String sim = sp.getString("sim", null);
			if(!TextUtils.isEmpty(sim)){
				//获取当前手机的sim卡
				TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
				String currentSim = tm.getSimSerialNumber(); //获取当前sim卡信息
				if(sim.equals(currentSim)){
					LogUtils.i("手机安全");
				}else{
					LogUtils.i("sim卡已变化,发送报警短信");
					String phone = sp.getString("safe_phone", "");
					//发送短信给安全号码
					SmsManager smsManager = SmsManager.getDefault();
					smsManager.sendTextMessage(phone, null, "sim已变更", null, null);
				}
			}
		}
	}

}
