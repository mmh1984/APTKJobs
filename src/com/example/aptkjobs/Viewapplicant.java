package com.example.aptkjobs;

import java.io.File;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Viewapplicant extends Activity {

TextView txtic,txtname,txtemail,txtphone,txtgender,txtqual,txtreligion,txtsubject,txtmessage,txtreference;
Button send;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewapplicant);
		
		Intent in=getIntent();
		Bundle bn=in.getExtras();
		String ic=bn.getString("icno");
		String jid=bn.getString("jobid");
		String job=bn.getString("jobtitle");
		String added=bn.getString("regno");
		Toast.makeText(getApplicationContext(),ic,Toast.LENGTH_SHORT).show();

		txtic=(TextView) findViewById(R.id.appic);
		txtname=(TextView) findViewById(R.id.appname);
		txtemail=(TextView) findViewById(R.id.appemail);
		txtphone=(TextView) findViewById(R.id.appphone);
		txtgender=(TextView) findViewById(R.id.appgender);
		txtqual=(TextView) findViewById(R.id.appqualification);
		txtreligion=(TextView) findViewById(R.id.appreligion);
		txtsubject=(TextView) findViewById(R.id.txtsubject);
		txtreference=(TextView) findViewById(R.id.txtreference);
		send=(Button) findViewById(R.id.btnsendapp);
		txtmessage=(TextView) findViewById(R.id.txtreplymessage);
		txtsubject.setText("RE: Appliying for " + job);
		txtreference.setText("Job REFERENCE: " + jid + "\nCompany no: " + added);
		Button view=(Button) findViewById(R.id.btnviewcert);
		
		
		SQLiteDatabase db=openOrCreateDatabase("APTK",Context.MODE_PRIVATE,null);
		Cursor c=db.rawQuery("SELECT * FROM applicant WHERE icnumber="+ ic +"",null);
		
		while (c.moveToNext()){
			txtic.setText(c.getString(0));
			txtname.setText(c.getString(2));
			txtemail.setText(c.getString(3));
			txtphone.setText(c.getString(4));
			txtgender.setText(c.getString(6));
			txtqual.setText(c.getString(10));
			txtreligion.setText(c.getString(9));
		}
		
		
		show_image();
	send.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			
			if (txtmessage.getText().length()==0){
				txtmessage.setError("Please input a message");
			
			}
			else {
			AlertDialog.Builder build=new AlertDialog.Builder(Viewapplicant.this);
			build.setMessage("Choose Operation");
			build.setCancelable(false);
			
			build.setPositiveButton("Send and exit",new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					SQLiteDatabase db=openOrCreateDatabase("APTK",Context.MODE_PRIVATE,null);
					String subject,reference,message,icnumber;
					icnumber=txtic.getText().toString();
					subject=txtsubject.getText().toString();
					reference=txtreference.getText().toString();
					message=txtmessage.getText().toString();
					Date today=new Date();
					String daterep=today.toString();
					
					db.execSQL("INSERT INTO applicantinbox (subject,reference,message,datereplied,icnumber)"
							+ " VALUES('"+ subject +"','"+ reference +"',"
							+ "'"+ message +"','"+ daterep +"',"+ icnumber +")");
					
					finish();
				}
			});
			
			build.setNegativeButton("Cancel and exit",new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					startActivity(new Intent(Viewapplicant.this,Companyinbox.class));
					finish();
				}
			});
			
		
			AlertDialog alert=build.create();
			alert.show();
			}
		}
	});
	

	view.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String username=txtic.getText().toString();
			Intent in=new Intent(Viewapplicant.this,Showcert.class);
			in.putExtra("useric", username);
			startActivity(in);
		}
	});
	}
	

	
	public void show_image() {
		ImageView profile=(ImageView) findViewById(R.id.imageView1);
		
	
		String sdcard=Environment.getExternalStorageDirectory().getAbsolutePath();
		String useric=txtic.getText().toString();
		String path=sdcard+ "/aptkimages/applicant/"+ useric  + ".png";
		File img=new File(path);
		if (img.exists()){
		
			profile.setImageBitmap(BitmapFactory.decodeFile(path));
		}
		else {
			
		}
		
		
	}
}
