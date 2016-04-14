package com.phy.mobilesafe.activity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import com.phy.mobilesafe.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;

/**
 * ���ĸ�����ҳ��
 * @author Administrator
 */
@EActivity(R.layout.activity_setup4)
public class Setup4Activity extends Activity {
	
	private SharedPreferences mPref;
	
	@AfterViews
	void ConfigSharedPre(){
		mPref = getSharedPreferences("config", MODE_PRIVATE);
	}
	
	//��һҳ
			public void next(View v){
				startActivity(new Intent(this,LostFindActivity.class));
				finish();
				//���������л��Ķ���,���ý��붯�����˳�����
				overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
				//����ap,��ʾ��չʾ����ҳ,�´ν���Ͳ�չʾ��
				mPref.edit().putBoolean("configed", true).commit();
				
			}
			
		//��һҳ
			public void previous(View v){
				startActivity(new Intent(this,Setup3Activity_.class));
				finish();
				//���������л��Ķ���,���ý��붯�����˳�����
				overridePendingTransition(R.anim.tran_previous_in, R.anim.tran_previous_out);
			
			}
}
