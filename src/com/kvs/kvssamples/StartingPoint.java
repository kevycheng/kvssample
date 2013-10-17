package com.kvs.kvssamples;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StartingPoint extends Activity {

	int nCounter;
	Button btnAddone, btnSubone;
	TextView txdisplay;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting_point);
        
        nCounter = 0;
        btnAddone = (Button) findViewById(R.id.btnAdd);
        btnSubone = (Button) findViewById(R.id.btnSub);
        txdisplay = (TextView) findViewById(R.id.textViewDisplay);
        
        btnAddone.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				nCounter++;
				txdisplay.setText("Your total is "+nCounter);
			}
		});
        
        btnSubone.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				nCounter--;
				txdisplay.setText("Your total is "+nCounter);
			}
		});
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.starting_point, menu);
        return true;
    }
    
}
