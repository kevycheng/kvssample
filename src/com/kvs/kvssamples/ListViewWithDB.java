package com.kvs.kvssamples;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kvs.kvssamples.ListViewSimple2.MyListButtonArrayAdapter;
import com.kvs.kvssamples.ListViewSimple2.MyPerformanceArrayAdapter;
import com.kvs.kvssamples.ListViewSimple2.MyListButtonArrayAdapter.ViewHolder;

public class ListViewWithDB extends Activity  implements View.OnClickListener{

	MyPerformanceArrayAdapter adapter;
	MyListButtonArrayAdapter buttonadapter;
	ArrayList<String> m_listitem = new ArrayList<String>();
	ArrayList<String> m_listsubitem = new ArrayList<String>();
	ArrayList<String> m_functionlistitem = new ArrayList<String>();
	boolean m_bShowControl = false;
	ListView functionlistview;
	ListView mylistview;
	
	private String m_Text = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listviewwithdb_layout);
		
		Log.e("onCreate", "1");
		
		Button btnControl;
		
		btnControl =  (Button) findViewById(R.id.functionControl);
		
		mylistview = (ListView) findViewById(R.id.listview);
		functionlistview = (ListView) findViewById(R.id.featurelistview);
		
		Log.e("onCreate", "2");
		
		kvsActivityMgr m_Mgr = new kvsActivityMgr(this);
		
		m_Mgr.open();
		
		Log.e("onCreate", "3");
		
		List<kvsActivity> activities = m_Mgr.getAllActivities();
		
		Log.e("onCreate", "4");
		
		for (int i = 0; i < activities.size(); ++i) {
			kvsActivity item = activities.get(i);
	    	m_listitem.add(item.getActivityName());
	    	m_listsubitem.add(String.valueOf(item.getId()));
	    }
		
		Log.e("onCreate", "5");
		
		adapter = new MyPerformanceArrayAdapter(this, m_listitem, m_listsubitem, m_listitem);
		
		// add list like function button.
		String strClassName[] = {"AddItem", "DeleteFirst", "ShowAll", "ShowCategory", "FilterItem"};
		for(int i = 0; i< strClassName.length; i++)
			m_functionlistitem.add(strClassName[i]);

	    buttonadapter = new MyListButtonArrayAdapter(this, m_functionlistitem );
	    
	    Log.e("onCreate", "6");
	    
	    mylistview.setAdapter(adapter);
	    Log.e("onCreate", "7");
	    functionlistview.setAdapter(buttonadapter);
	    
	    Log.e("onCreate", "8");
	    
	    mylistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

	    	@Override
	    	  public void onItemClick(AdapterView<?> parent, View view,
	    	    int position, long id) {
	    	    Toast.makeText(getApplicationContext(),
	    	      "Click ListItem Number " + position+ ", name = " + m_listitem.get(position)
	    	      + ", Sub name = " + m_listsubitem.get(position)
	    	      , Toast.LENGTH_LONG).show();
	    	    
	    	    // delete item
	    	    //RemoveDBAndListViewSameName(position);
	    	    // show detail view
	    	    AlertDialog.Builder builder = new AlertDialog.Builder(ListViewWithDB.this);
    			builder.setTitle("Entry Info");
    			// Set up the input
    			final TextView input = new TextView(ListViewWithDB.this);
    			// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
    			
    			kvsActivityMgr ex1 = new kvsActivityMgr(ListViewWithDB.this);
    			ex1.open();
    			kvsActivity ac = ex1.getEntryByID(Long.parseLong(m_listsubitem.get(position)));
    			ex1.close();
    			
    			Log.e("getEntryByID", "getEntryByID return");
    			
    			if(ac != null)
    			{
    				Log.e("getEntryByID", "ac is not Null ");
    				
    				String strInfo = "Name: " + ac.getActivityName() + ", Date: "+ac.getDate();
    				input.setText(strInfo);
    				builder.setView(input);
    			}
    			    			
    			// Set up the buttons
    			builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { 
    			    @Override
    			    public void onClick(DialogInterface dialog, int which) {
    			    	//m_Text = input.getText().toString();
    			    }
    			});

    			builder.show();
    			
	    	  }
	      });	
	    
	    functionlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

	    	@Override
	    	  public void onItemClick(AdapterView<?> parent, View view,
	    	    int position, long id) {
	    		
	    		String strFunction = m_functionlistitem.get(position);
	    		if( strFunction.equalsIgnoreCase("AddItem"))
	    		{
	    			AddDBAndListView();
	    		}
	    		else if( strFunction.equalsIgnoreCase("DeleteFirst"))
	    		{
	    			RemoveDBAndListView(0);
	    		}
	    		else if( strFunction.equalsIgnoreCase("ShowAll"))
	    		{
	    			RefreshAllItems();
	    		}
	    		else if( strFunction.equalsIgnoreCase("ShowCategory"))
	    		{
	    			RefreshCategoryItems();
	    		}
	    		else if( strFunction.equalsIgnoreCase("FilterItem"))
	    		{
 					AlertDialog.Builder builder = new AlertDialog.Builder(ListViewWithDB.this);
	    			builder.setTitle("Filter by Category");
	    			// Set up the input
	    			final EditText input = new EditText(ListViewWithDB.this);
	    			// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
	    			input.setInputType(InputType.TYPE_CLASS_TEXT);
	    			builder.setView(input);

	    			// Set up the buttons
	    			builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { 
	    			    @Override
	    			    public void onClick(DialogInterface dialog, int which) {
	    			    	m_Text = input.getText().toString();
	    			    }
	    			});
	    			builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	    			    @Override
	    			    public void onClick(DialogInterface dialog, int which) {
	    			        dialog.cancel();
	    			    }
	    			});

	    			builder.show();
	    			
	    			GetStringItems(m_Text);
	    		}
	    	  }

	      });	
	
	    functionlistview.setVisibility(View.GONE);
		m_bShowControl = false;
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
			case R.id.functionControl:
				ShowHideControlers();				
			break;
		}
		
	}
	
	private void ShowHideControlers() {
		if(m_bShowControl)
		{
			functionlistview.setVisibility(View.GONE);
			m_bShowControl = false;
		}
		else
		{
			functionlistview.setVisibility(View.VISIBLE);
			m_bShowControl = true;		
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
		
		//RefreshAllItems();
		
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
