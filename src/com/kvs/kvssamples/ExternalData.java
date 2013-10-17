package com.kvs.kvssamples;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ExternalData extends Activity implements OnItemSelectedListener, View.OnClickListener {
	
	private TextView canWrite, canRead;
	private String state;
	boolean canW, canR;
	Spinner spinner;
	String[] paths = { "Music", "Pictures", "Download" };
	File path = null;
	File file = null;
	EditText saveFile;
	Button confirm, save;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.externaldata);
		canWrite = (TextView)findViewById(R.id.tvCanWrite);
		canRead = (TextView)findViewById(R.id.tvCanRead);
		saveFile = (EditText)findViewById(R.id.etPath);
		confirm = (Button)findViewById(R.id.bConfirm);
		save =  (Button)findViewById(R.id.bSave);
		
		confirm.setOnClickListener(this);
		save.setOnClickListener(this);
		
		canWrite.setText("Unknown");
		canRead.setText("Unknown");

		checkState();
		
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(ExternalData.this, android.R.layout.simple_spinner_item, 
				paths);
		
		spinner = (Spinner)findViewById(R.id.spinner1);
		spinner.setAdapter(adapter);
		
		spinner.setOnItemSelectedListener(this);
		
	}

	private void checkState() {
		// TODO Auto-generated method stub
		state = Environment.getExternalStorageState();
		
		if(state.equals(Environment.MEDIA_MOUNTED)){
			// read and write
			canWrite.setText("TRUE");
			canRead.setText("TRUE");
			canW = canR = true;
		}
		else if(state.equals(Environment.MEDIA_MOUNTED_READ_ONLY)){
			// read and write
			canWrite.setText("False");
			canRead.setText("TRUE");
			canW = false;
			canR = true;
		}
		else {
			// read and write
			canWrite.setText("False");
			canRead.setText("False");
			canW = canR = false;
		}
	}


	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		int position = spinner.getSelectedItemPosition();
		switch (position)
		{
		case 0:
			path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
			break;
		case 1:
			path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
			break;
		case 2:
			path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
			break;
		}
	}


	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	public void onClick(View arg0){
		
		switch(arg0.getId()){
		case R.id.bSave:
			String strPath = saveFile.getText().toString();
			file = new File(path, strPath);
			
			checkState();
			
			if(canW == canR == true)
			{
				path.mkdirs();
				
				try {
					InputStream is = getResources().openRawResource(R.drawable.papers);
					OutputStream os = new FileOutputStream(file);
					byte[] data = new byte[is.available()];
					is.read(data);
					os.write(data);
					
					is.close();
					os.close();
					
					Toast t = Toast.makeText(ExternalData.this, "File saved", Toast.LENGTH_LONG);
					t.show();
					
					MediaScannerConnection.scanFile(ExternalData.this, 
							new String[] {file.toString()},
							null,
							new MediaScannerConnection.OnScanCompletedListener(){

								@Override
								public void onScanCompleted(String arg0,
										Uri arg1) {
									// TODO Auto-generated method stub
									Toast t = Toast.makeText(ExternalData.this, "scan completed", Toast.LENGTH_SHORT);
									t.show();
								}
					});
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			break;
		case R.id.bConfirm:
			save.setVisibility(View.VISIBLE);
			break;
		}
	}
	
}
