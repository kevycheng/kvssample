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
	Button sqlUpdate, sqlView, sqlEdit, sqlDelete, sqlGetInfo;
	EditText sqlName, sqlHotness, sqlRowID;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sqliteexample);
		
		sqlUpdate = (Button) findViewById(R.id.bSQLUpdate);
		sqlView = (Button) findViewById(R.id.bSQLOpenView);
		sqlName = (EditText) findViewById(R.id.etSQLName);
		sqlHotness = (EditText) findViewById(R.id.etSQLHotness);
		
		sqlRowID = (EditText) findViewById(R.id.etRowID);
		sqlEdit = (Button) findViewById(R.id.btnEdit);
		sqlDelete = (Button) findViewById(R.id.btnDelete);
		sqlGetInfo = (Button) findViewById(R.id.btnGetInfo);
		
		sqlView.setOnClickListener(this);
		sqlUpdate.setOnClickListener(this);
		
		sqlGetInfo.setOnClickListener(this);
		sqlEdit.setOnClickListener(this);
		sqlDelete.setOnClickListener(this);
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
		
			}
			catch (Exception e){
				didItWork = false;
				String error = e.toString();
				
				Dialog d = new Dialog(this);
				d.setTitle(error);
				TextView tv = new TextView(this);
				tv.setText("FAILED!!!");
				d.setContentView(tv);
				d.show();
				
			}finally{
				if(didItWork){
					Toast.makeText(getApplicationContext(),
				    	      "Success!! "
				    	      , Toast.LENGTH_LONG).show();
					
					Dialog d = new Dialog(this);
					d.setTitle("Heck Yea!");
					TextView tv = new TextView(this);
					tv.setText("Success");
					d.setContentView(tv);
					d.show();
				}
				else
				{
				
					
				}
			}
			break;
		case R.id.bSQLOpenView:
			Class ourClass;
			try {
				ourClass = Class.forName("com.kvs.kvssamples.SQLView");
				Intent openIntent = new Intent(this, ourClass);
				
				startActivity(openIntent);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
			
		case R.id.btnGetInfo:
			String s = sqlRowID.getText().toString();
			long l = Long.parseLong(s);
			
			HotOrNot bon = new HotOrNot(this);
			bon.open();
			String returnName = bon.getName(l);
			String returnHotness = bon.getDate(l);
			bon.close();
			
			sqlName.setText(returnName);
			sqlHotness.setText(returnHotness);
			
			break;
		case R.id.btnEdit:
			String name = sqlName.getText().toString();
			String hotness = sqlHotness.getText().toString();
			
			String sRow = sqlRowID.getText().toString();
			long lrow = Long.parseLong(sRow);
			
			HotOrNot ex = new HotOrNot(this);
			ex.open();
			ex.UpdateEntry(lrow, name, hotness);
			ex.close();
			break;
		case R.id.btnDelete:
			String sRow1 = sqlRowID.getText().toString();
			long lrow1 = Long.parseLong(sRow1);
			
			HotOrNot ex1 = new HotOrNot(this);
			ex1.open();
			ex1.DeleteEntry(lrow1);
			ex1.close();
			break;
			
		}
		
	}
}
