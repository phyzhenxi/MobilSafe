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
 * �ڶ���������ҳ
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
					//����sim����Ϣ
					TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
					String simSerialNumber = tm.getSimOperatorName(); //��ȡsim�����к�
					LogUtils.i("sim�����к�Ϊ:"+simSerialNumber);
					mPref.edit().putString("sim", simSerialNumber).commit();//����sim�����к�
				}
			}
		});
	}
	
	
	
	//��һҳ
		@Override
		public void showNextPage() {
			//���simû�а�,�Ͳ����������һ������
			String sim = mPref.getString("sim", null);
			if(TextUtils.isEmpty(sim)){
				ToastUtils.showToast(this, "�����sim��!");
				return ;
			}
			startActivity(new Intent(this,Setup3Activity_.class));
			finish();
			//���������л��Ķ���,���ý��붯�����˳�����
			overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
		}
		//��һҳ
		@Override
		public void showPreviousPage() {
			startActivity(new Intent(this,Setup1Activity_.class));
			finish();
			//���������л��Ķ���,���ý��붯�����˳�����
			overridePendingTransition(R.anim.tran_previous_in, R.anim.tran_previous_out);
		
		}
}
