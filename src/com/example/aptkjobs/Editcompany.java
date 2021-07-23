package com.example.aptkjobs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
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

public class Editcompany extends Activity implements OnClickListener {
	String reg="";
	String company="";
	String owner="";
	String IC="";
	String iccolor="";
	String address="";
	String desc="";
	String regdate="";
	String password="";
	String office="";
	String email="";
	EditText txtreg,txtcompany,txtowner,txtic,txtaddress,txtregdate,txtpassword,txtdesc,txtoffice,txtemail;
	
	Spinner ice;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editcompany);
		
		
		txtic=(EditText) findViewById(R.id.eowneric);
		txtreg=(EditText) findViewById(R.id.eregistration);
		txtcompany=(EditText) findViewById(R.id.ecompany);
		txtowner=(EditText) findViewById(R.id.eowner);
		txtaddress=(EditText) findViewById(R.id.ecaddress);
		txtpassword=(EditText) findViewById(R.id.ecpassword);
		txtdesc=(EditText) findViewById(R.id.edescription);
		txtregdate=(EditText) findViewById(R.id.edatereg);
		ice=(Spinner) findViewById(R.id.eice);
		txtoffice=(EditText) findViewById(R.id.ecphone);
		txtemail=(EditText) findViewById(R.id.ecemail);
		
		
		Intent in=getIntent();
		Bundle b=in.getExtras();
		String no=b.getString("logged");
		
		SQLiteDatabase db=openOrCreateDatabase("APTK",Context.MODE_PRIVATE,null);
		Cursor c=db.rawQuery("SELECT * FROM company WHERE regno="+ no +"",null);
		
		while (c.moveToNext()){
			txtreg.setText(c.getString(0));
			txtcompany.setText(c.getString(1));
			txtowner.setText(c.getString(2));
			txtic.setText(c.getString(3));
			txtpassword.setText(c.getString(4));
			txtaddress.setText(c.getString(6));
			txtdesc.setText(c.getString(7));
			txtregdate.setText(c.getString(8));
			txtoffice.setText(c.getString(9));
			txtemail.setText(c.getString(10));
			
			
		}
		
		Button update=(Button) findViewById(R.id.ebtnUpdate);
		Button cancel=(Button) findViewById(R.id.ebtnCancel);
		Button delete=(Button) findViewById(R.id.ebtnDelete);
		
		update.setOnClickListener(this);
		cancel.setOnClickListener(this);
		delete.setOnClickListener(this);

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
				if (txtpassword.getText().length()==0 || txtpassword.getText().length() > 10 ){
					txtpassword.setError("Password must not be more than 10 characters");
				}
			}
		});
		
		
		txtaddress.addTextChangedListener(new TextWatcher() {
			
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
				if (txtaddress.getText().length()==0){
					txtaddress.setError("Address is required");
				}
			}
		});
		
		
		txtowner.addTextChangedListener(new TextWatcher() {
			
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
				if (txtowner.getText().length()==0){
					txtowner.setError("Owener name is required");
				}
			}
		});
		
		
		
		txtcompany.addTextChangedListener(new TextWatcher() {
			
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
			if(txtcompany.getText().length()==0){
				txtcompany.setError("Company name is required");
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
				txtic.setError("IC number must be 8 digits");
			}
				
			}
		});
		
		
	
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId()==R.id.ebtnUpdate){
			update_company();
		}
		else if(v.getId()==R.id.ebtnCancel){
			finish();
		}
		else if(v.getId()==R.id.ebtnDelete){
			show_message();
		}
	}
	
	public void show_message() {
		AlertDialog.Builder build=new AlertDialog.Builder(Editcompany.this);
		build.setMessage("Delete your account? Your job post will be deleted as well");
		build.setCancelable(false);
		build.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
			String registration=txtreg.getText().toString();
			SQLiteDatabase db=openOrCreateDatabase("APTK",Context.MODE_PRIVATE,null);
			db.delete("company","regno="+ registration +"",null);
			db.delete("jobs","addedby="+ registration +"",null);
			db.delete("news","companyreg="+ registration +"",null);	
			db.delete("applyjob","regno="+ registration +"",null);
			db.delete("applicantinbox","reference LIKE '%"+ registration +"%'",null);
			finish();
			startActivity(new Intent(Editcompany.this,Main.class));
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
	
	public void update_company() {
		
		reg=txtreg.getText().toString();
		company=txtcompany.getText().toString();
		owner=txtowner.getText().toString();
		IC=txtic.getText().toString();
		password=txtpassword.getText().toString();
		iccolor=ice.getSelectedItem().toString();
		address=txtaddress.getText().toString();
		desc=txtdesc.getText().toString();
		regdate=txtregdate.getText().toString();
		office=txtoffice.getText().toString();
		email=txtemail.getText().toString();
		
		SQLiteDatabase db=openOrCreateDatabase("APTK",Context.MODE_PRIVATE,null);
		db.execSQL("UPDATE company SET cname='"+ company +"',oname='" + owner +"',oic='"+ IC +"',"
				+ "opass='" + password +"',iccolor='" + iccolor +"',address='" + address +"',companydesc='" + desc +"',"
						+ "datereg='" + regdate +"',officeno='" + office +"',email='" + email +"'"
				+ " WHERE regno=" + reg + "");
		
		
		
		Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
		startact();
	}
	
	public void startact() {
		
		Intent in=new Intent(Editcompany.this,Company.class);
		in.putExtra("loggedas",reg);
		startActivityForResult(in, 1);
		finish();
	}
	
	
}
