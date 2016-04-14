package com.phy.mobilesafe.activity;

import org.androidannotations.annotations.EActivity;

import com.phy.mobilesafe.R;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

/**
 * ��������ҳ
 * @author phy
 */
@EActivity(R.layout.activity_setup3)
public class Setup3Activity extends BaseSetupActivity {
	//��һҳ
			@Override
			public void showNextPage() {
				startActivity(new Intent(this,Setup4Activity_.class));
				finish();
				//���������л��Ķ���,���ý��붯�����˳�����
				overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
			}
			//��һҳ
			@Override
			public void showPreviousPage() {
				startActivity(new Intent(this,Setup2Activity_.class));
				finish();
				//���������л��Ķ���,���ý��붯�����˳�����
				overridePendingTransition(R.anim.tran_previous_in, R.anim.tran_previous_out);
			
			}
}
