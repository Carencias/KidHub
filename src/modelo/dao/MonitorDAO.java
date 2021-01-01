package modelo.dao;

import java.sql.SQLException;
import java.sql.Statement;

import modelo.conexion.Conexion;
import modelo.vo.MonitorVO;
import modelo.vo.PadreVO;

public class MonitorDAO extends UsuarioDAO{
	
	public void registrarMonitor(MonitorVO monitor) {
		conexion.openConnection();
		statement = conexion.getSt();
		
		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO MONITORS");
		query.append("(Username, PhoneNumber, Specialty) ");
		query.append("VALUES(");
		query.append("'" + monitor.getNombreUsuario() + "', '" + monitor.getTelefono() + "', '" + monitor.getEspecialidad() + "');");
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
