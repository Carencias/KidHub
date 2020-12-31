package modelo.dao;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import modelo.conexion.Conexion;
import modelo.vo.ActividadVO;
import modelo.vo.MonitorVO;

public class ActividadDAO {
	
	private static ActividadDAO actividadDAO = new ActividadDAO();
	
	private Conexion conexion;
	
	private ActividadDAO() {
		this.conexion  = new Conexion();
	}
	
	public ActividadDAO getActividadDAO() {
		return actividadDAO;
	}
	
	public void crearActividad(MonitorVO monitor, ActividadVO actividad) {
		Statement statement;
		
		conexion.openConnection();
		statement = conexion.getSt();
		
		StringBuilder insertQuery = new StringBuilder();
		insertQuery.append("INSERT INTO ACTIVITIES");
		insertQuery.append("(ActivityID, MonitorUsername, Name, StartDate, EndDate, Capacity, Address, Town, Type) ");
		insertQuery.append("VALUES(");
		insertQuery.append("'" + actividad.getIdActividad() + "', '" + monitor.getNombreUsuario() + "', '" + actividad.getNombre() + "', '" + actividad.getFechaInicio() + "', '");
		insertQuery.append(actividad.getFechaFin() + "', '" + actividad.getCapacidad() + "', '" + actividad.getDireccion().getTextoDireccion() + "', '" + actividad.getTipo() + "'");
		
		//TODO CAMPOS PARA LA DIRECCION?? @DIEGO CREE QUE SOBRAN
		//TODO OJO CON EL getTipo en el string
		//NO INSERTO DURATION PORQUE SE CALCULA EN UN TRIGGER SUPUESTAMENTE
		//DIRECCION???

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
		query.append("UPDATE ACTIVITIES SET");
		query.append(this.getSet(actividad));
		query.append(" WHERE ActivityID=" + actividad.getIdActividad());
		
        try {
			statement.executeUpdate(query.toString());
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public String getSet(ActividadVO actividad) {
		
		StringBuilder set = new StringBuilder();
		
		if(!actividad.getNombre().equals("")) {
			set.append("Name=" + actividad.getNombre() + ", ");
		}
		if(actividad.getFechaInicio()!=null) {
			set.append("StartDate=" + actividad.getFechaInicio() + ", ");
		}
		if(actividad.getFechaFin()!=null) {
			set.append("EndDate=" + actividad.getFechaFin() + ", ");
		}
		if(actividad.getCapacidad()!=-1) { //TODO SI NO SE CAMBIA LA ACTIVIDAD PONER UN -1
			set.append("Capacity=" + actividad.getCapacidad() + ", ");
		}
		if(!actividad.getDireccion().getTextoDireccion().equals("")) { //TODO SI NO SE CAMBIA LA ACTIVIDAD PONER UN -1
			set.append("Address=" + actividad.getDireccion().getTextoDireccion() + ", ");
		}
		if(!actividad.getDireccion().getCiudad().equals("")) {
			set.append("Town=" + actividad.getNombre() + ", ");
		}
		if(!actividad.getTipo().equals("")) {
			set.append("Type=" + actividad.getNombre() + ", ");
		}
		
		return set.substring(0, set.length()-2);
	}
	
	
	
	
	
	
	
}
