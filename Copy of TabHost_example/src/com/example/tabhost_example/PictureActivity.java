
package com.example.tabhost_example;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.widget.GridView;
import android.widget.Toast;

/**
 * @author hyg
 * 图片菜单主界面
 *
 */

public class PictureActivity extends Activity {
	
	private GridView gridView;
	private List<PictureItem> list;
	//public List<Uri> list2;
	private GrindAdapter grindadapter;
	private IntentFilter mIntentFilter;
	public static int pcolumn = 4;
	private int flag = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.picture_home);
		gridView = (GridView)findViewById(R.id.picturegridview);
		list = new ArrayList<PictureItem>();
		//list2 = new ArrayList<Uri>() ;
		//获取图片
		getPicture();  
		
		/*gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
				startActivity(new Intent(PictureActivity.this,ItemActivity.class));
				
			}
		});*/
	}
	
	public void getPicture(){
		
		int columnIndex = 0;
		String[] projection = {MediaStore.Images.Media.DATA};
		ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query( MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI,
                projection,
                null, 
                null, 
                null);
        if (cursor != null) {
            columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
           while(cursor.moveToNext()){ //cursor.moveToPosition(position);
        	   
            String imagePath = cursor.getString(columnIndex); 
            //File file = new File(imagePath);
            //Uri fileUri = Uri.fromFile(file);
            
            PictureItem pic = new PictureItem();
            pic.bitmapItem = imagePath;
            list.add(pic);
            
            //list2.add(fileUri);

            /*FileInputStream is = null;
            BufferedInputStream bis = null;
            
                try {
					is = new FileInputStream(new File(imagePath));
					bis = new BufferedInputStream(is);
					Bitmap bitmap = BitmapFactory.decodeStream(bis);
					Bitmap useThisBitmap = Bitmap.createScaledBitmap(bitmap, 100, 120, false);
	                PictureItem pic = new PictureItem();
	                pic.bitmapItem = useThisBitmap;
	                list.add(pic);
	                //Bitmap useThisBitmap = Bitmap.createScaledBitmap(bitmap, parent.getWidth(), parent.getHeight(), true);
	                //bitmap.recycle();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					try {
                    if (bis != null) {
                        bis.close();
                    }
                    if (is != null) {
                        is.close();
                    }
                   
                    projection = null;
                } catch (Exception e) {
                }
				}*/
	                             
                
           }	
           
        }
        cursor.close();
       
        
        		
		
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
		mIntentFilter.addAction("com.example.tabhost.picturecolum");
		this.registerReceiver(new BroadcastReceiver(){

			@Override
			public void onReceive(Context arg0, Intent arg1) {
				// TODO Auto-generated method stub
				
				pcolumn = arg1.getIntExtra("pic_column",4);
				
			}
			
		}, mIntentFilter);
		
		if(flag != pcolumn&&pcolumn<7&&pcolumn>3){
			Toast.makeText(PictureActivity.this, flag+"", Toast.LENGTH_LONG).show();
			
			gridView.setNumColumns(pcolumn);
			
			grindadapter = new GrindAdapter(PictureActivity.this, list);
			gridView.setAdapter(grindadapter);
			
			gridView.setClipToPadding(false);
			
			}
			
	}



	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		flag = pcolumn;
	}
	

}
