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
	
	/**
	 * Para conectar con la BBDD
	 */
	protected Conexion conexion;
	
	/**
	 * Para ejecutar queries en la BBDD
	 */
	protected Statement statement;
	
	static Logger logger = Logger.getLogger(UsuarioDAO.class);
	
	/**
	 * Constructor de la clase ActividadDAO, que inicializa el atributo conexion
	 */
	public UsuarioDAO() {
		this.conexion = new Conexion();	
	}
	
	/**
	 * Lanza una query para registrar un usuario a la base de datos
	 * @param usuario
	 * Contiene la informacion del usuario
	 * @throws SQLException
	 * Si hay algun error al ejecutar la query
	 */
	public void registrarUsuario(UsuarioVO usuario) throws SQLException{
		
		conexion.openConnection();
		statement = conexion.getSt();
		
		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO USERS");
		query.append("(Username, DNI, UserPassword, Email, FirstName, SecondName, BirthDate, Age, Type) ");
		query.append("VALUES(");
		query.append("'" + usuario.getNombreUsuario() + "', '" + usuario.getDni() + "', '" + usuario.getContrasena() + "', '" + usuario.getEmail() + "', '");
		query.append(usuario.getNombre() + "', '" + usuario.getApellidos() + "', '" + usuario.getFechaNacimiento() + "', '" + usuario.getEdad() + "', '" + usuario.getTextoTipo() + "');");
		
		logger.trace("Query lista para ser lanzada");
		statement.executeUpdate(query.toString());
		logger.trace("Query ejecutada con exito");
		conexion.closeConnection();
		registroEspecifico(usuario);
	}
	
	/**
	 * Termina el registro dependiendo del tipo de usuario
	 * @param usuario
	 *  contiene la informacion del usuario
	 * @throws SQLException
	 * Si hay algun error al ejecutar la query
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
	 * Lanza una query para obtener los datos de un usuario de la BBDD
	 * @param usuario
	 * Contiene la informacion del usuario
	 * @throws SQLException
	 * Si hay algun error al ejecutar la query
	 */
	public UsuarioVO loguearUsuario(UsuarioVO usuario) throws SQLException{

		ResultSet resultSet = null;
		conexion.openConnection();
		statement = conexion.getSt();
		
		StringBuilder query = new StringBuilder();
		query.append("SELECT * FROM USERS ");
		query.append("WHERE Username='"+ usuario.getNombreUsuario()+"';");
		
		logger.trace("Query lista para ser lanzada");
		resultSet = statement.executeQuery(query.toString());
		logger.trace("Query ejecutada con exito");
		if(resultSet.next()) {			
			switch(resultSet.getString("Type")) {
				case "Padre":
					usuario = new PadreVO();
					logger.trace("Usuario padre obtenido de la base de datos");
					this.setDatosBasicosUsuario(usuario, resultSet, TipoUsuario.PADRE);
					break;
				case "Hijo":
					usuario = new HijoVO();
				    logger.trace("Usuario hijo obtenido de la base de datos");
					this.setDatosBasicosUsuario(usuario, resultSet, TipoUsuario.HIJO);
					break;
				case "Monitor":
					usuario = new MonitorVO();
					logger.trace("Usuario monitor obtenido de la base de datos");
					this.setDatosBasicosUsuario(usuario, resultSet, TipoUsuario.MONITOR);
					break;
				default:
					conexion.closeConnection();
					throw new SQLException("Contraseña o usuarios desconocidos");
			}
		}else {
			conexion.closeConnection();
			throw new SQLException("Contraseña o usuarios desconocidos");
		}
		conexion.closeConnection();
		return usuario;
	}
	
	private void setDatosBasicosUsuario(UsuarioVO usuario, ResultSet resultSet, TipoUsuario tipo) throws SQLException {
		usuario.setNombreUsuario(resultSet.getString("Username"));
		usuario.setContrasena(resultSet.getString("UserPassword"));
		usuario.setNombre(resultSet.getString("FirstName"));
		usuario.setTipo(tipo);
	}

	/**
	 * Comprueba si la contrasena y el usuario introducidos son correctos
	 * @param usuario
	 * Contiene la contrasena y el usuario introducidos
	 * @return
	 * true si las credenciales son correctas
	 * @throws SQLException
	 * Si hay algun error al ejecutar la query
	 */
	public boolean credencialesCorrectas(UsuarioVO usuario) throws SQLException {
		ResultSet resultSet = null;
		conexion.openConnection();
		statement = conexion.getSt();
		boolean credencialesCorrectas = false;
		
		StringBuilder query = new StringBuilder();
		query.append("SELECT * FROM USERS ");
		query.append("WHERE Username='"+ usuario.getNombreUsuario()+"';");
		
		logger.trace("Query lista para ser lanzada");
		resultSet = statement.executeQuery(query.toString());
		logger.trace("Query ejecutada con exito");
		if(resultSet.next()) {
			if(usuario.getContrasena().equals(resultSet.getString("UserPassword"))) {
				logger.info("El usuario y la contrasena introducidos son correctos");
				credencialesCorrectas = true;
			}else {
				logger.error("Se ha introducido una contrasena incorrecta");
				credencialesCorrectas = false;
			}
		}else {
			logger.error("El usuario introducido no existe");
			credencialesCorrectas = false;
		}
		
		return credencialesCorrectas;
	}	
}
