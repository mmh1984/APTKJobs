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

public class Viewcompany extends Activity {
ListView company;
ArrayList<String> companyname;
ArrayList<Integer> companyid;
ArrayList<Integer> rating;
ArrayAdapter<String> arrayadapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewcompany);
		TextView user=(TextView) findViewById(R.id.txtloggedas);
		user.setText("Logged as: " + get_user());
		company=(ListView) findViewById(R.id.listView1);
		companyname=new ArrayList<String>();
		companyid=new ArrayList<Integer>();
		rating=new ArrayList<Integer>();
		
		SQLiteDatabase db=openOrCreateDatabase("APTK",Context.MODE_PRIVATE,null);
		Cursor c=db.rawQuery("SELECT * FROM company",null);
		
		
		while (c.moveToNext()){
			companyname.add(c.getString(1) + "\nRating: " + c.getString(11) + " out of 7 stars");
			companyid.add(Integer.parseInt(c.getString(0)));
			
		}
		
		arrayadapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_single_choice,companyname);
		
		company.setAdapter(arrayadapter);
		
		company.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
				show_message(arg2);
			}
		});
	}
	
	public String get_user() {
		Intent ii=getIntent();
		Bundle bn=ii.getExtras();
		String user=bn.getString("logged");
		return user;
	}
	
	public void show_message(final int index) {
		AlertDialog.Builder build=new AlertDialog.Builder(Viewcompany.this);
		build.setMessage("Choose an option");
		build.setCancelable(false);
		build.setPositiveButton("View company profile",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				Intent ii=new Intent(Viewcompany.this,Companyprofile.class);
				ii.putExtra("companyid",Integer.toString(companyid.get(index)));
				ii.putExtra("logged", get_user());
				startActivity(ii);
				finish();
			
			}
		});
		
		build.setNegativeButton("News from this company",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				Intent ii=new Intent(Viewcompany.this,Displaynews.class);
				ii.putExtra("companyid",Integer.toString(companyid.get(index)));
				ii.putExtra("logged", get_user());
				startActivity(ii);
				finish();
			}
		});
		
		AlertDialog alert=build.create();
		alert.show();
	}
}

