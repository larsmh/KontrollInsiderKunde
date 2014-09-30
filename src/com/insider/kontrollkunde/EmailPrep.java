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
	String date;
	Customer cust;
	Context context;
	File myDir;
	public EmailPrep(ArrayList<Mail> list, Customer cust, String date, Context context) {
		this.list = list;
		this.date = date;
		this.cust = cust;
		this.context = context;
		myDir = context.getDir("myDir", Context.MODE_PRIVATE);
	}
	
	public void createLocalEmail(){

//    	String email = cust.getEmail();
    	String email = "badeanda87@hotmail.com";
//    	String name = cust.getName();
    	String name = "thomas";
    	File file;    	
		
    	Log.d("Found: ",""+myDir.list().length);
		
		file = new File(myDir.getAbsolutePath(),name+myDir.list().length+".txt");
		//Create a new file.
		try {
			file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//write to the file.
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file.getAbsolutePath()));
			
			writer.write("badeanda87@hotmail.com");
			writer.newLine();
			writer.write(date);
			writer.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//read the file.
		
//		Log.d("Found this details: ",checkemail+" ,"+checkdate);
//		if(file.exists() && myDir.isDirectory()) Log.d("Found:", "Got file: "+file.getName());
		
//		for(File f: myDir.listFiles()) 
//			  Log.d("Found: ", f.getName());
		
		
//		if(myDir.list().length == 0) Log.d("Found no file ", myDir.getAbsolutePath());

	}
	
	public void setEmailListContent(){
		String lines[] = {"",""};
		String line;
		for(File f: myDir.listFiles()) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(f.getAbsolutePath()));
				try {
					for (int j = 0; j < 2; j++) {
						lines[j] = br.readLine();
					}
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Mail mail = new Mail();
			String[] toArr = {lines[0]}; 
            mail.setTo(toArr); 
            mail.setFrom("franangthomas@gmail.com"); 
            mail.setSubject("Stoooor til?"); 
            mail.setBody("Savner deg!\n"+
            			"Denne mailen ble sent: "+lines[1]+"\n"+
            			"Dette er mail number: "+f.getName()); 		
					
			list.add(mail);
			f.delete();
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
