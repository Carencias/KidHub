package modelo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import modelo.conexion.Conexion;
import modelo.vo.UsuarioVO;

public class UsuarioDAO {
	
	protected Conexion conexion;
	
	protected Statement statement;
	
	public UsuarioDAO() {
		this.conexion = new Conexion();
		
	}
	
	
	public void registrarUsuario(UsuarioVO usuario) {
		
		conexion.openConnection();
		statement = conexion.getSt();
		
		StringBuilder insertQuery = new StringBuilder();
		insertQuery.append("INSERT INTO USERS");
		insertQuery.append("(Username, DNI, UserPassword, Email, FirstName, SecondName, BirthDate, Type) ");
		insertQuery.append("VALUES(");
		insertQuery.append("'" + usuario.getNombreUsuario() + "', '" + usuario.getDni() + "', '" + usuario.getContrasena() + "', '" + usuario.getEmail() + "', '");
		insertQuery.append(usuario.getNombre() + "', '" + usuario.getApellidos() + "', '" + usuario.getFechaNacimiento() + "', '" + usuario.getTextoTipo() + "');");

        try {
			statement.executeUpdate(insertQuery.toString());
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void registroEspecifico(UsuarioVO usuario) {
		switch(usuario.getTipo()) {
		case PADRE:
			this.registrarPadre(usuario);
		case HIJO:
			this.registrarHijo(usuario);
		case MONITOR:
			this.registrarMonitor(usuario);
		}
	}
	
	public void registrarPadre(UsuarioVO usuario) {
		PadreDAO padreDAO = new PadreDAO();

		padreDAO.registrarPadre(usuario);
	}
	
	public void registrarHijo(UsuarioVO usuario) {
		HijoDAO hijoDAO = new HijoDAO();
		
		hijoDAO.registrarHijo(usuario);
	}
	
	public void registrarMonitor(UsuarioVO usuario){
		MonitorDAO monitorDAO = new MonitorDAO();
		
		monitorDAO.registrarHijo(usuario);
	}
	
	public void mostrarUsuarios() { //TODO solo para probar
		ResultSet resultSet = null;
        String selectSql = "SELECT Username, UserPassword FROM USERS;";
    	try {
			resultSet = statement.executeQuery(selectSql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	System.out.println(resultSet.toString()); //OJO AL NULLPOINTER
    	

	}
	
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
