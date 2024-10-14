
	package util;


	import java.sql.Connection;
	import java.sql.DriverManager;
	import java.sql.SQLException;

	public class DBConnectionUtil {
	    
	    // Hard-coded database connection details
	    private static final String DB_URL = "jdbc:mysql://localhost:3306/learn";
	    private static final String USERNAME = "root";
	    private static final String PASSWORD = "Jinisha@123";

	    public static Connection getConnection() throws SQLException {
	        return DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
	    }
	}

	
	

