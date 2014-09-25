package com.insider.kontrollkunde.model;

import java.util.ArrayList;
import java.util.List;

public class CustomerList {
	private List<Customer> list;
	
	public CustomerList(){
		list = new ArrayList<Customer>();
		getCustomers();
	}
	
	private void getCustomers(){
		Customer cust;
		String names[] = {"Per", "Nils", "Ola"};
		String emails[] = {"per@mail.com", "nils@mail.com", "ola@mail.com"};
		String depts[] = {"Trondheim", "Trondheim", "Oslo"};
		for(int i=0; i<3; i++){
			cust = new Customer(names[i], emails[i], depts[i]);
			list.add(cust);
		}
	}
	public List<Customer> getList(){
		return list;
	}
}
