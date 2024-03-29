package cs122b.Utilities;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {
	private static String driverClass = "com.mysql.jdbc.Driver";
	private static String connectionURL = "jdbc:mysql://localhost:3306/moviedb";



	private static DataSource dataSource = null;

	// http://entirej.com/connection-pooling-javafx/


	public static boolean loadDataSource(String userName, String pswd, boolean enableLog) {
		// check to see if we should enable logging
		if (!enableLog) {
			Properties p = new Properties(System.getProperties());
			p.put("com.mchange.v2.log.MLog", "com.mchange.v2.log.FallbackMLog");
			p.put("com.mchange.v2.log.FallbackMLog.DEFAULT_CUTOFF_LEVEL", "OFF"); // or any other
			System.setProperties(p);
		}
		ComboPooledDataSource cpds = new ComboPooledDataSource();

		try {
			cpds.setDriverClass(driverClass);
//			cpds.setAutoCommitOnClose(false);
			cpds.setJdbcUrl(connectionURL);
			cpds.setUser(userName);
			cpds.setPassword(pswd);
			cpds.setMinPoolSize(3);
			cpds.setAcquireIncrement(5);
			cpds.setMaxPoolSize(20);
			cpds.setBreakAfterAcquireFailure(true);
			cpds.setAcquireRetryAttempts(1);
			// test connection by getting one
			cpds.getConnection();

			// here we need to perform test query to ensure the connection was successful
		}
		catch (Exception e) {
			System.out.print(e.getMessage());
			cpds.close();
			return false;
		}
		dataSource = cpds;
		return true;
	}
	/**
     * gets a connection from the connection pool
     *
     */
	public static Connection getConnection() {
		Connection con = null;
		try
		{
			con = dataSource.getConnection();
			return con;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return con;
	}
}
