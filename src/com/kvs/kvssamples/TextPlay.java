package com.kvs.kvssamples;

import java.util.Random;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

public class TextPlay extends Activity implements View.OnClickListener{

	Button chkButton;
	ToggleButton passToggle;
	EditText input;
	EditText content;
	TextView display;
	String shareName = "Test shared name";
	SharedPreferences someData;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.text);
		
		InitializeComponent();
		
		passToggle.setOnClickListener(this);
		chkButton.setOnClickListener(this);
		
		someData = getSharedPreferences(shareName, 0);
		
	}

	private void InitializeComponent() {
		chkButton = (Button) findViewById(R.id.btnResult);
		passToggle = (ToggleButton) findViewById(R.id.chkPassword);
		input = (EditText)findViewById(R.id.editCommands);
		content = (EditText)findViewById(R.id.editContent);
		display = (TextView)findViewById(R.id.textResult);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.btnResult:

			// TODO Auto-generated method stub
			String check = input.getText().toString();
			if(check.contentEquals("left"))
			{
				display.setGravity(Gravity.LEFT);
			}
			else if(check.contentEquals("right"))
			{
				display.setGravity(Gravity.RIGHT);
			}
			else if(check.contentEquals("center"))
			{
				display.setGravity(Gravity.CENTER);
			}
			else if(check.contentEquals("blue"))
			{
				display.setTextColor(Color.BLUE);
			}
			else if(check.contentEquals("WTF"))
			{
				Random nRandom = new Random();
				display.setText("WTF!!");
				display.setTextSize(nRandom.nextInt(75));
				display.setTextColor(Color.rgb(nRandom.nextInt(255),nRandom.nextInt(255), nRandom.nextInt(255)));
				
				switch(nRandom.nextInt(3)){
				case 0:
					display.setGravity(Gravity.LEFT);
					break;
				case 1:
					display.setGravity(Gravity.RIGHT);
					break;
				case 2:
					display.setGravity(Gravity.CENTER);
					break;
				}
			}
			else if(check.contentEquals("save"))
			{
				// testing shared preference
				display.setText("SaveShared");
				String stringData = content.getText().toString();
				SharedPreferences.Editor editor = someData.edit();
				editor.putString("sharedString", stringData);
				editor.commit();
			}
			else if(check.contentEquals("load"))
			{
				// testing shared preference (load)
				display.setText("loadshared!!!");
				someData = getSharedPreferences(shareName, 0);
				String dataReturned = someData.getString("sharedString", "Couldn't load");
				display.setText(dataReturned);
			}
			else
			{
				display.setText("invalid");
				display.setGravity(Gravity.CENTER);
				display.setTextColor(Color.WHITE);
			}
			break;
		case R.id.chkPassword:
			if(passToggle.isChecked())
			{
				input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
			}
			else
			{
				input.setInputType(InputType.TYPE_CLASS_TEXT);
			}
			break;
		}
	}

}
