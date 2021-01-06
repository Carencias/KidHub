package modelo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import modelo.conexion.Conexion;
import modelo.vo.Direccion;
import modelo.vo.ParadaVO;
import modelo.vo.TrayectoVO;
import modelo.vo.ParadaVO.TipoParada;

/**
 * Clase encargada de registrar paradas y obtener paradas en la base de datos
 * @version 1.0
 */
public class ParadaDAO {
private Conexion conexion;
	
	private Statement statement;
	
    static Logger logger = Logger.getLogger(ParadaDAO.class);
	
	public ParadaDAO() {
		logger.trace("Objeto ParadaDAO creado");
		this.conexion = new Conexion();
	}
	
	/**
	 * Metodo que lanza la query para obtener las paradas de un trayecto a la base de datos
	 * @param actividad
	 *  ActividadVO con los datos de la actividad
	 * @throws SQLException
	 */
	public ArrayList<ParadaVO> mostrarParadas(TrayectoVO trayecto) throws SQLException{
		ResultSet resultSet = null;
		conexion.openConnection();
		statement = conexion.getSt();
		
		StringBuilder query = new StringBuilder();
		
		query.append("SELECT * FROM STOPS WHERE RideID='" + trayecto.getIdTrayecto() + "';");
		
		logger.trace("Query lista para ser lanzada");
		resultSet = statement.executeQuery(query.toString());
		logger.trace("Query ejecutada con exito");
		
		return this.obtenerParadas(resultSet);
	}
	
	/**
	 * Metodo privado que guarda los datos obtenidos en la ejecucion de la query en una lista
	 * @param resultSet
	 *  Resultado de la ejecucion de la query
	 * @return
	 *  Lista con las paradas
	 * @throws SQLException
	 */
	private ArrayList<ParadaVO> obtenerParadas(ResultSet resultSet) throws SQLException{
		ArrayList<ParadaVO> paradas = new ArrayList<ParadaVO>();
		ParadaVO parada;
		logger.trace("Obteniendo paradas de la ejecucion de la query");
		while(resultSet.next()) {
			parada = new ParadaVO();
			this.setDatosParada(parada, resultSet);
			paradas.add(parada);
		}
		conexion.closeConnection();
		return paradas;
	}
	
	/**
	 * Metodo privado que guarda los datos obtenidos en la ejecucion de la query en un objetoVO paradaVO
	 * @param parada
	 *  Objeto paradaVO en el que guardar los datos
	 * @param resultSet
	 *  Resultado de ejecucion de la query
	 * @throws SQLException
	 */
	private void setDatosParada(ParadaVO parada, ResultSet resultSet) throws SQLException{
		LocalDateTime fecha = this.convertToDate(resultSet.getString("StopDate"));
		parada.setFecha(fecha);
		
		Direccion direccion = this.convertToDireccion(resultSet.getString("Address"), resultSet.getString("Town"));
		parada.setDireccion(direccion);
		
		if(resultSet.getString("Type").equals("Destino")) {
			parada.setTipo(TipoParada.DESTINO);
		}else {
			parada.setTipo(TipoParada.ORIGEN);
		}		
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
}
