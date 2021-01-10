package modelo.dao;

import java.sql.SQLException;
import org.apache.log4j.Logger;
import modelo.vo.MonitorVO;

/**
 * Clase encargada de terminar el registro de los monitores en la base de datos
 * @version 1.0
 * @author Diego Simon Gonzalez, Pablo Bayon Gutierrez, Santiago Valbuena Rubio
 */
public class MonitorDAO extends UsuarioDAO{
	
	static Logger logger = Logger.getLogger(MonitorDAO.class);
	
	 /**
	 * Metodo que lanza la query para crear registrar un monitor en la base de datos
	 * @param monitor
	 *  MonitorVO con la informacion del monitor
	 * @throws SQLException
	 */
	public void registrarMonitor(MonitorVO monitor) throws SQLException{
		conexion.openConnection();
		statement = conexion.getSt();
		
		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO MONITORS");
		query.append("(Username, PhoneNumber, Specialty) ");
		query.append("VALUES(");
		query.append("'" + monitor.getNombreUsuario() + "', '" + monitor.getTelefono() + "', '" + monitor.getEspecialidad() + "');");
		
		logger.trace("Query lista para ser lanzada");
		statement.executeUpdate(query.toString());
		logger.trace("Query ejecutada con exito");
		conexion.closeConnection();
	}
}
