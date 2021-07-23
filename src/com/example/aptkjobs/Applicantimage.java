package com.example.aptkjobs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class Applicantimage extends Activity implements OnClickListener {
ImageView cimage;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.applicantimage);
		
		cimage=(ImageView) findViewById(R.id.applicantimg);
		Button save=(Button) findViewById(R.id.btnsaveimg2);
		show_image();
		cimage.setOnClickListener(this);
		save.setOnClickListener(this);
	}

	public void show_image() {
		
		
		
		String sdcard=Environment.getExternalStorageDirectory().getAbsolutePath();
		String path=sdcard+ "/aptkimages/company/"+ get_user() + ".png";
		File img=new File(path);
		if (img.exists()){
		
			cimage.setImageBitmap(BitmapFactory.decodeFile(path));
		}
		else {
			
		}
		
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		if (v.getId()==R.id.applicantimg){
		Intent in=new Intent();
		in.setType("image/");
		in.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(Intent.createChooser(in,"Select a picture"),1);
		}
		else if(v.getId()==R.id.btnsaveimg2){
		save_img();	
		}
	}
	
	
	public void save_img() {
		Bitmap bitmap;
		OutputStream output;
		cimage.setDrawingCacheEnabled(true);
		bitmap=cimage.getDrawingCache();
		
		
		File filepath=Environment.getExternalStorageDirectory();
		File dir=new File(filepath.getAbsolutePath() + "/aptkimages/applicant");
		dir.mkdirs();
		
		File file=new File(dir,get_user() + ".png");
		
		Toast.makeText(Applicantimage.this,"Image saved to sd card",Toast.LENGTH_SHORT).show();
		
		try {
			output=new FileOutputStream(file);
			bitmap.compress(Bitmap.CompressFormat.PNG,100,output);
			output.flush();
			output.close();
			Intent in=new Intent(Applicantimage.this,Applicantmenu.class);
			in.putExtra("loggedas",get_user());
			startActivity(in);
			finish();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		
	}

	public String get_user() {
		Intent ii=getIntent();
		Bundle bn=ii.getExtras();
		String user=bn.getString("logged");
		return user;
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		
		
		//Toast.makeText(getApplicationContext(),resultCode,Toast.LENGTH_SHORT).show();
		
		
		if(resultCode==Activity.RESULT_OK){
			
			cimage.setImageURI(data.getData());
		}
		else {
			Toast.makeText(getApplicationContext(),"No Image selected",Toast.LENGTH_SHORT).show();
		}
	
	}
}
