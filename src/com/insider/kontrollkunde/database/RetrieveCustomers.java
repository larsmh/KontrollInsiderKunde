package com.insider.kontrollkunde.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.insider.kontrollkunde.model.Globals;
import android.os.AsyncTask;

public class RetrieveCustomers extends AsyncTask<String, Integer, Long> {

	@Override
	protected Long doInBackground(String... params) {
		Connection con=null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			con = DriverManager.getConnection(params[0], params[1], params[2]);
	
			Statement stmt = null;
			ResultSet rs = null;
		
	        stmt = con.createStatement();
	        rs = stmt.executeQuery(params[3]);
	        while (rs.next()) {
	        	String name = rs.getString("name");
	        	String email = rs.getString("email");
	        	String dept = rs.getString("department");
	        	Globals.custList.insert(name, email, dept);
	        }
	        con.close();
	    }catch(SQLException e){
	        	e.printStackTrace();
	    }
		return null;
	}

}
