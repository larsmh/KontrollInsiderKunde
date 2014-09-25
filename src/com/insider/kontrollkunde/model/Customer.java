package com.insider.kontrollkunde.model;

public class Customer {
	private String name, email, department;
	
	public Customer(String name, String email, String department){
		this.name=name;
		this.email=email;
		this.department=department;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getDepartment() {
		return department;
	}
	public String toString(){
		return name;
	}
	
}
