package cms_sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MakeConnection {

	private static String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static String URL = "jdbc:oracle:thin:@localhost";
	private static String USER = "scott";
	private static String PASSWORD = "tiger";

	static Connection conn = null;
	private static MakeConnection mc;
	private MakeConnection() {}
	
	public static MakeConnection getInstance() {
 	if(mc == null) mc = new MakeConnection();
		return mc;		
	}
	
	public static Connection getConnection() {
		try {
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	
	
}
