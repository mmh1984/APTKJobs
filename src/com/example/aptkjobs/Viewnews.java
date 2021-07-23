package com.example.aptkjobs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Viewnews extends Activity {
	TextView newsid,newstitle,newsdate,newsvenue,newsaddress,newsdescription;
	String company="";
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.viewnews);
	
	newsid=(TextView) findViewById(R.id.newsid);
	newstitle=(TextView) findViewById(R.id.newstitle);
	newsdate=(TextView) findViewById(R.id.newsdate);
	newsvenue=(TextView) findViewById(R.id.newsvenue);
	newsaddress=(TextView) findViewById(R.id.newsvadd);
	newsdescription=(TextView) findViewById(R.id.newsDescription);
	Button delete=(Button) findViewById(R.id.btndeletenews);
	SQLiteDatabase db=openOrCreateDatabase("APTK",Context.MODE_PRIVATE,null);
	Cursor c=db.rawQuery("SELECT * FROM news WHERE newsid="+ get_newsid() + "",null);
	
	while (c.moveToNext()){
		newsid.setText(c.getString(0).toString());
		newstitle.setText(c.getString(1).toString());
		newsdate.setText(c.getString(2).toString());
		newsvenue.setText(c.getString(3).toString());
		newsaddress.setText(c.getString(4).toString());
		newsdescription.setText(c.getString(5).toString());
		company=c.getString(6);
	}
	
	if (get_user().equals("guest")){
		delete.setVisibility(View.GONE);
	}
	
	else if (get_user().equals("admin")){
	delete.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			show_message();
		}
	});
	}
	
}


public void show_message() {
	AlertDialog.Builder build=new AlertDialog.Builder(Viewnews.this);
	build.setMessage("Delete this news?");
	build.setCancelable(false);
	build.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
		
		@Override
		public void onClick(DialogInterface arg0, int arg1) {
			// TODO Auto-generated method stub
		String appic=get_newsid();
		SQLiteDatabase db=openOrCreateDatabase("APTK",Context.MODE_PRIVATE,null);
		db.delete("news","newsid=" + appic +"",null);
		finish();
		
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


public String get_newsid() {
	Intent ii=getIntent();
	Bundle bn=ii.getExtras();
	String user=bn.getString("newsid");
	return user;
}

public String get_user() {
	Intent ii=getIntent();
	Bundle bn=ii.getExtras();
	String user=bn.getString("user");
	return user;
}
}
