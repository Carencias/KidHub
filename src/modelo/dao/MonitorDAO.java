package modelo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;

import modelo.conexion.Conexion;
import modelo.vo.ActividadVO;
import modelo.vo.Direccion;
import modelo.vo.MonitorVO;
import modelo.vo.PadreVO;

public class MonitorDAO extends UsuarioDAO{
	
	public void registrarMonitor(MonitorVO monitor) throws SQLException{
		conexion.openConnection();
		statement = conexion.getSt();
		
		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO MONITORS");
		query.append("(Username, PhoneNumber, Specialty) ");
		query.append("VALUES(");
		query.append("'" + monitor.getNombreUsuario() + "', '" + monitor.getTelefono() + "', '" + monitor.getEspecialidad() + "');");
		statement.executeUpdate(query.toString());
		conexion.closeConnection();
	}

	public ArrayList<ActividadVO> mostrarActividades(MonitorVO usuarioActual) {
		ResultSet resultSet = null;
		conexion.openConnection();
		statement = conexion.getSt();
		
		StringBuilder query = new StringBuilder();
		query.append("SELECT * FROM ACTIVITIES ");
		query.append("WHERE MonitorUsername='"+ usuarioActual.getNombreUsuario()+"';");
				
		try {
			resultSet = statement.executeQuery(query.toString());
			
			return this.obtenerActividades(resultSet);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private ArrayList<ActividadVO> obtenerActividades(ResultSet resultSet) {
		ArrayList<ActividadVO> actividades = new ArrayList<ActividadVO>();
		ActividadVO actividad;
		try {
			while(resultSet.next()) {
				actividad = new ActividadVO();
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
				actividades.add(actividad);
			}
			return actividades;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private LocalDateTime convertToDate(String string) {
		String date = string.split(" ")[0];
		String time = string.split(" ")[1];
		
		int day = Integer.parseInt(date.split("/")[0]);
		int month = Integer.parseInt(date.split("/")[1]);
		int year = Integer.parseInt(date.split("/")[2]);
		
		int hour = Integer.parseInt(time.split(":")[0]);
		int min = Integer.parseInt(time.split(":")[1]);

		
		return LocalDateTime.of(year, month, day, hour, min);

	}

}
