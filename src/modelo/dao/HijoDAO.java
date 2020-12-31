package modelo.dao;

public class HijoDAO extends UsuarioDAO{
	private static HijoDAO hijoDAO = new HijoDAO();
	
	/*private Conexion conexion;
	
	private PadreDAO() {
		this.conexion = new Conexion();
	}*/
	
	public HijoDAO getHijoDAO() {
		return hijoDAO;
	}
}
