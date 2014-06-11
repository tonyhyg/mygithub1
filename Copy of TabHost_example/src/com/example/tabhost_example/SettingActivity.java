package com.example.tabhost_example;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

public class SettingActivity extends Activity {
	private String isimage;
	private ToggleButton switcher; 
	private TextView textView;
	private Button moreButton;
	private Button lessButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_home);
		switcher = (ToggleButton)findViewById(R.id.isimage_setting);
		textView = (TextView)findViewById(R.id.column_number);
		lessButton = (Button)findViewById(R.id.less_btn);
		moreButton = (Button)findViewById(R.id.more_btn);
		lessButton.setOnClickListener(new SetListener());
		moreButton.setOnClickListener(new SetListener());
		switcher.setOnCheckedChangeListener(new ToggleButton.OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					isimage = "yes";
					
				}else{
					isimage = "no";
				}
				Intent sendintent = new Intent();
				sendintent.setAction("com.example.tabhost.mybroadcast");
				sendintent.putExtra("ISIMAGE", isimage);
				SettingActivity.this.sendBroadcast(sendintent);
				
			}
			
		});
		
		
	}
	
	
	class SetListener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			String switchString = textView.getText().toString();
			int integer = Integer.parseInt(switchString);
			switch(arg0.getId()){
			case R.id.less_btn:
				if(integer>4)
					--integer;
				break;
			case R.id.more_btn:
				if(integer<6&&integer>3)
					++integer; 	
				break;
				default:break;
			}
			textView.setText(integer+"");
			Intent sendintent2 = new Intent();
			sendintent2.setAction("com.example.tabhost.picturecolum");
			sendintent2.putExtra("pic_column", integer);
			SettingActivity.this.sendBroadcast(sendintent2);
			
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}

}
