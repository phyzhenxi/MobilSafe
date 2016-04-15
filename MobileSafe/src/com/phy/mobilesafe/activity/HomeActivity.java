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
	
	private String[] mItems = new String[]{ "�ֻ�����", "ͨѶ��ʿ", "�������", "���̹���",
			"����ͳ��", "�ֻ�ɱ��", "��������", "�߼�����", "��������"};
	
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
	
	//���ü���
	@ItemClick
    public void gvHomeItemClicked(int position) {
		switch (position) {
		case 0:
			// �ֻ�����
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
	 * ��ʾ���뵯��
	 */
	protected void showPasswordDialog() {
		// �ж��Ƿ���������
		String savedPassword = mPref.getString("password", null);
		if(!TextUtils.isEmpty(savedPassword)){
			// �������뵯��
			showPasswordInputDialog();
		}else{
			// ���û�����ù�, ������������ĵ���
			showPasswordSetDailog();
		}
	}
	

	/**
	 * �������뵯��
	 */
	private void showPasswordInputDialog() {
		//����һ�������Ի���
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		final AlertDialog dialog = builder.create();
		View view = View.inflate(this, R.layout.dailog_input_password, null);
		// dialog.setView(view);// ���Զ���Ĳ����ļ����ø�dialog
		dialog.setView(view, 0, 0, 0, 0);// ���ñ߾�Ϊ0,��֤��2.x�İ汾������û����
		
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
						// Toast.makeText(HomeActivity.this, "��¼�ɹ�!",
						// Toast.LENGTH_SHORT).show();
						dialog.dismiss();
						// ��ת���ֻ�����ҳ
						startActivity(new Intent(HomeActivity.this,LostFindActivity_.class));
					} else {
						Toast.makeText(HomeActivity.this, "�������!",
								Toast.LENGTH_SHORT).show();
					}
				} else {
					etPassword.setError("��������ݲ���Ϊ��!");
					Toast.makeText(HomeActivity.this, "��������ݲ���Ϊ��!",
							Toast.LENGTH_SHORT).show();
				}
				}
		});
		
		btnCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismiss();// ����dialog
			}
		});
		dialog.show();
	}
	/**
	 * �������뵯��
	 */
	private void showPasswordSetDailog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		final AlertDialog dialog = builder.create();

		View view = View.inflate(this, R.layout.dailog_set_password, null);
		// dialog.setView(view);// ���Զ���Ĳ����ļ����ø�dialog
		dialog.setView(view, 0, 0, 0, 0);// ���ñ߾�Ϊ0,��֤��2.x�İ汾������û����

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
						// Toast.makeText(HomeActivity.this, "��¼�ɹ�!",
						// Toast.LENGTH_SHORT).show();

						// �����뱣������
						mPref.edit()
								.putString("password",
										MD5Utils.encode(password)).commit();

						dialog.dismiss();

						// ��ת���ֻ�����ҳ
						startActivity(new Intent(HomeActivity.this,
								LostFindActivity_.class));
					} else {
						Toast.makeText(HomeActivity.this, "�������벻һ��!",
								Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(HomeActivity.this, "��������ݲ���Ϊ��!",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();// ����dialog
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
