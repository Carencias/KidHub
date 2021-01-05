package modelo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
		
		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO STOPS");
		query.append("(RideID, StopDate, Duration, Address, Town, Type) ");
		query.append("VALUES(");
		query.append("'" + trayecto.getIdTrayecto() + "', '" + parada.getTextoFecha() + "', '");
		query.append(trayecto.getDuracion() + "', '" + parada.getDireccion().getTextoDireccion() + "', '" + parada.getDireccion().getCiudad() + "', '");
		
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
	
	public ArrayList<TrayectoVO> mostrarTrayectos(UsuarioVO usuarioActual) {
		ResultSet resultSet = null;
		conexion.openConnection();
		statement = conexion.getSt();
		
		StringBuilder query = new StringBuilder();
		
		if(usuarioActual.getNombreUsuario().equals("Todos")) {
			query.append("SELECT * FROM RIDES ");
		}else if(usuarioActual.getTipo() == TipoUsuario.PADRE) {
			query.append("SELECT * FROM RIDES ");
			query.append("WHERE ParentUsername='"+ usuarioActual.getNombreUsuario()+"';");
		}else if(usuarioActual.getTipo() == TipoUsuario.HIJO) {
			
			query.append("SELECT * FROM RIDES ");
			
			if(!usuarioActual.getNombreUsuario().equals("Todos")) {
				query.append("INNER JOIN RideKid ON RideKid.RideID = RIDES.RideID ");
				query.append("WHERE RideKid.KidUsername='"+ usuarioActual.getNombreUsuario()+"';");
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
}
