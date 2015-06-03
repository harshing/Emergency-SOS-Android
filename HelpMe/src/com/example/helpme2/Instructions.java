package com.example.helpme2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Instructions extends Activity {

	public static final String PREFS_NAME = "MyPreferences";

	Button BtCancel, BtOk, mBttn;
	final Context context = this;
	boolean trial;
	EditText etpasswd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_instr);
		String s = "\n INSTRUCTIONS \n Step 1 : \nSelect Contacts \n"
				+ "Step 2 : \nUsing the selected Contacts EmergencyHelp geneates a message for personalized for the user which contains the current location of the user and the Google Maps link and a specialized message of SOS.\n"
				+ "Step 3 : \nJust set this up and you are ready to go.\n"
				+ "NOTE: \n EmergencyHelp may use Cellular Data and SMS.\n";

		TextView ttview = (TextView) findViewById(R.id.textView1);
		ttview.setText(s);

		/*if (isFirstTime())
			setPassword();*/

		etpasswd = (EditText) findViewById(R.id.password);

		mBttn = (Button) findViewById(R.id.button1);
		mBttn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				
					Intent iNext = new Intent(
							"com.example.helpme2.SELECTCONTACTS");
					startActivity(iNext);
				
				
			}
		});
		
		
	}

			/*@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Intent iNext = new Intent(getApplicationContext(),
				// SelectContacts.class);
				// startActivity(iNext);
				// startNow();

				// get prompts.xml view
				LayoutInflater li = LayoutInflater.from(context);
				View promptsView = li.inflate(R.layout.dialog_pagenumber, null);

				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						context);

				// set prompts.xml to alertdialog builder
				alertDialogBuilder.setView(promptsView);

				
				 * final EditText userInput = (EditText) promptsView
				 * .findViewById(R.id.editTextDialogUserInput);
				 

				// set dialog message
				alertDialogBuilder
						.setCancelable(false)
						.setPositiveButton("OK",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										// get user input and set it to result
										Toast.makeText(getApplicationContext(),
												"YESS", Toast.LENGTH_LONG)
												.show();

										if (isFirstTime()) {
											Intent iNext = new Intent(
													"com.example.helpme2.SELECTCONTACTS");
											startActivity(iNext);
										} else {
											Intent iNext = new Intent(
													"com.example.helpme2.OPTIONS");
											startActivity(iNext);
										}
											

									}
								})
						.setNegativeButton("Cancel",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();
									}
								});

				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();

				// show it
				alertDialog.show();

			}
		});

	}*/

	/*
	 * protected void startNow() { // TODO Auto-generated method stub Context
	 * contxt=this; final Dialog dialog = new Dialog(contxt);
	 * dialog.setContentView(R.layout.dialog_pagenumber);
	 * dialog.setTitle("Password"); dialog.show(); BtOk =
	 * (Button)findViewById(R.id.btOk);
	 * 
	 * BtOk.setOnClickListener(new OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { // TODO Auto-generated method
	 * stub Intent iNext = new Intent("com.example.helpme2.OPTIONS");
	 * startActivity(iNext); } });
	 * 
	 * }
	 */

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

	public boolean check() {

		SharedPreferences sharedP = getSharedPreferences(PREFS_NAME, 0);

		String s = "";
		s += sharedP.getString("password", "NOT_FOUND");

		if (etpasswd.getText().toString().compareTo(s) == 0)
			trial = true;
		else
			trial = false;

		return trial;
	}

	/*public void setPassword() {
		SharedPreferences sharedP = getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = sharedP.edit();

		editor.putString("password", "1234");
	}*/

	public boolean isFirstTime() {
		// TODO Auto-generated method stub
		SharedPreferences preferences = getPreferences(MODE_PRIVATE);
		boolean ranBefore = preferences.getBoolean("RanBefore", false);
		if (!ranBefore) {
			// first time
			SharedPreferences.Editor editor = preferences.edit();
			editor.putBoolean("RanBefore", true);
			editor.commit();
		}
		return !ranBefore;
	}

}
