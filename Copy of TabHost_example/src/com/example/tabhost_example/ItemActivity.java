package com.example.tabhost_example;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;

import android.view.LayoutInflater;

import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher.ViewFactory;


/**
 * @author hyg  在GridView中点击其中某一张图片时跳转到的界面
 *   暂时只有上下翻页，上下翻页动画还有一点问题，没有图片放大操作
 */
public class ItemActivity extends Activity {

	private ImageSwitcher imageSwitcher;  
	  
	private float touchDownX , touchUpX; 
	//public List<Uri> list2;
	private Uri picUri;
	private List<PictureItem> list = new ArrayList<PictureItem>();
	int index;  
	//int index = 0;  
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.imageswitcher);
		
		//list = GrindAdapter.list;
		//picUri = (Uri)getIntent().getParcelableExtra("piclist");
		list = (List<PictureItem>)getIntent().getSerializableExtra("image_uri");
		
		//Object [] objs = (Object[])getIntent().getSerializableExtra("piclist");
		
		//for(Object oo:objs)
			//list.add((PictureItem) oo);
		index = (int)getIntent().getIntExtra("start_index", 0);
		//index = getIntent().getExtras().getInt("start_index");
		imageSwitcher = (ImageSwitcher)findViewById(R.id.imageSwitcher);
		imageSwitcher.setFactory(new MyViewFactory());
		//Bitmap bitmap = list.get(start_indext).bitmapItem;
		//BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
				//(BitmapDrawable)bitmap;      
		//Drawable drawable = (Drawable)bitmapDrawable; 
		String imagePath = list.get(index).bitmapItem;
    	File file = new File(imagePath);
        Uri fileUri = Uri.fromFile(file);
		imageSwitcher.setImageURI(fileUri);
		//imageSwitcher.setImageURI(list.get(index).bitmapItem);
		//(list.get(start_indext).bitmapItem);
		imageSwitcher.setOnTouchListener(new OnTouchListener()
		{
			public boolean onTouch(View arg0, MotionEvent arg1) 
			{
				if (arg1.getAction() == MotionEvent.ACTION_DOWN)
			{
				touchDownX = arg1.getX();
				return true;
			}else if(arg1.getAction() == MotionEvent.ACTION_UP)
			{
				touchUpX = arg1.getX();
				if (touchDownX - touchUpX > 100)//左滑
				{
					if (index >0 ) 
					{
						imageSwitcher.setInAnimation(ItemActivity.this, R.anim.switcher_out);
						imageSwitcher.setOutAnimation(ItemActivity.this, R.anim.switcher_in);
						--index;
						String imagePath1 = list.get(index).bitmapItem;
				    	File file1 = new File(imagePath1);
				        Uri fileUri1 = Uri.fromFile(file1);
						imageSwitcher.setImageURI(fileUri1);					
						//imageSwitcher.setImageResource(list.get(index).bitmapItem);
					}

				}else
		             {
		                if (index < list.size())
		                    {
		                          imageSwitcher.setInAnimation(ItemActivity.this, R.anim.switcher_in);
		                          imageSwitcher.setOutAnimation(ItemActivity.this, R.anim.switcher_out);
		                          ++index;
		                     	  String imagePath2 = list.get(index).bitmapItem;
		                     	  File file2 = new File(imagePath2);
		                     	  Uri fileUri2 = Uri.fromFile(file2);
		                     	  imageSwitcher.setImageURI(fileUri2);		                                     
		                     }
		             }
		                     return true;
			}

		           return false;

		 }
		});

		 

		
	}
	public class MyViewFactory implements  ViewFactory{

		@Override
		public View makeView() {
			 
			ImageView imageView = (ImageView)LayoutInflater.from(getApplicationContext()).inflate(R.layout.image_switcher_view, imageSwitcher, false);  			  
            return imageView;  
		}	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		 
		return super.onCreateOptionsMenu(menu);
	}

}


