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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listviewsimple1_layout);
		
		sqladd = (Button) findViewById(R.id.additem);
		sqldelete = (Button) findViewById(R.id.deleteitem);
		
		final ListView mylistview = (ListView) findViewById(R.id.listview);
		
		final String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
		        "Android", "iPhone", "WindowsMobile" };

	    for (int i = 0; i < values.length; ++i) {
	    	m_listitem.add(values[i]);
	    }
	    
	    adapter = new MyPerformanceArrayAdapter(this, m_listitem, m_listitem, m_listitem);
	    
	    mylistview.setAdapter(adapter);
	        
	    mylistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

	    	@Override
	    	  public void onItemClick(AdapterView<?> parent, View view,
	    	    int position, long id) {
	    	    Toast.makeText(getApplicationContext(),
	    	      "Click ListItem Number " + position+ ", name = " + values[position]
	    	      , Toast.LENGTH_LONG).show();
	    	  }
	      });	    
	    
	}
	
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.additem:
			Log.e("adapter.add", "befoe adapter.add...");
			String[] comments = new String[] { "Cool", "Very nice", "Hate it" };
		     int nextInt = new Random().nextInt(3);
		    m_listitem.add(comments[nextInt]);
		    adapter.notifyDataSetChanged();
			Log.e("adapter.add", "after adapter.add...");
			break;
		case R.id.deleteitem:
			String strFire = adapter.getItem(0);
			m_listitem.remove(0);
			break;
		}
		adapter.notifyDataSetChanged();
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
