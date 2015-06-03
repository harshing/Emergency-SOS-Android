package com.example.helpme2;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.gsm.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Options extends Activity implements LocationListener {
	
	boolean trial;
	LocationManager locationManager;
	SensorManager mSensorManager;
	Accelerometer mSensorListener;
	int flag=0;
	SharedPreferences sharedPrefs;
	String number="+918100993029",name="harsh",usermail="a@abc.com",emailid="b@abc.com",password="post",provider,loc,address;
	double latitude,longitude;
	String name1, name2, number1, number2, rEmail, yEmail, pass;
	String nos[];
	EditText tt1;
	public static final String PREFS_NAME = "MyPreferences";
	
	/*SharedPreferences sharedPrefs = PreferenceManager
			.getDefaultSharedPreferences(this);
*/
	private static final int RESULT_SETTINGS = 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show);
		//nos=getIntent().getExtras().getStringArray("nos");
		showUserSettings();
		shake();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case R.id.menu_settings:
			Intent i = new Intent(this, UserSettingActivity.class);
			startActivity(i);
			break;
			//startActivityForResult(i, RESULT_SETTINGS);
		case R.id.menu_contacts:
			Intent i1 = new Intent(this, SelectContacts.class);
			//startActivityForResult(i1,RESULT_OK);
			startActivity(i1);
			break;

		}

		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		case RESULT_SETTINGS:
			showUserSettings();
			break;

		}

	}

	private void showUserSettings() {
		
		 sharedPrefs= PreferenceManager
					.getDefaultSharedPreferences(this);

		StringBuilder builder = new StringBuilder();

		builder.append("\n SMS:"
				+ sharedPrefs.getBoolean("prefSMS", false));

		builder.append("\n MMS:"
				+ sharedPrefs.getBoolean("prefMMS", false));
		
		builder.append("\n Email:"
				+ sharedPrefs.getBoolean("prefEmail", false));
		
		builder.append("\n Use Data:"
				+ sharedPrefs.getBoolean("prefData", false));
				
		SharedPreferences set = getSharedPreferences(PREFS_NAME, 0);		
		
		name1 = set.getString("name1", "Dexter");
		name2 = set.getString("name2", "Morgan");
		number1 = set.getString("no1", "1234");
		number2 = set.getString("no2", "5678");
		yEmail = set.getString("senderEmail", "abc@gmail.com");
		rEmail = set.getString("recvEmail", "abc@gmail.com");
		pass = set.getString("senderPass", "123456");

		StringBuilder builder1 = new StringBuilder();
		builder1.append("\n Name: " + set.getString("name1", "Dexter"));
		builder1.append("\n Number: " + set.getString("no1", "1234"));
		builder1.append("\n Name: " + set.getString("name2", "Morgan"));
		builder1.append("\n Number: " + set.getString("no2", "5678"));
		builder1.append("\n Your E-Mail: "
				+ set.getString("senderEmail", "abc@gmail.com"));
		builder1.append("\n Reviever E-Mail: "
				+ set.getString("recvEmail", "abc@gmail.com"));

		TextView tt1 = (TextView) findViewById(R.id.editText1);
		TextView tt2 = (TextView) findViewById(R.id.editText2);

		tt1.setText(builder.toString());
		tt2.setText(builder1.toString());
	
	}
	
	private void shake()
	{
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mSensorListener = new Accelerometer();   

		mSensorListener.setOnShakeListener(new Accelerometer.OnShakeListener() {

		public void onShake() {
			Toast.makeText(getApplicationContext(), "Shake!", Toast.LENGTH_SHORT).show();
		    int i=0;
			if(flag==0)
		    {
				Toast.makeText(getApplicationContext(), "Shake Detected!", Toast.LENGTH_SHORT).show();
				DataLocation();
//		    		if(sharedPrefs.getBoolean("prefSMS", true)&&sharedPrefs.getBoolean("prefEmail", true))
//		    		{
//		    			DataLocation();
//		    			sendSMS1(name,number,latitude,longitude,address);
//		    			sendEmail(usermail,password,emailid,latitude,longitude,address);
//		    		}
//		    		
		    		if(sharedPrefs.getBoolean("prefSMS", true))
		    		{
		    			//sendSMS(name,number,loc);
						sendSMS(name1, number1, loc);
						sendSMS(name2, number2, loc);
		    		}
		    	
		    		if(sharedPrefs.getBoolean("prefEmail", true))
		    		{
		    			sendEmail(yEmail,pass,rEmail,loc,address);
		    		}
		    		
		    		if(sharedPrefs.getBoolean("prefMMS", true))
		    		{
		    			sendMMS(number1);
		    	
		    		}
		    	
		    }
			flag=1;
		 }     
	});

	}
	
	@SuppressWarnings("deprecation")
	private void sendSMS(String name, String number, String loc)
	{
		SmsManager.getDefault().sendTextMessage(number, null, "Hey"+" "+name+" , I may be in danger!\nMy nearest location is:" + loc, null, null);
		Toast.makeText(getApplicationContext(), "sms"+loc, Toast.LENGTH_SHORT).show();
	}
	

	private void sendMMS(String number)
	{
		Intent i=new Intent("com.example.helpme2.RECORDER");
		i.putExtra("nos", number1);
		startActivity(i);
	}
	
	private void sendEmail(String usermail, String password, String emailid, String loc, String address)
	{
		Mail m = new Mail(usermail, password); 
		String[] toArr = {emailid}; 
		m.setTo(toArr); 
		m.setFrom(usermail); 
		m.setSubject("IMPORTANT:Help Needed"); 
		m.setBody("Hey , I may be in danger!"+ loc +"\nMy nearest address:"+address); 
		try {  
		  if(m.send()) { 
		    Toast.makeText(this, "Email was sent successfully.", Toast.LENGTH_LONG).show(); 
		  } else { 
		    Toast.makeText(this, "Email was not sent.", Toast.LENGTH_LONG).show(); 
		  } 
		} catch(Exception e) { 
		  Log.e("MailApp", "Could not send email", e); 
		} 

	}
	
	
	
	private void DataLocation()
	{
		locationManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
		
		Criteria criteria=new Criteria();
		
		provider=locationManager.getBestProvider(criteria, false);
		
		if(provider!=null && !provider.equals(""))
		{
			Location loc=locationManager.getLastKnownLocation(provider);
			
			locationManager.requestLocationUpdates(provider, 2000, 1, this );
			
			if(loc!=null)
			
				onLocationChanged(loc);
			
			else
			
				Toast.makeText(getBaseContext(), "Location can't be retrieved", Toast.LENGTH_SHORT).show();
		}
		else
		{
			Toast.makeText(getBaseContext(), "No Provider Found", Toast.LENGTH_SHORT).show();
		}
	}
	@Override
	protected void onResume() {
	    super.onResume();
	    mSensorManager.registerListener(mSensorListener,
	        mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
	        SensorManager.SENSOR_DELAY_UI);
	  }

	  @Override
	  protected void onPause() {
	    mSensorManager.unregisterListener(mSensorListener);
	    super.onStop();
	  }

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		loc="http://maps.google.com/maps?q=loc:"+location.getLatitude()+","+location.getLongitude();
//		Geocoder geocoder;
//		List<Address> addresses;
//		geocoder = new Geocoder(this, Locale.getDefault());
//		
//		try {
//			addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(), 1);
//			address=addresses.get(0).getAddressLine(0)+" "+addresses.get(0).getAddressLine(1)+" "+addresses.get(0).getAddressLine(2);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}


}
	

