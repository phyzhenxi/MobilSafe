package com.phy.mobilesafe.activity;

import java.io.File;
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
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.phy.mobilesafe.R;
import com.phy.mobilesafe.utils.StreamUtils;
@EActivity(R.layout.activity_splash)
public class SplashActivity extends Activity {

	
	protected static final int CODE_UPDATE_DIALOG = 0;
	protected static final int CODE_URL_ERROR = 1;
	protected static final int CODE_NET_ERROR = 2;
	protected static final int CODE_JSON_ERROR = 3;
	protected static final int CODE_ENTER_HOME = 4; //进入主页面

	
	@ViewById(R.id.text_Version)
	public TextView tvVersion;
	
	@ViewById(R.id.tv_progress)
	public TextView tvProgress; //下载进度展示

	private String mVersionName; //版本名
	protected int mVersionCode;  //版本号

	protected String mDesc;//版本描述

	protected String mDownloadUrl; //下载地址
	
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case CODE_UPDATE_DIALOG:
				showUpdateDailog();
				break;
			case CODE_URL_ERROR:
				Toast.makeText(SplashActivity.this, "url错误", Toast.LENGTH_SHORT).show();
				enterHome();
				break;
			case CODE_NET_ERROR:
				Toast.makeText(SplashActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
				enterHome();
				break;
			case CODE_JSON_ERROR:
				Toast.makeText(SplashActivity.this, "数据解析错误", Toast.LENGTH_SHORT).show();
				enterHome();
				break;
			case CODE_ENTER_HOME:
				enterHome();
				break;
			default:
				break;
			}
		};
	};
	
	@AfterViews
    void ShowAndCheckVersion() {
		tvVersion.setText("版本号:"+getVersionName());
		checkVersion();
		}
	
	
	/**
	 * 获取当前主配置文件的版本名称
	 * @return
	 */
	private String getVersionName(){
		PackageManager packageManager = getPackageManager();
		try {
			//获取包的信息
			PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
			
			//int versionCode = packageInfo.versionCode;
			String versionName = packageInfo.versionName;
			return versionName;
		} catch (NameNotFoundException e) {
			//没有找到包名的时候会走此异常
			e.printStackTrace();
		}
		return "";
	}
	
	
	/**
	 * 获取当前主配置文件的版本号
	 * @return
	 */
	private int getVersionCode(){
		PackageManager packageManager = getPackageManager();
		try {
			//获取包的信息
			PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
			
			//int versionCode = packageInfo.versionCode;
			int versionCode = packageInfo.versionCode;
			return versionCode;
		} catch (NameNotFoundException e) {
			//没有找到包名的时候会走此异常
			e.printStackTrace();
		}
		return -1;
	}
	
	/**
	 * 从服务器获取版本信息进行校验
	 * @return
	 */
	private String checkVersion(){
		
		final long startTime = System.currentTimeMillis();
		
		new Thread(){
			

			@Override
			public void run() {
				Message msg = Message.obtain(); //获取一个消息
				HttpURLConnection conn = null;
				try {
			//本机地址用Localhost，如果模拟器加载本机PC地址，需要用10.0.2.2来替换
			//主线程超过5秒会阻塞，所以不能再主线程中访问网络信息
			URL url = new URL("http://10.0.2.2:8080/update.json");
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET"); //设置请求方式
			conn.setConnectTimeout(5000); //设置连接超时的时间
			conn.setReadTimeout(5000);  //设置相应超时时间，连接上了，服务器迟迟不给相应
			conn.connect();  //连接服务器
			
			int responseCode = conn.getResponseCode(); //获取相应码
			if(responseCode==200){
				InputStream inputStream = conn.getInputStream(); //获取相应输入流
				String result = StreamUtils.readFromStream(inputStream); //将输入流转换成String
				
				//解析json
			JSONObject jo = new JSONObject(result);
			mVersionName = jo.getString("versionName");
			mVersionCode = jo.getInt("versionCode");
			mDesc = jo.getString("description");
			mDownloadUrl = jo.getString("downloadUrl");
			Log.i("result", mVersionName+mVersionCode+mDesc+mDownloadUrl);
			if(mVersionCode>getVersionCode()){ //判断是否有更新
				//服务器的VersionCode大于本地的VersionCode
				//说明有更新，弹出升级对话框
				msg.what = CODE_UPDATE_DIALOG;
				
			}else{
				//没有版本更新
				msg.what = CODE_ENTER_HOME;
			}
			
			}
		}catch (JSONException e) {
			msg.what = CODE_JSON_ERROR;
			e.printStackTrace();
		}catch (MalformedURLException e) {
			msg.what = CODE_NET_ERROR;
			//url错误异常
			e.printStackTrace();
		} catch (IOException e) {
			msg.what = CODE_URL_ERROR;
			//网络错误异常
			e.printStackTrace();
		}finally{
			//强制休眠一段时间，保证闪屏页展示2s
			long endTime = System.currentTimeMillis();
			long timeUsed = endTime-startTime;
			if(timeUsed<2000){
				try {
					Thread.sleep(2000-timeUsed);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			mHandler.sendMessage(msg);
			if(conn!=null){
				conn.disconnect(); //关闭网络连接
			}
		}
		}
		}.start();
		return "";
	}

	/**
	 * 显示升级对话框
	 */
	protected void showUpdateDailog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("最新版本:"+mVersionName);
		builder.setMessage(mDesc);
		builder.setPositiveButton("立刻更新", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Log.i("update", "立即更新");
				downloadApk();
			}
		});
		builder.setNegativeButton("以后更新", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				enterHome();
			}
		});
		builder.create().show();
	}
	
	/**
	 * 下载apk文件，更新
	 * 
	 */
	protected void downloadApk() {
		if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){ //判断SD卡是否存在
			Toast.makeText(SplashActivity.this, "无SD卡", Toast.LENGTH_SHORT).show();
		}
		tvProgress.setVisibility(View.VISIBLE); //显示进度
		String target = Environment.getExternalStorageDirectory()+"/update/update.apk";
		//XUtils
		HttpUtils utils = new HttpUtils();
		utils.download(mDownloadUrl, target, new RequestCallBack<File>() {
			/**
			 * 表示文件下载进度
			 * isUploading 是否在上传
			 */
			@Override
			public void onLoading(long total, long current, boolean isUploading) {
				super.onLoading(total, current, isUploading);
				Log.i("onLoading", "下载进度:"+current+"/"+total);
				tvProgress.setText("下载进度:"+current*100/total+"%");
			}
			/**
			 * 下载成功
			 */
			@Override
			public void onSuccess(ResponseInfo<File> arg0) {
				Log.i("download_success", "下载成功");
				Toast.makeText(SplashActivity.this, "下载成功", Toast.LENGTH_SHORT).show();
				
			}
			
			@Override
			public void onFailure(HttpException arg0, String arg1) {
				Toast.makeText(SplashActivity.this, "下载失败", Toast.LENGTH_SHORT).show();
			}
		});
	}


	/**
	 * 进入主页面
	 */
	private void enterHome(){
		Intent intent = new Intent(this,HomeActivity_.class);
		startActivity(intent);
		finish();  //跳转完成后关闭当前闪屏页面
	}

}
