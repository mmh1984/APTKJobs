package com.example.aptkjobs;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.widget.*;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Appreg extends Activity implements OnClickListener{

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
		setContentView(R.layout.appreg);
		Button save=(Button) findViewById(R.id.btnSave);
		Button cancel=(Button) findViewById(R.id.btnCancel);
		
		save.setOnClickListener(this);
		cancel.setOnClickListener(this);
		txtic=(EditText) findViewById(R.id.icnumber);
		 txtname=(EditText) findViewById(R.id.fullname);
		 dt=(EditText) findViewById(R.id.dob);
		 g=(Spinner) findViewById(R.id.gender);
		 txtadd=(EditText) findViewById(R.id.txtaddress);
		r=(Spinner) findViewById(R.id.race);
		 rg=(Spinner) findViewById(R.id.religion);
		 ica=(Spinner) findViewById(R.id.ica);
		 q=(Spinner) findViewById(R.id.qualification);
		 txtpassword=(EditText) findViewById(R.id.cpassword);
		 txtemail=(EditText) findViewById(R.id.email);
		 txtphone=(EditText) findViewById(R.id.phone);
		 
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
		 dt.addTextChangedListener(new TextWatcher() {
			
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
				if(dt.getText().length()!=10){
					dt.setError("Invalid date (dd/MM/yyyy)");
				}
				else {
					if (check_date(dt.getText().toString())){
						
					}
					else {
						dt.setError("age must be between 18-60");
					}
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
		 
		 txtic.addTextChangedListener(new TextWatcher() {
			
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
				if(txtic.getText().length()==0 || txtic.getText().length()!=8){
					txtic.setError("Invalid IC number");
					
				}
				else {
					SQLiteDatabase db=openOrCreateDatabase("APTK",Context.MODE_PRIVATE,null);
					Cursor c=db.rawQuery("SELECT * FROM applicant WHERE icnumber=" + txtic.getText().toString() + "" , null);
					if (c.moveToFirst()){
						txtic.setError("IC number already registered");
					}
					
					Cursor x=db.rawQuery("SELECT * FROM company WHERE regno=" + txtic.getText().toString() + "" , null);
					if (x.moveToFirst()){
						txtic.setError("IC number already registered");
					}
					
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
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId()==R.id.btnSave){
			if (check_ic()==true){
			
			if (check_date(dt.getText().toString())==true){	
			save_record();
			}
			else {
				Toast.makeText(getApplicationContext(),"Invalid date (dd/MM/yyyy)",Toast.LENGTH_SHORT).show();
			}
			}
			else {
				Toast.makeText(getApplicationContext(),"IC number already taken",Toast.LENGTH_SHORT).show();
			}
		}
		else if(v.getId()==R.id.btnCancel){
			show_message();
		}
	}
	
	public boolean check_ic () {
		boolean y;
		y=true;
		SQLiteDatabase db=openOrCreateDatabase("APTK",Context.MODE_PRIVATE,null);
		Cursor c=db.rawQuery("SELECT * FROM applicant WHERE icnumber=" + txtic.getText().toString() + "" , null);
		if (c.moveToFirst()){
			txtic.setError("IC number already registered");
			y=false;
		}
		
		Cursor x=db.rawQuery("SELECT * FROM company WHERE regno=" + txtic.getText().toString() + "" , null);
		if (x.moveToFirst()){
			txtic.setError("IC number already registered");
			y=false;
		}
		return y;
	}
	
	public void show_message() {
		AlertDialog.Builder build=new AlertDialog.Builder(Appreg.this);
		build.setMessage("Cancel this operation?");
		build.setCancelable(false);
		build.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				startActivity(new Intent(Appreg.this,Main.class));
				Appreg.this.finish();
				
			}
		});
		
		build.setNegativeButton("No",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
			 dialog.cancel();	
			}
		});
		
		AlertDialog alert=build.create();			
		alert.show();
	}

	public void save_record() {
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
		
		String add="INSERT INTO applicant VALUES(" + icnumber + ",'" + password +"','" + fullname +"',"
				+ "'" + email +"','" + phone +"','" + DOB +"','" + gender +"','" + address +"',"
						+ "'" + race +"','" + religion +"','" + qualification +"')";
		
		db.execSQL(add);
		AlertDialog.Builder build=new AlertDialog.Builder(Appreg.this);
		build.setMessage("Succesfully Registered your account");
		build.setCancelable(false);
		build.setPositiveButton("Proceed to login",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
			
				startActivity(new Intent(Appreg.this,Main.class));
				Appreg.this.finish();
				
			}
		});
		
		AlertDialog alert=build.create();
		alert.show();
		
	}
	
	public boolean check_date(String d){
		boolean flag=true;
		if (d.length()!=10){
			Toast.makeText(getApplicationContext(),"Invalid date (dd/MM/yyyy)",Toast.LENGTH_SHORT).show();
			flag=false;
		}
		else {
		Date now=Calendar.getInstance().getTime();
	
		DateFormat dt=new SimpleDateFormat("dd/MM/yyyy");
		String today=dt.format(now);
		int yeartoday=Integer.parseInt(today.substring(6));
		int yob=Integer.parseInt(d.substring(6));
		int age=yeartoday-yob;
		
		if(age < 18 || age >60){
			flag=false;
			Toast.makeText(getApplicationContext(),"Error: your age( " + age + ") Age must be between 18-60",Toast.LENGTH_SHORT).show();
		}
		else {
			flag =true;
		}
		
		}
		return flag;
	}
}


