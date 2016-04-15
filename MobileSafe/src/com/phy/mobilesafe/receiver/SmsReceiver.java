package com.phy.mobilesafe.receiver;

import java.util.List;

import org.androidannotations.annotations.EReceiver;

import com.lidroid.xutils.util.LogUtils;
import com.phy.mobilesafe.R;
import com.phy.mobilesafe.service.LocationService_;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.telephony.SmsMessage;
/**
 * ���ض���
 * @author phy
 */
@EReceiver
public class SmsReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		//��intent�л�ȡpdus��ʽ�Ķ�������
		Object[] objects = (Object[]) intent.getExtras().get("pdus");
		// �������140�ֽ�,
		// �����Ļ�,���Ϊ�������ŷ���,������һ������,��Ϊ���ǵĶ���ָ��ܶ�,����forѭ��ִֻ��һ��
		for (Object object : objects) {
			SmsMessage message = SmsMessage.createFromPdu((byte[]) object);
			//������Դ����
			String originationAddress = message.getOriginatingAddress();
			String messageBody = message.getMessageBody();// ��������
			LogUtils.i(originationAddress+":"+messageBody);
			
			if("#*alarm*#".equals(messageBody)){
				// ���ű�������, ��ʹ�ֻ���Ϊ����,Ҳ�ܲ�������, ��Ϊʹ�õ���ý��������ͨ��,�������޹�
				MediaPlayer player = MediaPlayer.create(context, R.raw.sgile);
				player.setVolume(1f, 1f);
				player.setLooping(true);
				player.start();
				// �ж϶��ŵĴ���, �Ӷ�ϵͳ����app���ղ���������
				abortBroadcast();
			}else if("#*location*#".equals(messageBody)){
				// ��ȡ��γ������,������λ����
				context.startService(new Intent(context,LocationService_.class));
				SharedPreferences sp = context.getSharedPreferences("config",
						Context.MODE_PRIVATE);
				String location = sp.getString("location",
						"getting location...");
				System.out.println("location:" + location);
				
				abortBroadcast();// �ж϶��ŵĴ���, �Ӷ�ϵͳ����app���ղ���������
			}else if ("#*wipedata*#".equals(messageBody)) {
				System.out.println("Զ���������");
				abortBroadcast();
			} else if ("#*lockscreen*#".equals(messageBody)) {
				System.out.println("Զ������");
				abortBroadcast();
			}
		}
	}
	

}
