package com.insider.kontrollkunde;

import java.util.ArrayList;

import com.insider.kontrollkunde.model.Customer;

import android.os.AsyncTask;

public class SendEmailTask extends AsyncTask<Void, Void, Void> {

	ArrayList<Mail> emailList;
	
	public SendEmailTask(ArrayList<Mail> emailList) {
		this.emailList = emailList;
	}
    	
	@Override
	protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub
	
		try {
			for (int i = 0; i < emailList.size(); i++) {
				
            	emailList.get(i).send();
            	
			}
        	
        	for (int i = 0; i < emailList.size(); i++) {
				
        		emailList.remove(i);
            	
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
		}
    
}
