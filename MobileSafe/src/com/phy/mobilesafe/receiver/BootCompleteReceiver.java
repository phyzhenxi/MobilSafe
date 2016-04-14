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
 * �����ֻ����������Ĺ㲥
 * @author phy
 */
public class BootCompleteReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		SharedPreferences sp = context.getSharedPreferences("config", context.MODE_PRIVATE);
		boolean protect = sp.getBoolean("protect", false);
		//ֻ���ڷ������������²��ܽ���sim���ж�
		if(protect){
			String sim = sp.getString("sim", null);
			if(!TextUtils.isEmpty(sim)){
				//��ȡ��ǰ�ֻ���sim��
				TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
				String currentSim = tm.getSimSerialNumber(); //��ȡ��ǰsim����Ϣ
				if(sim.equals(currentSim)){
					LogUtils.i("�ֻ���ȫ");
				}else{
					LogUtils.i("sim���ѱ仯,���ͱ�������");
					String phone = sp.getString("safe_phone", "");
					//���Ͷ��Ÿ���ȫ����
					SmsManager smsManager = SmsManager.getDefault();
					smsManager.sendTextMessage(phone, null, "sim�ѱ��", null, null);
				}
			}
		}
	}

}
