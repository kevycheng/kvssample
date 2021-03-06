package com.kvs.kvssamples;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.kvs.kvssamples.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ListViewSimple2  extends Activity  implements View.OnClickListener{

	Button sqladd, sqldelete, viewAll, viewCategoy;
	
	MyPerformanceArrayAdapter adapter;
	ArrayList<String> m_listitem = new ArrayList<String>();
	ArrayList<String> m_listsubitem = new ArrayList<String>();
	//private kvsActivityMgr m_Mgr;

	ArrayList<String> m_functionlistitem = new ArrayList<String>();
	MyListButtonArrayAdapter featureadapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listviewsimple1_layout);
		
		Log.e("onCreate", "1");
		
		sqladd = (Button) findViewById(R.id.additem);
		sqldelete = (Button) findViewById(R.id.deleteitem);
		viewAll =  (Button) findViewById(R.id.allItem);
		viewCategoy = (Button) findViewById(R.id.singleItem);
				
		Log.e("onCreate", "2");
		
		final ListView mylistview = (ListView) findViewById(R.id.listview);
		final ListView functionlistview = (ListView) findViewById(R.id.featurelistview);
		
		// load db
		Log.e("onCreate", "3");
		
		kvsActivityMgr m_Mgr = new kvsActivityMgr(ListViewSimple2.this);
		Log.e("onCreate", "3.5");
		
		m_Mgr.open();
		Log.e("onCreate", "4");
		
		List<kvsActivity> activities = m_Mgr.getAllActivities();
		//List<kvsActivity> activities = m_Mgr.getAllActivitiesNonDuplicate();
		
		Log.e("onCreate", "5");
			
		for (int i = 0; i < activities.size(); ++i) {
			kvsActivity item = activities.get(i);
	    	m_listitem.add(item.getActivityName());
	    	m_listsubitem.add(String.valueOf(item.getId()));
	    }
	    
		Log.e("onCreate", "6");
		
	    adapter = new MyPerformanceArrayAdapter(this, m_listitem, m_listsubitem, m_listitem);
	    
	    Log.e("onCreate", "7");
	    
	    // add list like function button.
	    m_functionlistitem.add("FilterVeryNice");
	    m_functionlistitem.add("FilterHateIt");
	    m_functionlistitem.add("FilterCool");
	    featureadapter = new MyListButtonArrayAdapter(this, m_functionlistitem );
	    
	    mylistview.setAdapter(adapter);
	    functionlistview.setAdapter(featureadapter);
	    
	    mylistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

	    	@Override
	    	  public void onItemClick(AdapterView<?> parent, View view,
	    	    int position, long id) {
	    	    Toast.makeText(getApplicationContext(),
	    	      "Click ListItem Number " + position+ ", name = " + m_listitem.get(position)
	    	      + ", Sub name = " + m_listsubitem.get(position)
	    	      , Toast.LENGTH_LONG).show();
	    	    
	    	    // delete item
	    	    RemoveDBAndListViewSameName(position);
	    	  }
	      });	
	    
	    functionlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

	    	@Override
	    	  public void onItemClick(AdapterView<?> parent, View view,
	    	    int position, long id) {
	    		
	    		String strFunction = m_functionlistitem.get(position);
	    		if( strFunction.equalsIgnoreCase("FilterVeryNice"))
	    		{
	    			GetStringItems("Very Nice");
	    		}
	    		else if( strFunction.equalsIgnoreCase("FilterHateIt"))
	    		{
	    			GetStringItems("Hate it");
	    		}
	    		else if( strFunction.equalsIgnoreCase("FilterCool"))
	    		{
	    			GetStringItems("Cool");
	    		}
	    		
	    	    //Toast.makeText(getApplicationContext(),
	    	    //  "Click ListItem Number " + position+ ", name = " + m_functionlistitem.get(position)
	    	    //  , Toast.LENGTH_LONG).show();
	    	  }

	      });	  
	    
	    //functionlistview.setVisibility(View.GONE);
	}
	
	private void GetStringItems(String strName) {
		kvsActivityMgr ex1 = new kvsActivityMgr(this);
		ex1.open();
		List<kvsActivity> activities = ex1.getItemByCategory(strName);
		
		m_listitem.clear();
		m_listsubitem.clear();
		
		for (int i = 0; i < activities.size(); ++i) {
			kvsActivity item = activities.get(i);
	    	m_listitem.add(item.getActivityName());
	    	m_listsubitem.add(String.valueOf(item.getId()));
	    }
		
		ex1.close();
		adapter.notifyDataSetChanged();
	}
	
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.additem:
			// add db and listview
			AddDBAndListView();
			break;
		case R.id.deleteitem:		
			RemoveDBAndListView(0);
			break;
		case R.id.allItem:
			RefreshAllItems();
			break;
		case R.id.singleItem:
			RefreshCategoryItems();
			break;
		}
		
	}

	private void RefreshCategoryItems() {
		kvsActivityMgr ex1 = new kvsActivityMgr(this);
		ex1.open();
		List<kvsActivity> activities = ex1.getAllCategory();
			
		m_listitem.clear();
		m_listsubitem.clear();
		
		for (int i = 0; i < activities.size(); ++i) {
			kvsActivity item = activities.get(i);
	    	m_listitem.add(item.getActivityName());
	    	m_listsubitem.add(String.valueOf(item.getId()));
	    }
		
		ex1.close();	
		
		adapter.notifyDataSetChanged();
	}

	private void RefreshAllItems() {
		
		kvsActivityMgr ex1 = new kvsActivityMgr(this);
		ex1.open();
		List<kvsActivity> activities = ex1.getAllActivities();
			
		m_listitem.clear();
		m_listsubitem.clear();
		
		for (int i = 0; i < activities.size(); ++i) {
			kvsActivity item = activities.get(i);
	    	m_listitem.add(item.getActivityName());
	    	m_listsubitem.add(String.valueOf(item.getId()));
	    }
		
		ex1.close();	
		
		adapter.notifyDataSetChanged();
	}

	private void RemoveDBAndListView(int nIndex) {
		if(m_listsubitem.size() <= nIndex)
			return;
		
		long id = Long.parseLong(m_listsubitem.get(nIndex));
		
		kvsActivityMgr ex1 = new kvsActivityMgr(this);
		ex1.open();
		
		//if(m_listitem.size()>0 && m_listsubitem.size()>0)
		{		
			ex1.DeleteEntryByID(id);
			
			m_listitem.remove(nIndex);
			m_listsubitem.remove(nIndex);
		}
		
		ex1.close();
		
		adapter.notifyDataSetChanged();
	}
	
	private void RemoveDBAndListViewSameName(int nIndex) {
		if(m_listsubitem.size() <= nIndex)
			return;
		
		String strId = m_listitem.get(nIndex);
		
		kvsActivityMgr ex1 = new kvsActivityMgr(this);
		ex1.open();
		
		//if(m_listitem.size()>0 && m_listsubitem.size()>0)
		{		
			ex1.DeleteCategory(strId);
			
			//m_listitem.remove(nIndex);
			//m_listsubitem.remove(nIndex);
		}
		
		Log.e("RemoveDBAndListViewSameName", "RefreshAllItems");
		RefreshAllItems();
		Log.e("RemoveDBAndListViewSameName", "RefreshAllItems2");
		
		ex1.close();
		Log.e("RemoveDBAndListViewSameName", "close");
		
		adapter.notifyDataSetChanged();
	}

	private void AddDBAndListView() {
		
		Log.e("AddDBAndListView", "befoe add...");
		String[] comments = new String[] { "Cool", "Very nice", "Hate it" };
	     int nextInt = new Random().nextInt(3);
	     
/*	     
	     int nNewID = 1; // database start from 1
	     if(m_listitem.size()>0)
	     {
	    	 int nLast = (m_listsubitem.size()-1);
	    	 String nLastID = m_listsubitem.get(nLast);
		     int nID = Integer.parseInt(nLastID);
		     nNewID = nID +1;
	     }
	     
	     String sNewID = String.valueOf(nNewID);
	     
	    m_listitem.add(comments[nextInt]);
	    m_listsubitem.add(sNewID);
	    adapter.notifyDataSetChanged();
	    */
		Log.e("adapter.add", "after adapter.add...");
		
		kvsActivityMgr entry = new kvsActivityMgr(this);
		entry.open();
		entry.createEntry(comments[nextInt], comments[nextInt]);
		
		// refresh all
		RefreshAllItems();
		
		entry.close();
		
		adapter.notifyDataSetChanged();
		
	}


	public class MyListButtonArrayAdapter extends ArrayAdapter<String> {
		  private final Activity context;
		  
		  private final ArrayList<String> names;

		  class ViewHolder {
		    public TextView text;
		    public ImageView image;
		  }
		  
		  public MyListButtonArrayAdapter(Activity context, ArrayList<String> m_listitem) {
		    super(context, R.layout.menu_list_item, m_listitem);
		    this.context = context;
		    this.names = m_listitem;
		  }

		  @Override
		  public View getView(int position, View convertView, ViewGroup parent) {
		    View rowView = convertView;
		    if (rowView == null) {
		      LayoutInflater inflater = context.getLayoutInflater();
		      rowView = inflater.inflate(R.layout.menu_list_item, null);
		      ViewHolder viewHolder = new ViewHolder();
		      viewHolder.text = (TextView) rowView.findViewById(R.id.label);
		      viewHolder.image = (ImageView) rowView
		          .findViewById(R.id.icon);
		      rowView.setTag(viewHolder);
		    }

		    ViewHolder holder = (ViewHolder) rowView.getTag();
		    String s = names.get(position);

		    holder.text.setText(s);

		    return rowView;
		  }
		}
	
	public class MyPerformanceArrayAdapter extends ArrayAdapter<String> {
		  private final Activity context;
		  
		  private final ArrayList<String> names;
		  private final ArrayList<String> names2;
		  private final ArrayList<String> counter;

		  class ViewHolder {
		    public TextView text;
		    public TextView text2;
		    public TextView tvcounter;
		    public ImageView image;
		  }

		  
		  public MyPerformanceArrayAdapter(Activity context, ArrayList<String> m_listitem, ArrayList<String> m_listitem2, ArrayList<String> m_listitem3) {
		    super(context, R.layout.listviewsimple_list_item, m_listitem);
		    this.context = context;
		    this.names = m_listitem;
		    this.names2 = m_listitem2;
		    this.counter = m_listitem3;
		  }

		  @Override
		  public View getView(int position, View convertView, ViewGroup parent) {
		    View rowView = convertView;
		    if (rowView == null) {
		      LayoutInflater inflater = context.getLayoutInflater();
		      rowView = inflater.inflate(R.layout.listviewsimple_list_item, null);
		      ViewHolder viewHolder = new ViewHolder();
		      viewHolder.text = (TextView) rowView.findViewById(R.id.label);
		      viewHolder.text2 = (TextView) rowView.findViewById(R.id.label2);
		      viewHolder.tvcounter = (TextView) rowView.findViewById(R.id.tvCounter);
		      viewHolder.image = (ImageView) rowView
		          .findViewById(R.id.icon);
		      rowView.setTag(viewHolder);
		    }

		    ViewHolder holder = (ViewHolder) rowView.getTag();
		    String s = names.get(position);
		    String s2 = names2.get(position);
		    holder.text.setText(s);
		    holder.text2.setText(s2);
		    if (s.startsWith("Windows7") || s.startsWith("iPhone")
		        || s.startsWith("Solaris")) {
		      holder.image.setImageResource(R.drawable.digg);
		      holder.tvcounter.setText("100");
		    } else {
		      holder.image.setImageResource(R.drawable.delicious);
		    }

		    return rowView;
		  }
		}
}
