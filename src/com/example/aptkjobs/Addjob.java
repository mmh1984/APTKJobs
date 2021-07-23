
//the function of this class is to add a job of each company
package com.example.aptkjobs;

import java.util.Date;
import java.util.Random;

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

public class Addjob extends Activity implements OnClickListener{
	//Declare all global varaibles here
String jobtitle,jobdesc,jobtype,startdate,enddate,requirements,salary,jobid;
EditText txtjob,txtdesc,txtstart,txtend,txtsalary;
Spinner sjobtype,squalification;
TextView txtref;
Button save,cancel;
String addedby;
	@Override
	//this section will load all the contents and loading the add job activity
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addjob); //set the view to the addjob xml (display)
		Date today=new Date(); //get today's date
		//get all the id of the controls and save it to the global variables (blue)
		txtref=(TextView) findViewById(R.id.refno);
		txtref.setText(get_id());
		txtjob=(EditText) findViewById(R.id.jobtitle);
		txtdesc=(EditText) findViewById(R.id.jobdesc);
		txtstart=(EditText) findViewById(R.id.startdate);
		txtend=(EditText) findViewById(R.id.enddate);
		txtsalary=(EditText) findViewById(R.id.salary);
		sjobtype=(Spinner) findViewById(R.id.jobtype);
		squalification=(Spinner) findViewById(R.id.jobqualification);
		Button save=(Button) findViewById(R.id.btnSaveJ);
		Button cancel=(Button) findViewById(R.id.btnCancelJ);
		txtstart.setText(today.toString());
		
		save.setOnClickListener(this); //when save button is clicked
		cancel.setOnClickListener(this); //when cancel button is clicked
		
	}
	//this function will generate a random number as ID and cehck it from the database.if the ID
	//is taken then it will generate until the id is unique
	public String get_id() {
		
		Random r=new Random();
		boolean flag=true;
		SQLiteDatabase db=openOrCreateDatabase("APTK",Context.MODE_PRIVATE, null);
		int x=0;
	
		while (flag){
			x=r.nextInt(1000000); //generate random number from 0 to 1000000
		
		Cursor c=db.rawQuery("SELECT jobid FROM jobs WHERE jobid=" + x + "", null);
			if(c.moveToFirst()){
				flag=true;
			}
			else {
				flag=false;
				
			}
		}
		
		return Integer.toString(x);
		
	}
	
	//this procedure will check which button is clicked
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId()==R.id.btnCancelJ){ //if cancel is clicked
			show_message(); //go to procedure show_message()
		}
		if (v.getId()==R.id.btnSaveJ){ //if save is clicked
			save_record(); //go to procedure save_record()
		}
	}
	
	//this procedure will display the alert message notifying the user 
	public void show_message(){
		AlertDialog.Builder b=new AlertDialog.Builder(Addjob.this);
		b.setMessage("Cancel this operation?"); //the message of the dialog
		b.setCancelable(false);
		b.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			//if yes then exit this activity
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				Addjob.this.finish();
			}
		});
		
		b.setNegativeButton("No", new DialogInterface.OnClickListener() {
			//if the user clicked no thenit will cancel the dialog
			@Override
			public void onClick(DialogInterface dialog, int arg1) {
				// TODO Auto-generated method stub
				dialog.cancel();
			}
		});
		
		AlertDialog alert=b.create();
		alert.show(); //show the alert dialog
	}
	
	//this procedure will save all the contents to the database APTK
	public void save_record() {
		jobtitle=txtjob.getText().toString();
		jobdesc=txtdesc.getText().toString();
		jobtype=sjobtype.getSelectedItem().toString();
		startdate=txtstart.getText().toString();
		enddate=txtend.getText().toString();
		requirements=squalification.getSelectedItem().toString();
		salary=txtsalary.getText().toString();
		jobid=txtref.getText().toString();
		Intent get=getIntent();
		Bundle bd=get.getExtras();
		addedby=bd.getString("logged");
		
		SQLiteDatabase db=openOrCreateDatabase("APTK",Context.MODE_PRIVATE, null);
		db.execSQL("INSERT INTO jobs(jobid,jobtitle,jobdesc,jobtype,sdate,edate,requirements,salary,addedby) VALUES("+jobid+",'"+ jobtitle +"','"+ jobdesc +"','"+ jobtype +"',"
				+ "'"+ startdate +"','"+ enddate +"','"+ requirements +"',"+ salary +",'" + addedby + "')");
		
		AlertDialog.Builder b=new AlertDialog.Builder(Addjob.this);
		b.setMessage("Jobs Added");
		b.setCancelable(false);
		b.setPositiveButton("Post another job?",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int arg1) {
				// TODO Auto-generated method stub
				dialog.cancel();
			}
		});
		
		b.setNegativeButton("No",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int arg1) {
				// TODO Auto-generated method stub
				Intent in=new Intent(Addjob.this,Companymenu.class);
				finish();
			
				
				
			}
		});
		
		AlertDialog alert=b.create();
		alert.show();
	}
}
