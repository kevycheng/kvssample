package com.kvs.kvssamples;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class OpenedClass  extends Activity  implements View.OnClickListener, OnCheckedChangeListener {

	TextView question, test;
	Button returnData;
	RadioGroup selectionList;
	String gotBread, sendData;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.send);
		initialization();
		
		
		SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        //boolean bMusic = getPrefs
        String str = getPrefs.getString("Name", "Kevy default string");
        String strValue = getPrefs.getString("list", "4");
        
        if(strValue.contentEquals("1"))
        {
        	question.setText(str);
        }
        else
        {
        	
        	Bundle gotBasket = getIntent().getExtras();
        	gotBread = gotBasket.getString("key");
        	question.setText(gotBread);
        }
	}

	private void initialization() {
		question = (TextView)findViewById(R.id.tvQuestion);
		test = (TextView)findViewById(R.id.tvText);
		returnData = (Button)findViewById(R.id.bReturn);
		returnData.setOnClickListener(this);
		selectionList = (RadioGroup)findViewById(R.id.rgAnswers);
		selectionList.setOnCheckedChangeListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.bReturn:
			Intent person = new Intent();
			Bundle backpack = new Bundle();
			backpack.putString("answer", sendData);
			person.putExtras(backpack);
			setResult(RESULT_OK, person);
			finish();
			break;
		}
		
	}

	@Override
	public void onCheckedChanged(RadioGroup arg0, int arg1) {
		// TODO Auto-generated method stub
		switch(arg1){
		case R.id.rCrazy:
			sendData = "Probably Right!";
			break;
		case R.id.rSexy:
			sendData = "Definitely Right!";
			break;
		case R.id.rBoth:
			sendData = "Spot on !";
			break;
		
		}
		test.setText(sendData);
		
	}
	
}
