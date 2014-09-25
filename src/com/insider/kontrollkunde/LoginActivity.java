package com.insider.kontrollkunde;

import com.insider.kontrollkunde.model.Globals;
import com.insider.kontrollkunde.model.User;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class LoginActivity extends ActionBarActivity {
	private Spinner dept;
	private EditText phonenr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		SharedPreferences userData = getSharedPreferences("UserFile", 0);
		String phoneData = userData.getString("phonenr", "null");
		String deptData = userData.getString("dept", "null");
		if(!phoneData.equals("null") && !deptData.equals("null"))
			nextActivity(phoneData, deptData);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		String depts[] = {"Trondheim", "Oslo", "Bergen"};
		dept = (Spinner) findViewById(R.id.dept);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, depts);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		dept.setAdapter(adapter);
		
		phonenr = (EditText) findViewById(R.id.phonenr);
	}
	public void loggin(View view){
		String phoneInput=phonenr.getText().toString();
		String deptInput=dept.getSelectedItem().toString();
		Log.d("!!!!", phoneInput+" "+deptInput);
		//if(detailsExists(phoneInput, deptInput)){
			SharedPreferences userData = getSharedPreferences("UserFile", 0);
			SharedPreferences.Editor editor = userData.edit();
			editor.putString("phonenr", phoneInput);
			editor.putString("dept", deptInput);
			editor.commit();
			nextActivity(phoneInput, deptInput);
		//}
	}
	private void nextActivity(String phonenr, String dept){
		Globals.user = new User(phonenr, dept);
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
