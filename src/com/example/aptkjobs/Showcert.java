package com.example.aptkjobs;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class Showcert extends Activity implements OnClickListener{


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.showcert);
		Button c1=(Button) findViewById(R.id.btnc1);
		Button c2=(Button) findViewById(R.id.btnc2);
		Button r=(Button) findViewById(R.id.btnr);
		
		c1.setOnClickListener(this);
		c2.setOnClickListener(this);
		r.setOnClickListener(this);
		
		show_image("certificate1");
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId()==R.id.btnc1){
			show_image("certificate1");
			
		}
		
		else if (v.getId()==R.id.btnc2){
			show_image("certificate2");
			
		}
		if (v.getId()==R.id.btnr){
			show_image("resume");
			
		}
		
	}

	public void show_image(String content) {
		ImageView profile=(ImageView) findViewById(R.id.imageView1);
		
		Intent in=getIntent();
		Bundle bn=in.getExtras();
		
		String sdcard=Environment.getExternalStorageDirectory().getAbsolutePath();
		String useric=bn.getString("useric");
		String path=sdcard+ "/aptkimages/applicant/"+ useric  + "/" + content + ".png";
		File img=new File(path);
		if (img.exists()){
		
			profile.setImageBitmap(BitmapFactory.decodeFile(path));
		}
		else {
			
		}
		
		
	}
	
}
