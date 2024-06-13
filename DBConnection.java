package website;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DBConnection {
	public static Connection getConnection() {
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String userid = "ora_user";
		String userpw = "1234";
		Connection con = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, userpw);
			con.setAutoCommit(false);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
}