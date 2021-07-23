package com.example.aptkjobs;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class Companyprofile extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.companyprofile);
		final TextView cid=(TextView) findViewById(R.id.companyid);
		TextView cname=(TextView) findViewById(R.id.companyname);
		TextView profile=(TextView) findViewById(R.id.companyprofile);
		TextView caddress=(TextView) findViewById(R.id.companyaddress);
		TextView cemail=(TextView) findViewById(R.id.companyemail);
		TextView cphone=(TextView) findViewById(R.id.companyphone);
		final RatingBar rate=(RatingBar) findViewById(R.id.ratingBar1);
		Button upvote=(Button) findViewById(R.id.btnRate);
	    float rating=0;
	    show_image();
		SQLiteDatabase db=openOrCreateDatabase("APTK",Context.MODE_PRIVATE,null);
		Cursor c=db.rawQuery("SELECT * FROM company WHERE regno=" + get_id() +"",null);
		
		while (c.moveToNext()){
			cid.setText(c.getString(0));
			cname.setText(c.getString(1));
			profile.setText(c.getString(7));
			caddress.setText(c.getString(6));
			cemail.setText(c.getString(10));
			cphone.setText(c.getString(9));
			rating=Float.parseFloat(c.getString(11));
			rate.setRating(rating);
			
		}
		
		upvote.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				float r=rate.getRating();
				
				if (r==7){
					set_intent();
					
					finish();
				}
				else {
				r++;
				SQLiteDatabase db=openOrCreateDatabase("APTK",Context.MODE_PRIVATE,null);
				db.execSQL("UPDATE company SET rating="+ r +" WHERE regno='"+ cid.getText().toString() +"'");
				Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
			    set_intent();
				finish();
				}
				
			}
		});
	
	}
	
	public void show_image() {
	ImageView profile=(ImageView) findViewById(R.id.imageView1);
	
	
	String sdcard=Environment.getExternalStorageDirectory().getAbsolutePath();
	String path=sdcard+ "/aptkimages/company/"+ get_id() + ".png";
	File img=new File(path);
	if (img.exists()){
		
		profile.setImageBitmap(BitmapFactory.decodeFile(path));
	}
}
	
	

	public void set_intent() {
		Intent ii=getIntent();
		Bundle bn=ii.getExtras();
		String log=bn.getString("logged");
		
		Intent in=new Intent(Companyprofile.this,Viewcompany.class);
		in.putExtra("logged",log);
		startActivity(in);
	}
	
	public String get_id() {
		Intent ii=getIntent();
		Bundle bn=ii.getExtras();
		String id=bn.getString("companyid");
		return id;
	}
}
