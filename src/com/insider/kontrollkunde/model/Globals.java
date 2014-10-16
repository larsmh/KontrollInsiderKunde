package com.insider.kontrollkunde.model;
import java.util.ArrayList;

import com.insider.kontrollkunde.Mail;
import com.insider.kontrollkunde.database.CustomerListDB;

public class Globals {
	public static User user;
	public static CustomerList custList=new CustomerList();
	public static ArrayList<Mail> emaiList = new ArrayList<Mail>();
	public static boolean userFound=false;
	public static CustomerListDB custDB;
}
