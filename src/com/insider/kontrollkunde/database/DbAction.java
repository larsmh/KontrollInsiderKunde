package com.insider.kontrollkunde.database;

import java.sql.*;

import android.util.Log;

public class DbAction {
	
	public Connection connectDatabase(){
		Connection con=null;
		try {
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.d("!!!", "class!!!!");
			}
			
			con = DriverManager.getConnection(
					"jdbc:mysql://78.91.9.182:3306/insider",
					"franang_admin",
					"tranduil123");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.d("!!!", "Fail");
		}
		return con;
	}
}
