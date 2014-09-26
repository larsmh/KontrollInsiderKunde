package com.insider.kontrollkunde;

import java.util.ArrayList;
import java.util.Calendar;

import com.insider.kontrollkunde.database.DbAction;
import com.insider.kontrollkunde.model.Customer;
import com.insider.kontrollkunde.model.CustomerList;
import com.insider.kontrollkunde.model.Globals;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
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
    	DbAction db = new DbAction();
    	db.connectDatabase();
    	Customer cust = getCustomer(custSelect.getText().toString());
    	//Setting customer to global var.
    	Calendar c = Calendar.getInstance();
    	String date=c.get(Calendar.DATE)+"."+c.get(Calendar.MONTH)+"."+c.get(Calendar.YEAR)+" "
    			+c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE);
    	//sendMail(cust, date);

    	//registrer jobb i database
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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    public void sendEmail(Customer cust, String date ){
    	

    	Mail mail = new Mail("franangthomas@gmail.com", "pass");
		String[] toArr = {"badeanda87@hotmail.com"}; 
        mail.setTo(toArr); 
        mail.setFrom("from_email"); 
        mail.setSubject("Halla balla."); 
        mail.setBody("Ipsum sorem. Sender mail fra Kontroll Insider Kunde!.\n"+"Sendt: "+date);
        
        emailList.add(mail);
        
        ConnectivityManager connec = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connec != null && 
            (connec.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED) || 
            (connec.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED)){ 
                //You are connected, do something online.
        	Toast.makeText(getApplicationContext(), "Email sendt!", Toast.LENGTH_SHORT).show();
        	
        	for (int i = 0; i < emailList.size(); i++) {
				
            	new SendEmailTask(emailList.get(i)).execute();
			}
        	
        } else if (connec.getNetworkInfo(0).getState() == NetworkInfo.State.DISCONNECTED ||
                 connec.getNetworkInfo(1).getState() == NetworkInfo.State.DISCONNECTED ) {            
                //Not connected.        
                Toast.makeText(getApplicationContext(), "Ingen tilgang til internett.", Toast.LENGTH_LONG).show();
        } 
    	
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
