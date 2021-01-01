package modelo.dao;

import java.sql.SQLException;
import java.sql.Statement;

import modelo.conexion.Conexion;
import modelo.vo.HijoVO;
import modelo.vo.PadreVO;

public class PadreDAO extends UsuarioDAO{
	
	
	/*public void registrarPadre(PadreVO padre) {
		Statement statement;
		
		conexion.openConnection();
		statement = conexion.getSt();
		
		StringBuilder insertQuery = new StringBuilder();
		insertQuery.append("INSERT INTO PARENTS");
		insertQuery.append("(Username, DNI, UserPassword, Email, FirstName, SecondName, BirthDate, Type, PhoneNumber) ");
		insertQuery.append("VALUES(");
		insertQuery.append("'" + padre.getNombreUsuario() + "', '" + padre.getDni() + "', '" + padre.getContrasena() + "', '" + padre.getEmail() + "', '");
		insertQuery.append(padre.getNombre() + "', '" + padre.getApellidos() + "', '" + padre.getFechaNacimiento() + "', '" + padre.getTipo().toString() + "', '" + padre.getTelefono() + "'");
		
		//TODO CAMPOS PARA LA DIRECCION?? @DIEGO CREE QUE SOBRAN
		//TODO OJO CON EL getTipo en el string

        try {
			statement.executeUpdate(insertQuery.toString());
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}*/
	
	
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
	
	
}
