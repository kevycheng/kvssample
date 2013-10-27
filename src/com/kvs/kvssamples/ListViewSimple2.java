package com.kvs.kvssamples;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.kvs.kvssamples.R;

import android.app.Activity;
import android.content.Context;
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

	Button sqladd, sqldelete;
	
	MyPerformanceArrayAdapter adapter;
	ArrayList<String> m_listitem = new ArrayList<String>();
	ArrayList<String> m_listsubitem = new ArrayList<String>();
	//private kvsActivityMgr m_Mgr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listviewsimple1_layout);
		
		Log.e("onCreate", "1");
		
		sqladd = (Button) findViewById(R.id.additem);
		sqldelete = (Button) findViewById(R.id.deleteitem);
		
		Log.e("onCreate", "2");
		
		final ListView mylistview = (ListView) findViewById(R.id.listview);
		
		// load db
		Log.e("onCreate", "3");
		
		kvsActivityMgr m_Mgr = new kvsActivityMgr(ListViewSimple2.this);
		Log.e("onCreate", "3.5");
		
		m_Mgr.open();
		Log.e("onCreate", "4");
		
		List<kvsActivity> activities = m_Mgr.getAllActivities();
		
		Log.e("onCreate", "5");
		
		Log.e("onCreate", "6");
		
		Log.e("onCreate", "7");
		
		for (int i = 0; i < activities.size(); ++i) {
			kvsActivity item = activities.get(i);
	    	m_listitem.add(item.getActivityName());
	    	m_listsubitem.add(String.valueOf(item.getId()));
	    }
	    
		Log.e("onCreate", "8");
		
	    adapter = new MyPerformanceArrayAdapter(this, m_listitem, m_listsubitem, m_listitem);
	    
	    Log.e("onCreate", "9");
	    
	    mylistview.setAdapter(adapter);
	        
	    mylistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

	    	@Override
	    	  public void onItemClick(AdapterView<?> parent, View view,
	    	    int position, long id) {
	    	    Toast.makeText(getApplicationContext(),
	    	      "Click ListItem Number " + position+ ", name = " + m_listitem.get(position)
	    	      , Toast.LENGTH_LONG).show();
	    	  }
	      });	    
	}
	
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.additem:
			// add db and listview
			AddDBAndListView();
			break;
		case R.id.deleteitem:		
			RemoveDBAndListView();
			
			break;
		}
		adapter.notifyDataSetChanged();
	}

	private void RemoveDBAndListView() {
		//String strFire = adapter.getItem(0);

		//String sRow1 = sqlRowID.getText().toString();
		//long lrow1 = Long.parseLong(sRow1);
		
		kvsActivityMgr ex1 = new kvsActivityMgr(this);
		ex1.open();
		
		if(m_listitem.size()>0 && m_listsubitem.size()>0)
		{
			long id = Long.parseLong(m_listsubitem.get(0));
			
			ex1.DeleteEntry(id);
			
			m_listitem.remove(0);
			m_listsubitem.remove(0);
		}
		
		ex1.close();
		
	}

	private void AddDBAndListView() {
		
		Log.e("AddDBAndListView", "befoe add...");
		String[] comments = new String[] { "Cool", "Very nice", "Hate it" };
	     int nextInt = new Random().nextInt(3);
	     
	     
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
	    
		Log.e("adapter.add", "after adapter.add...");
		
		kvsActivityMgr entry = new kvsActivityMgr(this);
		entry.open();
		entry.createEntry(comments[nextInt], comments[nextInt]);
		entry.close();
		
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
