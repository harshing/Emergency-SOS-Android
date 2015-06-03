package com.example.helpme2;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class SelectContacts extends Activity {

	public static final String PREFS_NAME = "MyPreferences";
	

	Button btSelect;
	Button btok,put;
	String name1, name2, number1, number2;
	TextView tVName1, tVName2, tVNo1, tVNo2;
	EditText etSndr,etPass,etRecv;
	TabHost tabHost;
	String nos[],name[];
	int i=0,flag=0;
	
	boolean trial;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select);

		btSelect = (Button) findViewById(R.id.btSelCon);
		btok = (Button) findViewById(R.id.btOk);
		
		tVName1 = (TextView) findViewById(R.id.tvName1);
		tVName2 = (TextView) findViewById(R.id.tvName2);
		tVNo1 = (TextView) findViewById(R.id.tvNo1);
		tVNo2 = (TextView) findViewById(R.id.tvNo2);
		etSndr = (EditText)findViewById(R.id.eTsenderEmail);
		etPass = (EditText)findViewById(R.id.eTsenderPass);
		etRecv = (EditText)findViewById(R.id.eTrecvEmail);
//		eTpasswd1 = (EditText)findViewById(R.id.etPasswd1);
//		eTpasswd2 = (EditText)findViewById(R.id.etPasswd2);
		
		tabHost = ((TabHost) findViewById(R.id.tabhost));
		tabHost.setup();
		
		TabHost.TabSpec localTabSpec1 = tabHost.newTabSpec("TAB 1");
		localTabSpec1.setContent(R.id.tab1);
		localTabSpec1.setIndicator("SELECT CONTACTS");
		
		TabHost.TabSpec localTabSpec2 = tabHost.newTabSpec("TAB 2");
		localTabSpec2.setIndicator("SET UP MAIL COFIGURATION");
		localTabSpec2.setContent(R.id.tab2);
		
		
		tabHost.addTab(localTabSpec1);
		tabHost.addTab(localTabSpec2);

		onClickListner();

	}

	public void onClickListner() {

		btSelect.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				/*
				 * select contacts
				 */
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
				intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
				startActivityForResult(intent, 1);

				/*
				 * Toast.makeText(getBaseContext(), "Get Contacts",
				 * Toast.LENGTH_LONG).show();
				 */
			}
		});
		
		/*put.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v){
				
				int i=0;
				
				tVName1.setText(name[i]);
				tVNo1.setText(nos[i++]);
				tVName2.setText(name[i]);
				tVNo2.setText(nos[i++]);
				
			}
		});*/

		btok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				

				/*
				 * save in Preferences and start Screen 4
				 */

				SharedPreferences set = getSharedPreferences(PREFS_NAME, 0);
				SharedPreferences.Editor editor = set.edit();
				editor.putString("name1", tVName1.getText().toString());
				editor.putString("no1", tVNo1.getText().toString());
				editor.putString("name2", tVName2.getText().toString());
				editor.putString("no2", tVNo2.getText().toString());
				editor.putString("senderEmail", etSndr.getText().toString());
				editor.putString("senderPass", etPass.getText().toString());
				editor.putString("recvEmail", etRecv.getText().toString());
				
				editor.commit();
				
			
				
				Log.d("btOK:::::::::::", "Before Intent");

				Intent intent1 = new Intent("com.example.helpme2.OPTIONS");
				intent1.putExtra("nos", nos);
				startActivity(intent1);

			}

		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (data != null) {
			Uri uri = data.getData();

			if (uri != null) {
				Cursor c = null;
				try {
					c = getContentResolver()
							.query(uri,
									new String[] {
											ContactsContract.CommonDataKinds.Phone.NUMBER,
											ContactsContract.CommonDataKinds.Phone.TYPE,
											ContactsContract.CommonDataKinds.Identity.DISPLAY_NAME },
									null, null, null);

					if (c != null && c.moveToFirst()) {
						// mNumber = c.getString(0);
						// int type = c.getInt(1);
						
						number1 = c.getString(0);
						name1 = c.getString(2);
						
						flag++;
							
						if(flag == 1){
							tVName1.setText(name1);
							tVNo1.setText(number1);
						
						} else if(flag == 2) {
							tVName2.setText(name1);
							tVNo2.setText(number1);
						} else
							Toast.makeText(getApplicationContext(), "Not Possible", Toast.LENGTH_LONG).show();
							
						
						//tVNo1.append( "\nName:" + name1 + "\nNumber:" + number1 + "\n" );
						
						/*if (tVName1.getText().toString().compareTo(null) == 0) {
							// For First
							tVName1.setText(name1);
							tVNo1.setText(number1);
						} else {
							// For Second
							tVName2.setText(name1);
							tVNo2.setText(number1);
						} if(tVName1.getText().toString().compareTo(null) != 0 && tVName2.getText().toString().compareTo(null) != 0 ){
							//default
							tVName1.setText(name1);
							tVNo1.setText(number1);
						}*/

						// mContactdisplayName = c.getString(2);
					}
				} finally {
					if (c != null) {
						c.close();
					}
				}
			}
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		// finish();
	}

}
