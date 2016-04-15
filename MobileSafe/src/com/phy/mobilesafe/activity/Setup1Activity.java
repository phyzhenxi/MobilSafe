package com.phy.mobilesafe.activity;

import org.androidannotations.annotations.EActivity;

import com.phy.mobilesafe.R;
import com.phy.mobilesafe.activity.Setup2Activity_;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
/**
 * 第一个设置向导页面
 * @author phy
 */
@EActivity(R.layout.activity_setup1)
public class Setup1Activity extends BaseSetupActivity {
		
	//下一页
	@Override
	public void showNextPage() {
		startActivity(new Intent(this,Setup2Activity_.class));
		finish();
		//两个界面切换的动画,设置进入动画和退出动画
		overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
	}

	@Override
	public void showPreviousPage() {
		
	}
}
