package modelo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import modelo.vo.HijoVO;
import modelo.vo.PadreVO;
import modelo.vo.UsuarioVO;

/**
 * Clase encargada de lanzar las querys de registrar, agregarHijos, mostrarHijos, obtenerHijos de la base de datos
 * @version 1.0
 * @author Diego Simon Gonzalez, Pablo Bayon Gutierrez, Santiago Valbuena Rubio
 */
public class PadreDAO extends UsuarioDAO{
	
	static Logger logger = Logger.getLogger(PadreDAO.class);
	
	/**
	 * Metodo que lanza la query para registrar un padre en la base de datos
	 * @param padre
	 *  PadreVO con la informacion del padre a registrar
	 * @throws SQLException
	 */
	public void registrarPadre(PadreVO padre) throws SQLException{
		conexion.openConnection();
		statement = conexion.getSt();
		
		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO PARENTS");
		query.append("(Username, PhoneNumber) ");
		query.append("VALUES(");
		query.append("'" + padre.getNombreUsuario() + "', '" + padre.getTelefono() + "');");
		
		logger.trace("Query lista para ser lanzada");
		statement.executeUpdate(query.toString());
		logger.trace("Query ejecutada con exito");
		conexion.closeConnection();
	}

	/**
	 * Metodo que agrega un hijo a un padre en la base de datos
	 * @param hijo
	 *  Hijo a agreagar a la base de datos
	 * @param padre
	 *  Padre al que se le agregara el hijo
	 * @throws SQLException
	 */
	public void agregarHijoAPadre(HijoVO hijo, PadreVO padre) throws SQLException{
		conexion.openConnection();
		statement = conexion.getSt();
		
		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO ParentKid");
		query.append("(ParentUsername, KidUsername) ");
		query.append("VALUES(");
		query.append("'" + padre.getNombreUsuario() + "', '" + hijo.getNombreUsuario() + "');");
			
		logger.trace("Query lista para ser lanzada");
		statement.executeUpdate(query.toString());
		logger.trace("Query ejecutada con exito");
		
		conexion.closeConnection();
	}
	
	/**
	 * Metodo que devuelve una lista con los hijos del padre
	 * @param padre
	 *  PadreVO con los datos del padre
	 * @return
	 *  Devuelve una lista con los Hijos del padre
	 */
	public ArrayList<HijoVO> mostrarHijos(UsuarioVO padre) throws SQLException{
		conexion.openConnection();
		statement = conexion.getSt();
		ResultSet resultSet = null;
		
		StringBuilder query = new StringBuilder();
		query.append("SELECT KidUsername ");
		query.append("FROM ParentKid ");
		query.append("WHERE ParentUsername='" + padre.getNombreUsuario() + "';");
		
		logger.trace("Query lista para ser lanzada");
		resultSet = statement.executeQuery(query.toString());
		logger.trace("Query ejecutada con exito");
		
		return this.obtenerHijos(resultSet);
	}
	
	/**
	 * Metodo privado que rellena con la informacion obtenida en la query los hijos
	 * @param resultSet
	 *  Resultado de la query
	 * @return
	 *  Devuelve una lista con los hijos del padre
	 */
	private ArrayList<HijoVO> obtenerHijos(ResultSet resultSet) throws SQLException{
		ArrayList<HijoVO> hijos = new ArrayList<HijoVO>();
		HijoVO hijo;
		while(resultSet.next()) {
			hijo = new HijoVO();
			hijo.setNombreUsuario(resultSet.getString("KidUsername"));
			hijos.add(hijo);
		}
		conexion.closeConnection();
		return hijos;
	}
}
