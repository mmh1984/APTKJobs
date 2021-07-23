package com.example.aptkjobs;

import java.util.ArrayList;

import javax.xml.datatype.Duration;

import com.example.aptkjobs.R.id;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.ListActivity;

public class Company extends Activity{
	ArrayList <Integer> jobid;
	ListView joblist;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.company);
		ArrayList <String> jobs=new ArrayList<String>();
		jobid=new ArrayList<Integer>();
		TextView t1=(TextView) findViewById(R.id.txtv1);
		String log=get_user();
		t1.setText("Logged in as: " + log);
		joblist=(ListView) findViewById(R.id.joblist);
		SQLiteDatabase db=openOrCreateDatabase("APTK",Context.MODE_PRIVATE,null);
		Cursor c=db.rawQuery("SELECT * FROM jobs WHERE addedby=" + log  + "", null);
		
		
			while (c.moveToNext()){
				jobs.add("Job ID: " + c.getString(0) + "\nJob Title:" + c.getString(1));
				jobid.add(Integer.parseInt(c.getString(0)));
			}
		
		
		ArrayAdapter<String> arrayadapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_single_choice,jobs);
		
		joblist.setAdapter(arrayadapter);
		
		joblist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				String selected=(String) joblist.getItemAtPosition(arg2);
				int js=jobid.get(arg2);
				Intent in=new Intent(Company.this,Viewjob.class);
				in.putExtra("jobid",Integer.toString(js));
				startActivity(in);
			}
		});
		
	}

	

	public String get_user() {
		Intent get=getIntent();
		Bundle b=get.getExtras();
		String user=b.getString("logged");
		
		return user;
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater menu1=getMenuInflater();
		menu1.inflate(R.menu.companymenu, menu);
		return true;
	}
	
	
	
	public void show_message() {
		AlertDialog.Builder b=new AlertDialog.Builder(Company.this);
		b.setMessage("Are you sure you want to exit?");
		b.setCancelable(false);
		b.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int arg1) {
				// TODO Auto-generated method stub
				startActivity(new Intent(Company.this,Main.class));
				Company.this.finish();
			}
		});
		
		b.setNegativeButton("No", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int arg1) {
				// TODO Auto-generated method stub
				dialog.cancel();
			}
		});
		AlertDialog alert=b.create();
		b.show();
	}
	
}
