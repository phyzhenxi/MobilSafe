package com.phy.mobilesafe.activity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import com.lidroid.xutils.util.LogUtils;
import com.phy.mobilesafe.R;
import com.phy.mobilesafe.activity.Setup1Activity_;
import com.phy.mobilesafe.activity.Setup3Activity_;
import com.phy.mobilesafe.utils.ToastUtils;
import com.phy.mobilesafe.view.SettingItemView;

import android.app.Activity;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 第二个设置向导页
 * @author phy
 *
 */
@EActivity(R.layout.activity_setup2)
public class Setup2Activity extends BaseSetupActivity {

	@ViewById(R.id.siv_sim)
	protected SettingItemView sivSim;
	
	@AfterViews
	void saveSimCheck(){
		String sim = mPref.getString("sim", null);
		if(!TextUtils.isEmpty(sim)){
			sivSim.setChecked(true);
		}else{
			sivSim.setChecked(false);
		}
		
		sivSim.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(sivSim.isChecked()){
					sivSim.setChecked(false);
					mPref.edit().remove("sim").commit();
				}else{
					sivSim.setChecked(true);
					//保存sim卡信息
					TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
					String simSerialNumber = tm.getSimOperatorName(); //获取sim卡序列号
					LogUtils.i("sim卡序列号为:"+simSerialNumber);
					mPref.edit().putString("sim", simSerialNumber).commit();//保存sim卡序列号
				}
			}
		});
	}
	
	
	
	//下一页
		@Override
		public void showNextPage() {
			//如果sim没有绑定,就不允许进入下一个界面
			String sim = mPref.getString("sim", null);
			if(TextUtils.isEmpty(sim)){
				ToastUtils.showToast(this, "必须绑定sim卡!");
				return ;
			}
			startActivity(new Intent(this,Setup3Activity_.class));
			finish();
			//两个界面切换的动画,设置进入动画和退出动画
			overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
		}
		//上一页
		@Override
		public void showPreviousPage() {
			startActivity(new Intent(this,Setup1Activity_.class));
			finish();
			//两个界面切换的动画,设置进入动画和退出动画
			overridePendingTransition(R.anim.tran_previous_in, R.anim.tran_previous_out);
		
		}
}
