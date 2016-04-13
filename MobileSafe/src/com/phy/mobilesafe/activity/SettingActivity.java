package com.phy.mobilesafe.activity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import com.phy.mobilesafe.R;
import com.phy.mobilesafe.view.SettingItemView;

import android.app.Activity;
/**
 * 设置中心
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
		//从SharedPreferences取设置内容,如果为true,就自动更新
		boolean autoUpdate = mPref.getBoolean("auto_update", true);
		
		if(autoUpdate)
			// sivUpdate.setDesc("自动更新已开启");
			sivUpdate.setChecked(true);
		else
			// sivUpdate.setDesc("自动更新已关闭");
			sivUpdate.setChecked(false);
		
		sivUpdate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// 判断当前的勾选状态
				if(sivUpdate.isChecked()){
					// 设置不勾选
					sivUpdate.setChecked(false);
					// sivUpdate.setDesc("自动更新已关闭");
					// 更新sp
					mPref.edit().putBoolean("auto_update", false).commit();
				}else{
					sivUpdate.setChecked(true);
					// sivUpdate.setDesc("自动更新已开启");
					// 更新sp
					mPref.edit().putBoolean("auto_update", true).commit();
				}
			}
		});
		
		}
	
	
	
}
