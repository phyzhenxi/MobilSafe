package com.phy.mobilesafe.activity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import com.phy.mobilesafe.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;

/**
 * 第四个引导页面
 * @author Administrator
 */
@EActivity(R.layout.activity_setup4)
public class Setup4Activity extends BaseSetupActivity {
	
	private SharedPreferences mPref;
	
	@AfterViews
	void ConfigSharedPre(){
		mPref = getSharedPreferences("config", MODE_PRIVATE);
	}
	
			//下一页
			@Override
			public void showNextPage() {
				startActivity(new Intent(this,LostFindActivity_.class));
				finish();
				//两个界面切换的动画,设置进入动画和退出动画
				overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
				//更新ap,表示已展示过向导页,下次进入就不展示了
				mPref.edit().putBoolean("configed", true).commit();
				
			}
			//上一页
			@Override
			public void showPreviousPage() {
				startActivity(new Intent(this,Setup3Activity_.class));
				finish();
				//两个界面切换的动画,设置进入动画和退出动画
				overridePendingTransition(R.anim.tran_previous_in, R.anim.tran_previous_out);
			
			}
}
