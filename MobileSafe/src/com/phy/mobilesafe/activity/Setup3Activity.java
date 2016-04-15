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
 * 第三个向导页
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
	
	// 下一页
	@Override
	public void showNextPage() {
		String phone = etPhone.getText().toString().trim();
		if(TextUtils.isEmpty(phone)){
			ToastUtils.showToast(this, "安全号码不能为空!");
			return;
		}
		mPref.edit().putString("safe_phone", phone).commit();
		startActivity(new Intent(this, Setup4Activity_.class));
		finish();
		// 两个界面切换的动画,设置进入动画和退出动画
		overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
	}

	// 上一页
	@Override
	public void showPreviousPage() {
		startActivity(new Intent(this, Setup2Activity_.class));
		finish();
		// 两个界面切换的动画,设置进入动画和退出动画
		overridePendingTransition(R.anim.tran_previous_in, R.anim.tran_previous_out);

	}
	/**
	 * 选择联系人
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
			// 替换-和空格
			phone = phone.replaceAll("-", "").replaceAll(" ", "");
			etPhone.setText(phone);// 把电话号码设置给输入框
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
