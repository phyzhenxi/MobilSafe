package com.phy.mobilesafe.activity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import com.phy.mobilesafe.R;
import com.phy.mobilesafe.utils.ToastUtils;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

/**
 * ��������ҳ
 * 
 * @author phy
 */
@EActivity(R.layout.activity_setup3)
public class Setup3Activity extends BaseSetupActivity {

	@ViewById(R.id.et_phone)
	EditText etPhone;
	
	@AfterViews
	void setEtPhoneNumber(){
		String phone = mPref.getString("safe_phone", "");
		etPhone.setText(phone);
	}
	
	// ��һҳ
	@Override
	public void showNextPage() {
		String phone = etPhone.getText().toString().trim();
		if(TextUtils.isEmpty(phone)){
			ToastUtils.showToast(this, "��ȫ���벻��Ϊ��!");
			return;
		}
		mPref.edit().putString("safe_phone", phone).commit();
		startActivity(new Intent(this, Setup4Activity_.class));
		finish();
		// ���������л��Ķ���,���ý��붯�����˳�����
		overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
	}

	// ��һҳ
	@Override
	public void showPreviousPage() {
		startActivity(new Intent(this, Setup2Activity_.class));
		finish();
		// ���������л��Ķ���,���ý��붯�����˳�����
		overridePendingTransition(R.anim.tran_previous_in, R.anim.tran_previous_out);

	}
	/**
	 * ѡ����ϵ��
	 * @param v
	 */
	public void selectContact(View v){
		Intent intent = new Intent(this,ContactActivity_.class);
		startActivityForResult(intent, 1);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode==Activity.RESULT_OK){
			String phone = data.getStringExtra("phone");
			// �滻-�Ϳո�
			phone = phone.replaceAll("-", "").replaceAll(" ", "");
			etPhone.setText(phone);// �ѵ绰�������ø������
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
