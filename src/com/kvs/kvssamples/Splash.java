package com.kvs.kvssamples;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class Splash extends Activity{
	
	MediaPlayer sound;
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        
        sound = MediaPlayer.create(Splash.this, R.raw.galaxy_s5_sms);
        
        SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean bMusic = getPrefs.getBoolean("MusicCheckbox", true);
        
        if(bMusic == true)
        	sound.start();
        
        Thread timer = new Thread(){
        	public void run(){
        		try{
        			sleep(1000);
        			 
        		}catch(InterruptedException e){
        			e.printStackTrace();
        		}finally{
        			Intent openStartingpoint = new Intent("com.kvs.kvssamples.Menu");
        			startActivity(openStartingpoint);
        		}
        	}
        };
        
        timer.start();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		sound.release();
		finish();
	}

}
