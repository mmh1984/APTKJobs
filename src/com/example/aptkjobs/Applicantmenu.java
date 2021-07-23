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

public class Applicantmenu extends Activity implements OnClickListener{
	String loggedas="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout .applicantmenu);
		
		Intent in=getIntent();
		Bundle bn=in.getExtras();
		loggedas=bn.getString("loggedas");
		ImageButton profile=(ImageButton) findViewById(R.id.imageBtnProfile);
		ImageButton apply=(ImageButton) findViewById(R.id.imageBtnApply);
		ImageButton inbox=(ImageButton) findViewById(R.id.imageBtnInbox);
		ImageButton logout=(ImageButton) findViewById(R.id.imageBtnLogout);
		ImageButton company=(ImageButton) findViewById(R.id.imagebtnCompany);
		ImageButton upload=(ImageButton) findViewById(R.id.imagebtnUpload);
		Button changeimg=(Button) findViewById(R.id.btnchange2);
		show_image();
		profile.setOnClickListener(this);
		apply.setOnClickListener(this);
		inbox.setOnClickListener(this);
		logout.setOnClickListener(this);
		company.setOnClickListener(this);
		changeimg.setOnClickListener(this);
		upload.setOnClickListener(this);
	
		
	}

	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Intent ii;
		switch(arg0.getId()){
		case R.id.imageBtnProfile:
			ii=new Intent(Applicantmenu.this,Ediapplicant.class);
			ii.putExtra("logged",loggedas);
			startActivity(ii);
			break;
		case R.id.imageBtnApply:
			ii=new Intent(Applicantmenu.this,Applicant.class);
			ii.putExtra("logged",loggedas);
			startActivity(ii);
			break;
			
		case R.id.imageBtnInbox:
			ii=new Intent(Applicantmenu.this,Applicantinbox.class);
			ii.putExtra("logged",loggedas);
			startActivity(ii);
			break;
		
		case R.id.imagebtnCompany:
			ii=new Intent(Applicantmenu.this,Viewcompany.class);
			ii.putExtra("logged",loggedas);
			startActivity(ii);
			break;
			
		case R.id.imagebtnUpload:
			ii=new Intent(Applicantmenu.this,Certificate1.class);
			ii.putExtra("logged",loggedas);
			startActivity(ii);
			break;
			
		case R.id.btnchange2:
			ii=new Intent(Applicantmenu.this,Applicantimage.class);
			ii.putExtra("logged",loggedas);
			startActivity(ii);
			finish();
			break;
		
			
		case R.id.imageBtnLogout:
			show_message();
			break;
			
			
			
		}
	}
	public void show_image() {
		ImageView profile=(ImageView) findViewById(R.id.applicantprofile);
		
	
		String sdcard=Environment.getExternalStorageDirectory().getAbsolutePath();
		String path=sdcard+ "/aptkimages/applicant/"+ loggedas + ".png";
		File img=new File(path);
		if (img.exists()){
		
			profile.setImageBitmap(BitmapFactory.decodeFile(path));
		}
		else {
			
		}
		
		
	}
	public void show_message(){
		AlertDialog.Builder build=new AlertDialog.Builder(Applicantmenu.this);
		build.setMessage("Confirm logout?");
		build.setCancelable(false);
		build.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				startActivity(new Intent(Applicantmenu.this,Main.class));
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
