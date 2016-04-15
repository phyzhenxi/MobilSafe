package com.phy.mobilesafe.activity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import com.phy.mobilesafe.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

/**
 * ���ĸ�����ҳ��
 * @author Administrator
 */
@EActivity(R.layout.activity_setup4)
public class Setup4Activity extends BaseSetupActivity {
	
	@ViewById(R.id.cb_protect)
	CheckBox cbProtect;
	
	@AfterViews
	void CheckProAndSetPro(){
		boolean protect = mPref.getBoolean("protect", false);
		// ����sp�����״̬,����checkbox
		if (protect) {
			cbProtect.setText("���������Ѿ�����");
			cbProtect.setChecked(true);
		} else {
			cbProtect.setText("��������û�п���");
			cbProtect.setChecked(false);
		}
		cbProtect.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					cbProtect.setText("���������Ѿ�����");
					mPref.edit().putBoolean("protect", true).commit();
				} else {
					cbProtect.setText("��������û�п���");
					mPref.edit().putBoolean("protect", false).commit();
				}
			}
		});
	}
	
			//��һҳ
			@Override
			public void showNextPage() {
				startActivity(new Intent(this,LostFindActivity_.class));
				finish();
				//���������л��Ķ���,���ý��붯�����˳�����
				overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
				//����ap,��ʾ��չʾ����ҳ,�´ν���Ͳ�չʾ��
				mPref.edit().putBoolean("configed", true).commit();
				
			}
			//��һҳ
			@Override
			public void showPreviousPage() {
				startActivity(new Intent(this,Setup3Activity_.class));
				finish();
				//���������л��Ķ���,���ý��붯�����˳�����
				overridePendingTransition(R.anim.tran_previous_in, R.anim.tran_previous_out);
			
			}
}
