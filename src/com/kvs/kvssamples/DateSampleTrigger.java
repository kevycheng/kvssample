package com.kvs.kvssamples;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DateSampleTrigger extends Activity implements View.OnClickListener{

	Button start;
	EditText eventName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.datesampletrigger);
		
		start = (Button)findViewById(R.id.btnLauncher);
		eventName = (EditText)findViewById(R.id.etName);
		
		start.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.btnLauncher:
			// bundle and launch activity
			String bread = eventName.getText().toString();
			Bundle basket = new Bundle();
			basket.putString("eventname", bread);
			Intent a = new Intent(DateSampleTrigger.this, DateSample.class);
			a.putExtras(basket);
			startActivity(a);
			break;
		}
	}

}
