package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
	private Connection connection;
	private String username = "root";
	private String password = "password";
	
	public Connection getConnection() {
		try {
			 Class.forName("com.mysql.jdbc.Driver"); 
			 connection = DriverManager.getConnection("jdbc:mysql://gradiance.cfpvmtqensd2.us-east-2.rds.amazonaws.com:3306/Gradiance",username,password); 
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
		
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
