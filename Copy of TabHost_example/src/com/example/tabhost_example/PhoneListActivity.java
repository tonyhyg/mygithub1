package com.example.tabhost_example;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



import android.app.Activity;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts.Photo;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @author hyg  
 * 通讯录Activity    
 */
public class PhoneListActivity extends Activity {
	private ListView mListView;
	private List<PhoneItem> list;
	private String change_flag = "no";
	
	
	private static final String[] PHONES_PROJECTION = new String[] {
	    Phone.DISPLAY_NAME, Phone.NUMBER, Photo.PHOTO_ID,Phone.CONTACT_ID };
	//private Myreceiver mMyreceiver;
	private IntentFilter  mIntentFilter;
	private static String fString = "yes";
	
	/**
	 * 汉字转换成拼音的类
	 */
	private CharacterParser characterParser;
	//private List<PhoneItem> SourceDateList;
	private SideBar sideBar;
	private TextView dialog;
	private MyAdapter phoneAdapter;
	private MyAdapter2 phoneAdapter2;
	/**
	 * 根据拼音来排列ListView里面的数据类
	 */
	private PinyinComparator pinyinComparator;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.phone_home);
		mListView = (ListView)findViewById(R.id.phonelist);
	    list = new ArrayList<PhoneItem>();
	 
	    getPhoneContacts();
	    getSIMContacts();

	}

	private void getPhoneContacts() {  
		
		ContentResolver resolver = PhoneListActivity.this.getContentResolver();  
		// 获取手机联系人  
		Cursor phoneCursor = resolver.query(Phone.CONTENT_URI,PHONES_PROJECTION, null, null, null);  
		if (phoneCursor != null) {  
		    while (phoneCursor.moveToNext()) {  
		    //得到手机号码
		    String phoneNumber = phoneCursor.getString(1);	
		    //String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);  
		    //当手机号码为空的或者为空字段 跳过当前循环  
		    if (TextUtils.isEmpty(phoneNumber))  
		        continue;  
		    //得到联系人名称  
		    
		    String contactName = phoneCursor.getString(0);  
		    //String contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);  
		    //得到联系人ID  
		    Long contactid = phoneCursor.getLong(3);  
		    //Long contactid = phoneCursor.getLong(PHONES_CONTACT_ID_INDEX);  
		    //得到联系人头像ID  
		    Long photoid = phoneCursor.getLong(2);  
		    //Long photoid = phoneCursor.getLong(PHONES_PHOTO_ID_INDEX);  
		    //得到联系人头像Bitamp  
		    Bitmap contactPhoto = null;  
		    //photoid 大于0 表示联系人有头像 如果没有给此人设置头像则给他一个默认的  
		    if(photoid > 0 ) {  
		        Uri uri =ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI,contactid);  
		        InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(resolver, uri);  
		        contactPhoto = BitmapFactory.decodeStream(input);  
		    }else {  
		        contactPhoto = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);  
		    }  
		    PhoneItem person = new PhoneItem();
		    person.nameString = contactName;  
		    person.phoneString = phoneNumber;
		    person.icoString = contactPhoto;
		    list.add(person);
		    
		    }  
		     
		} 
		phoneCursor.close(); 
	}  
	
	
	private void getSIMContacts() {  
		ContentResolver resolver = PhoneListActivity.this.getContentResolver();  
		// 获取Sims卡联系人  
		Uri uri = Uri.parse("content://icc/adn");  
		Cursor phoneCursor = resolver.query(uri, PHONES_PROJECTION, null, null,  
		    null);  
		if (phoneCursor != null) {  
		    while (phoneCursor.moveToNext()) {  
		  
		    // 得到手机号码  
		    String phoneNumber = phoneCursor.getString(1);  
		    // 当手机号码为空的或者为空字段 跳过当前循环  
		    if (TextUtils.isEmpty(phoneNumber))  
		        continue;  
		    // 得到联系人名称  
		    String contactName = phoneCursor  
		        .getString(0);  
		    //Sim卡中没有联系人头像 
		    
		    PhoneItem person = new PhoneItem();
		    person.nameString = contactName;  
		    person.phoneString = phoneNumber;
		    person.icoString = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
		    list.add(person);
		    //mContactsName.add(contactName);  
		    //mContactsNumber.add(phoneNumber);  
		    }  
		      
		}  
		phoneCursor.close();
	 } 

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}




	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	    
		mIntentFilter = new IntentFilter();
		mIntentFilter.addAction("com.example.tabhost.mybroadcast");
		this.registerReceiver(new BroadcastReceiver(){

			@Override
			public void onReceive(Context arg0, Intent arg1) {
				// TODO Auto-generated method stub
				fString = arg1.getStringExtra("ISIMAGE");
				
				
			}
			
		}, mIntentFilter);
		//Toast.makeText(PhoneListActivity.this, fString, Toast.LENGTH_LONG).show();
		
		
		if(!change_flag.equals(fString)){
			if ("yes".equals(fString)) {
				phoneAdapter = new MyAdapter(PhoneListActivity.this,list);	
				mListView.setAdapter(phoneAdapter);
				 initViews("yes");
			}else if("no".equals(fString)){
				phoneAdapter2 = new MyAdapter2(PhoneListActivity.this, list);
				mListView.setAdapter(phoneAdapter2);
				initViews("no");
			
			}
			//Toast.makeText(PhoneListActivity.this, change_flag, Toast.LENGTH_SHORT).show();
		}
		
		
		
		
	}


	private void initViews(final String  isImages) {
		//实例化汉字转拼音类
		characterParser = CharacterParser.getInstance();
		
		pinyinComparator = new PinyinComparator();
		
		sideBar = (SideBar) findViewById(R.id.sidrbar);
		dialog = (TextView) findViewById(R.id.dialog);
		sideBar.setTextView(dialog);
		
		//设置右侧触摸监听
		sideBar.setOnTouchingLetterChangedListener(new com.example.tabhost_example.SideBar.OnTouchingLetterChangedListener() {
			
			@Override
			public void onTouchingLetterChanged(String s) {
				//该字母首次出现的位置
				int position = 0;
				if("yes".equals(isImages)){
					position = phoneAdapter.getPositionForSection(s.charAt(0));
				}else{
					position = phoneAdapter2.getPositionForSection(s.charAt(0));
				}
				if(position != -1){
					mListView.setSelection(position);
				}
				
			}
		});
		
		//sortListView = (ListView) findViewById(R.id.country_lvcountry);
		/*mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//这里要利用adapter.getItem(position)来获取当前position所对应的对象
				Toast.makeText(getApplication(), ((SortModel)adapter.getItem(position)).getName(), Toast.LENGTH_SHORT).show();
			}
		});*/
		
		list = filledData(list);
		
		// 根据a-z进行排序源数据
		Collections.sort(list, pinyinComparator);
		if("yes".equals(isImages)){
			phoneAdapter = new MyAdapter(PhoneListActivity.this, list);
			mListView.setAdapter(phoneAdapter);
		}else{
			phoneAdapter2 = new MyAdapter2(PhoneListActivity.this, list);
			mListView.setAdapter(phoneAdapter2);
		}
		
		
	}
	
	
	
	private List<PhoneItem> filledData(List<PhoneItem> mlist){
		List<PhoneItem> mSortList = new ArrayList<PhoneItem>();
		
		for(int i=0; i<mlist.size(); i++){
			PhoneItem sortModel = new PhoneItem();
			sortModel.nameString = mlist.get(i).nameString;
			sortModel.icoString = mlist.get(i).icoString;
			sortModel.phoneString = mlist.get(i).phoneString;
			sortModel.buttonString = mlist.get(i).buttonString;
			//汉字转换成拼音
			String pinyin = characterParser.getSelling(mlist.get(i).nameString);
			String sortString = pinyin.substring(0, 1).toUpperCase();
			
			// 正则表达式，判断首字母是否是英文字母
			if(sortString.matches("[A-Z]")){
				sortModel.nameLetter = sortString.toUpperCase();
			}else{
				sortModel.nameLetter = "#";
			}
			
			mSortList.add(sortModel);
		}
		return mSortList;
		
	}
	

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		change_flag = fString;
	}

}
