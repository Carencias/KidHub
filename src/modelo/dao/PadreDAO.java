package modelo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import modelo.conexion.Conexion;
import modelo.vo.ActividadVO;
import modelo.vo.HijoVO;
import modelo.vo.PadreVO;
import modelo.vo.UsuarioVO;

public class PadreDAO extends UsuarioDAO{
	
	public void registrarPadre(PadreVO padre) throws SQLException{
		conexion.openConnection();
		statement = conexion.getSt();
		
		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO PARENTS");
		query.append("(Username, PhoneNumber) ");
		query.append("VALUES(");
		query.append("'" + padre.getNombreUsuario() + "', '" + padre.getTelefono() + "');");
				
		statement.executeUpdate(query.toString());
		conexion.closeConnection();
	}


	public void agregarHijoAPadre(HijoVO hijo, PadreVO padre) throws SQLException{
		conexion.openConnection();
		statement = conexion.getSt();
		
		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO ParentKid");
		query.append("(ParentUsername, KidUsername) ");
		query.append("VALUES(");
		query.append("'" + padre.getNombreUsuario() + "', '" + hijo.getNombreUsuario() + "');");
		System.out.println(query.toString());
				
		statement.executeUpdate(query.toString());
		conexion.closeConnection();
	}
	
	
	public ArrayList<HijoVO> mostrarHijos(UsuarioVO padre){
		conexion.openConnection();
		statement = conexion.getSt();
		ResultSet resultSet = null;
		
		StringBuilder query = new StringBuilder();
		query.append("SELECT KidUsername ");
		query.append("FROM ParentKid ");
		query.append("WHERE ParentUsername='" + padre.getNombreUsuario() + "';");
		
		try {
			resultSet = statement.executeQuery(query.toString());
			return this.obtenerHijos(resultSet);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		conexion.closeConnection();
		
		return null;
		
	}
	
	private ArrayList<HijoVO> obtenerHijos(ResultSet resultSet){
		ArrayList<HijoVO> hijos = new ArrayList<HijoVO>();
		HijoVO hijo;
		try {
			while(resultSet.next()) {
				hijo = new HijoVO();
				this.setDatosHijo(hijo, resultSet);
				hijos.add(hijo);
			}
			return hijos;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; //TODO con esto no se liara??
	}
	
	private void setDatosHijo(HijoVO hijo, ResultSet resultSet) throws SQLException{
		//Creo que solo necesito el nombre de usuario
		hijo.setNombreUsuario(resultSet.getString("KidUsername"));
	}
	
	
}
