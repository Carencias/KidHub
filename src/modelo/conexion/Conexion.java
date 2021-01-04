package modelo.conexion;
import java.sql.*;
import org.apache.log4j.Logger;


/**
 * Clase que se encarga de establecer la conexion con la base de datos.
 * @version 1.0
 */
public class Conexion {
	
	//Atributo conexion
	 Connection con;
	 //Atributo para los logs
	 static Logger logger = Logger.getLogger(Conexion.class);
	 
	 public Conexion() {}
	 
	 /**
	  * Metodo que abre la conexion con la base de datos especificada en los strings.
	  */
	 public void openConnection() {
		 try {
			 logger.info("Intentando establecer la conexion con la base de datos");
			 String userName = "kidhub"; //Change if you used a different one
			 String password = "kidhub"; //Change if you used a different one
			 String dbname = "kidhub";
			 String hostname = "spreding.online";
			 String port = "3306";
			 String url = "jdbc:mysql://"+hostname+":"+port+"/"+dbname+"?serverTimezone=UTC";
			 Class.forName("com.mysql.fabric.jdbc.FabricMySQLDriver");
			 con = DriverManager.getConnection(url, userName, password);
			 logger.info("Conexion con la base de datos realizada");
		 }
		 catch(Exception e) {
			 logger.info("Error al realizar la conexion con la base de datos: "+e.getMessage());
		 }
	 }

	 /**
	  * Metodo que cierra la conexion con la base de datos mysql
	  */
	 public void closeConnection() {
		 try {
			 con.close();
			 logger.info("Conexion con la base de datos cerrada");
		 }
		 catch (SQLException e) {
			 logger.fatal("Error al cerrar conexion con la base de datos");
		 }
	 }
	 
	/**
	 * Metodo que devuelve un statement de la conexion
	 * @return Statement
	 * 	Statement de la conexion
	 */
	public Statement getSt() {
		try {
			logger.trace("Devolviendo statement");
			return con.createStatement();		
		} catch (SQLException e) {
			logger.error("No se pudo devolver el statement");
		}
		return null;
	}
	
	/**
	 * Metodo que devuelve un objeto Connection con la conexion a la base de datos
	 * 
	 * @return Connection
	 * 	Conexion con la base de datos
	 */
	public Connection getConnection() {
		logger.trace("Devolviendo referencia a la conexion");
		return this.con;
	}
}
