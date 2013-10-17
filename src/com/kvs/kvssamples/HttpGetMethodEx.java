package com.kvs.kvssamples;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.widget.Toast;

public class HttpGetMethodEx {
	
	public String getInternetData() throws Exception{
		BufferedReader in = null;
		String data = null;
		try{
			
			HttpClient httpclient = new DefaultHttpClient();
			URI website = new URI("http://www.google.com");
			HttpGet httpget = new HttpGet(website);
			        
			httpget.setURI(website);
			
			HttpResponse response = httpclient.execute(httpget);
			
			in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			
			StringBuffer sb = new StringBuffer("");
			String l = "";
			String nl = System.getProperty("line.separator");
			
			while((l=in.readLine())!=null){
				sb.append(l + nl);
			}
			in.close();
			data =sb.toString(); 
			return data;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(in != null)
			{
				try{
					in.close();
					return data;
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		return "error";
	}
}
