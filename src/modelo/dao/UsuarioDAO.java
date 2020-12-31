package modelo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import modelo.conexion.Conexion;
import modelo.vo.UsuarioVO;

public class UsuarioDAO {
	
	private static UsuarioDAO usuario = new UsuarioDAO();
	
	protected Conexion conexion;
	
	protected UsuarioDAO() {
		this.conexion = new Conexion();
	}
	
	public UsuarioDAO getUsuarioDAO() {
		return usuario;
	}
	
	/*public void registrarUsuario(UsuarioVO usuario) {
		Statement statement;
		
		conexion.openConnection();
		statement = conexion.getSt();
		
		StringBuilder insertQuery = new StringBuilder();
		insertQuery.append("INSERT INTO ");
		insertQuery.append(this.getNombreTabla(usuario));
		insertQuery.append("(Username, DNI, UserPassword, Email, FirstName, SecondName, BirthDate, Type) ");
		insertQuery.append("VALUES(");
		insertQuery.append("'" + usuario.getNombreUsuario() + "', '" + usuario.getDni() + "', '" + usuario.getContrasena() + "', '" + usuario.getEmail() + "', '");
		insertQuery.append(usuario.getNombre() + "', '" + usuario.getApellidos() + "', '" + usuario.getFechaNacimiento() + "', '" + usuario.getEmail() + "'");

        try {
			statement.executeUpdate(insertQuery.toString());
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private String getNombreTabla(UsuarioVO usuario) {
		String nombreTabla="";
		
		switch(usuario.getTipo()) {
			case PADRE:
				nombreTabla = "PARENTS";
			case HIJO:
				nombreTabla = "KIDS";
			case MONITOR:
				nombreTabla = "MONITORS";
		}
		
		return nombreTabla;
	}*/
	
	/*
	Conexion con = new Conexion();
	ResultSet resultSet;
	con.openConnection();
	Statement statement = con.getSt();
		
        // Create and execute a SELECT SQL statement.
        String selectSql = "SELECT Username, UserPassword FROM USERS;";
        String insert = "INSERT INTO USERS(Username, DNI, UserPassword, Email, FirstName, SecondName, BirthDate, Type) VALUES('PACO', '02770768G', 'JUANCARLOS', 'sant@san.com', 'santi', 'poidaw', '2008-7-04', 'PADRE');";
        	resultSet = statement.executeQuery(selectSql);

	*/
}
