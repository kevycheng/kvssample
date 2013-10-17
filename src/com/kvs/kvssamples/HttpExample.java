package com.kvs.kvssamples;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class HttpExample extends Activity  implements View.OnClickListener{
	TextView httpStuff;
	EditText strCity;
	Button GetWeather;
	HttpClient client;
	
	final static String URL = "http://api.twitter.com/1/statuses/user_timeline.json?screen_name=";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.httpexample);
		httpStuff = (TextView)findViewById(R.id.tvhttp);
		strCity = (EditText)findViewById(R.id.etCity);
		strCity.setText("Taipei");
		
		GetWeather = (Button)findViewById(R.id.btnGet);
		GetWeather.setOnClickListener(this);	
/*		httpStuff = (TextView)findViewById(R.id.tvhttp);
		HttpGetMethodEx test = new HttpGetMethodEx();
		
		String returned;
		try {
			returned = test.getInternetData();
			httpStuff.setText(returned);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	*/	
		
		// note, httpclient should used in thread( async task )
		//HTTPGetTask task = new HTTPGetTask();
		//HTTPGetWeather task = new HTTPGetWeather();
		//task.execute(new String[]{"Taipei"});
		
		//client = new DefaultHttpClient();
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.btnGet:
			httpStuff.setText("Query Weather...");
			// 1. get city from textview
			String city = strCity.getText().toString();
			
			// 2. run thread to query weather.
			HTTPGetWeather task = new HTTPGetWeather();
			task.execute(new String[]{city});
			break;
		}
	}
	
	public JSONObject lastTweet(String username) throws ClientProtocolException, IOException, JSONException{
		StringBuilder url = new StringBuilder(URL);
		
		HttpGet get = new HttpGet(url.toString());
		
		HttpResponse response = client.execute(get);
		int status = response.getStatusLine().getStatusCode();
		if(status == 200){
			HttpEntity e = response.getEntity();
			String data = EntityUtils.toString(e);
			
			JSONArray timeline = new JSONArray(data);
			JSONObject last = timeline.getJSONObject(0);
			
			return last;
			
		} else {
			Toast.makeText(HttpExample.this, "error", Toast.LENGTH_LONG).show();
			return null;
		}
	}

	private class HTTPGetTask extends AsyncTask<String, Void, String> {
		
		@Override
		protected String doInBackground(String... params) {
			
			HttpGetMethodEx test = new HttpGetMethodEx();
			
			String returned;
			
			try {
				returned = test.getInternetData();
				
				return returned;
			} catch (Exception e) {				
				e.printStackTrace();
			}
			return "HTTPTask Error";
		
		}
		
		protected void onPostExecute(String result){
			httpStuff.setText(result);
		}
	}
	
	private class HTTPGetWeather extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... arg0) {
			WeatherHttpClient test = new WeatherHttpClient();
			
			String returned;
			
			try {
				returned = test.getInternetData(arg0[0], true);

				// get xml reader
				SAXParserFactory spf = SAXParserFactory.newInstance();
				SAXParser sp = spf.newSAXParser();
				XMLReader xr = sp.getXMLReader();
				
				HandlingXMLStuff doingWork = new HandlingXMLStuff();
				xr.setContentHandler(doingWork);
				
				InputSource is = new InputSource(new StringReader(returned));
				
				xr.parse(is);
								
				String information = doingWork.getInformation();
				
				return information;
				//return returned;
			} catch (Exception e) {				
				e.printStackTrace();
			}
			return "HTTPWeather Error";
		}
		
		protected void onPostExecute(String result){
			httpStuff.setText(result);
		}
	}


}
