package com.phy.mobilesafe.activty;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.phy.mobilesafe.R;
import com.phy.mobilesafe.utils.StreamUtils;
@EActivity(R.layout.activity_main)
public class SplashActivity extends Activity {

	
	protected static final int CODE_UPDATE_DIALOG = 0;
	protected static final int CODE_URL_ERROR = 1;
	protected static final int CODE_NET_ERROR = 2;
	protected static final int CODE_JSON_ERROR = 3;

	@ViewById(R.id.text_Version)
	public TextView tvVersion;

	private String mVersionName; //�汾��
	protected int mVersionCode;  //�汾��

	protected String mDesc;//�汾����

	protected Object mDownloadUrl; //���ص�ַ
	
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case CODE_UPDATE_DIALOG:
				showUpdateDailog();
				break;
			case CODE_URL_ERROR:
				Toast.makeText(SplashActivity.this, "url����", Toast.LENGTH_SHORT).show();
				break;
			case CODE_NET_ERROR:
				Toast.makeText(SplashActivity.this, "�������", Toast.LENGTH_SHORT).show();
				break;
			case CODE_JSON_ERROR:
				Toast.makeText(SplashActivity.this, "���ݽ�������", Toast.LENGTH_SHORT).show();
				break;

			default:
				break;
			}
		};
	};
	
	@AfterViews
    void ShowAndCheckVersion() {
		tvVersion.setText("�汾��:"+getVersionName());
		checkVersion();
		}
	
	
	/**
	 * ��ȡ��ǰ�������ļ��İ汾����
	 * @return
	 */
	private String getVersionName(){
		PackageManager packageManager = getPackageManager();
		try {
			//��ȡ������Ϣ
			PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
			
			//int versionCode = packageInfo.versionCode;
			String versionName = packageInfo.versionName;
			return versionName;
		} catch (NameNotFoundException e) {
			//û���ҵ�������ʱ����ߴ��쳣
			e.printStackTrace();
		}
		return "";
	}
	
	
	/**
	 * ��ȡ��ǰ�������ļ��İ汾��
	 * @return
	 */
	private int getVersionCode(){
		PackageManager packageManager = getPackageManager();
		try {
			//��ȡ������Ϣ
			PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
			
			//int versionCode = packageInfo.versionCode;
			int versionCode = packageInfo.versionCode;
			return versionCode;
		} catch (NameNotFoundException e) {
			//û���ҵ�������ʱ����ߴ��쳣
			e.printStackTrace();
		}
		return -1;
	}
	
	/**
	 * �ӷ�������ȡ�汾��Ϣ����У��
	 * @return
	 */
	private String checkVersion(){
		
		new Thread(){
			

			@Override
			public void run() {
				Message msg = Message.obtain(); //��ȡһ����Ϣ
				HttpURLConnection conn = null;
				try {
			//������ַ��Localhost�����ģ�������ر���PC��ַ����Ҫ��10.0.2.2���滻
			//���̳߳���5������������Բ��������߳��з���������Ϣ
			URL url = new URL("http://192.168.1.145:8080/update.json");
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET"); //��������ʽ
			conn.setConnectTimeout(5000); //�������ӳ�ʱ��ʱ��
			conn.setReadTimeout(5000);  //������Ӧ��ʱʱ�䣬�������ˣ��������ٳٲ�����Ӧ
			conn.connect();  //���ӷ�����
			
			int responseCode = conn.getResponseCode(); //��ȡ��Ӧ��
			if(responseCode==200){
				InputStream inputStream = conn.getInputStream(); //��ȡ��Ӧ������
				String result = StreamUtils.readFromStream(inputStream); //��������ת����String
				
				//����json
			JSONObject jo = new JSONObject(result);
			mVersionName = jo.getString("versionName");
			mVersionCode = jo.getInt("versionCode");
			mDesc = jo.getString("description");
			mDownloadUrl = jo.get("downloadUrl");
			Log.i("result", mVersionName+mVersionCode+mDesc+mDownloadUrl);
			if(mVersionCode>getVersionCode()){ //�ж��Ƿ��и���
				//��������VersionCode���ڱ��ص�VersionCode
				//˵���и��£����������Ի���
				msg.what = CODE_UPDATE_DIALOG;
				
			}
			
			}
		}catch (JSONException e) {
			msg.what = CODE_JSON_ERROR;
			e.printStackTrace();
		}catch (MalformedURLException e) {
			msg.what = CODE_NET_ERROR;
			//url�����쳣
			e.printStackTrace();
		} catch (IOException e) {
			msg.what = CODE_URL_ERROR;
			//��������쳣
			e.printStackTrace();
		}finally{
			mHandler.sendMessage(msg);
			if(conn!=null){
				conn.disconnect(); //�ر���������
			}
		}
		}
		}.start();
		return "";
	}

	/**
	 * ��ʾ�����Ի���
	 */
	protected void showUpdateDailog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("���°汾:"+mVersionName);
		builder.setMessage(mDesc);
		builder.setPositiveButton("���̸���", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Log.i("update", "��������");
			}
		});
		builder.setNegativeButton("�Ժ����", null);
		builder.create().show();
	}

}
