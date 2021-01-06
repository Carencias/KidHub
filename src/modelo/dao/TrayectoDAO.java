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

public class TrayectoDAO {

	private Conexion conexion;
	
	private Statement statement;
	
    static Logger logger = Logger.getLogger(TrayectoDAO.class);
	
	public TrayectoDAO() {
		this.conexion = new Conexion();
	}
	
	//TODO logs y javadoc
	
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
				
		System.out.println(query.toString());
		
		statement.executeUpdate(query.toString(), Statement.RETURN_GENERATED_KEYS);
		
		ResultSet generatedKeys = statement.getGeneratedKeys();
		
		if(generatedKeys.next()) {
			trayecto.setIdTrayecto(generatedKeys.getInt(1));
		}
		
		this.agregarParadaATrayecto(trayecto, trayecto.getOrigen());
		this.agregarParadaATrayecto(trayecto, trayecto.getDestino());

	}
	
	public void borrarTrayecto(TrayectoVO trayecto) {
		Statement statement;
		
		conexion.openConnection();
		statement = conexion.getSt();
		
		StringBuilder query = new StringBuilder();
		query.append("DELETE FROM RIDES WHERE ");
		query.append("RideID='" + trayecto.getIdTrayecto() + "'; ");
				
        try {
			statement.executeUpdate(query.toString());
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void agregarParadaATrayecto(TrayectoVO trayecto, ParadaVO parada) {
		conexion.openConnection();
		statement = conexion.getSt();
		
		//TODO QUITAR LA DURACION DE LA TABLA STOPS
		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO STOPS");
		query.append("(RideID, StopDate, Duration, Address, Town, Type) ");
		query.append("VALUES(");
		query.append("'" + trayecto.getIdTrayecto() + "', '" + parada.getTextoFecha() + "', '");
		query.append(trayecto.getDuracion() + "', '" + parada.getDireccion().getTextoDireccion() + "', '" + parada.getDireccion().getCiudad() + "', '");
		query.append(parada.getTextoTipo() + "');");
		
		/*if(parada.getTipo() == TipoParada.ORIGEN) {
			query.append("Origen');");

		}else {
			query.append("Destino');");
		}*/
		
				
		try {
			statement.executeUpdate(query.toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		conexion.closeConnection();		
	}

	public void apuntarHijoATrayecto(HijoVO hijo, TrayectoVO trayecto) {
		conexion.openConnection();
		statement = conexion.getSt();
		
		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO RideKid");
		query.append("(RideID, KidUsername) ");
		query.append("VALUES(");
		query.append("'" + trayecto.getIdTrayecto() + "', '" + hijo.getNombreUsuario() + "');");
				
		try {
			statement.executeUpdate(query.toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		conexion.closeConnection();
		
	}

	public void desapuntarHijoDeTrayecto(HijoVO hijo, TrayectoVO trayecto) {		
		conexion.openConnection();
		statement = conexion.getSt();
		
		StringBuilder query = new StringBuilder();
		query.append("DELETE FROM RideKid WHERE ");
		query.append("RideID='" + trayecto.getIdTrayecto() + "'AND KidUsername='" + hijo.getNombreUsuario() + "';");
		
		System.out.println(query);
				
        try {
			statement.executeUpdate(query.toString());		
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	
	public ArrayList<TrayectoVO> mostrarTrayectos(UsuarioVO usuario) {
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
			System.out.println(query.toString());
		}	
		
		try {
			System.out.println(query.toString());
			resultSet = statement.executeQuery(query.toString());
			//TODO si lo cierro peta pero entonces cuando se cierra?? 
			//conexion.closeConnection();
			return this.obtenerTrayectos(resultSet);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	
	public void rellenarTrayecto(TrayectoVO trayecto) throws SQLException{
		ResultSet resultSet = null;
		conexion.openConnection();
		statement = conexion.getSt();
		
		StringBuilder query = new StringBuilder();
		query.append("SELECT * FROM ACTIVITIES ");
		query.append("WHERE ActivityID='"+ trayecto.getIdTrayecto()+"';");
		
		resultSet = statement.executeQuery(query.toString());
		while(resultSet.next()) {
			this.setDatosTrayecto(trayecto, resultSet);
		}
		conexion.closeConnection();
	}
	
	
	private ArrayList<TrayectoVO> obtenerTrayectos(ResultSet resultSet) {
		ArrayList<TrayectoVO> trayectos = new ArrayList<TrayectoVO>();
		TrayectoVO trayecto;
		try {
			while(resultSet.next()) {
				trayecto = new TrayectoVO();
				this.setDatosTrayecto(trayecto, resultSet);
				trayectos.add(trayecto);
			}
			return trayectos;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; //TODO con esto no se liara??
	}
	
	/**
	 * Se incluyen los datos extraidos de la BBDD en el objeto contenedor trayecto
	 * @param trayecto
	 * Contendra los datos extraidos de la BBDD
	 * @param resultSet
	 * Contiene los datos extraidos de la BBDD que hay que pasar al trayecto
	 * @throws SQLException
	 * Si hay algun error al ejecutar la query
	 */
	private void setDatosTrayecto(TrayectoVO trayecto, ResultSet resultSet) throws SQLException{
		
		ActividadVO actividad = new ActividadVO();		
		int id = Integer.parseInt(resultSet.getString("ActivityID"));
		actividad.setIdActividad(id);
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
		
		
		statement.executeUpdate(query.toString());
		
		this.modificarParada(trayecto, trayecto.getOrigen());
		this.modificarParada(trayecto, trayecto.getDestino());
		
		logger.trace("Query para modificar trayecto lista para ser lanzada");	
		logger.trace("Query para modificar trayecto ejecutada con exito");
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
		conexion.openConnection();
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
		conexion.closeConnection();
	}
	
}
