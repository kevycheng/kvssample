package com.kvs.kvssamples;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class kvsActivityMgr {
	
	private kvsDBHelper m_dbHelper;
	private final Context m_ourContext;
	private SQLiteDatabase m_ourDatabase;
	
	public static final String KEY_ROWID = "_id";
	public static final String KEY_NAME = "persons_name";
	public static final String KEY_DATE = "persons_date";
	
	private static final String DATABASE_NAME = "Activity2.db";
	private static final String DATABASE_TABLE = "ActivityTable2";
	private static final int DATABASE_VERSION = 1;
	
	private String[] m_strColum = { KEY_ROWID, KEY_NAME, KEY_DATE };

public class kvsDBHelper extends SQLiteOpenHelper{
		
	public kvsDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		
		Log.e("onCreate", "First onCreate Database!!!!!");
		
		String DATABASE_CREATE = "create table " + DATABASE_TABLE 
				+ "(" + KEY_ROWID + " integer primary key autoincrement, "
				+ KEY_NAME + " text not null, "
                + KEY_DATE + " text not null);";
        db.execSQL(DATABASE_CREATE);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		// Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
 
        // Create tables again
        onCreate(db);
	}
}
	
	public kvsActivityMgr(Context c){
		Log.e("kvsActivityMgr", "kvsActivityMgr");
		m_ourContext = c;
	}
	
	public kvsActivityMgr open() throws SQLException{
		m_dbHelper = new kvsDBHelper(m_ourContext);
		m_ourDatabase = m_dbHelper.getWritableDatabase();
		return this;
	}
	
	public void  close(){
		m_dbHelper.close();
	}

	public void createEntry(String name, String date) {
		String log = "Name:" + name + ", date:" + date;
		Log.e("createEntry", log);
		
		ContentValues values = new ContentValues();
        values.put(KEY_NAME, name); // Contact Name
        values.put(KEY_DATE, date); // Contact Phone
        
        m_ourDatabase.insert(DATABASE_TABLE, null, values);
        m_ourDatabase.close();
	}

	private kvsActivity cursorToActivity(Cursor cIndex) {
		kvsActivity ac = new kvsActivity();
		
		int nRow = cIndex.getColumnIndex(KEY_ROWID);
		int nName = cIndex.getColumnIndex(KEY_NAME);
		int nDate = cIndex.getColumnIndex(KEY_DATE);
		
		ac.setId(cIndex.getLong(nRow));
		ac.setActivityName(cIndex.getString(nName));
		ac.setDate(cIndex.getString(nDate));
		
	    return ac;
	  }
	
	public List<kvsActivity> getAllActivities() {
		
		Log.e("getAllActivities", "getAllActivities...");
		
		Cursor cIndex = m_ourDatabase.query(DATABASE_TABLE, m_strColum, null, null, null, null, null);
		
		int nRow = cIndex.getColumnIndex(KEY_ROWID);
		int nName = cIndex.getColumnIndex(KEY_NAME);
		int nDate = cIndex.getColumnIndex(KEY_DATE);
		
		List<kvsActivity> activities = new ArrayList<kvsActivity>();
		
		for(cIndex.moveToFirst(); !cIndex.isAfterLast(); cIndex.moveToNext())
		{
			kvsActivity ac = cursorToActivity(cIndex);
			activities.add(ac);
		}
		
		cIndex.close();
		
		return activities;
	}
	
	public String getData() {
		Log.e("getData", "getData...");

		Cursor cIndex = m_ourDatabase.query(DATABASE_TABLE, m_strColum, null, null, null, null, null);
		String result = "";
		
		int nRow = cIndex.getColumnIndex(KEY_ROWID);
		int nName = cIndex.getColumnIndex(KEY_NAME);
		int nDate = cIndex.getColumnIndex(KEY_DATE);
		
		List<kvsActivity> activities = new ArrayList<kvsActivity>();
		
		for(cIndex.moveToFirst(); !cIndex.isAfterLast(); cIndex.moveToNext())
		{
			result = result + cIndex.getString(nRow) + " " + cIndex.getString(nName) + " " + cIndex.getString(nDate) +  "\n";
			
			kvsActivity ac = cursorToActivity(cIndex);
			activities.add(ac);
		}
		
		for(int i = 0; i<activities.size(); i++)
		{
			kvsActivity ac = activities.get(i);
			String log = "data: "+String.valueOf(ac.getId()) + "," + ac.getActivityName() + ", " + ac.getDate();
			Log.e("get all data", log);
		}
		
		return result;
	}

	public String getName(long l) {
		String log = "Id: "+String.valueOf(l);
		Log.e("getName", log);
		// TODO Auto-generated method stub
		String[] strColum = new String[] { KEY_ROWID, KEY_NAME, KEY_DATE }; 
		Cursor c = m_ourDatabase.query(DATABASE_TABLE, strColum, KEY_ROWID + "=" + l,null, null, null, null);
		if(c!=null){
			c.moveToFirst();
			int nName = c.getColumnIndex(KEY_NAME);
			String name = c.getString(nName);
			return name;
		}
		return null;
	}

	public String getDate(long l) {
		String log = "Id: "+String.valueOf(l);
		Log.e("getHotness", log);
		
		// TODO Auto-generated method stub
		String[] strColum = new String[] { KEY_ROWID, KEY_NAME, KEY_DATE }; 
		Cursor c = m_ourDatabase.query(DATABASE_TABLE, strColum, KEY_ROWID + "=" + l,null, null, null, null);
		if(c!=null){
			c.moveToFirst();
			int nDate = c.getColumnIndex(KEY_DATE);
			String name = c.getString(nDate);
			return name;
		}
		return null;
	}

	public void UpdateEntry(long lrow, String name, String date) {
		String log = "Id: "+String.valueOf(lrow) + ", name:" + name + ", date:" + date;
		Log.e("getDate", log);
		//Log.e("UpdateEntry", "UpdateEntry...");
		// TODO Auto-generated method stub
		ContentValues cvUpdate = new ContentValues();
		cvUpdate.put(KEY_NAME, name);
		cvUpdate.put(KEY_DATE, date);
		m_ourDatabase.update(DATABASE_TABLE, cvUpdate, KEY_ROWID + "=" + lrow, null);
		
	}

	public void DeleteEntry(long lrow1) {
		Log.e("DeleteEntry", "DeleteEntry...");
		// TODO Auto-generated method stub
		m_ourDatabase.delete(DATABASE_TABLE, KEY_ROWID  + "=" + lrow1, null);
		
	}

}
