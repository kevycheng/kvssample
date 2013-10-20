package com.kvs.kvssamples;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class HotOrNot{

	public static final String KEY_ROWID = "_id";
	public static final String KEY_NAME = "persons_name";
	public static final String KEY_HOTNESS = "persons_hotness";
	
	private static final String DATABASE_NAME = "HotOrNot.db";
	private static final String DATABASE_TABLE = "peopleTable";
	private static final int DATABASE_VERSION = 1;
	
	private DbHelper ourHelper;
	private final Context ourContext;
	private SQLiteDatabase ourDatabase;
	
	private static class DbHelper extends SQLiteOpenHelper{
		public DbHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		
		@Override
		public void onCreate(SQLiteDatabase db) {
			
			//String DATABASE_CREATE = "create table "
			//	      + DATABASE_TABLE + "(" + KEY_ROWID
			//	      + " integer primary key autoincrement, " + KEY_NAME
			//	      + " text not null);";
			
			// TODO Auto-generated method stub
			String DATABASE_CREATE = "create table " + DATABASE_TABLE 
					+ "(" + KEY_ROWID + " integer primary key autoincrement, "
					+ KEY_NAME + " text not null, "
	                + KEY_HOTNESS + " text not null);";
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
	
	public HotOrNot(Context c){
		ourContext = c;
		
	}
	
	public HotOrNot open() throws SQLException{
		ourHelper = new DbHelper(ourContext);
		ourDatabase = ourHelper.getWritableDatabase();
		return this;
	}
	
	public void  close(){
		ourHelper.close();
	}

	public void createEntry(String name, String hotness) {
		// TODO Auto-generated method stub
		
		ContentValues values = new ContentValues();
        values.put(KEY_NAME, name); // Contact Name
        values.put(KEY_HOTNESS, hotness); // Contact Phone
        
		ourDatabase.insert(DATABASE_TABLE, null, values);
		ourDatabase.close();
		
	}
}
