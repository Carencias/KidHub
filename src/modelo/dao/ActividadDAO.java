package modelo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.apache.log4j.Logger;

import modelo.KidHubException;
import modelo.conexion.Conexion;
import modelo.vo.ActividadVO;
import modelo.vo.Direccion;
import modelo.vo.HijoVO;
import modelo.vo.UsuarioVO;
import modelo.vo.UsuarioVO.TipoUsuario;

/**
 * Clase encargada de lanzar las querys de crear, modificar, borrar, apuntarHijoAActividad y desapuntarHijoAActivida a la base de datos
 * @version 1.0
 */
public class ActividadDAO {
	
	//Referencia a la conxeion con la base de datos
	private Conexion conexion;
	
	//Referencia a un statemnt
	private Statement statement;
	
    static Logger logger = Logger.getLogger(ActividadDAO.class);
	/**
	 * Constructor de la clase ActividadDAO, que inicializa el atributo conexion
	 */
	public ActividadDAO() {
		this.conexion = new Conexion();
	}
	
	/**
	 * Metodo que lanza la query para crear una actividad a la base de datos
	 * @param actividad
	 *  ActividadVO con los datos de la actividad
	 * @throws SQLException
	 */
	public void crearActividad(ActividadVO actividad) throws SQLException{
		conexion.openConnection();
		statement = conexion.getSt();
		
		StringBuilder insertQuery = new StringBuilder();
		insertQuery.append("INSERT INTO ACTIVITIES");
		insertQuery.append("(MonitorUsername, Name, StartDate, Duration, EndDate, Capacity, PlacesAvailable, Address, Town, Type) ");
		insertQuery.append("VALUES(");
		insertQuery.append("'" + actividad.getMonitor().getNombreUsuario() + "', '" + actividad.getNombre() + "', '" + actividad.getTextoInicio() + "', '" + actividad.getDuracion() + "', '" );
		insertQuery.append(actividad.getTextoFin() + "', '" + actividad.getCapacidad() + "', '" + actividad.getCapacidad() + "', '"  + actividad.getDireccion().getTextoDireccion() + "', '");
		insertQuery.append(actividad.getDireccion().getCiudad() + "', '" + actividad.getTipo() + "');");
		logger.trace("Query lista para ser lanzada");
		statement.executeUpdate(insertQuery.toString(), Statement.RETURN_GENERATED_KEYS);
		logger.trace("Query ejecutada con exito");
		ResultSet generatedKeys = statement.getGeneratedKeys();
		
		if(generatedKeys.next()) {
			actividad.setIdActividad(generatedKeys.getInt(1));
		}
		conexion.closeConnection();
	}
	
	/**
	 * Metodo que lanza la query para modificar una actividad a la base de datos
	 * @param actividad
	 *  ActividadVO con los datos de la actividad
	 */
	public void modificarActividad(ActividadVO actividad) throws SQLException{
		conexion.openConnection();
		statement = conexion.getSt();
		
		StringBuilder query = new StringBuilder();
		query.append("UPDATE ACTIVITIES SET ");
		query.append("Name='" + actividad.getNombre() + "', ");
		query.append("StartDate='" + actividad.getTextoInicio() + "', ");
		query.append("Duration='" + actividad.getDuracion() + "', ");
		query.append("EndDate='" + actividad.getTextoFin() + "', ");
		query.append("Capacity='" + actividad.getCapacidad() + "', ");
		query.append("Address='" + actividad.getDireccion().getTextoDireccion() + "', ");
		query.append("Town='" + actividad.getDireccion().getCiudad() + "', ");
		query.append("Type='" + actividad.getTipo() + "'");
		query.append(" WHERE ActivityID='" + actividad.getIdActividad()+"';");
		
		logger.trace("Query lista para ser lanzada");	
		statement.executeUpdate(query.toString());
		logger.trace("Query ejecutada con exito");
		conexion.closeConnection();
	}
	
	/**
	 * Metodo que lanza la query para borrar una actividad a la base de datos
	 * @param actividad
	 *  ActividadVO con los datos de la actividad que sera borrada
	 */
	public void borrarActividad(ActividadVO actividad) throws SQLException{
		Statement statement;
		
		conexion.openConnection();
		statement = conexion.getSt();
		
		StringBuilder query = new StringBuilder();
		query.append("DELETE FROM ACTIVITIES WHERE ");
		query.append("ActivityID='" + actividad.getIdActividad() + "'; ");
		
		logger.trace("Query lista para ser lanzada");
		statement.executeUpdate(query.toString());
		logger.trace("Query ejecutada con exito");
		conexion.closeConnection();
		
	}

