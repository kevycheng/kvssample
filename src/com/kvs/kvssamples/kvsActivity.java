package com.kvs.kvssamples;

public class kvsActivity {
	
	  private long id;
	  private String strActivityName;
	  private String strDate; 

	  public long getId() {
	    return id;
	  }

	  public void setId(long id) {
	    this.id = id;
	  }

	  public String getActivityName() {
	    return strActivityName;
	  }

	  public void setActivityName(String comment) {
	    this.strActivityName = comment;
	  }
	  
	  public String getDate() {
		  return strDate;
	  }
	  
	  public void setDate(String date) {
		  this.strDate = date;
	  }

	  // Will be used by the ArrayAdapter in the ListView
	  @Override
	  public String toString() {
	    return strActivityName;
	  }
}
