package com.phy.mobilesafe.activity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import com.phy.mobilesafe.R;
import com.phy.mobilesafe.activity.Setup1Activity_;

import android.app.Activity;
import android.content.Intent;
/**
 * 手机防盗页面
 * @author phy
 *
 */
import android.content.SharedPreferences;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
@EActivity(R.layout.activity_lost_find)
public class LostFindActivity extends Activity {
	
	private SharedPreferences mPrefs;
	@ViewById(R.id.tv_safe_phone)
	protected TextView tvSafePhone;
	
	@ViewById(R.id.iv_protect)
	protected ImageView ivProtect;
	
	@AfterViews
	void readAndConfigShared(){
		mPrefs = getSharedPreferences("config", MODE_PRIVATE);
		//判断是否进入过设置向导
		boolean configed = mPrefs.getBoolean("configed", false);
		if(configed){
			//根据sp更新安全号码
			String phone = mPrefs.getString("safe_phone", "");
			tvSafePhone.setText(phone);
			//根据sp更新保护锁状态
			boolean protect = mPrefs.getBoolean("protect", false);
			if(protect){
				ivProtect.setImageResource(R.drawable.lock);
			}else{
				ivProtect.setImageResource(R.drawable.unlock);
			}
		}else{
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
		startActivity(new Intent(this,Setup1Activity_.class));
		finish();
	}
	
	
	
}
