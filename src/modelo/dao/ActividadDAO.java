package modelo.dao;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import modelo.conexion.Conexion;
import modelo.vo.ActividadVO;
import modelo.vo.MonitorVO;

public class ActividadDAO {
		
	private Conexion conexion;
	
	private Statement statement;
	
	public ActividadDAO() {
		this.conexion = new Conexion();
	}
	
	public void crearActividad(ActividadVO actividad) {
		Statement statement;
		
		conexion.openConnection();
		statement = conexion.getSt();
		
		StringBuilder insertQuery = new StringBuilder();
		insertQuery.append("INSERT INTO ACTIVITIES");
		insertQuery.append("(ActivityID, MonitorUsername, Name, StartDate, Duration, EndDate, Capacity, Address, Town, Type) ");
		insertQuery.append("VALUES(");
		insertQuery.append("'" + actividad.getIdActividad() + "', '" + actividad.getMonitor().getNombreUsuario() + "', '" + actividad.getNombre() + "', '" + actividad.getTextoInicio() + "', '" + actividad.getDuracion() + "', '" );
		insertQuery.append(actividad.getTextoFin() + "', '" + actividad.getCapacidad() + "', '" + actividad.getDireccion().getTextoDireccion() + "', '" + actividad.getDireccion().getCiudad() + "', '" + actividad.getTipo() + "');");
				
        try {
			statement.executeUpdate(insertQuery.toString());
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	
	
	
	
	
	
	
}
