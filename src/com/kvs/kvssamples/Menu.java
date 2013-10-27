package com.kvs.kvssamples;

//import org.codechimp.apprater.AppRater;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Menu extends ListActivity{

	String strClassName[] = {"TestDatabaseActivity", "SQLiteExample", "DateSampleTrigger", "ListViewSimple2", "DateSample", "StartingPoint", "TextPlay", "Email", "Camera", "Data", "InternalData", "ExternalData", "ListViewSimple1", "HttpExample"};
	String strDisplayName[] = {"00.TestDatabaseActivity", "01.SQLiteExample", "02.Date launcher", "03.ListView&DB", "Date Sample", "StartingPoint", "TextPlay", "Email", "Camera", "Data", "InternalData", "ExternalData", "ListViewSimple1", "HttpExample"};

	private ArrayAdapter<String> adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_layout);
		
		//adapter = new ArrayAdapter<String>(Menu.this, android.R.layout.simple_list_item_1, strClassName);
		adapter = new ArrayAdapter<String>(Menu.this, R.layout.menu_list_item, R.id.label, strDisplayName);
		//ArrayAdapter<String> adapter = new ArrayAdapter<String>(Menu.this, R.layout.list_item, strClassName);
		setListAdapter(adapter);
		
		// This will keep a track of when the app was first used and whether to show a prompt
		// It should be the default implementation of AppRater
		//AppRater.app_launched(Menu.this);
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		
		String name = strClassName[position];
		
		try{
			Class ourClass = Class.forName("com.kvs.kvssamples."+name);
			
			Intent openIntent = new Intent(Menu.this, ourClass);
			
			startActivity(openIntent);
			 
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
	
		
	}

	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		MenuInflater blowUp = getMenuInflater();
		blowUp.inflate(R.menu.cool_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId())
		{
		case R.id.aboutUS:
			Intent i = new Intent("com.kvs.kvssamples.AboutUS");
			startActivity(i);
			break;
		case R.id.preferences:
			Intent j = new Intent("com.kvs.kvssamples.Prefs");
			startActivity(j);
			break;
		case R.id.exit:
			finish();
			break;
		}
		return false;
	}



}
