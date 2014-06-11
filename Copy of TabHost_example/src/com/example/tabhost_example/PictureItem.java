package com.example.tabhost_example;



import java.io.Serializable;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;


public class PictureItem implements Serializable{
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//public Bitmap bitmapItem ;
	 public String  bitmapItem;
	 }
/*public class PictureItem implements Parcelable{
	
	
	//public Bitmap bitmapItem ;
	 public Uri  bitmapItem;

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.
		
	}
	
	 public static final Parcelable.Creator<PictureItem> CREATOR = new Parcelable.Creator<PictureItem>() { 
		          @Override 
		          public PictureItem createFromParcel(Parcel source) { 
		              // 从Parcel中读取数据，返回person对象  
		              return new PictureItem(source.readInt(), source.readString()); 
		          } 
		   
		          @Override 
		          public PictureItem[] newArray(int size) { 
		              return new PictureItem[size]; 
		          } 
		     }; 
	

}*/