	/**
	 * Metodo que rellena los datos de la actividad con los que se encuentren en la base de datos
	 * @param actividad
	 *  ActividadVO en la que se rellenaran los datos
	 */
	public void rellenarActividad(ActividadVO actividad) throws SQLException{
		ResultSet resultSet = null;
		conexion.openConnection();
		statement = conexion.getSt();
		
		StringBuilder query = new StringBuilder();
		query.append("SELECT * FROM ACTIVITIES ");
		query.append("WHERE ActivityID='"+ actividad.getIdActividad()+"';");
		
		logger.trace("Query lista para ser lanzada");
		resultSet = statement.executeQuery(query.toString());
		logger.trace("Query ejecutada con exito");
		while(resultSet.next()) {
			this.setDatosActividad(actividad, resultSet);
		}
		conexion.closeConnection();
	}

	/**
	 * Metodo que lanza la query que apunta un hijo a una actividad
	 * @param hijo
	 *  HijoVO con la informacion del hijo
	 * @param actividad
	 *  ActividadVO con la informacion de la actividad
	 * @throws SQLException
	 * @throws KidHubException 
	 */
	public void apuntarHijoAActividad(HijoVO hijo, ActividadVO actividad) throws SQLException, KidHubException{
		conexion.openConnection();
		statement = conexion.getSt();
		
		int plazas = this.getPlazas(actividad);
		
		if(plazas == 0) {
			throw new KidHubException("No hay plazas disponibles"); 
		}
		
		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO ActivityKid");
		query.append("(KidUsername, ActivityID) ");
		query.append("VALUES(");
		query.append("'" + hijo.getNombreUsuario() + "', '" + actividad.getIdActividad() + "');");
			
		logger.trace("Query lista para ser lanzada");
		statement.executeUpdate(query.toString());
		logger.trace("Query ejecutada con exito");
		
		this.setPlazas(actividad, plazas-1);
		
		conexion.closeConnection();
	}

	/**
	 * Metodo que lanza la query que desapunta un hijo a una actividad
	 * @param hijo
	 *  HijoVO con la informacion del hijo
	 * @param actividad
	 *  ActividadVO con la informacion de la actividad
	 * @throws SQLException
	 * @throws KidHubException 
	 */
	public void desapuntarHijoDeActividad(HijoVO hijo, ActividadVO actividad) throws SQLException, KidHubException{
		conexion.openConnection();
		Statement statement = conexion.getSt();
		
		StringBuilder query = new StringBuilder();
		query.append("DELETE FROM ActivityKid WHERE ");
		query.append("ActivityID='" + actividad.getIdActividad() + "'AND KidUsername='" + hijo.getNombreUsuario() + "';");
		
		logger.trace("Query lista para ser lanzada");
		statement.executeUpdate(query.toString());
		logger.trace("Query ejecutada con exito");
		
		this.setPlazas(actividad, this.getPlazas(actividad)+1);
		
		conexion.closeConnection();
	}
	
	
	private int getPlazas(ActividadVO actividad) throws SQLException, KidHubException {
		int plazas=0;
		statement = conexion.getSt();
				
		String query = "SELECT PlacesAvailable FROM ACTIVITIES WHERE ActivityID='"+ actividad.getIdActividad()+"';";
				
		ResultSet resultSet = statement.executeQuery(query);
		
		if(resultSet.next()) {
			plazas = resultSet.getInt("PlacesAvailable");
		}

		logger.info("Quedan: " + plazas + " en la actividad " + actividad.getIdActividad());
				
		return plazas;
		
	}
	

	private void setPlazas(ActividadVO actividad, int plazas) throws SQLException {
		StringBuilder query = new StringBuilder();
		
		query.append("UPDATE ACTIVITIES SET ");
		query.append("PlacesAvailable='" + plazas + "'");
		query.append(" WHERE ActivityID='" + actividad.getIdActividad()+"';");
		
		statement.executeUpdate(query.toString());
		logger.info("Ahora quedan: " + plazas + " en la actividad " + actividad.getIdActividad());
	}

