package com.kvs.kvssamples;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Data  extends Activity  implements View.OnClickListener {

	Button start, startFor;
	EditText sendET;
	TextView gotAnswer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.get);
		initialization();
	}

	private void initialization() {
		// TODO Auto-generated method stub
		start = (Button)findViewById(R.id.bSA);
		startFor = (Button)findViewById(R.id.bSAFR);
		sendET = (EditText)findViewById(R.id.etSend);
		gotAnswer = (TextView)findViewById(R.id.tvGot);
		
		start.setOnClickListener(this);
		startFor.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch(v.getId()){
		case R.id.bSA:
			String bread = sendET.getText().toString();
			Bundle basket = new Bundle();
			basket.putString("key", bread);
			Intent a = new Intent(Data.this, OpenedClass.class);
			a.putExtras(basket);
			startActivity(a);
			break;
		case R.id.bSAFR:
			String bread2 = sendET.getText().toString();
			Bundle basket2 = new Bundle();
			basket2.putString("key", bread2);
			Intent i = new Intent(Data.this, OpenedClass.class);
			i.putExtras(basket2);
			startActivityForResult(i, 0);
			break;
		}
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_OK)
		{
			Bundle basket = data.getExtras();
			String s = basket.getString("answer");
			gotAnswer.setText(s);
		}
	}
	
}
