package modelo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import modelo.conexion.Conexion;
import modelo.vo.HijoVO;
import modelo.vo.MonitorVO;
import modelo.vo.PadreVO;
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
        
        conexion.closeConnection();
                
		registroEspecifico(usuario);
        //TODO descomentar para registrar en las tablas de padre, hijo, monitor... Pero hay campos del padre que faltan por poner
	}
	
	public void registroEspecifico(UsuarioVO usuario) {
		switch(usuario.getTipo()) {
			case PADRE:
				new PadreDAO().registrarPadre((PadreVO) usuario);
				break;
			case HIJO:
				new HijoDAO().registrarHijo((HijoVO) usuario);
				break;
			case MONITOR:
				new MonitorDAO().registrarMonitor((MonitorVO) usuario);
		}
	}
	
	public void mostrarUsuarios() { //TODO solo para probar
		ResultSet resultSet = null;
		conexion.openConnection();
		statement = conexion.getSt();
        String selectSql = "SELECT * FROM USERS;";
    	try {
			resultSet = statement.executeQuery(selectSql);
	    	
	    	while(resultSet.next()) {
	    		System.out.println(resultSet.getString(1));
	    	}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
}
