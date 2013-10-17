package com.kvs.kvssamples;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class InternalData extends Activity implements OnClickListener{

	EditText sharedData;
	TextView dataResults;
	FileOutputStream fos;
	String fileName = "InternalString.txt";
	TextView internalPath;
	TextView internalCache;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.internaldata);
		setupVariables();
	}

	private void setupVariables() {
		Button save = (Button) findViewById(R.id.btnSave);
		Button load = (Button) findViewById(R.id.btnLoad);
		sharedData = (EditText)findViewById(R.id.editInput);
		dataResults = (TextView)findViewById(R.id.textResult);
		internalPath = (TextView)findViewById(R.id.textPath);
		internalCache = (TextView)findViewById(R.id.textCache);
		save.setOnClickListener(this);
		load.setOnClickListener(this);
		/*
		try {
			fos = openFileOutput(fileName, Context.MODE_PRIVATE);
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			Toast t = Toast.makeText(this, "openFileOutput, File not Found!", Toast.LENGTH_LONG);
			t.show();
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		// print out internal path
		String strInternalPath = getFilesDir().getPath();
		//Toast t = Toast.makeText(this, strInternalPath, Toast.LENGTH_LONG);
		//t.show();
		internalPath.setText(strInternalPath);
		
		String strInternalCache = getCacheDir().getPath();
		internalCache.setText(strInternalCache);
		
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.btnSave:
			String data = sharedData.getText().toString();
			// Saving data via file
			/*
			File f = new File(fileName);
			try {
				fos = new FileOutputStream(f);
				fos.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/
			try {
				fos = openFileOutput(fileName, Context.MODE_PRIVATE);
				fos.write(data.getBytes());
				fos.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case R.id.btnLoad:
			/*String collected = null;
			FileInputStream fis = null;
			try {
				fis = openFileInput(fileName);
				byte[] dataArray = new byte[fis.available()];
				while(fis.read(dataArray) != -1)
				{
					collected = new String(dataArray);
				}
				fis.close();
				dataResults.setText(collected);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/
			// async task below
			new loadSomeStuff().execute(fileName);
			break;
	
		}
		
	}

	public class loadSomeStuff extends AsyncTask<String, Integer, String>
	{
		ProgressDialog dlg;
		
		protected void onPreExecute(){
			dlg = new ProgressDialog(InternalData.this);
			//dlg.setProgressStyle()
			dlg.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			dlg.setMax(100);
			dlg.show();
		}
		
		

		@Override
		protected String doInBackground(String... arg0) {
			String collected = null;
			FileInputStream fis = null;
			
			for(int i = 0; i<20; i++){
				publishProgress(5);
				try {
					Thread.sleep(44);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			dlg.dismiss();
			
			try {
				fis = openFileInput(fileName);
				byte[] dataArray = new byte[fis.available()];
				while(fis.read(dataArray) != -1)
				{
					collected = new String(dataArray);
				}
				fis.close();
				return collected;
				//dataResults.setText(collected);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
		protected void onProgressUpdate(Integer...progress){
			dlg.incrementProgressBy(progress[0]);
		}
		
		protected void onPostExecute(String result){
			dataResults.setText(result);
		}
	}
	
}
