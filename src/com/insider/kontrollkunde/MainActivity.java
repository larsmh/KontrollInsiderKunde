package com.insider.kontrollkunde;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;

import com.insider.kontrollkunde.database.DbAction;
import com.insider.kontrollkunde.model.Customer;
import com.insider.kontrollkunde.model.CustomerList;
import com.insider.kontrollkunde.model.Globals;
import com.insider.kontrollkunde.model.User;

import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore.Files;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
	private AutoCompleteTextView custSelect;
	public ArrayList<Mail> emailList;
	DbAction db;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Globals.custList = new CustomerList();
        
        emailList = new ArrayList<Mail>();
        custSelect = (AutoCompleteTextView) findViewById(R.id.custselect);
        ArrayAdapter<Customer> adapter = new ArrayAdapter<Customer>(this, android.R.layout.simple_list_item_1, Globals.custList.getList());
        custSelect.setAdapter(adapter);
        
    }
    public void register(View view){
    	Customer cust = getCustomer(custSelect.getText().toString());
    	if(cust==null){
    		return;
    	}
    	//Setting customer to global var.
    	Calendar c = Calendar.getInstance();
    	String date=c.get(Calendar.DATE)+"."+(c.get(Calendar.MONTH)+1)+"."+c.get(Calendar.YEAR)+" "
    			+c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE);
    	//Sending email
    	//sendEmail(cust, date);

    	//registrer jobb i database
    	db = new DbAction();
    	db.registerJob(cust.getName(), date);
    }
    
    private Customer getCustomer(String name){
    	for(Customer cust : Globals.custList.getList()){
    		if(name.equals(cust.getName()))
    			return cust;
    	}
    	return null;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_log_out) {
        	SharedPreferences userData = getSharedPreferences("UserFile", 0);
			SharedPreferences.Editor editor = userData.edit();
			editor.putString("phonenr", "null");
			editor.putString("dept", "null");
			editor.commit();
			
			Globals.user = null;
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
    }
    
    public void sendEmail(Customer cust, String date ){

    	EmailPrep prepper = new EmailPrep(emailList, cust, date, this.getBaseContext());
    	prepper.createLocalEmail();
    	prepper.printNumberOfFiles();
//    	if(file.exists() && myDir.isDirectory())
//    		Log.d("Found a file: ", file.getName());
//    	else Log.d("Found no file ","lol");
    	
        ConnectivityManager connec = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connec != null && 
            (connec.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED) || 
            (connec.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED)){ 
                //You are connected, do something online.
        	//1: Check if there is emails waiting to be sent.
        	//2: Add to emailList
        	//3: Delete emailFile
        	
        	prepper.setEmailListContent();
        	
        	Log.d("Lum", "Number of emails in list: "+emailList.size());
        	for (int i = 0; i < emailList.size(); i++) {
				
            	new SendEmailTask(emailList.get(i)).execute();
            	
			}
        	
        	for (int i = 0; i < emailList.size(); i++) {
				
        		emailList.remove(i);
            	
			}
        	
        	Toast.makeText(getApplicationContext(), "Email sendt!", Toast.LENGTH_SHORT).show();
        	
        	
        	
        } else if (connec.getNetworkInfo(0).getState() == NetworkInfo.State.DISCONNECTED ||
                 connec.getNetworkInfo(1).getState() == NetworkInfo.State.DISCONNECTED ) {            
                //Not connected.    
        	Log.d("Lum", "Number of emails in list: "+emailList.size());
        		
                Toast.makeText(getApplicationContext(), "Ingen tilgang til internett.", Toast.LENGTH_LONG).show();
        }
//        prepper.deleteAllFiles();
    }
    
    //Class to make a background thread sending the email.
    class SendEmailTask extends AsyncTask<Void, Void, Void>{
    	Mail mail;
    	public SendEmailTask(Mail mail) {
			// TODO Auto-generated constructor stub
    		this.mail = mail;
		}
    	
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
		
			try {
				mail.send();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			return null;
		}
    }
}
