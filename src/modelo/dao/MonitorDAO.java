package modelo.dao;

import java.sql.SQLException;
import java.sql.Statement;
import modelo.conexion.Conexion;
import modelo.vo.MonitorVO;


public class MonitorDAO extends UsuarioDAO{
	
	public void registrarMonitor(MonitorVO monitor) throws SQLException{
		conexion.openConnection();
		statement = conexion.getSt();
		
		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO MONITORS");
		query.append("(Username, PhoneNumber, Specialty) ");
		query.append("VALUES(");
		query.append("'" + monitor.getNombreUsuario() + "', '" + monitor.getTelefono() + "', '" + monitor.getEspecialidad() + "');");
		statement.executeUpdate(query.toString());
		conexion.closeConnection();
	}
}
