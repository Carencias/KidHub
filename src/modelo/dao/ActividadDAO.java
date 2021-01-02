package modelo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;

import modelo.conexion.Conexion;
import modelo.vo.ActividadVO;
import modelo.vo.Direccion;
import modelo.vo.HijoVO;
import modelo.vo.MonitorVO;
import modelo.vo.UsuarioVO;

public class ActividadDAO {
		
	private Conexion conexion;
	
	private Statement statement;
	
	public ActividadDAO() {
		this.conexion = new Conexion();
	}
	
	public void crearActividad(ActividadVO actividad) throws SQLException{
		Statement statement;
		
		conexion.openConnection();
		statement = conexion.getSt();
		
		StringBuilder insertQuery = new StringBuilder();
		insertQuery.append("INSERT INTO ACTIVITIES");
		insertQuery.append("(ActivityID, MonitorUsername, Name, StartDate, Duration, EndDate, Capacity, Address, Town, Type) ");
		insertQuery.append("VALUES(");
		insertQuery.append("'" + actividad.getIdActividad() + "', '" + actividad.getMonitor().getNombreUsuario() + "', '" + actividad.getNombre() + "', '" + actividad.getTextoInicio() + "', '" + actividad.getDuracion() + "', '" );
		insertQuery.append(actividad.getTextoFin() + "', '" + actividad.getCapacidad() + "', '" + actividad.getDireccion().getTextoDireccion() + "', '" + actividad.getDireccion().getCiudad() + "', '" + actividad.getTipo() + "');");
				
		statement.executeUpdate(insertQuery.toString());
	}
	
	public void modificarActividad(ActividadVO actividad) {
		Statement statement;
		
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
				
        try {
			statement.executeUpdate(query.toString());
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void borrarActividad(ActividadVO actividad) {
		Statement statement;
		
		conexion.openConnection();
		statement = conexion.getSt();
		
		StringBuilder query = new StringBuilder();
		query.append("DELETE FROM ACTIVITIES WHERE ");
		query.append("ActivityID='" + actividad.getIdActividad() + "'; ");
				
        try {
			statement.executeUpdate(query.toString());
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void rellenarActividad(ActividadVO actividad) {
		ResultSet resultSet = null;
		conexion.openConnection();
		statement = conexion.getSt();
		
		StringBuilder query = new StringBuilder();
		query.append("SELECT * FROM ACTIVITIES ");
		query.append("WHERE ActivityID='"+ actividad.getIdActividad()+"';");
				
		try {
			resultSet = statement.executeQuery(query.toString());
			
			this.rellenarActividad(resultSet, actividad);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void rellenarActividad(ResultSet resultSet, ActividadVO actividad) {
		try {
			if(resultSet.next()) {
				actividad.setNombre(resultSet.getString("Name"));
				actividad.setInicio(convertToDate(resultSet.getString("StartDate")));
				actividad.setFin(convertToDate(resultSet.getString("EndDate")));
				actividad.setDuracion();
				actividad.setCapacidad(resultSet.getInt("Capacity"));
				
				String direccionCompleta = resultSet.getString("Address");
				String calle = direccionCompleta.split(",")[0];
				String numero = direccionCompleta.split(",")[1];
				String codigoPostal = direccionCompleta.split(",")[2];
				String ciudad = resultSet.getString("Town");
				
				actividad.setDireccion(new Direccion(calle, Integer.parseInt(numero), codigoPostal, ciudad));
				actividad.setTipo(resultSet.getString("Type"));

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private LocalDateTime convertToDate(String stringDateTime) {
		String date = stringDateTime.split(" ")[0];
		String time = stringDateTime.split(" ")[1];
		
		int day = Integer.parseInt(date.split("/")[0]);
		int month = Integer.parseInt(date.split("/")[1]);
		int year = Integer.parseInt(date.split("/")[2]);
		
		int hour = Integer.parseInt(time.split(":")[0]);
		int min = Integer.parseInt(time.split(":")[1]);

		
		return LocalDateTime.of(year, month, day, hour, min);

	}

	public void apuntarHijoAActividad(HijoVO hijo, ActividadVO actividad) {
		conexion.openConnection();
		statement = conexion.getSt();
		
		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO ActivityKid");
		query.append("(KidUsername, ActivityID) ");
		query.append("VALUES(");
		query.append("'" + hijo.getNombreUsuario() + "', '" + actividad.getIdActividad() + "');");
				
		try {
			statement.executeUpdate(query.toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		conexion.closeConnection();
	}

	public void desapuntarHijoDeActividad(HijoVO hijo, ActividadVO actividad) {
		Statement statement;
		
		conexion.openConnection();
		statement = conexion.getSt();
		
		StringBuilder query = new StringBuilder();
		query.append("DELETE FROM ActivityKid WHERE ");
		query.append("ActivityID='" + actividad.getIdActividad() + "'AND KidUsername='" + hijo.getNombreUsuario() + "';");
				
        try {
			statement.executeUpdate(query.toString());
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
}
