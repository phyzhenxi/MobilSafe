package com.phy.mobilesafe.activity;

import org.androidannotations.annotations.EActivity;

import com.phy.mobilesafe.R;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

/**
 * 第三个向导页
 * @author phy
 */
@EActivity(R.layout.activity_setup3)
public class Setup3Activity extends BaseSetupActivity {
	//下一页
			@Override
			public void showNextPage() {
				startActivity(new Intent(this,Setup4Activity_.class));
				finish();
				//两个界面切换的动画,设置进入动画和退出动画
				overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
			}
			//上一页
			@Override
			public void showPreviousPage() {
				startActivity(new Intent(this,Setup2Activity_.class));
				finish();
				//两个界面切换的动画,设置进入动画和退出动画
				overridePendingTransition(R.anim.tran_previous_in, R.anim.tran_previous_out);
			
			}
}
