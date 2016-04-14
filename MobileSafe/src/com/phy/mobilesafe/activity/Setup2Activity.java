package com.phy.mobilesafe.activity;

import org.androidannotations.annotations.EActivity;

import com.phy.mobilesafe.R;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

/**
 * 第二个设置向导页
 * @author phy
 *
 */
@EActivity(R.layout.activity_setup2)
public class Setup2Activity extends Activity {

	//下一页
		public void next(View v){
			startActivity(new Intent(this,Setup3Activity_.class));
			finish();
			//两个界面切换的动画,设置进入动画和退出动画
			overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
		}
		
	//上一页
		public void previous(View v){
			startActivity(new Intent(this,Setup1Activity_.class));
			finish();
			//两个界面切换的动画,设置进入动画和退出动画
			overridePendingTransition(R.anim.tran_previous_in, R.anim.tran_previous_out);
		
		}
}
