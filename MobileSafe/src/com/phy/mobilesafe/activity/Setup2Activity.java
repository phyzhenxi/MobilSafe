package com.phy.mobilesafe.activity;

import org.androidannotations.annotations.EActivity;

import com.phy.mobilesafe.R;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

/**
 * �ڶ���������ҳ
 * @author phy
 *
 */
@EActivity(R.layout.activity_setup2)
public class Setup2Activity extends Activity {

	//��һҳ
		public void next(View v){
			startActivity(new Intent(this,Setup3Activity_.class));
			finish();
			//���������л��Ķ���,���ý��붯�����˳�����
			overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
		}
		
	//��һҳ
		public void previous(View v){
			startActivity(new Intent(this,Setup1Activity_.class));
			finish();
			//���������л��Ķ���,���ý��붯�����˳�����
			overridePendingTransition(R.anim.tran_previous_in, R.anim.tran_previous_out);
		
		}
}
