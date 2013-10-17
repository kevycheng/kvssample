package com.kvs.kvssamples;

import java.util.Calendar;

//import com.jaf.examples.datedata.DbAdapter;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

public class DateSample extends Activity implements View.OnClickListener{

	TextView event;
	TextView year;
	TextView month;
	TextView date;
	TextView time;
	TextView daydiff;
	Button update;
	TextView logtime;
	Button btnLog;
	
	String strEvent;
	
	//private DbAdapter mDbHelper;
	//SharedPreferences prefs;// = getSharedPreferences("datesample", 0);
	SharedPreferences LogPref;
	
	private Calendar mDate = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.datesample);
		
		InitializeComponent();
		
		update.setOnClickListener(this);
		btnLog.setOnClickListener(this);
	}

	private void InitializeComponent() {
		
		event = (TextView)findViewById(R.id.tvEvent);
		year = (TextView)findViewById(R.id.tvYear);
		month = (TextView)findViewById(R.id.tvMonth);
		date = (TextView)findViewById(R.id.tvDate);
		time = (TextView)findViewById(R.id.tvTime);
		daydiff = (TextView)findViewById(R.id.tvDayDiff);
		update = (Button) findViewById(R.id.btnUpdate);
		btnLog = (Button) findViewById(R.id.btnLog);
		logtime = (TextView)findViewById(R.id.tvLogtime);
		
		Calendar rightNow = Calendar.getInstance();
		UpdateCurrentTime(rightNow);
		
		// get bundle here.
		Bundle eventBundle = getIntent().getExtras();
		if(eventBundle != null)
		{
			strEvent = eventBundle.getString("eventname");
			event.setText(strEvent);
		}
    	
    	//question.setText(gotBread);
    	
		int Logdate = LoadDateDate();

		//long datelog = prefs.getLong("datelog", 0);
		if(Logdate >0)
		{
			// set log time
			String stringValue = Integer.toString(Logdate);
			
        	logtime.setText(stringValue);
        	
        	// convert date to calendar
        	
        	int nLastYear = Logdate/10000;
        	int nLastMonth = (Logdate/100)%100;
        	int nLastDay = Logdate%100;
        	
        	Calendar c = Calendar.getInstance();
        	c.set(Calendar.YEAR, nLastYear);
        	c.set(Calendar.MONTH, nLastMonth);
        	c.set(Calendar.DAY_OF_MONTH, nLastDay);
        	
        	// compare with logged time.
        	int dates = rightNow.get(Calendar.DAY_OF_MONTH);
    		int years = rightNow.get(Calendar.YEAR);
    		int months = rightNow.get(Calendar.MONTH); // note, janurary == 0.
    		
    		int nDayDiff = 365*(years - nLastYear)+ 30*(months - nLastMonth) + (dates - nLastDay);
        	if(nDayDiff > 0)
        	{
        		stringValue = Integer.toString(nDayDiff);
        		daydiff.setText(stringValue);
        	}
        	
        	//UpdateCurrentTime(c);
		}
	}

	private int LoadDateDate() {
		LogPref = getSharedPreferences(strEvent, 0);
		
		long datelog = LogPref.getLong("datelog", 0);
		return (int)datelog;
	}
	
	private void SaveDateData(int nDateWrap) {
		SharedPreferences.Editor editor = LogPref.edit();
		editor.putLong("datelog", (long)nDateWrap);
		editor.commit();
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.btnUpdate:
			Calendar rightNow = Calendar.getInstance();
			UpdateCurrentTime(rightNow);
			break;
		case R.id.btnLog:
			showDatePicker();
			//UpdateCurrentTime();
			break;
			
		}
		
	}

	private void showDatePicker() {
		DatePickerDialog dialog;
		
		if (mDate == null){
    		final Calendar c = Calendar.getInstance();

    		dialog = new DatePickerDialog(
                    this, mDateSetListener, 
                    c.get(Calendar.YEAR),
                    c.get(Calendar.MONTH),
                    c.get(Calendar.DAY_OF_MONTH)
                    );
    	} else {
    	
            dialog = new DatePickerDialog(
	                this, mDateSetListener, 
	                mDate.get(Calendar.YEAR),
	                mDate.get(Calendar.MONTH),
	                mDate.get(Calendar.DAY_OF_MONTH)
	                );
    	}
    	
    	dialog.show();
	}

	private void UpdateCurrentTime(Calendar rightNow) {
		//Calendar rightNow = Calendar.getInstance();
		
		int dates = rightNow.get(Calendar.DAY_OF_MONTH);
		int years = rightNow.get(Calendar.YEAR);
		int months = rightNow.get(Calendar.MONTH); // note, janurary == 0.
		
		String stringValue = Integer.toString(years);
		
		year.setText(stringValue);
		
		switch(months+1)
		{
			case 1:
				month.setText("January");
				break;
			case 2:
				month.setText("Feb.");
				break;
			case 3:
				month.setText("march");
				break;
			case 4:
				month.setText("April");
				break;
			case 5:
				month.setText("May");
				break;
			case 6:
				month.setText("June");
				break;
			case 7:
				month.setText("July");
				break;
			case 8:
				month.setText("Aug.");
				break;
			case 9:
				month.setText("September");
				break;
			case 10:
				month.setText("Octorber");
				break;
			case 11:
				month.setText("Novenber");
				break;
			case 12:
				month.setText("December");
				break;
		}
		
		stringValue = Integer.toString(dates);
		date.setText(stringValue);
		
		Time today = new Time(Time.getCurrentTimezone());
		today.setToNow();
		time.setText(today.format("%k:%M:%S"));
		
	}

	DatePickerDialog.OnDateSetListener mDateSetListener =
	        new DatePickerDialog.OnDateSetListener() 
	        {
	            public void onDateSet(DatePicker view, int year, int monthOfYear,
	                    int dayOfMonth) 
	            {
	            	Calendar c = Calendar.getInstance();
	            	c.set(Calendar.YEAR, year);
	            	c.set(Calendar.MONTH, monthOfYear);
	            	c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
					
	            	//UpdateCurrentTime(c);
	            	
	            	int nDateWrap = dayOfMonth + 100*monthOfYear + 10000*year;
	            	
	            	String stringValue = Integer.toString(nDateWrap);
	            	logtime.setText(stringValue);
	            	//mDbHelper.updateDateRecord(dateId, c);
	            	
	            	SaveDateData(nDateWrap);
	            	//mListCursor.requery();
	            	//mListCursor.moveToFirst();
	                //populateDate();
	            }

				
	    };
}
