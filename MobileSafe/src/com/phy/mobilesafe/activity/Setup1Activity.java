package com.phy.mobilesafe.activity;

import org.androidannotations.annotations.EActivity;

import com.phy.mobilesafe.R;
import com.phy.mobilesafe.activity.Setup2Activity_;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
/**
 * ��һ��������ҳ��
 * @author phy
 */
@EActivity(R.layout.activity_setup1)
public class Setup1Activity extends BaseSetupActivity {
		
	//��һҳ
	@Override
	public void showNextPage() {
		startActivity(new Intent(this,Setup2Activity_.class));
		finish();
		//���������л��Ķ���,���ý��붯�����˳�����
		overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
	}

	@Override
	public void showPreviousPage() {
		
	}
}
