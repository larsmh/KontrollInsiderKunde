package com.insider.kontrollkunde.model;

public class User {
	private String phonenr, department;
	private boolean admin;
	
	public User(String phonenr, String department, boolean admin){
		this.phonenr=phonenr;
		this.department=department;
		this.admin=admin;
	}
	public User(String phonenr, boolean admin){
		this.phonenr=phonenr;
		this.department="n/a";
		this.admin=admin;
	}
	public String getPhonenr() {
		return phonenr;
	}

	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department){
		this.department=department;
	}
	
	public boolean getAdmin(){
		return admin;
	}
}
