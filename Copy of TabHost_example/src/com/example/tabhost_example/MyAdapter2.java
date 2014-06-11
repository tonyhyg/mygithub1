package com.example.tabhost_example;

import java.util.List;



import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.net.Uri;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

/**
 * @author hyg  通讯录适配器
 *
 */
public class MyAdapter2 extends BaseAdapter implements  SectionIndexer{
	private Context mContext;
	private List<PhoneItem> list;
	private LayoutInflater mInflater;  
	public MyAdapter2(Context context,List<PhoneItem> list) {
		// TODO Auto-generated constructor stub
		mContext = context;
		this.list = list;
		mInflater=LayoutInflater.from(this.mContext);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final int person_index = position;
		ViewHolder holder = null;
		if(holder==null){  
            holder=new ViewHolder();  
            convertView=mInflater.inflate(R.layout.listitem2, null);  
            //holder.icoImageView = (ImageView)convertView.findViewById(R.id.name_ico);
            holder.nameTextView = (TextView)convertView.findViewById(R.id.name_text2);
            holder.phoneTextView = (TextView)convertView.findViewById(R.id.phone_view2);
            holder.mButton = (Button)convertView.findViewById(R.id.call_btn2);          
            convertView.setTag(holder);  
        }else {
            holder=(ViewHolder)convertView.getTag(); 
        }
		
		// holder.icoImageView.setImageBitmap(list.get(position).icoString);
         holder.nameTextView.setText(list.get(position).nameString);
         final String phonenumber = list.get(position).phoneString;
         holder.phoneTextView.setText(list.get(position).phoneString);
         holder.mButton.setText("call");
		 
        /* holder.icoImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(mContext, "联系人"+person_index, Toast.LENGTH_SHORT).show();
				
			}
		});*/
         holder.mButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Toast.makeText(mContext, "是否拨打电话"+"list.get(position).phoneString", Toast.LENGTH_SHORT).show();
				//Log.i("hehe","gfgdfgdfgd");
				AlertDialog.Builder  dialog = new AlertDialog.Builder(mContext);
				dialog.setTitle("拨打该号码？");
				dialog.setIcon(R.drawable.ic_launcher);
				dialog.setMessage(phonenumber);
				dialog.setPositiveButton("yes", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:10000"));
						//intent.setDataAndType(Uri.parse("tel:10000"), Intent.ACTION_CALL);
						//intent.setClass(Intent.ACTION_CALL, Uri.parse("tel:10000"));
						mContext.startActivity(intent);
						
					}

				});
				dialog.setNegativeButton("cancle", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						//realdDialog.hide();
						
					}
				});
				dialog.setCancelable(false);
				final AlertDialog realdDialog = dialog.create();
				realdDialog.show();
				
			}
		});
		return convertView;
	}
	
	
	
	public final class ViewHolder{
		public ImageView icoImageView;
		public TextView nameTextView;
		public TextView phoneTextView;
		public Button mButton;
	}
	
	@Override
	public int getPositionForSection(int sectionIndex) {
		for (int i = 0; i < getCount(); i++) {
			String sortStr = list.get(i).nameLetter;
			char firstChar = sortStr.toUpperCase().charAt(0);
			if (firstChar == sectionIndex) {
				return i;
			}
		}
		
		return -1;
	}

	@Override
	public int getSectionForPosition(int position) {
		// TODO Auto-generated method stub
		return list.get(position).nameLetter.charAt(0);
	}

	
	/**
	 * 提取英文的首字母，非英文字母用#代替。
	 * 
	 * @param str
	 * @return
	 */
	private String getAlpha(String str) {
		String  sortStr = str.trim().substring(0, 1).toUpperCase();
		// 正则表达式，判断首字母是否是英文字母
		if (sortStr.matches("[A-Z]")) {
			return sortStr;
		} else {
			return "#";
		}
	}
	
	
	
	@Override
	public Object[] getSections() {
		// TODO Auto-generated method stub
		return null;
	}
	

}

