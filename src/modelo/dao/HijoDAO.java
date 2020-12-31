package modelo.dao;

import java.sql.SQLException;

import modelo.vo.HijoVO;

public class HijoDAO extends UsuarioDAO{
	
	public void registrarHijo(HijoVO hijo) {
		conexion.openConnection();
		statement = conexion.getSt();
		
		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO KIDS");
		query.append("(Username) ");
		query.append("VALUES(");
		query.append("'" + hijo.getNombreUsuario() + "'");
		update(query.toString());
	}
	
	public void update(String query) {
        try {
			statement.executeUpdate(query.toString());
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
