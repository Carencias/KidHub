package modelo.conexion;
import java.sql.*;

public class Conexion {
	 Connection con;
	 
	 public Conexion() {
	 }

	 public void openConnection() {
		 try {
			 String userName = "kidhub"; //Change if you used a different one
			 String password = "kidhub"; //Change if you used a different one
			 String dbname = "kidhub";
			 String hostname = "spreding.online";
			 String port = "3306";
			 String url = "jdbc:mysql://"+hostname+":"+port+"/"+dbname+"?serverTimezone=UTC";
			 //String url = "jdbc:mysql://"+hostname+"/"+dbname+"?serverTimezone=UTC"; //Sometimes, also valid without specifying the port
			 Class.forName("com.mysql.fabric.jdbc.FabricMySQLDriver");
			 con = DriverManager.getConnection(url, userName, password);
		
			 System.out.println("Connection to the database done: " + con.getClass().getName());
		 }
		 catch(Exception e) {
			 System.out.println("Error in the connection. Detailed message:\n");
			 System.out.println(e.getMessage());
			 e.printStackTrace();
		 }
	 }

	//To close a connection once all the queries have been done
	 public void closeConnection() {
		 try {
			 con.close();
			 System.out.println("Connection closed");
		 }
		 catch (SQLException e) {
			 System.out.println("Error when closing connection");
		 }
	 }

	public Statement getSt() {
		try {
			return con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
