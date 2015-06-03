package com.example.helpme2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

public class HelpMe22 extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help_me2);
		
		
		Thread timer = new Thread() {
			public void run(){
				
				try{
					sleep(3000);
				}	catch(InterruptedException e) {
					e.printStackTrace();
					Toast.makeText(getApplicationContext(), "ERROR..!" , Toast.LENGTH_LONG).show();
				} finally {
					Intent open =  new Intent("com.example.helpme2.INSTRUCTIONS");
					startActivity(open);
				}
			}
			
		};
		
		timer.start();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_help_me2, menu);
		return true;
	}

}
