package modelo.dao;

import java.sql.SQLException;

import modelo.vo.HijoVO;

public class HijoDAO extends UsuarioDAO{
	
	public void registrarHijo(HijoVO hijo) throws SQLException{
		conexion.openConnection();
		statement = conexion.getSt();
		
		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO KIDS");
		query.append("(Username) ");
		query.append("VALUES(");
		query.append("'" + hijo.getNombreUsuario() + "');");
		statement.executeUpdate(query.toString());
		 conexion.closeConnection();
	}
}
