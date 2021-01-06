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

public class ParadaDAO {
private Conexion conexion;
	
	private Statement statement;
	
    static Logger logger = Logger.getLogger(TrayectoDAO.class);
	
	public ParadaDAO() {
		this.conexion = new Conexion();
	}
	
	public ArrayList<ParadaVO> mostrarParadas(TrayectoVO trayecto) throws SQLException{
		ResultSet resultSet = null;
		conexion.openConnection();
		statement = conexion.getSt();
		
		StringBuilder query = new StringBuilder();
		
		query.append("SELECT * FROM STOPS WHERE RideID='" + trayecto.getIdTrayecto() + "';");
		
		resultSet = statement.executeQuery(query.toString());
		
		return this.obtenerParadas(resultSet);
	}
	
	private ArrayList<ParadaVO> obtenerParadas(ResultSet resultSet) throws SQLException{
		
		ArrayList<ParadaVO> paradas = new ArrayList<ParadaVO>();
		ParadaVO parada;
		
		while(resultSet.next()) {
			parada = new ParadaVO();
			this.setDatosParada(parada, resultSet);
			paradas.add(parada);
		}

		return paradas;
	}
	
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
	
	private Direccion convertToDireccion(String direccionCompleta, String ciudad) {
		
		String calle = direccionCompleta.split(",")[0];
		String numero = direccionCompleta.split(",")[1];
		String codigoPostal = direccionCompleta.split(",")[2];
		
		return new Direccion(calle, Integer.parseInt(numero), codigoPostal, ciudad);		
	}
	
	
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
