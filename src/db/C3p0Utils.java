package db;

import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class C3p0Utils {
	
	private  static final  ComboPooledDataSource cpds = new ComboPooledDataSource();
	private static Connection connection = null;
	
	public static Connection getConnection() {
		try {
			connection = cpds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	public static void closeConnection() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	

}
