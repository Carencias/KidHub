package modelo.conexion;
import java.sql.*;

public class Conexion {
	 Connection con;
	 Statement st;
	 ResultSet rs;
	 
	 public Conexion() {
	 }

	 public void openConnection() {
		 try {
			 String userName = "kidhub"; //Change if you used a different one
			 String password = "Parapapa@10"; //Change if you used a different one
			 String dbname = "kidhub";
			 String hostname = "localhost";
			 String port = "3306";
			 String url = "jdbc:mysql://"+hostname+":"+port+"/"+dbname+"?serverTimezone=UTC";
			 //String url = "jdbc:mysql://"+hostname+"/"+dbname+"?serverTimezone=UTC"; //Sometimes, also valid without specifying the port
			 Class.forName("com.mysql.cj.jdbc.Driver");
			 con = DriverManager.getConnection(url, userName, password);
		
			 System.out.println("Connection to the database done: " + con.getClass().getName());
		 }
		 catch(Exception e) {
			 System.out.println("Error in the connection. Detailed message:\n");
			 System.out.println(e.getMessage()); 
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
	 
	 public void showDataUser() {
		 try {
			 while (rs.next()) {
				 String strPlat = rs.getString("username");
				 String strBrand = rs.getString("password");
				 System.out.println(strPlat + ", " + strBrand);
			 }
		 } catch (Exception e) {
			 System.out.println("Error visualizing data");
		 }
		}
	 
	 public void getDataUser() {
		 try {
			 st = con.createStatement();
			 rs = st.executeQuery("SELECT * FROM user");
			 System.out.println("Table opened");
		 } catch (SQLException e) {
			 System.out.println("Error when opening table");
		 }
		}
}
