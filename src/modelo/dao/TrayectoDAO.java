package modelo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import modelo.Logica;
import modelo.conexion.Conexion;
import modelo.vo.ActividadVO;
import modelo.vo.HijoVO;
import modelo.vo.PadreVO;
import modelo.vo.ParadaVO;
import modelo.vo.ParadaVO.TipoParada;
import modelo.vo.UsuarioVO.TipoUsuario;
import modelo.vo.TrayectoVO;
import modelo.vo.TrayectoVO.TipoTrayecto;
import modelo.vo.UsuarioVO;

/**
 * Clase encargada de manejar los trayectos
 * @version 1.0
 */
public class TrayectoDAO {

	private Conexion conexion;
	
	private Statement statement;
	
    static Logger logger = Logger.getLogger(TrayectoDAO.class);
	
	public TrayectoDAO() {
		logger.trace("Creado trayectoDAO");
		this.conexion = new Conexion();
	}

	/**
	 * Registra un trayecto en la base de datos
	 * @param trayecto
	 *  Objeto TrayectoVO con los datos del trayecto a registrar
	 * @throws SQLException
	 *  Lanza una excepcion si se produce un error en el registro
	 */
	public void crearTrayecto(TrayectoVO trayecto) throws SQLException {
		Statement statement;
		
		conexion.openConnection();
		statement = conexion.getSt();
		
		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO RIDES");
		query.append("(ActivityID, ParentUsername, Capacity, Type) ");
		query.append("VALUES(");
		query.append("'" + trayecto.getActividad().getIdActividad() + "', '" + trayecto.getPadre().getNombreUsuario() + "', '");
		query.append(trayecto.getCapacidad() + "', '" + trayecto.getTipo() + "');");
		
		logger.trace("Query lista para ser lanzada");
		statement.executeUpdate(query.toString(), Statement.RETURN_GENERATED_KEYS);
		logger.trace("Query ejecutada con exito");
		
		ResultSet generatedKeys = statement.getGeneratedKeys();
		
		if(generatedKeys.next()) {
			trayecto.setIdTrayecto(generatedKeys.getInt(1));
		}
		
		this.agregarParadaATrayecto(trayecto, trayecto.getOrigen());
		this.agregarParadaATrayecto(trayecto, trayecto.getDestino());
		conexion.closeConnection();
	}
	
	/**
	 * Borra un trayecto de la base de datos
	 * @param trayecto
	 *  TrayectoVO con los datos del trayecto a borrar
     * @throws SQLException
	 *  Lanza una excepcion si se produce un error al borrar
	 */
	public void borrarTrayecto(TrayectoVO trayecto) throws SQLException{
		Statement statement;
		
		conexion.openConnection();
		statement = conexion.getSt();
		
		StringBuilder query = new StringBuilder();
		query.append("DELETE FROM RIDES WHERE ");
		query.append("RideID='" + trayecto.getIdTrayecto() + "'; ");
		
		logger.trace("Query lista para ser lanzada");
		statement.executeUpdate(query.toString());
		logger.trace("Query ejecutada con exito");
		
		conexion.closeConnection();
	}

	/**
	 * Registra una parada a un trayecto en la base de datos
	 * @param trayecto
	 *  Objeto TrayectoVO con la informacion del trayecto
	 * @param parada
	 *  Objeto ParadaVO con la informacion de la parada
	 */
	public void agregarParadaATrayecto(TrayectoVO trayecto, ParadaVO parada) throws SQLException{
		conexion.openConnection();
		statement = conexion.getSt();
		
		//TODO QUITAR LA DURACION DE LA TABLA STOPS
		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO STOPS");
		query.append("(RideID, StopDate, Address, Town, Type) ");
		query.append("VALUES(");
		query.append("'" + trayecto.getIdTrayecto() + "', '" + parada.getTextoFecha() + "', '");
		query.append(parada.getDireccion().getTextoDireccion() + "', '" + parada.getDireccion().getCiudad() + "', '");
		query.append(parada.getTextoTipo() + "');");
		
		logger.trace("Query lista para ser lanzada");
		statement.executeUpdate(query.toString());
		logger.trace("Query ejecutada con exito");
		
		conexion.closeConnection();		
	}

