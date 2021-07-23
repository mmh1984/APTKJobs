package com.example.aptkjobs;

import java.util.Random;

import android.app.Activity;
import android.content.Context;
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
import android.widget.TextView;
import android.widget.Toast;

public class Addnews extends Activity {
	//global variables
EditText title,edate,evenue,eaddress,edetails;
TextView id;
int strid;
	@Override
//main activity
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.addnews);
	//assign the controls to the variables
	title=(EditText) findViewById(R.id.txtnewstitle);
	edate=(EditText) findViewById(R.id.txtnewsdate);
	evenue=(EditText) findViewById(R.id.txtnewsvenue);
	eaddress=(EditText) findViewById(R.id.txtnewsaddress);
	edetails=(EditText) findViewById(R.id.txtnewsdetails);
	TextView id=(TextView) findViewById(R.id.lblNewsID);
	Button savenews=(Button) findViewById(R.id.btnSaveNews);
	
	//if button savenews is clicked
	savenews.setOnClickListener(new OnClickListener() {
		
		@Override
		//save the contents of the news to the database
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String newsid,newstitle,ndate,venue,address,description,regno;
			newsid=Integer.toString(strid);
			newstitle=title.getText().toString();
			ndate=edate.getText().toString();
			venue=evenue.getText().toString();
			description=edetails.getText().toString();
			address=eaddress.getText().toString();
			regno=get_user();
			
			SQLiteDatabase db=openOrCreateDatabase("APTK",Context.MODE_PRIVATE,null);
			db.execSQL("INSERT INTO news (newsid,newstitle,newsdate,newsvenue,newsaddress,newsdescription,companyreg) VALUES("
					+ ""+ newsid +",'"+ newstitle +"','"+ ndate +"','"+ venue +"','"+address +"','"+ description +"',"+ regno +")");
		
			Toast.makeText(getApplicationContext(),"POST added",Toast.LENGTH_LONG).show();
			Intent in=new Intent(Addnews.this,News.class);
			in.putExtra("logged", get_user());
			startActivity(in);
			finish();
		}
	});
	
	strid=get_id(); //get the id from procedure get_id()
	id.setText("REFERENCE NO:" + strid);
	
	
	//the contents of this section is to validate each input when the user types
	title.addTextChangedListener(new TextWatcher() {
		
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
			if(title.getText().length()==0){
				title.setError("Title is required");
			}
		}
	});
	
	edate.addTextChangedListener(new TextWatcher() {
		
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
			if (edate.getText().length()==0){
				edate.setError("Date required");
			}
		}
	});
	
	evenue.addTextChangedListener(new TextWatcher() {
		
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
			if(evenue.getText().length()==0){
				evenue.setError("Venue required");
			}
		}
	});
	
	eaddress.addTextChangedListener(new TextWatcher() {
		
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
			
			if(eaddress.getText().length()==0){
				eaddress.setError("Address required");
			}
		}
	});
	
	edetails.addTextChangedListener(new TextWatcher() {
		
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
			if(edetails.getText().length()==0){
				edetails.setError("Required");
			}
		}
	});
	
}

public int get_id() {
	
	
	boolean flag=true;
	SQLiteDatabase db=openOrCreateDatabase("APTK",Context.MODE_PRIVATE, null);
	int x=0;

	while (flag){
	
	Cursor c=db.rawQuery("SELECT newsid FROM news WHERE newsid=" + x + "", null);
		if(c.moveToFirst()){
			flag=true;
		}
		else {
			flag=false;
			x++;	
			
		}
	}
	
	return x;
}

public String get_user() {
	Intent ii=getIntent();
	Bundle bn=ii.getExtras();
	String user=bn.getString("logged");
	return user;
}
}
