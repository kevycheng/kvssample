package com.kvs.kvssamples;

public class XMLDataCollected {
	int temp = 0;
	String strCity = null;
	
	public void setCity(String c){
		strCity = c;
	}
	
	public void setTemp(int t){
		temp = t;
	}
	
	public String dataToString(){
		return "In "+ strCity + " the current temperature in F is " + temp + "degree.";
	}
}
