package com.example.aptkjobs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Ediapplicant extends Activity implements OnClickListener{
	String icnumber="";
	String fullname="";
	String DOB="";
	String gender="";
	String address="";
	String race="";
	String religion="";
	String qualification="";
	String pass="";
	String email="";
	String password="";
	String phone="";
	EditText txtic,txtname,txtadd,txtpassword,txtemail,txtphone,dt;

	Spinner g,r,rg,q,ica;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editapplicant);
		
		Button update=(Button) findViewById(R.id.eabtnUpdate);
		Button cancel=(Button) findViewById(R.id.eabtnUpdate);
		
		
		Intent in=getIntent();
		Bundle bn=in.getExtras();
		icnumber=bn.getString("logged");
		 txtic=(EditText) findViewById(R.id.eaicnumber);
		 txtname=(EditText) findViewById(R.id.eafullname);
		 dt=(EditText) findViewById(R.id.eadob);
		 g=(Spinner) findViewById(R.id.eagender);
		 txtadd=(EditText) findViewById(R.id.eatxtaddress);
		r=(Spinner) findViewById(R.id.earace);
		 rg=(Spinner) findViewById(R.id.eareligion);
		 ica=(Spinner) findViewById(R.id.eaica);
		 q=(Spinner) findViewById(R.id.eaqualification);
		 txtpassword=(EditText) findViewById(R.id.eacpassword);
		 txtemail=(EditText) findViewById(R.id.eaemail);
		 txtphone=(EditText) findViewById(R.id.eaphone);
		 
		 txtic.setText(icnumber);
		 
		 Button delete=(Button) findViewById(R.id.eabtndelete);
		 
		 update.setOnClickListener(this);
		 cancel.setOnClickListener(this);
		 delete.setOnClickListener(this);
		 
		SQLiteDatabase db=openOrCreateDatabase("APTK",Context.MODE_PRIVATE,null);
		Cursor c=db.rawQuery("SELECT * FROM applicant WHERE icnumber="+ icnumber +"", null); 
		while (c.moveToNext()){
			
			txtpassword.setText(c.getString(1));
			txtname.setText(c.getString(2));
			txtemail.setText(c.getString(3));
			txtphone.setText(c.getString(4));
			dt.setText(c.getString(5));
			txtadd.setText(c.getString(7));
			
			
		}
		 
		 
		 txtphone.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if(txtphone.getText().length()==0 || txtphone.getText().length() > 11)	{
					txtphone.setError("Phone number must be valid");
				}
			}
		});
		 
		 txtemail.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				if (!txtemail.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+")) {

                  txtemail.setError("Invalid Email Address");

               }
				
			}
		});
		 
		 txtpassword.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if(txtpassword.getText().length()==0 || txtpassword.getText().length() > 10 || txtpassword.getText().length()<6){
					txtpassword.setError("Password must not be empty or greater than 10 charcters.Minimum of 6 Characters");
				}
				
			}
		});
		 
		
		txtname.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				if (txtname.getText().length() ==0){
					txtname.setError("name is required");
				}
				
			}
		});
		
		
		
	}
	@Override
	public void onClick(View arg0) {
		switch(arg0.getId()){
		case R.id.eabtnUpdate:
			update_applicant();
			break;
			
		case R.id.eabtndelete:
			show_message();
			break;
		
		case R.id.eabtnCancel:
			finish();
			break;
		}
		
	}
	public void show_message() {
		AlertDialog.Builder build=new AlertDialog.Builder(Ediapplicant.this);
		build.setMessage("Delete your account? Your job post will be deleted as well");
		build.setCancelable(false);
		build.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
			String appic=txtic.getText().toString();
			SQLiteDatabase db=openOrCreateDatabase("APTK",Context.MODE_PRIVATE,null);
			db.delete("applicant","icnumber=" + appic +"",null);
			db.delete("applicantinbox","icnumber=" + appic +"",null);
			db.delete("applyjob","appic=" + appic +"",null);
			finish();
			startActivity(new Intent(Ediapplicant.this,Main.class));
			}
		});
		
		build.setNegativeButton("No",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
			arg0.cancel();	
			}
		});
		
		AlertDialog alert=build.create();
		alert.show();
	}
	
	public void update_applicant(){
		SQLiteDatabase db = openOrCreateDatabase("APTK",Context.MODE_PRIVATE,null);
		icnumber=txtic.getText().toString();
		password=txtpassword.getText().toString();
		fullname=txtname.getText().toString();
		email=txtemail.getText().toString();
		phone=txtphone.getText().toString();
		DOB=dt.getText().toString();
		gender=g.getSelectedItem().toString();
		address=txtadd.getText().toString();
		race=r.getSelectedItem().toString();
		religion=rg.getSelectedItem().toString();
		qualification=q.getSelectedItem().toString();
		
		db.execSQL("UPDATE applicant SET password='"+ password +"',fullname='"+ fullname +"',"
				+ "email='"+ email +"',phone='"+ phone +"',dob='"+ DOB +"',gender='"+ gender +"',address='"+address+"',"
						+ "race='"+ race +"',religion='"+ religion +"',qualification='"+ qualification +"' WHERE "
								+ "icnumber=icnumber");
		
		Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
		startact();
		
	}
	
public void startact() {
		
		Intent in=new Intent(Ediapplicant.this,Applicant.class);
		in.putExtra("loggedas",icnumber);
		startActivityForResult(in, 1);
		finish();
	}
}
