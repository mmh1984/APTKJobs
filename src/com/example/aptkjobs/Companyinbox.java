package com.example.aptkjobs;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Companyinbox extends Activity {
	ListView inbox;
	String log,id;
	ArrayList<Integer> appid;
	ArrayList<Integer> refid;
	ArrayList<String> jobtitle;
	int index;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.companyinbox);
		
		Intent ii=getIntent();
		Bundle bd=ii.getExtras();
		log=bd.getString("logged");
		TextView ic=(TextView) findViewById(R.id.txtcin);
		ic.setText("Logged in as: " +log);
		ArrayList <String> message=new ArrayList<String>();
		
		appid=new ArrayList<Integer>();
		refid=new ArrayList<Integer>();
		jobtitle=new ArrayList<String>();
		inbox=(ListView) findViewById(R.id.companyin);
		
		SQLiteDatabase db=openOrCreateDatabase("APTK",Context.MODE_PRIVATE,null);
		Cursor c=db.rawQuery("SELECT * FROM applyjob WHERE regno="+ log +"", null);
		String icno="";
		String title="";
			while (c.moveToNext()){
				id=c.getString(0);
				icno=c.getString(4);
				title=c.getString(6);
				message.add("Job Title: " + title +"\nIC: " + icno + "\nName: " + c.getString(3));
				appid.add(Integer.parseInt(icno));
				refid.add(Integer.parseInt(c.getString(1)));
				jobtitle.add(title);
			}
		
		
		ArrayAdapter<String> arrayadapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_single_choice,message);
		
		inbox.setAdapter(arrayadapter);
		inbox.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				index=arg2;
				AlertDialog.Builder build=new AlertDialog.Builder(Companyinbox.this);
				build.setMessage("Choose Operation");
				build.setCancelable(false);
				build.setPositiveButton("Open message",new DialogInterface.OnClickListener() {
				
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						Intent in=new Intent(Companyinbox.this,Viewapplicant.class);
						in.putExtra("icno",Integer.toString(appid.get(index)));
						in.putExtra("jobid",Integer.toString(refid.get(index)));
						in.putExtra("regno",log);
						in.putExtra("jobtitle", jobtitle.get(index));
						startActivity(in);
						//finish();
					}
				});
				
				build.setNegativeButton("Delete Message",new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						SQLiteDatabase db=openOrCreateDatabase("APTK",Context.MODE_PRIVATE,null);
						db.delete("applyjob","id="+ id + "",null);
						finish();
						
						
					}
				});
				
				build.setNeutralButton("Cancel",new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						
					}
				});
			
				AlertDialog alert=build.create();
				alert.show();
				
			}
		});
				
	}
	
	
}