	/**
	 * Metodo que devuelve una lista con las actividades relacionadas con el usuario
	 * @param usuariovo
	 *  Usuario del que se quieren obtener las actividades
	 * @return
	 *  Lista de actividades ActividadVO del usuario
	 * @throws SQLException
	 */
	public ArrayList<ActividadVO> mostrarActividades(UsuarioVO usuariovo) throws SQLException{
		ResultSet resultSet = null;
		conexion.openConnection();
		statement = conexion.getSt();
		
		StringBuilder query = new StringBuilder();
		
		if(usuariovo.getTipo() == TipoUsuario.MONITOR) {
			query.append("SELECT * FROM ACTIVITIES ");
			query.append("WHERE MonitorUsername='"+ usuariovo.getNombreUsuario()+"';");
		}else if(usuariovo.getTipo() == TipoUsuario.HIJO) {
			query.append("SELECT * FROM ACTIVITIES ");
			if(!usuariovo.getNombreUsuario().equals("TODOS")) {
				query.append("INNER JOIN ActivityKid ON ActivityKid.ActivityID = ACTIVITIES.ActivityID ");
				query.append("WHERE ActivityKid.KidUsername='"+ usuariovo.getNombreUsuario()+"';");
			}
		
		}				
		logger.trace("Query lista para ser lanzada");
		resultSet = statement.executeQuery(query.toString());
		logger.trace("Query ejecutada con exito");
		
		return this.obtenerActividades(resultSet);
	}

	/**
	 * Metodo privado que rellena una lista de actividades con las actividades obtenidas de la base de datos
	 * @param resultSet
	 *  Resultado de la ejecucion de la query con los datos de las actividades
	 * @return
	 *  Lista de ActividadVO con los datos de las actividades
	 * @throws SQLException
	 */
	private ArrayList<ActividadVO> obtenerActividades(ResultSet resultSet) throws SQLException{
		logger.trace("Rellenando lista de actividades");
		ArrayList<ActividadVO> actividades = new ArrayList<ActividadVO>();
		ActividadVO actividad;
		while(resultSet.next()) {
			actividad = new ActividadVO();
			this.setDatosActividad(actividad, resultSet);
			actividades.add(actividad);
		}
		conexion.closeConnection();
		return actividades;
	}
	
	/**
	 * Metodo privado que rellena una actividad de los datos obtenidos en la query de un resultSet
	 * @param actividad
	 *  ActividadVO en la que se introduciran los datos
	 * @param resultSet
	 *  Resultado de la ejecucion de la query
	 * @throws SQLException
	 */
	private void setDatosActividad(ActividadVO actividad, ResultSet resultSet) throws SQLException {
		actividad.setIdActividad(Integer.parseInt(resultSet.getString("ActivityID")));
		actividad.setNombre(resultSet.getString("Name"));
		actividad.setInicio(convertToDate(resultSet.getString("StartDate")));
		actividad.setFin(convertToDate(resultSet.getString("EndDate")));
		actividad.setDuracion();
		actividad.setCapacidad(resultSet.getInt("Capacity"));		
		actividad.setDireccion(this.convertToDireccion(resultSet.getString("Address"), resultSet.getString("Town")));
		actividad.setTipo(resultSet.getString("Type"));
	}
	
	/**
	 * Metodo privado que convierte una fecha en string a LocalDateTime
	 * @param stringDateTime
	 *  String con la fecha
	 * @return
	 *  Objeto LocalDateTime de la fecha
	 */
	private LocalDateTime convertToDate(String stringDateTime) {
		logger.trace("Conviertiendo la fecha a LocalDateTime");
		String date = stringDateTime.split(" ")[0];
		String time = stringDateTime.split(" ")[1];
		
		int day = Integer.parseInt(date.split("/")[0]);
		int month = Integer.parseInt(date.split("/")[1]);
		int year = Integer.parseInt(date.split("/")[2]);
		
		int hour = Integer.parseInt(time.split(":")[0]);
		int min = Integer.parseInt(time.split(":")[1]);

		return LocalDateTime.of(year, month, day, hour, min);
	}
	
	/**
	 * Metodo privado que separa la direccion 
	 * @param direccionCompleta
	 *  Direccion completa sin la ciudad
	 * @param ciudad
	 *  Ciudad
	 * @return
	 *  Objeto direccion
	 */
	private Direccion convertToDireccion(String direccionCompleta, String ciudad) {
		
		String calle = direccionCompleta.split(",")[0];
		String numero = direccionCompleta.split(",")[1];
		String codigoPostal = direccionCompleta.split(",")[2];
		
		return new Direccion(calle, Integer.parseInt(numero), codigoPostal, ciudad);		
	}

	
}
