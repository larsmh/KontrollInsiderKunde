package com.insider.kontrollkunde;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

import com.insider.kontrollkunde.model.Customer;

public class EmailPrep {

	ArrayList<Mail> list;
	String date, msg;
	Customer cust;
	Context context;
	File myDir;
	
	public EmailPrep(ArrayList<Mail> list, Customer cust, String date, Context context) {
	}
	
	public EmailPrep(ArrayList<Mail> list, Customer cust, String date, Context context, String msg) {
		this.list = list;
		this.date = date;
		this.cust = cust;
		this.context = context;
		this.msg = msg;
		myDir = context.getDir("myDir", Context.MODE_PRIVATE);
		Log.d("!!emailPrep", ""+msg);
	}
	
	public void createLocalEmail(){

		String email = cust.getEmail();
//    	String email = "badeanda87@hotmail.com";
    	String name = cust.getName();
    	String[] s = {email, date, msg};
//    	String name = "thomas";
    	File file;    	
		
    	Log.d("!!inne i CreateLocalEamil", "lol "+msg+" "+s[0]+" "+s[1]+" "+s[2]);
		file = new File(myDir.getAbsolutePath(),name+myDir.list().length+".txt");
		//Create a new file.
		try {
			file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//write to the file.
		BufferedWriter writer;
		
		try {
			writer = new BufferedWriter(new FileWriter(file.getAbsolutePath()));
			
				
				writer.write(s[0]);
				writer.newLine();
				writer.write(s[1]);
				writer.newLine();
				writer.write(s[2]);
				writer.newLine();
			
			writer.flush();
			writer.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setEmailListContent(){
		String lines[] = {"","",""};
		String line;
		
		if( myDir.list().length != 0){
		for(File f: myDir.listFiles()) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(f.getAbsolutePath()));
				try {
					for (int j = 0; j < 3; j++) {
						lines[j] = br.readLine();
						Log.d("!!", lines[j]);
					}
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
//			Log.d("!!checkckck", "lol "+msg+" "+lines[0]+" "+lines[1]+" "+lines[2]);
			
			Mail mail = new Mail();
			String[] toArr = {lines[0]}; 
            mail.setTo(toArr); 
            mail.setFrom("franangthomas@gmail.com"); 
            if(msg != ""){
            	Log.d("!!checkckck", "lol "+msg+" "+lines[0]+" "+lines[1]+" "+lines[2]);
            	mail.setSubject("Vask ikke mulig p� grunn av avvik");
            	mail.setBody(lines[2]+"\n"+
            			"Denne mailen ble sent: "+lines[1]+"\n"+
            			"Dette er mail number: "+f.getName()); 		
				
            }
            else {mail.setSubject("Kvittering for utf�rt vask"); 
            	mail.setBody("Vask utf�rt av Kjekken Kjakansen\n"+
            			"Denne mailen ble sent: "+lines[1]+"\n"+
            			"Dette er mail number: "+f.getName());
            }
					
			list.add(mail);
			f.delete();
		}
	}
	}
	
	public ArrayList<Mail> getEmailList(){
		
		return list;
	}
	
	public void printNumberOfFiles(){
		Log.d("Lumm","Number of files: "+myDir.list().length);
		
	}
	
	public void deleteAllFiles(){
		for(File f: myDir.listFiles()) 
			  f.delete();
		
	}
	
}
