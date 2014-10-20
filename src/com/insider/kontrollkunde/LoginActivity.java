package com.insider.kontrollkunde;

import com.insider.kontrollkunde.database.DbAction;
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
import android.widget.Toast;

public class LoginActivity extends ActionBarActivity {
	private EditText phonenr;
	private EditText password;
	private DbAction db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		SharedPreferences userData = getSharedPreferences("UserFile", 0);
		String phoneData = userData.getString("phonenr", "null");
		String pwData = userData.getString("password", "null");
		String deptData = userData.getString("dept", "null");
		boolean adminData = userData.getBoolean("admin", false);
		if(!phoneData.equals("null") && !pwData.equals("null") && !deptData.equals("null")){
			Globals.user = new User(phoneData, pwData, deptData, adminData);
			nextActivity();
		}

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		/*String depts[] = {"Trondheim", "Oslo", "Bergen"};
		dept = (Spinner) findViewById(R.id.dept);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, depts);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		dept.setAdapter(adapter);*/
		
		phonenr = (EditText) findViewById(R.id.phonenr);
		password = (EditText) findViewById(R.id.password);
		
	}
	public void loggin(View view){
		String phoneInput=phonenr.getText().toString();
		String pwInput=password.getText().toString();
		if(userExists(phoneInput, pwInput)){
			Globals.userFound=false;
			SharedPreferences userData = getSharedPreferences("UserFile", 0);
			SharedPreferences.Editor editor = userData.edit();
			editor.putString("phonenr", Globals.user.getPhonenr());
			editor.putString("password", Globals.user.getPassword());
			editor.putString("dept", Globals.user.getDepartment());
			editor.putBoolean("admin", Globals.user.getAdmin());
			editor.commit();
			nextActivity();
		}
		else{
			Toast.makeText(getApplicationContext(), 
				"Kan ikke finne denne brukeren. Telefonnummeret kan være feil, eller nettilgangen dårlig",
     			Toast.LENGTH_LONG).show();
		}
	}
	private void nextActivity(){
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}
	private boolean userExists(String phonenr, String password){
		db = new DbAction();
		db.retrieveUser(phonenr);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(Globals.userFound){
			return Globals.user.getPassword().equals(password);
		}
		return false;
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
    @Override
    public void onBackPressed() {
    	moveTaskToBack(true);
    }
}
