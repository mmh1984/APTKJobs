package com.example.aptkjobs;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class Companymenu extends Activity implements OnClickListener{
String loggedas="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.companymenu);
		Intent in=getIntent();
		Bundle bn=in.getExtras();
		loggedas=bn.getString("loggedas");
		
		
		
		ImageButton profile=(ImageButton) findViewById(R.id.imageBtnProfile);
		ImageButton inbox=(ImageButton) findViewById(R.id.imageBtnInbox);
		ImageButton post=(ImageButton) findViewById(R.id.imageBtnPost);
		ImageButton logout=(ImageButton) findViewById(R.id.imageBtnLogout);
		ImageButton view=(ImageButton) findViewById(R.id.imageBtnView);
		ImageButton news=(ImageButton) findViewById(R.id.imageBtnNews);
		Button changeimg=(Button) findViewById(R.id.btnchange);
		show_image();
		profile.setOnClickListener(this);
		inbox.setOnClickListener(this);
		post.setOnClickListener(this);
		logout.setOnClickListener(this);
		view.setOnClickListener(this);
		news.setOnClickListener(this);
		changeimg.setOnClickListener(this);
	}

	
	public void show_image() {
		ImageView profile=(ImageView) findViewById(R.id.imgcompany);
		
	
		String sdcard=Environment.getExternalStorageDirectory().getAbsolutePath();
		String path=sdcard+ "/aptkimages/company/"+ loggedas + ".png";
		File img=new File(path);
		if (img.exists()){
			
			profile.setImageBitmap(BitmapFactory.decodeFile(path));
		}
		else {
			
		}
		
		
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Intent ii;
		switch(arg0.getId()){
		
		case R.id.btnchange:
			ii=new Intent(Companymenu.this,Companyimage.class);
			ii.putExtra("logged",loggedas);
			startActivity(ii);
			finish();
			break;
		
		case R.id.imageBtnProfile:
			ii=new Intent(Companymenu.this,Editcompany.class);
			ii.putExtra("logged",loggedas);
			startActivity(ii);
			break;
			
		case R.id.imageBtnInbox:
			ii=new Intent(Companymenu.this,Companyinbox.class);
			ii.putExtra("logged",loggedas);
			startActivity(ii);
			break;
			
		case R.id.imageBtnPost:
			ii=new Intent(Companymenu.this,Addjob.class);
			ii.putExtra("logged",loggedas);
			startActivity(ii);
			break;
			
		case R.id.imageBtnView:
			ii=new Intent(Companymenu.this,Company.class);
			ii.putExtra("logged",loggedas);
			startActivity(ii);
			break;
			
		case R.id.imageBtnNews:
			ii=new Intent(Companymenu.this,News.class);
			ii.putExtra("logged",loggedas);
			startActivity(ii);
			break;
			
		case R.id.imageBtnLogout:
			show_message();
			break;
			
		
		}
		
		
	}
	
	public void show_message(){
		AlertDialog.Builder build=new AlertDialog.Builder(Companymenu.this);
		build.setMessage("Confirm logout?");
		build.setCancelable(false);
		build.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				startActivity(new Intent(Companymenu.this,Main.class));
				finish();
			}
			
		});
		
		build.setNegativeButton("No", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				arg0.cancel();
			}
		});
		
		AlertDialog alert=build.create();
		alert.show();
	}
}
