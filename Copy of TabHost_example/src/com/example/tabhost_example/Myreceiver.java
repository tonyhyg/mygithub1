package com.example.tabhost_example;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


/**
 * @author hyg  �㲥������
 * ��ʱֻд��Ҫ�����Ƿ���ʾͼƬ�����ܻ�û���
 *
 */
public class Myreceiver extends BroadcastReceiver {
	private Context mContext;
	public String isimage;
	@Override
	public void onReceive(Context arg0, Intent arg1) {
		// TODO Auto-generated method stub
		mContext = arg0;
		isimage = arg1.getStringExtra("ISIMAGE");
		
		
		

	}

}
