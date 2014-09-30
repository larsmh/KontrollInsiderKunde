package com.insider.kontrollkunde.database;


public class DbAction {
	String url ="jdbc:mysql://mysql.stud.ntnu.no:3306/franang_insider";
	String user = "franang_admin";
	String pw = "tranduil123";
	
	public void connectDatabase(){
		/*Connection con=null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.d("!!!", "class!!!!");
		}
		try {
			Log.d("!!!", "Trying");
			String url ="jdbc:mysql://mysql.stud.ntnu.no:3306/franang_insider";
			String user = "franang_admin";
			String pw = "tranduil123";
			con = DriverManager.getConnection(url, user, pw);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.d("!!!", "Fail");
		}*/
			
			String query = "select * from customer";
			new DBThread().execute(url, user, pw, query);
	}
}
