package com.example.aptkjobs;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class News extends Activity implements OnClickListener {
ArrayList<String> newstitle;
ArrayList<Integer> newsid;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news);
		TextView count=(TextView) findViewById(R.id.txtnewscount);
		ImageButton post=(ImageButton) findViewById(R.id.imageBtnAddnews);
		post.setOnClickListener(this);
		
		count.setText("You have (" + count_news () + ") post");
		
		newstitle=new ArrayList<String>();
		newsid=new ArrayList<Integer>();
		
		ListView newslist=(ListView) findViewById(R.id.newslist);
		
		SQLiteDatabase db=openOrCreateDatabase("APTK",Context.MODE_PRIVATE,null);
		Cursor c=db.rawQuery("SELECT * FROM news WHERE companyreg="+ get_user() +"", null);
		String id="";
		String title="";
			while (c.moveToNext()){
				id=c.getString(0);
				title=c.getString(1);
				newstitle.add(id + ":" + title + "\nVenue:" + c.getString(3));
				newsid.add(Integer.parseInt(id));
				
			}
		
		
		ArrayAdapter<String> arrayadapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_single_choice,newstitle);
		
		newslist.setAdapter(arrayadapter);
		
		newslist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent ii=new Intent(News.this,Viewnews.class);
				ii.putExtra("newsid",Integer.toString(newsid.get(arg2)));
				ii.putExtra("user","admin");
				startActivity(ii);
				finish();
				
			}
		});
		
	}
	
	
	public int count_news() {
		SQLiteDatabase db=openOrCreateDatabase("APTK",Context.MODE_PRIVATE,null);
		Cursor c=db.rawQuery("SELECT * FROM news WHERE companyreg=" + get_user() + "",null);
		int count=0;
		if (c.moveToFirst()){
			while (c.moveToNext()){
			count++;
			}
		}
		return count;
	}
	public String get_user() {
		Intent ii=getIntent();
		Bundle bn=ii.getExtras();
		String user=bn.getString("logged");
		return user;
	}


	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Intent in=new Intent(News.this,Addnews.class);
		in.putExtra("logged", get_user());
		startActivity(in);
		finish();
	}
}
