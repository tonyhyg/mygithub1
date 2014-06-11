
package com.example.tabhost_example;



import android.os.Bundle;

import android.app.ActivityGroup;


import android.content.Intent;
import android.graphics.Color;


import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabWidget;



/**
 * @author hyg
 * ������     ��������tabhostÿ���˵�ʱ��ת����Ӧ��Activity
 * PhoneListActivity(ͨѶ¼)��PictureActivity��ͼƬ����SettingActivity�����ã�
 */
public class MainActivity extends ActivityGroup {
	//private View lastView; //��¼�ϴ���ʾ��ͼ  
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final TabHost host = (TabHost)findViewById(R.id.tabhost);
		final TabWidget tabWidget = (TabWidget)findViewById(android.R.id.tabs);
		//LocalActivityManager mLocalActivityManager = new LocalActivityManager(this, false);
		//setup(mLocalActivityManager);
		host.setup(); 
		host.setup(this.getLocalActivityManager()); 
		LayoutInflater myInflater = LayoutInflater.from(this);//.inflate(R.layout.activity_main, host.getTabContentView(), false);
		myInflater.inflate(R.layout.phone_home, host.getTabContentView(),false);
		myInflater.inflate(R.layout.picture_home, host.getTabContentView(),false);
		myInflater.inflate(R.layout.setting_home, host.getTabContentView(),false);
		
		
		TabHost.TabSpec specone = host.newTabSpec("phone");
		specone.setIndicator("ͨѶ¼", getResources().getDrawable(R.drawable.ic_launcher));
		//specone.setContent(R.id.l1);
		//final ListView mListView = (ListView)findViewById(R.id.phonelist);
		specone.setContent(new Intent(this,PhoneListActivity.class));
		
		
		TabHost.TabSpec spectwo = host.newTabSpec("picture");
		spectwo.setIndicator("ͼƬ", getResources().getDrawable(R.drawable.ic_launcher));
		spectwo.setContent(new Intent(this,PictureActivity.class));
		
		TabHost.TabSpec specthree = host.newTabSpec("setting");
		specthree.setIndicator("����", getResources().getDrawable(R.drawable.ic_launcher));
		specthree.setContent(new Intent(this,SettingActivity.class));
		host.addTab(specone);
		host.addTab(spectwo);
		host.addTab(specthree);
		for(int i = 1;i<3;i++)
		tabWidget.getChildAt(i).setBackgroundColor(Color.GRAY);
		host.setCurrentTab(0);
		tabWidget.getChildAt(0).setBackgroundColor(Color.CYAN);
		host.setOnTabChangedListener(new OnTabChangeListener() {                         
			@Override              
            public void onTabChanged(String tabId) {
                  for(int i = 0; i < tabWidget.getChildCount(); i++) {   
                	  View tabView = tabWidget.getChildAt(i);      
                	  if(host.getCurrentTab() == i) {          
                		  tabView.setBackgroundColor(Color.CYAN); 
                		  	/*if(host.getCurrentTab()==0){
                		  		//showListView();
                		  		}else if(host.getCurrentTab()==1){
                		  			//showGridView();
                		  			
                		  		}else if(host.getCurrentTab()==2){
                		  			//showSetting();
                		  		}*/
                		  
                		  } else {                          
                			  tabView.setBackgroundColor(Color.GRAY);     //δѡ�е���ɫ         
                			  //host.getTabContentView().setVisibility(View.GONE);
                        }                
                     }           
              }          
     });  
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
