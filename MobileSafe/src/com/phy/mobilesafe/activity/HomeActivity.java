package com.phy.mobilesafe.activity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.DrawableRes;
import org.androidannotations.annotations.res.StringRes;

import com.phy.mobilesafe.R;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

@EActivity(R.layout.activity_home)
public class HomeActivity extends Activity {
	
	@ViewById(R.id.gv_home)
	protected GridView gvHome;
	
	
	private String[] mItem = new String[]{ "手机防盗", "通讯卫士", "软件管理", "进程管理",
			"流量统计", "手机杀毒", "缓存清理", "高级工具", "设置中心"};
	
	private int[] mPics = new int[]{R.drawable.home_safe,R.drawable.home_callmsgsafe, R.drawable.home_apps,
			R.drawable.home_taskmanager, R.drawable.home_netmanager,
			R.drawable.home_trojan, R.drawable.home_sysoptimize,
			R.drawable.home_tools, R.drawable.home_settings};
	@AfterViews
	void updateViewWithDate() {
		gvHome.setAdapter(new HomeAdapter());
	}
	
	class HomeAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return mItem.length;
		}

		@Override
		public Object getItem(int position) {
			return mItem[position];
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
			tvItem.setText(mItem[position]);
			return view;
		}
		
	}
}
