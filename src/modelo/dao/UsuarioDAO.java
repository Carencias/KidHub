package modelo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import modelo.conexion.Conexion;
import modelo.vo.HijoVO;
import modelo.vo.MonitorVO;
import modelo.vo.PadreVO;
import modelo.vo.UsuarioVO;
import modelo.vo.UsuarioVO.TipoUsuario;

/**
 * Clase encargada de registrar usuarios y obtener usuarios en la base de datos
 * @version 1.0
 */
public class UsuarioDAO {
	
	//Referencia a la conxeion con la base de datos
	protected Conexion conexion;
	
	//Referencia a un statemnt
	protected Statement statement;
	
	static Logger logger = Logger.getLogger(UsuarioDAO.class);
	/**
	 * Constructor de la clase ActividadDAO, que inicializa el atributo conexion
	 */
	public UsuarioDAO() {
		this.conexion = new Conexion();	
	}
	
	/**
	 * Metodo que lanza una query para registrar un usuario a la base de datos
	 * @param usuario
	 *  UsuarioVO con la informacion del usuario
	 * @throws SQLException
	 */
	public void registrarUsuario(UsuarioVO usuario) throws SQLException{
		
		conexion.openConnection();
		statement = conexion.getSt();
		
		StringBuilder insertQuery = new StringBuilder();
		insertQuery.append("INSERT INTO USERS");
		insertQuery.append("(Username, DNI, UserPassword, Email, FirstName, SecondName, BirthDate, Age, Type) ");
		insertQuery.append("VALUES(");
		insertQuery.append("'" + usuario.getNombreUsuario() + "', '" + usuario.getDni() + "', '" + usuario.getContrasena() + "', '" + usuario.getEmail() + "', '");
		insertQuery.append(usuario.getNombre() + "', '" + usuario.getApellidos() + "', '" + usuario.getFechaNacimiento() + "', '" + usuario.getEdad() + "', '" + usuario.getTextoTipo() + "');");
		
		logger.trace("Query lista para ser lanzada");
		statement.executeUpdate(insertQuery.toString());
		logger.trace("Query ejecutada con exito");
		conexion.closeConnection();
		registroEspecifico(usuario);
	}
	
	/**
	 * Metodo que termina el registro dependiendo del tipo de usuario
	 * @param usuario
	 *  UsuarioVO con la informacion del usuario
	 * @throws SQLException
	 */
	private void registroEspecifico(UsuarioVO usuario) throws SQLException{
		switch(usuario.getTipo()) {
			case PADRE:
				new PadreDAO().registrarPadre((PadreVO) usuario);
				break;
			case HIJO:
				new HijoDAO().registrarHijo((HijoVO) usuario);
				break;
			case MONITOR:
				new MonitorDAO().registrarMonitor((MonitorVO) usuario);
			break;
		}
	}
	
	/**
	 * Metodo que lanza una query para obtener un usuario de la base de datos
	 * @param usuario
	 *  UsuarioVO con la informacion del usuario
	 * @return
	 *  Devuelve un UsuarioVO con la informacion del usuario obtenida de la base de datos
	 * @throws SQLException
	 */
	public UsuarioVO loguearUsuario(UsuarioVO usuario) throws SQLException{
		ResultSet resultSet = null;
		conexion.openConnection();
		statement = conexion.getSt();
		
		StringBuilder selectQuery = new StringBuilder();
		selectQuery.append("SELECT * FROM USERS ");
		selectQuery.append("WHERE Username='"+ usuario.getNombreUsuario()+"';");
		
		logger.trace("Query lista para ser lanzada");
		resultSet = statement.executeQuery(selectQuery.toString());
		logger.trace("Query ejecutada con exito");
		if(resultSet.next()) {
			switch(resultSet.getString("Type")) {
				case "Padre":
					logger.trace("Usuario padre obtenido de la base de datos");
					PadreVO padre = new PadreVO();
					padre.setNombreUsuario(resultSet.getString("Username"));
					padre.setContrasena(resultSet.getString("UserPassword"));
					padre.setNombre(resultSet.getString("FirstName"));
					padre.setTipo(TipoUsuario.PADRE);
					conexion.closeConnection();
					return padre;
				case "Hijo":
				    logger.trace("Usuario hijo obtenido de la base de datos");
					HijoVO hijo = new HijoVO();
					hijo.setNombreUsuario(resultSet.getString("Username"));
					hijo.setContrasena(resultSet.getString("UserPassword"));
					hijo.setNombre(resultSet.getString("FirstName"));
					hijo.setTipo(TipoUsuario.HIJO);
					conexion.closeConnection();
					return hijo;
				case "Monitor":
					logger.trace("Usuario monitor obtenido de la base de datos");
					MonitorVO monitor = new MonitorVO();
					monitor.setNombreUsuario(resultSet.getString("Username"));
					monitor.setContrasena(resultSet.getString("UserPassword"));
					monitor.setNombre(resultSet.getString("FirstName"));
					monitor.setTipo(TipoUsuario.MONITOR);
					conexion.closeConnection();
					return monitor;
				default:
					conexion.closeConnection();
					throw new SQLException("Contraseña o usuarios desconocidos");
			}
		}else {
			conexion.closeConnection();
			throw new SQLException("Contraseña o usuarios desconocidos");
		}
	}	
}
