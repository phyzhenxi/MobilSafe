package com.phy.mobilesafe.activity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import com.phy.mobilesafe.R;
import com.phy.mobilesafe.activity.Setup1Activity_;

import android.app.Activity;
import android.content.Intent;
/**
 * �ֻ�����ҳ��
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
		//�ж��Ƿ�����������
		boolean configed = mPrefs.getBoolean("configed", false);
		if(configed){
			//����sp���°�ȫ����
			String phone = mPrefs.getString("safe_phone", "");
			tvSafePhone.setText(phone);
			//����sp���±�����״̬
			boolean protect = mPrefs.getBoolean("protect", false);
			if(protect){
				ivProtect.setImageResource(R.drawable.lock);
			}else{
				ivProtect.setImageResource(R.drawable.unlock);
			}
		}else{
			//��ת����ҳ����
			startActivity(new Intent(this,Setup1Activity_.class));
			finish();
		}
	}
	
	/**
	 * ���½���������
	 * @param v
	 */
	public void reEnter(View v){
		startActivity(new Intent(this,Setup1Activity_.class));
		finish();
	}
	
	
	
}
