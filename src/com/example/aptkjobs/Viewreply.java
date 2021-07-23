package com.example.aptkjobs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

public class Viewreply extends Activity {
TextView subject,reference,message,datereplied;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewreply);
		Intent in=getIntent();
		Bundle bn=in.getExtras();
		String daterep=bn.getString("daterep");
		
		SQLiteDatabase db=openOrCreateDatabase("APTK",Context.MODE_PRIVATE,null);
		Cursor c=db.rawQuery("SELECT * FROM applicantinbox WHERE datereplied='" + daterep + "'",null);
		
		subject=(TextView) findViewById(R.id.repSubject);
		reference=(TextView) findViewById(R.id.repReference);
		message=(TextView) findViewById(R.id.repMessage);
		datereplied=(TextView) findViewById(R.id.repDatesent);
		
		
		while (c.moveToNext()){
			subject.setText(c.getString(0));
			reference.setText(c.getString(1));
			message.setText(c.getString(2));
			datereplied.setText(c.getString(3));
			
		}
		
	}
}
