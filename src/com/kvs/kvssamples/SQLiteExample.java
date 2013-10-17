package com.kvs.kvssamples;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SQLiteExample extends Activity implements View.OnClickListener{
	Button sqlUpdate, sqlView;
	EditText sqlName, sqlHotness;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sqliteexample);
		
		sqlUpdate = (Button) findViewById(R.id.bSQLUpdate);
		sqlView = (Button) findViewById(R.id.bSQLOpenView);
		sqlName = (EditText) findViewById(R.id.etSQLName);
		sqlHotness = (EditText) findViewById(R.id.etSQLHotness);
		
		sqlView.setOnClickListener(this);
		sqlUpdate.setOnClickListener(this);
	}
	@Override
	public void onClick(View arg0) {
		switch( arg0.getId()){
		case R.id.bSQLUpdate:
			boolean didItWork = true;
			try{
			String name = sqlName.getText().toString();
			String hotness = sqlHotness.getText().toString();
			
			HotOrNot entry = new HotOrNot(SQLiteExample.this);
			entry.open();
			entry.createEntry(name, hotness);
			entry.close();
			
			Dialog d = new Dialog(this);
			d.setTitle("Heck Yea!");
			TextView tv = new TextView(this);
			tv.setText("Success!");
			
			}
			catch (Exception e){
				didItWork = false;
			}finally{
				if(didItWork){
					Toast.makeText(getApplicationContext(),
				    	      "Success!! "
				    	      , Toast.LENGTH_LONG).show();
					
					Dialog d = new Dialog(this);
					d.setTitle("Heck Yea!");
					TextView tv = new TextView(this);
					tv.setText("Success");
				}
				else
				{
					Toast.makeText(getApplicationContext(),
				    	      "fail!! "
				    	      , Toast.LENGTH_LONG).show();
					
					Dialog d = new Dialog(this);
					d.setTitle("Heck Yea!");
					TextView tv = new TextView(this);
					tv.setText("FAILED!!!");
				}
			}
			break;
		case R.id.bSQLOpenView:
			Intent i = new Intent("com.kvs.kvssamples.SQLView");
			startActivity(i);
			break;
			
		}
		
	}
}
