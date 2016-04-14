package com.phy.mobilesafe.activity;

import com.phy.mobilesafe.R;

import android.app.Activity;
import android.content.Intent;
/**
 * 手机防盗页面
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
		//判断是否进入过设置向导
		boolean configed = mPrefs.getBoolean("configed", false);
		if(configed)
			setContentView(R.layout.activity_lost_find);
		else{
			//跳转设置页面向导
			startActivity(new Intent(this,Setup1Activity_.class));
			finish();
		}
			
	}
	
	/**
	 * 重新进入设置向导
	 * @param v
	 */
	public void reEnter(View v){
		startActivity(new Intent(this,Setup1Activity.class));
		finish();
	}
	
	
	
}
