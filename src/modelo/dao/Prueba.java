package modelo.dao;
import modelo.conexion.*;

public class Prueba {
	
	public Prueba() {
		Conexion con = new Conexion();
		con.openConnection();
		con.getDataUser();
		con.showDataUser();
		con.closeConnection();
	}
}
