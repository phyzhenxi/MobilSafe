package com.phy.mobilesafe.activity;

import java.util.ArrayList;
import java.util.HashMap;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ViewById;

import com.lidroid.xutils.util.LogUtils;
import com.phy.mobilesafe.R;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.widget.ListView;
import android.widget.SimpleAdapter;
@EActivity(R.layout.activity_contact)
public class ContactActivity extends Activity {
	@ViewById(R.id.lv_list)
	ListView lvList;
	
	private ArrayList<HashMap<String,String>> readContact;
	
	@AfterViews
	void readContactAndLvlist(){
		readContact = readContact();
		lvList.setAdapter(new SimpleAdapter(this, readContact, R.layout.contact_list_item, new String[]{"name","phone"}, new int[]{R.id.tv_name,R.id.tv_phone}));
	}
	
	@ItemClick
	public void lvListItemClicked(int position){
		// ��ȡ��ǰitem�ĵ绰����
		String phone = readContact.get(position).get("phone");
		Intent intent = new Intent();
		intent.putExtra("phone", phone);
		setResult(Activity.RESULT_OK, intent);
		// �����ݷ���intent�з��ظ���һ��ҳ��
		finish();
	}

	/**
	 * ��ϵͳ��contact���ж�ȡ��ϵ����Ϣ
	 * @return list
	 */
	private ArrayList<HashMap<String, String>> readContact() {
		// ����,��raw_contacts�ж�ȡ��ϵ�˵�id("contact_id")
		// ���, ����contact_id��data���в�ѯ����Ӧ�ĵ绰�������ϵ������
		// Ȼ��,����mimetype�������ĸ�����ϵ��,�ĸ��ǵ绰����
		Uri rawContactsUri = Uri.parse("content://com.android.contacts/raw_contacts");
		Uri dataUri = Uri.parse("content://com.android.contacts/data");
		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		// ��raw_contacts�ж�ȡ��ϵ�˵�id("contact_id")
		Cursor rawContactsCursor = getContentResolver().query(rawContactsUri, new String[]{
		"contact_id"}, null,null,null);
		
		if(rawContactsCursor!=null){
			while(rawContactsCursor.moveToNext()){
				String contactId = rawContactsCursor.getString(0);
				LogUtils.i(contactId);
				// ����contact_id��data���в�ѯ����Ӧ�ĵ绰�������ϵ������, ʵ���ϲ�ѯ������ͼview_data
				Cursor dataCursor = getContentResolver().query(dataUri, new String[]{"data1","mimetype"}, "contact_id=?",new String[]{contactId}, null);
				if(dataCursor!=null){
					HashMap<String, String> map = new HashMap<String, String>();
					while(dataCursor.moveToNext()){
						String data1 = dataCursor.getString(0);
						String mimetype = dataCursor.getString(1);
						if("vnd.android.cursor.item/phone_v2".equals(mimetype)){
							map.put("phone", data1);
						}else if("vnd.android.cursor.item/name".equals(mimetype)){
							map.put("name", data1);
						}
					}
					list.add(map);
					dataCursor.close();
				}
			}
			rawContactsCursor.close();
		}
		return list;
	}
}
