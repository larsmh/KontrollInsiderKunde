package com.insider.kontrollkunde.model;

public class User {
	private String phonenr, department;
	
	public User(String phonenr, String department){
		this.phonenr=phonenr;
		this.department=department;
	}
	public String getPhonenr() {
		return phonenr;
	}

	public String getDepartment() {
		return department;
	}
}
