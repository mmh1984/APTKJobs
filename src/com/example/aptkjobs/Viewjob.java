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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Viewjob extends Activity implements OnClickListener {
	String jobtitle,jobdesc,jobtype,startdate,enddate,requirements,salary,jobid;
	EditText txtjob,txtdesc,txtstart,txtend,txtsalary;
	Spinner sjobtype,squalification;
	TextView txtref;
	Button save,cancel;
	String addedby="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewjob);
		
		Intent in=getIntent();
		Bundle b=in.getExtras();
		String ref=b.getString("jobid");
		txtref=(TextView) findViewById(R.id.vrefno);
		txtref.setText(ref);
		txtjob=(EditText) findViewById(R.id.vjobtitle);
		txtdesc=(EditText) findViewById(R.id.vjobdesc);
		txtstart=(EditText) findViewById(R.id.vstartdate);
		txtend=(EditText) findViewById(R.id.venddate);
		txtsalary=(EditText) findViewById(R.id.vsalary);
		sjobtype=(Spinner) findViewById(R.id.vjobtype);
		squalification=(Spinner) findViewById(R.id.vjobqualification);
		Button update=(Button) findViewById(R.id.cUpdate);
		Button delete=(Button) findViewById(R.id.cDelete);
		
		SQLiteDatabase db=openOrCreateDatabase("APTK",Context.MODE_PRIVATE,null);
		Cursor c=db.rawQuery("SELECT * FROM jobs WHERE jobid=" + ref + "",null);
		
		while (c.moveToNext()){
			txtjob.setText(c.getString(1));
			txtdesc.setText(c.getString(2));
			txtstart.setText(c.getString(4));
			txtend.setText(c.getString(5));
			txtsalary.setText(c.getString(7));
			addedby=c.getString(8);
		}
		
		update.setOnClickListener(this);
		delete.setOnClickListener(this);
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.cUpdate:
			update_record();
			break;
		case R.id.cDelete:
			delete_job();
			break;
		}
		
	}
	
	public void update_record() {
		jobtitle=txtjob.getText().toString();
		jobdesc=txtdesc.getText().toString();
		jobtype=sjobtype.getSelectedItem().toString();
		startdate=txtstart.getText().toString();
		enddate=txtend.getText().toString();
		requirements=squalification.getSelectedItem().toString();
		salary=txtsalary.getText().toString();
		jobid=txtref.getText().toString();
		
		
		
		SQLiteDatabase db=openOrCreateDatabase("APTK",Context.MODE_PRIVATE, null);
		db.execSQL("UPDATE jobs SET jobtitle='" + jobtitle + "',jobdesc='" + jobdesc + "',"
				+ "jobtype='" + jobtype + "',sdate='" + startdate + "',edate='" + enddate + "',"
						+ "requirements='" + requirements + "',salary='" + salary + "'"
								+ " WHERE jobid="+ jobid +"");
		
		Toast.makeText(getApplicationContext(),"Job Updated",Toast.LENGTH_SHORT).show();
		startact();
		finish();
	}

	public void delete_job() {
		AlertDialog.Builder b=new AlertDialog.Builder(Viewjob.this);
		b.setMessage("Delete this job?");
		b.setCancelable(false);
		b.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				jobid=txtref.getText().toString();
				SQLiteDatabase db=openOrCreateDatabase("APTK",Context.MODE_PRIVATE,null);
				db.delete("jobs","jobid="+ jobid +"",null);
				startact();
				finish();
			}
		});
		
		b.setNegativeButton("Back to jobs",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				startact();
				finish();
			}
		});
		
		AlertDialog alert=b.create();
		alert.show();
	}
	
	public void startact() {
		Intent in=new Intent(Viewjob.this,Company.class);
		in.putExtra("logged",addedby);
		startActivityForResult(in, 1);
	}
	
}
