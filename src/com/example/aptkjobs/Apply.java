package com.example.aptkjobs;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

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

public class Apply extends Activity implements OnClickListener {

	TextView lbljob,lbljobdesc,lbljobtype,lbledate,lblerequire,lblsalary,lblcompany;
	String job,applicant,applicantic,dateapplied,jobid,addedby,id;
	String qualificationlist[];
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.apply);
		
		lbljob=(TextView) findViewById(R.id.aJob);
		lbljobdesc=(TextView) findViewById(R.id.aJobDesc);
		lbljobtype=(TextView) findViewById(R.id.aJobType);
		lbledate=(TextView) findViewById(R.id.aEdate);
		lblerequire=(TextView) findViewById(R.id.aRequirements);
		lblsalary=(TextView) findViewById(R.id.aSalary);
		lblcompany=(TextView) findViewById(R.id.aCompany);
		load_qualification();
		
		Button btnApply=(Button) findViewById(R.id.btnApply);
		
		
		Intent get=getIntent();
		Bundle bn=get.getExtras();
		jobid=bn.getString("jobid");
		applicantic=bn.getString("icno");
		
		SQLiteDatabase db=openOrCreateDatabase("APTK",Context.MODE_PRIVATE,null);
		Cursor c=db.rawQuery("SELECT * FROM jobs WHERE jobid="+ jobid +"", null);
		
		while (c.moveToNext()){
			lbljob.setText(c.getString(1));
			lbljobdesc.setText(c.getString(2));
			lbljobtype.setText(c.getString(3));
			lbledate.setText(c.getString(5));
			lblerequire.setText(c.getString(6));
			lblsalary.setText(c.getString(7));
			addedby=c.getString(8);
		}
		
		Cursor x=db.rawQuery("SELECT * FROM company WHERE regno="+ addedby +"",null);
		while (x.moveToNext()){
			lblcompany.setText(x.getString(1));
		}
		
		btnApply.setOnClickListener(this);
		
	}
	
	public void load_qualification() {
		qualificationlist=new String[9];
		qualificationlist[0]="1 to 3 O level";
		qualificationlist[1]="4 above O level";
		qualificationlist[2]="A level (1 above)";
		qualificationlist[3]="PND";
		qualificationlist[4]="ND";
		qualificationlist[5]="HNC";
		qualificationlist[6]="HND";
		qualificationlist[7]="Degree";
		qualificationlist[8]="Masters";
		
		
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId()==R.id.btnApply){
			
			
			apply_job();
			
		}
		
		
	}
	
	
	public void apply_job() {
		
		SQLiteDatabase db=openOrCreateDatabase("APTK",Context.MODE_PRIVATE,null);
		String qualification="";
		Cursor c=db.rawQuery("SELECT fullname,qualification FROM applicant WHERE icnumber=" + applicantic + "" ,null);
		while (c.moveToNext()){
			applicant=c.getString(0);
			qualification=c.getString(1);
		}
		id=get_id();
		
	    Date today=new Date();
	    
	   job=lbljob.getText().toString();
	
	   if (check_qualification(lblerequire.getText().toString(),qualification)) {
		   db.execSQL("INSERT INTO applyjob (id,jobid,regno,appname,appic,dateapplied,jobtitle) VALUES ("+ id +",'"+ jobid +"',"
					+ "'"+ addedby +"','"+ applicant +"','"+ applicantic +"','"+ today.toString() +"','" + job +"')");
			
			Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
			Apply.this.finish();
			
		   }
		   else {
			  show_message();
			  finish();
		   }
	   }
	   
	
	
	public boolean check_qualification(String jq,String q){
		boolean flag=true;
		int index1=0;
		int index2=0;
		for (int i=0;i<9;i++){
			if (qualificationlist[i].equals(jq)){
				index1=i;
			}
			if (qualificationlist[i].equals(q)){
				index2=i;
			}
		}
		
		if(index1==index2){
			flag=true;
		
		}
		else if(index1 > index2){
			Toast.makeText(getApplicationContext(),"Your qualification is lower than required",Toast.LENGTH_SHORT).show();
			flag=false;
		}
		else if (index1<index2){
			Toast.makeText(getApplicationContext(),"Your qualification is higher than required",Toast.LENGTH_SHORT).show();
			flag=false;
		}
		
		return flag;
	}
	
	public void show_message() {
		AlertDialog.Builder build=new AlertDialog.Builder(Apply.this);
		build.setMessage("You are not qualified for this job");
		build.setCancelable(false);
		build.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
			finish();
			
			}
		});
		
	
		
		AlertDialog alert=build.create();
		alert.show();
	}

	
public String get_id() {
		
		Random r=new Random();
		boolean flag=true;
		SQLiteDatabase db=openOrCreateDatabase("APTK",Context.MODE_PRIVATE, null);
		int x=0;
	
		while (flag){
			x=r.nextInt(1000000);
		
		Cursor c=db.rawQuery("SELECT id FROM applyjob WHERE id=" + x + "", null);
			if(c.moveToFirst()){
				flag=true;
			}
			else {
				flag=false;
				
			}
		}
		
		return Integer.toString(x);
		
	}
}
