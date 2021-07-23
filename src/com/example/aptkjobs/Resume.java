package com.example.aptkjobs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

public class Resume extends Activity {
ImageView v1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.certificate1);
		
		v1=(ImageView) findViewById(R.id.imageView1);
		
		v1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in=new Intent();
				in.setType("image/");
				in.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(Intent.createChooser(in,"Select Certificate 2"),1);
			}
		});
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		
		
		//Toast.makeText(getApplicationContext(),resultCode,Toast.LENGTH_SHORT).show();
		
		
		if(resultCode==Activity.RESULT_OK){
			
			v1.setImageURI(data.getData());
			show_message();
		}
		else {
			Toast.makeText(getApplicationContext(),"No Image selected",Toast.LENGTH_SHORT).show();
		}

	}

	public void save_image(){
		
		Bitmap bitmap;
		OutputStream output;
		v1.setDrawingCacheEnabled(true);
		bitmap=v1.getDrawingCache();
		
		
		File filepath=Environment.getExternalStorageDirectory();
		File dir=new File(filepath.getAbsolutePath() + "/aptkimages/applicant/" + get_user() + "/");
		dir.mkdirs();
		
		File file=new File(dir,"resume.png");
		
		Toast.makeText(Resume.this,"Resume saved to SD card",Toast.LENGTH_SHORT).show();
		
		try {
			output=new FileOutputStream(file);
			bitmap.compress(Bitmap.CompressFormat.PNG,100,output);
			output.flush();
			output.close();
			finish();
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		
		}

	public void show_message() {
		AlertDialog.Builder b=new AlertDialog.Builder(Resume.this);
		b.setMessage("Success, all certificates uploaded");
		b.setCancelable(false);
		b.setPositiveButton("Back to menu",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				save_image();
				
			}
		});
		
		
		
		
		AlertDialog alert=b.create();
		alert.show();
	}


	public String get_user(){
		Intent in=getIntent();
		Bundle bn=in.getExtras();
		String user=bn.getString("logged");
		return user;
	}
}