package com.phy.mobilesafe.activity;

import com.phy.mobilesafe.R;

import android.app.Activity;
import android.content.Intent;
/**
 * �ֻ�����ҳ��
 * @author phy
 *
 */
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class LostFindActivity extends Activity {
	
	private SharedPreferences mPrefs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mPrefs = getSharedPreferences("config", MODE_PRIVATE);
		//�ж��Ƿ�����������
		boolean configed = mPrefs.getBoolean("configed", false);
		if(configed)
			setContentView(R.layout.activity_lost_find);
		else{
			//��ת����ҳ����
			startActivity(new Intent(this,Setup1Activity_.class));
			finish();
		}
			
	}
	
	/**
	 * ���½���������
	 * @param v
	 */
	public void reEnter(View v){
		startActivity(new Intent(this,Setup1Activity.class));
		finish();
	}
	
	
	
}
