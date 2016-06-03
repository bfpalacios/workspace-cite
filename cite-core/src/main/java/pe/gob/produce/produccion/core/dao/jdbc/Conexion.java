package pe.gob.produce.produccion.core.dao.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
	
	public static Connection obtenerConexion() throws SQLException {
		
		String connectionString =
	            "jdbc:sqlserver://WINDOWS-SMFT2I7\\SQLEXPRESS:1433;"
	            + "database=DBCITE_V3;"
	            + "user=GERARDO;"
	            + "password=tapara;"
	            + "loginTimeout=60;";
		
		
		Connection con = null;
		
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = DriverManager.getConnection(connectionString);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}


}