	/**
	 * Apunta un hijo a un trayecto en la base de datos
	 * @param hijo
	 *  Objeto HijoVO con la informacion del hijo a apuntar
	 * @param trayecto
	 *  Objeto TrayectoVO con la informacion del trayecto
	 */
	public void apuntarHijoATrayecto(HijoVO hijo, TrayectoVO trayecto) throws SQLException{
		conexion.openConnection();
		statement = conexion.getSt();
		
		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO RideKid");
		query.append("(RideID, KidUsername) ");
		query.append("VALUES(");
		query.append("'" + trayecto.getIdTrayecto() + "', '" + hijo.getNombreUsuario() + "');");
		
		logger.trace("Query lista para ser lanzada");
		statement.executeUpdate(query.toString());
		logger.trace("Query ejecutada con exito");
		
		conexion.closeConnection();	
	}

	/**
	 * Despaunta un hijo a un trayecto en la base de datos
	 * @param hijo
	 *  Objeto HijoVO con la informacion del hijo a apuntar
	 * @param trayecto
	 *  Objeto TrayectoVO con la informacion del trayecto
	 */
	public void desapuntarHijoDeTrayecto(HijoVO hijo, TrayectoVO trayecto) throws SQLException{		
		conexion.openConnection();
		statement = conexion.getSt();
		
		StringBuilder query = new StringBuilder();
		query.append("DELETE FROM RideKid WHERE ");
		query.append("RideID='" + trayecto.getIdTrayecto() + "'AND KidUsername='" + hijo.getNombreUsuario() + "';");
		
		logger.trace("Query lista para ser lanzada");
		statement.executeUpdate(query.toString());
		logger.trace("Query ejecutada con exito");
		
		conexion.closeConnection();			
	}
	
	/**
	 * Muestra los trayectos de un usuario
	 * @param usuario
	 *  Objeto UsuarioVO con la informacion del usuario
	 * @return
	 *  Lista con los trayectos del usuario existentes en la base de datos
	 */
	public ArrayList<TrayectoVO> mostrarTrayectos(UsuarioVO usuario) throws SQLException{
		ResultSet resultSet = null;
		conexion.openConnection();
		statement = conexion.getSt();
		
		StringBuilder query = new StringBuilder();
		//TODO comprobar lo de TODOS Y PROPIOS EN TODOS LOS SITIOS
		query.append("SELECT * FROM RIDES ");
		
		if(usuario.getNombreUsuario().equals("PROPIOS")) {
			query.append("WHERE ParentUsername='"+ Logica.getLogica().getUsuarioActual().getNombreUsuario()+"';");
		}else if(usuario.getTipo() == TipoUsuario.HIJO) {

			if(!usuario.getNombreUsuario().equals("TODOS")) {
				query.append("INNER JOIN RideKid ON RideKid.RideID = RIDES.RideID ");
				query.append("WHERE RideKid.KidUsername='"+ usuario.getNombreUsuario()+"';");
			}
		}	
		
		logger.trace("Query lista para ser lanzada");
		resultSet = statement.executeQuery(query.toString());
		logger.trace("Query ejecutada con exito");
		
		return this.obtenerTrayectos(resultSet);
	}
	
	/**
	 * Crea una lista de trayectosVO con los datos proporcionados tras la ejecucion de la query
	 * @param resultSet
	 *  Resultado de ejecucion de la query
	 * @return
	 *  Lista de trayectoVO
	 */
	private ArrayList<TrayectoVO> obtenerTrayectos(ResultSet resultSet) throws SQLException{
		logger.trace("Creando lista de trayectos");
		ArrayList<TrayectoVO> trayectos = new ArrayList<TrayectoVO>();
		TrayectoVO trayecto;
		
		while(resultSet.next()) {
			trayecto = new TrayectoVO();
			this.setDatosTrayecto(trayecto, resultSet);
			trayectos.add(trayecto);
		}
		conexion.closeConnection();
		return trayectos;		
	}
	
