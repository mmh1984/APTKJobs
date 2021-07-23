package com.example.aptkjobs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;



import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class Empreg extends Activity implements OnClickListener {
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
Button save,cancel;
Spinner ice;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.empreg);
		txtic=(EditText) findViewById(R.id.owneric);
		txtreg=(EditText) findViewById(R.id.registration);
		txtcompany=(EditText) findViewById(R.id.company);
		txtowner=(EditText) findViewById(R.id.owner);
		txtaddress=(EditText) findViewById(R.id.caddress);
		txtpassword=(EditText) findViewById(R.id.cpassword);
		txtdesc=(EditText) findViewById(R.id.description);
		txtregdate=(EditText) findViewById(R.id.datereg);
		ice=(Spinner) findViewById(R.id.ice);
		txtoffice=(EditText) findViewById(R.id.cphone);
		txtemail=(EditText) findViewById(R.id.cemail);
		Button save=(Button) findViewById(R.id.btnSavec);
		Button cancel=(Button) findViewById(R.id.btnCancelc);
		
		
		cancel.setOnClickListener(this);
		save.setOnClickListener(this);

		
		
		
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
		
		txtreg.addTextChangedListener(new TextWatcher() {
			
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
				if(txtreg.getText().length()==0){
					txtreg.setError("Registration number required");
				}
				else {
				SQLiteDatabase db=openOrCreateDatabase("APTK",Context.MODE_PRIVATE,null);
				Cursor c=db.rawQuery("SELECT * FROM company WHERE regno='" + txtreg.getText().toString() + "'" , null);
				if (c.moveToFirst()){
					txtreg.setError("Registration number already taken");
				}
				}
			}
		});
		
	}
	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		if(v.getId()==R.id.btnSavec){
			if (check_ic()==true){
				save_record();
			}
			else {
				Toast.makeText(getApplicationContext(),"Registration number already taken",Toast.LENGTH_SHORT).show();
			}
			
		}
		else if(v.getId()==R.id.btnCancelc){
			AlertDialog.Builder build=new AlertDialog.Builder(Empreg.this);
			build.setMessage("Cancel this operation?");
			build.setCancelable(false);
			build.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					startActivity(new Intent(Empreg.this,Main.class));
					Empreg.this.finish();
				}
			});
			
			build.setNegativeButton("No", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
				   dialog.cancel();
					
				}
			});
			
			AlertDialog alert=build.create();
			alert.show();
		}
	}
	
public boolean check_ic() {
	boolean x;
	
	SQLiteDatabase db=openOrCreateDatabase("APTK",Context.MODE_PRIVATE,null);
	Cursor c=db.rawQuery("SELECT * FROM company WHERE regno='" + txtreg.getText().toString() + "'" , null);
	if (c.moveToFirst()){
		txtreg.setError("Registration number already taken");
		x=false;
	}
	else {
		x=true;
	}
	return x;
}	

public void save_record() {
	SQLiteDatabase db=openOrCreateDatabase("APTK", Context.MODE_PRIVATE,null);
	
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
	int rating=0;
	
	String insert="INSERT INTO company VALUES("+ reg +",'" + company + "','" + owner + "',"
			+ "" + IC + ",'" + password + "','" + iccolor + "','" + address + "','" + desc + "',"
					+ "'" + regdate + "','" + office + "','" + email + "'," + rating + ")";
	

	db.execSQL(insert);
	AlertDialog.Builder build=new AlertDialog.Builder(Empreg.this);
	build.setMessage("Employee Record saved");
	build.setCancelable(false);
	build.setPositiveButton("Proceed to Login",new DialogInterface.OnClickListener() {
		
		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			startActivity(new Intent(Empreg.this,Main.class));
			
		Empreg.this.finish();
			
		}
	});
	
	AlertDialog alert=build.create();
	alert.show();
	
}


}


