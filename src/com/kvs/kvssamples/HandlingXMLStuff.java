package com.kvs.kvssamples;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class HandlingXMLStuff extends DefaultHandler{

	XMLDataCollected info = new XMLDataCollected();
	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		//super.startElement(uri, localName, qName, attributes);
		if(localName.equals("city"))
		{
			String city = attributes.getValue("name");
			info.setCity(city);
		}
		else if(localName.equals("temperature")){
			String strtemp = attributes.getValue("value");
			int temp = Integer.parseInt(strtemp);
			temp = (int) (temp - 275.15);
			info.setTemp(temp);
		}
	}

	public String getInformation()
	{
		return info.dataToString();
	}
}