	/**
	 * Obtiene los datos de un trayecto con la informacion del trayecto existente en la base de datos
	 * @param trayecto
	 *  TrayectoVO en el que se almacenaran los datos
	 * @throws SQLException
	 *  Si se produce un error al obtener los datos
	 */
	public void rellenarTrayecto(TrayectoVO trayecto) throws SQLException{
		ResultSet resultSet = null;
		conexion.openConnection();
		statement = conexion.getSt();
		
		StringBuilder query = new StringBuilder();
		query.append("SELECT * FROM RIDES ");
		query.append("WHERE RideID='"+ trayecto.getIdTrayecto()+"';");
		
		resultSet = statement.executeQuery(query.toString());
		while(resultSet.next()) {
			this.setDatosTrayecto(trayecto, resultSet);
		}
		conexion.closeConnection();
	}
	
	
	/**
	 * Se incluyen los datos extraidos de la BBDD en el objeto contenedor trayecto
	 * @param trayecto
	 *  Contendra los datos extraidos de la BBDD
	 * @param resultSet
	 *  Contiene los datos extraidos de la BBDD que hay que pasar al trayecto
	 * @throws SQLException
	 *  Si hay algun error al ejecutar la query
	 */
	private void setDatosTrayecto(TrayectoVO trayecto, ResultSet resultSet) throws SQLException{
		
		ActividadVO actividad = new ActividadVO();		
		int id = Integer.parseInt(resultSet.getString("ActivityID"));
		actividad.setIdActividad(id);
		Logica.getLogica().rellenarActividad(actividad);
		trayecto.setActividad(actividad);
		trayecto.setIdTrayecto(Integer.parseInt(resultSet.getString("RideID")));
		
		PadreVO padre = new PadreVO();
		padre.setNombre(resultSet.getString("ParentUsername"));
		trayecto.setPadre(padre);
		trayecto.setCapacidad(Integer.parseInt(resultSet.getString("Capacity")));
		
		if(resultSet.getString("Type").equals("IDA")) {
			trayecto.setTipo(TipoTrayecto.IDA);		
		}else {
			trayecto.setTipo(TipoTrayecto.VUELTA);
		}
		ArrayList<ParadaVO> paradas = new ParadaDAO().mostrarParadas(trayecto);
		//Siempre sera un array de 2 paradas, una de cada
		for(ParadaVO parada: paradas) {
			if(parada.getTipo().equals(TipoParada.ORIGEN)) {
				trayecto.setOrigen(parada);
			}else {
				trayecto.setDestino(parada);
			}
		}
	}

	/**
	 * Modifica los valores del trayecto almacenado en la BBDD con ID = parametroTrayectoID
	 * @param trayecto
	 * Contiene los nuevos valores a insertar en la tabla
	 * @throws SQLException
	 * Si hay algun error al ejecutar la query
	 */
	public void modificarTrayecto(TrayectoVO trayecto) throws SQLException {
		conexion.openConnection();
		statement = conexion.getSt();
		
		StringBuilder query = new StringBuilder();
		query.append("UPDATE RIDES SET ");		
		query.append("ParentUsername='" + trayecto.getPadre().getNombreUsuario() + "', ");
		query.append("Capacity='" + trayecto.getCapacidad() + "', ");
		query.append("Type='" + trayecto.getTipo() + "'");
		query.append(" WHERE RideID='" + trayecto.getIdTrayecto()+"';");
		
		logger.trace("Query para modificar trayecto lista para ser lanzada");
		statement.executeUpdate(query.toString());
		logger.trace("Query para modificar trayecto ejecutada con exito");
		this.modificarParada(trayecto, trayecto.getOrigen());
		this.modificarParada(trayecto, trayecto.getDestino());		
		
		conexion.closeConnection();
	}
	
	/**
	 * Modifica los valores pasados a traves del parametro parada en la BBDD
	 * @param trayecto
	 * Trayecto al que pertenece la parada
	 * @param parada
	 * Contiene los valores a modificar
	 * @throws SQLException
	 * Si hay algun error al ejecutar la query
	 */
	private void modificarParada(TrayectoVO trayecto, ParadaVO parada) throws SQLException {
		statement = conexion.getSt();
		
		StringBuilder query = new StringBuilder();
		query.append("UPDATE STOPS SET ");		
		query.append("StopDate='" + parada.getTextoFecha() + "', ");
		query.append("Address='" + parada.getDireccion().getTextoDireccion() + "', ");
		query.append("Town='" + parada.getDireccion().getCiudad() + "' ");
		query.append(" WHERE RideID='" + trayecto.getIdTrayecto()+"' AND Type='" + parada.getTextoTipo() + "';");
		

		logger.trace("Query para modificar trayecto lista para ser lanzada");	
		statement.executeUpdate(query.toString());
		logger.trace("Query para modificar trayecto ejecutada con exito");
	}
}
