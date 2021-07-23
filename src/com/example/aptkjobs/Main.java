package com.example.aptkjobs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends Activity{
EditText username,password;
String user="";
String pass="";
RadioButton r1,r2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		create_database();
		username=(EditText) findViewById(R.id.username);
		password=(EditText) findViewById(R.id.loginpassword);
		ImageButton login=(ImageButton) findViewById(R.id.imageBtnLogin);
		Spinner sp=(Spinner) findViewById(R.id.logintype);	
		r1=(RadioButton) findViewById(R.id.rbApplicant);
		r2=(RadioButton) findViewById(R.id.rbCompany);
		
		sp.setOnItemSelectedListener(new OnItemSelectedListener() {
			
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				
				if(arg0.getSelectedItemPosition()==2){
					startActivity(new Intent(Main.this,Empreg.class));
					finish();
				}
				else if(arg0.getSelectedItemPosition()==1){
					startActivity(new Intent(Main.this,Appreg.class));
					finish();
				}
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});

		login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(check_inputs()==true){
					user=username.getText().toString();
					pass=password.getText().toString();
				  if(r1.isChecked()){
					  applicant_login(user,pass);
					  
				  }
				  else if(r2.isChecked()){
					  //Toast.makeText(getApplicationContext(),r2.getText().toString(),Toast.LENGTH_SHORT ).show();
					  company_login(user,pass);
				  }
				}
				
			}
		});
		
	
		
	}
	
	public boolean check_inputs() {
		if (username.getText().length()==0 || username.getText().toString().equals("")){
			username.setError("Username required");
			return false;
		}
		else if(password.getText().length()==0 ||password.getText().toString().equals("")){
			password.setError("Password required");
			return false;
		}
		else {
			return true;
		}
	}
	
	public void applicant_login(String x,String y){
		SQLiteDatabase db=openOrCreateDatabase("APTK",Context.MODE_PRIVATE, null);
		Cursor c=db.rawQuery("SELECT * FROM applicant WHERE icnumber="+ x +" AND password='" + y + "'",null);
		
		if(c.moveToFirst()){
			Toast.makeText(getApplicationContext(),"Login Successfull",Toast.LENGTH_SHORT).show();
			Intent m=new Intent(Main.this,Applicantmenu.class);
			m.putExtra("loggedas", x);
			startActivity(m);
			finish();
			
		}
		else {
			Toast.makeText(getApplicationContext(),"Login failed.Username/password not found",Toast.LENGTH_SHORT).show();
		
		}
	}
	
	public void company_login(String x,String y){
		SQLiteDatabase db=openOrCreateDatabase("APTK",Context.MODE_PRIVATE, null);
		Cursor c=db.rawQuery("SELECT * FROM company WHERE regno="+ x +" AND opass='" + y + "'",null);
		
		if(c.moveToFirst()){
			Toast.makeText(getApplicationContext(),"Login Successfull",Toast.LENGTH_SHORT).show();
			Intent m=new Intent(Main.this,Companymenu.class);
			m.putExtra("loggedas", x);
			startActivity(m);
			finish();
		}
		else {
			Toast.makeText(getApplicationContext(),"Login failed.Username/password not found",Toast.LENGTH_SHORT).show();
			
		}
	}
	
	public void create_database() {
		SQLiteDatabase db = openOrCreateDatabase("APTK",Context.MODE_PRIVATE,null);
		String create="CREATE TABLE IF NOT EXISTS applicant(icnumber INT(8),"
				+ "password VARCHAR ,fullname VARCHAR ,email VARCHAR ,"
				+ "phone VARCHAR ,dob VARCHAR ,gender VARCHAR ,address VARCHAR, "
				+ "race VARCHAR ,religion VARCHAR,qualification VARCHAR)";
		db.execSQL(create);
		
		db.execSQL("CREATE TABLE IF NOT EXISTS company(regno INT,cname VARCHAR,oname VARCHAR,"
				+ "oic INT,opass VARCHAR,iccolor VARCHAR,address VARCHAR,"
				+ "companydesc VARCHAR,datereg VARCHAR,officeno VARCHAR,email VARCHAR,rating INT)");
		
		db.execSQL("CREATE TABLE IF NOT EXISTS jobs(jobid INTEGER PRIMARYKEY, jobtitle VARCHAR,jobdesc VARCHAR,jobtype VARCHAR,"
				+ "sdate VARCHAR,edate VARCHAR,requirements VARCHAR,salary INT,addedby VARCHAR)");
		
		db.execSQL("CREATE TABLE IF NOT EXISTS applyjob(id INTEGER PRIMARY KEY,jobid VARCHAR,regno INT,"
				+ "appname VARCHAR,appic VARCHAR,dateapplied VARCHAR,jobtitle VARCHAR)");
		
		db.execSQL("CREATE TABLE IF NOT EXISTS applicantinbox(subject VARCHAR,reference VARCHAR,"
				+ "message VARCHAR,datereplied VARCHAR,icnumber INT)");
		
		db.execSQL("CREATE TABLE IF NOT EXISTS news(newsid INTEGER PRIMARY KEY,newstitle VARCHAR,newsdate VARCHAR,"
				+ "newsvenue VARCHAR,newsaddress VARCHAR,newsdescription VARCHAR,companyreg INTEGER)");
	}
	
	
}
