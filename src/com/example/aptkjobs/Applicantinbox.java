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
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Applicantinbox extends Activity {
	ListView inbox;
	String log;
	int index;
	ArrayList<String> daterep;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.applicantinbox);
		
		Intent ii=getIntent();
		Bundle bd=ii.getExtras();
		log=bd.getString("logged");
		TextView ic=(TextView) findViewById(R.id.txtain);
		ic.setText("Logged in as: " +log);
		ArrayList <String> message=new ArrayList<String>();
		daterep=new ArrayList<String>();
		inbox=(ListView) findViewById(R.id.applicantin);
		
		SQLiteDatabase db=openOrCreateDatabase("APTK",Context.MODE_PRIVATE,null);
		Cursor c=db.rawQuery("SELECT * FROM applicantinbox WHERE icnumber="+ log +"", null);
		
		while (c.moveToNext()){
			message.add("Subject: " + c.getString(0));
			daterep.add(c.getString(3));
		}
	ArrayAdapter<String> arrayadapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_single_choice,message);
		
		inbox.setAdapter(arrayadapter);
		
		inbox.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				index=arg2;
				AlertDialog.Builder build=new AlertDialog.Builder(Applicantinbox.this);
				build.setMessage("Choose operation:");
				build.setCancelable(false);
				
				build.setPositiveButton("View message",new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						
						Intent in=new Intent(Applicantinbox.this,Viewreply.class);
						in.putExtra("daterep", daterep.get(index));
						startActivity(in);
						finish();
						
					}
				});
				
				build.setNegativeButton("Delete message",new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						
						SQLiteDatabase db=openOrCreateDatabase("APTK",Context.MODE_PRIVATE,null);
						db.delete("applicantinbox","datereplied='" + daterep.get(index) + "'",null);
						finish();
					}
				});
				
				build.setNeutralButton("Cancel",new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						arg0.cancel();
					}
				});
				
				AlertDialog alert=build.create();
				alert.show();
				
			
				
			}
		});
		
	}
}
