package com.phy.mobilesafe.activity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ViewById;

import com.phy.mobilesafe.R;
import com.phy.mobilesafe.activity.LostFindActivity_;
import com.phy.mobilesafe.activity.SettingActivity_;
import com.phy.mobilesafe.utils.MD5Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

@EActivity(R.layout.activity_home)
public class HomeActivity extends Activity {
	
	@ViewById(R.id.gv_home)
	protected GridView gvHome;
	
	private SharedPreferences mPref;
	
	private String[] mItems = new String[]{ "手机防盗", "通讯卫士", "软件管理", "进程管理",
			"流量统计", "手机杀毒", "缓存清理", "高级工具", "设置中心"};
	
	private int[] mPics = new int[]{R.drawable.home_safe,R.drawable.home_callmsgsafe, R.drawable.home_apps,
			R.drawable.home_taskmanager, R.drawable.home_netmanager,
			R.drawable.home_trojan, R.drawable.home_sysoptimize,
			R.drawable.home_tools, R.drawable.home_settings};
	@AfterViews
	void updateViewWithDate() {
		mPref = getSharedPreferences("config", MODE_PRIVATE);
		HomeAdapter ha = new HomeAdapter();
		gvHome.setAdapter(ha);
		
	}
	
	//设置监听
	@ItemClick
    public void gvHomeItemClicked(int position) {
		switch (position) {
		case 0:
			// 手机防盗
			showPasswordDialog();
			break;
		case 8:
			startActivity(new Intent(HomeActivity.this,SettingActivity_.class));
			break;

		default:
			break;
		}
		
    }
	
	/**
	 * 显示密码弹窗
	 */
	protected void showPasswordDialog() {
		// 判断是否设置密码
		String savedPassword = mPref.getString("password", null);
		if(!TextUtils.isEmpty(savedPassword)){
			// 输入密码弹窗
			showPasswordInputDialog();
		}else{
			// 如果没有设置过, 弹出设置密码的弹窗
			showPasswordSetDailog();
		}
	}
	

	/**
	 * 输入密码弹窗
	 */
	private void showPasswordInputDialog() {
		//创建一个弹出对话框
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		final AlertDialog dialog = builder.create();
		View view = View.inflate(this, R.layout.dailog_input_password, null);
		// dialog.setView(view);// 将自定义的布局文件设置给dialog
		dialog.setView(view, 0, 0, 0, 0);// 设置边距为0,保证在2.x的版本上运行没问题
		
		final EditText etPassword = (EditText) view.findViewById(R.id.et_password);
		Button btnOK = (Button) view.findViewById(R.id.btn_ok);
		Button btnCancel = (Button) view.findViewById(R.id.btn_cancel);
		btnOK.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String password = etPassword.getText().toString();
				if(!TextUtils.isEmpty(password)){
					String savePasswrod = mPref.getString("password",null);
					if(MD5Utils.encode(password).equals(savePasswrod)){
						// Toast.makeText(HomeActivity.this, "登录成功!",
						// Toast.LENGTH_SHORT).show();
						dialog.dismiss();
						// 跳转到手机防盗页
						startActivity(new Intent(HomeActivity.this,LostFindActivity_.class));
					} else {
						Toast.makeText(HomeActivity.this, "密码错误!",
								Toast.LENGTH_SHORT).show();
					}
				} else {
					etPassword.setError("输入框内容不能为空!");
					Toast.makeText(HomeActivity.this, "输入框内容不能为空!",
							Toast.LENGTH_SHORT).show();
				}
				}
		});
		
		btnCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismiss();// 隐藏dialog
			}
		});
		dialog.show();
	}
	/**
	 * 设置密码弹窗
	 */
	private void showPasswordSetDailog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		final AlertDialog dialog = builder.create();

		View view = View.inflate(this, R.layout.dailog_set_password, null);
		// dialog.setView(view);// 将自定义的布局文件设置给dialog
		dialog.setView(view, 0, 0, 0, 0);// 设置边距为0,保证在2.x的版本上运行没问题

		final EditText etPassword = (EditText) view
				.findViewById(R.id.et_password);
		final EditText etPasswordConfirm = (EditText) view
				.findViewById(R.id.et_password_confirm);

		Button btnOK = (Button) view.findViewById(R.id.btn_ok);
		Button btnCancel = (Button) view.findViewById(R.id.btn_cancel);

		btnOK.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String password = etPassword.getText().toString();
				String passwordConfirm = etPasswordConfirm.getText().toString();
				// password!=null && !password.equals("")
				if (!TextUtils.isEmpty(password) && !passwordConfirm.isEmpty()) {
					if (password.equals(passwordConfirm)) {
						// Toast.makeText(HomeActivity.this, "登录成功!",
						// Toast.LENGTH_SHORT).show();

						// 将密码保存起来
						mPref.edit()
								.putString("password",
										MD5Utils.encode(password)).commit();

						dialog.dismiss();

						// 跳转到手机防盗页
						startActivity(new Intent(HomeActivity.this,
								LostFindActivity_.class));
					} else {
						Toast.makeText(HomeActivity.this, "两次密码不一致!",
								Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(HomeActivity.this, "输入框内容不能为空!",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();// 隐藏dialog
			}
		});

		dialog.show();
	}
	
	class HomeAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return mItems.length;
		}

		@Override
		public Object getItem(int position) {
			return mItems[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = View.inflate(HomeActivity.this, R.layout.home_list_item, null);
			ImageView ivItem = (ImageView) view.findViewById(R.id.iv_item);
			TextView tvItem = (TextView) view.findViewById(R.id.tv_item);
			ivItem.setImageResource(mPics[position]);
			tvItem.setText(mItems[position]);
			return view;
		}
		
	}
}
