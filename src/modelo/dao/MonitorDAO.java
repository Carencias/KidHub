package modelo.dao;

import java.sql.SQLException;
import java.sql.Statement;

import modelo.conexion.Conexion;
import modelo.vo.PadreVO;

public class MonitorDAO extends UsuarioDAO{
	
	private static MonitorDAO monitorDAO = new MonitorDAO();
	
	//private Conexion conexion;
	
	/*private MonitorDAO() {
		this.conexion = new Conexion();
	}*/
	
	public MonitorDAO getMonitorDAO() {
		return monitorDAO;
	}
	
	
	public void registrarMonitor(PadreVO monitor) {
		Statement statement;
		
		conexion.openConnection();
		statement = conexion.getSt();
		
		StringBuilder insertQuery = new StringBuilder();
		insertQuery.append("INSERT INTO MONITORS");
		insertQuery.append("(Username, DNI, UserPassword, Email, FirstName, SecondName, BirthDate, Type, PhoneNumber) ");
		insertQuery.append("VALUES(");
		insertQuery.append("'" + monitor.getNombreUsuario() + "', '" + monitor.getDni() + "', '" + monitor.getContrasena() + "', '" + monitor.getEmail() + "', '");
		insertQuery.append(monitor.getNombre() + "', '" + monitor.getApellidos() + "', '" + monitor.getFechaNacimiento() + "', '" + monitor.getTipo().toString() + "', '" + monitor.getTelefono() + "'");
		
		//TODO CAMPOS PARA LA DIRECCION?? @DIEGO CREE QUE SOBRAN
		//TODO OJO CON EL getTipo en el string

        try {
			statement.executeUpdate(insertQuery.toString());
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
