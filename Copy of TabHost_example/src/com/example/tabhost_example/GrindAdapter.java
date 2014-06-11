package com.example.tabhost_example;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * @author hyg  从GridView适配器
 *
 */
public class GrindAdapter extends BaseAdapter {
	private Context mContext;
	public List<PictureItem> list ;
	//public List<Uri> list2;
	public GrindAdapter(Context context,List<PictureItem> Llist){
		mContext = context;
		list = Llist;	
	}
	

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		final int position = arg0; 
		final ImageView mImageView;
		if (arg1 == null) { 
			mImageView = new ImageView(mContext); 
			
        } else { 
        	mImageView = (ImageView) arg1; 
        } 
		/*设定图片给imageView对象*/
	      //Bitmap bm = BitmapFactory.decodeFile(list.
	       //                     get(arg0).toString());
	      
	     // mImageView.setImageBitmap(bm);
		 /*重新设定Layout的宽高*/
		DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
		int widthpix = metrics.widthPixels;
		//int heightpix = metrics.heightPixels;
		int width = widthpix/PictureActivity.pcolumn;
		int height = width*3/2;
	    mImageView.setLayoutParams(new GridView.LayoutParams(width, height));
		/*重新设定图片的宽高*/
		mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
	    mImageView.setPadding(8, 8, 8, 8);
	    //Bitmap oldBitmap = list.get(arg0).bitmapItem;
	    //Bitmap useThisBitmap = Bitmap.createScaledBitmap(oldBitmap, 100, 120, false);
	    Bitmap newbitmap = null;
	    // 读取uri所在的图片
	    try {
	    	
	    	String imagePath = list.get(arg0).bitmapItem;
	    	File file = new File(imagePath);
            Uri fileUri = Uri.fromFile(file);
			Bitmap bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(),fileUri );
			newbitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    mImageView.setImageBitmap(newbitmap);
	    mImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bundle bundle = new Bundle();
				bundle.putInt("start_index", position);				
				bundle.putSerializable("image_uri", (Serializable)list);				
				Intent intent = new Intent();				
				intent.putExtras(bundle);				
				intent.setClass(mContext, ItemActivity.class);
				mContext.startActivity(intent);
				
			}
		});
	     
	      /*传回imageView对象*/
	      return mImageView;
		//return null;
	}

}
