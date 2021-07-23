package com.example.aptkjobs;

import java.util.ArrayList;

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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Applicant extends Activity{
ListView joblist;
String log;
ArrayList<Integer> jobid;
ArrayList <String> jobs;
ArrayAdapter<String> arrayadapter;
TextView search;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.applicant);
		
		Intent ii=getIntent();
		Bundle bd=ii.getExtras();
		log=bd.getString("logged");
		TextView ic=(TextView) findViewById(R.id.txtv2);
		search=(TextView) findViewById(R.id.txtsearch);
		ic.setText("Logged in as: " +log);
		
		jobs=new ArrayList<String>();
		jobid=new ArrayList<Integer>();
		joblist=(ListView) findViewById(R.id.joblist2);
		search_jobs("all");
		
		search.addTextChangedListener(new TextWatcher() {
		
		
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				arrayadapter.clear();
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				arrayadapter.clear();
				search_jobs(search.getText().toString());
			}
		});
		
		
		joblist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				int js=jobid.get(arg2);
				
				SQLiteDatabase db=openOrCreateDatabase("APTK",Context.MODE_PRIVATE,null);
			
				Cursor x=db.rawQuery("SELECT * FROM applyjob WHERE jobid="+ js +" AND appic="+ log +"", null);
				
				if (x.moveToFirst()){
				
					show_message(js);
				}	
				else {
					Intent in=new Intent(Applicant.this,Apply.class);
					in.putExtra("jobid",Integer.toString(js));
					in.putExtra("icno", log);
					startActivity(in);	
				}
					
			
			}
		});
		
	}
	
	public void search_jobs(String key) {
	
		SQLiteDatabase db=openOrCreateDatabase("APTK",Context.MODE_PRIVATE,null);
		Cursor c;
		if (key.equals("all")) {
			c=db.rawQuery("SELECT jobid,jobtitle FROM jobs", null);
		}
		else {
		c=db.rawQuery("SELECT jobid,jobtitle FROM jobs WHERE jobtitle LIKE '%"+ key +"%'", null);
		}
		
			while (c.moveToNext()){
				jobs.add("Job ID: " + c.getString(0) + "\nJob Title:" + c.getString(1));
				jobid.add(Integer.parseInt(c.getString(0)));
			}
		
		
		arrayadapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_single_choice,jobs);
		
		joblist.setAdapter(arrayadapter);
	}
	public void show_message(final int s) {
		AlertDialog.Builder build=new AlertDialog.Builder(Applicant.this);
		build.setMessage("You already applied for this job.Cancel you application?");
		build.setCancelable(false);
		build.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				SQLiteDatabase db=openOrCreateDatabase("APTK",Context.MODE_PRIVATE,null);
				db.delete("applyjob","jobid="+ s +"",null);
				Toast.makeText(getApplicationContext(),"Application Cancelled",Toast.LENGTH_SHORT).show();
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
	
	public void ask_message() {
	  AlertDialog.Builder b=new AlertDialog.Builder(Applicant.this);
	  b.setMessage("Are you sure you want to sign out?");
	  b.setCancelable(false);
	  b.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		
		@Override
		public void onClick(DialogInterface arg0, int arg1) {
			// TODO Auto-generated method stub
			startActivity(new Intent(Applicant.this,Main.class));
			Applicant.this.finish();
			
		}
	});
	  b.setNegativeButton("No", new DialogInterface.OnClickListener() {
		
		@Override
		public void onClick(DialogInterface arg0, int arg1) {
			// TODO Auto-generated method stub
		arg0.cancel();
		}
	});
	  
	  AlertDialog alert=b.create();
	  alert.show();
	}
}
