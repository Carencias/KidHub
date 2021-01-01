package modelo.dao;

import java.sql.SQLException;
import java.sql.Statement;

import modelo.conexion.Conexion;
import modelo.vo.HijoVO;
import modelo.vo.PadreVO;

public class PadreDAO extends UsuarioDAO{
	
	public void registrarPadre(PadreVO padre) {
		conexion.openConnection();
		statement = conexion.getSt();
		
		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO PARENTS");
		query.append("(Username, PhoneNumber) ");
		query.append("VALUES(");
		query.append("'" + padre.getNombreUsuario() + "', '" + padre.getTelefono() + "');");
				
		update(query.toString());
	}
	
	
	public void update(String query) {
		conexion.openConnection();
		statement = conexion.getSt();
		
        try {
			statement.executeUpdate(query.toString());
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public void agregarHijoAPadre(HijoVO hijo, PadreVO padre) {
		conexion.openConnection();
		statement = conexion.getSt();
		
		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO ParentKid");
		query.append("(ParentUsername, KidUsername) ");
		query.append("VALUES(");
		query.append("'" + padre.getNombreUsuario() + "', '" + hijo.getNombreUsuario() + "');");
				
		update(query.toString());
		
	}
	
	
}
