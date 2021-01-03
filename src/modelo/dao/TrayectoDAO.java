package modelo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import modelo.conexion.Conexion;
import modelo.vo.ParadaVO;
import modelo.vo.ParadaVO.TipoParada;
import modelo.vo.TrayectoVO;

public class TrayectoDAO {

	private Conexion conexion;
	
	private Statement statement;
	
	public TrayectoDAO() {
		this.conexion = new Conexion();
	}
	
	public void crearTrayecto(TrayectoVO trayecto) throws SQLException {
		Statement statement;
		
		conexion.openConnection();
		statement = conexion.getSt();
		
		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO RIDES");
		query.append("(ActivityID, ParentUsername, Capacity, Type");
		query.append("VALUES(");
		query.append("'" + trayecto.getActividad().getIdActividad() + "', '" + trayecto.getPadre().getNombreUsuario() + "', '");
		query.append(trayecto.getCapacidad() + "', '" + trayecto.getTipo() + "');");
				
		statement.executeUpdate(query.toString());
		
		ResultSet generatedKeys = statement.getGeneratedKeys();
		
		if(generatedKeys.next()) {
			trayecto.setIdTrayecto(generatedKeys.getInt(1));
		}
	}

	public void agregarParadaATrayecto(ParadaVO parada) {
		conexion.openConnection();
		statement = conexion.getSt();
		
		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO STOPS");
		query.append("(RideID, StopDate, Duration, Address, Town, Type) ");
		query.append("VALUES(");
		query.append("'" + parada.getTrayecto().getIdTrayecto() + "', '" + parada.getTextoFecha() + "', '");
		query.append(parada.getDuracion() + "', '" + parada.getDireccion().getTextoDireccion() + "', '" + parada.getDireccion().getCiudad() + "', '");
		
		if(parada.getTipo() == TipoParada.ORIGEN) {
			query.append("Origen');");

		}else {
			query.append("Destino');");
		}
		
				
		try {
			statement.executeUpdate(query.toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		conexion.closeConnection();		
	}

}
