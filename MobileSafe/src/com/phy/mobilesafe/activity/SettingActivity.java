package com.phy.mobilesafe.activity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import com.phy.mobilesafe.R;
import com.phy.mobilesafe.view.SettingItemView;

import android.app.Activity;
/**
 * ��������
 * 
 * @author Kevin
 * 
 */
import android.content.SharedPreferences;
import android.view.View;
import android.view.View.OnClickListener;

@EActivity(R.layout.activity_setting)
public class SettingActivity extends Activity {
	@ViewById(R.id.siv_update)
	protected SettingItemView sivUpdate;
	
	protected SharedPreferences mPref;
	
	@AfterViews
	void GetAndSetSharedPreferences(){
		mPref = getSharedPreferences("config", MODE_PRIVATE);
		//��SharedPreferencesȡ��������,���Ϊtrue,���Զ�����
		boolean autoUpdate = mPref.getBoolean("auto_update", true);
		
		if(autoUpdate)
			// sivUpdate.setDesc("�Զ������ѿ���");
			sivUpdate.setChecked(true);
		else
			// sivUpdate.setDesc("�Զ������ѹر�");
			sivUpdate.setChecked(false);
		
		sivUpdate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// �жϵ�ǰ�Ĺ�ѡ״̬
				if(sivUpdate.isChecked()){
					// ���ò���ѡ
					sivUpdate.setChecked(false);
					// sivUpdate.setDesc("�Զ������ѹر�");
					// ����sp
					mPref.edit().putBoolean("auto_update", false).commit();
				}else{
					sivUpdate.setChecked(true);
					// sivUpdate.setDesc("�Զ������ѿ���");
					// ����sp
					mPref.edit().putBoolean("auto_update", true).commit();
				}
			}
		});
		
		}
	
	
	
}
