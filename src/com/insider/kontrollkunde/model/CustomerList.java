package com.insider.kontrollkunde.model;

import java.util.ArrayList;
import java.util.List;

import com.insider.kontrollkunde.database.DbAction;

public class CustomerList {
	private List<Customer> list;
	private DbAction db;
	
	public CustomerList(){
		list = new ArrayList<Customer>();
		getCustomers();
	}
	
	private void getCustomers(){
		db = new DbAction();
    	db.retrieveCustomers();
		
	}
	public List<Customer> getList(){
		return list;
	}
	public void insert(String name, String email, String dept){
		list.add(new Customer(name, email, dept));
	}
}
