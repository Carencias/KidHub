package modelo.dao;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import modelo.vo.HijoVO;

/**
 * Clase encargada de terminar el registro de los hijos en la base de datos
 * @version 1.0
 * @author Diego Simon Gonzalez, Pablo Bayon Gutierrez, Santiago Valbuena Rubio
 */
public class HijoDAO extends UsuarioDAO{
	
	static Logger logger = Logger.getLogger(HijoDAO.class);
	
	/**
	 * Metodo que lanza la query para crear registrar un hijo en la base de datos
	 * @param hijo
	 *  HijoVO con la informacion del hijo
	 * @throws SQLException
	 */
	public void registrarHijo(HijoVO hijo) throws SQLException{
		conexion.openConnection();
		statement = conexion.getSt();
		
		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO KIDS");
		query.append("(Username) ");
		query.append("VALUES(");
		query.append("'" + hijo.getNombreUsuario() + "');");
		
		logger.trace("Query lista para ser lanzada");
		statement.executeUpdate(query.toString());
		logger.trace("Query ejecutada con exito");
		
		conexion.closeConnection();
	}
}
