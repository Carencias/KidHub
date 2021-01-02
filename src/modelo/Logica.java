package modelo;

import java.sql.SQLException;

import controlador.Controller;
import modelo.dao.ActividadDAO;
import modelo.dao.PadreDAO;
import modelo.dao.UsuarioDAO;
import modelo.vo.ActividadVO;
import modelo.vo.HijoVO;
import modelo.vo.MonitorVO;
import modelo.vo.PadreVO;
import modelo.vo.UsuarioVO;
import modelo.vo.UsuarioVO.TipoUsuario;

public class Logica {
	
	private UsuarioVO usuarioActual;
	private static Logica logica;
	private Controller controlador;
	
	private Logica() {
	}
	
	public static Logica getLogica() {
		if(logica == null) {
			logica = new Logica();
		}
		return logica;
	}
	
	public void setUsuarioActual(UsuarioVO usuario){
		this.usuarioActual = usuario;
	}
	
	public void setControlador(Controller controlador) {
		this.controlador = controlador;
	}
	
	public void registrarUsuario(UsuarioVO usuario) throws KidHubException, SQLException{
		StringBuffer error = new StringBuffer();
		if(usuario.getNombre().equals("") || usuario.getApellidos().equals("") || usuario.getContrasena().equals("") || usuario.getNombreUsuario().equals("") || usuario.getFechaNacimiento().equals("") || usuario.getDni().equals("") || usuario.getEmail().equals("")) {
			error.append("Campos sin rellenar");
		}
		//TODO mas comprobaciones?
		if(error.length() != 0) {
			throw new KidHubException(error.toString());
		}
		new UsuarioDAO().registrarUsuario(usuario);
		
		if(usuario.getTipo()==TipoUsuario.HIJO) {
			agregarHijoAPadre((HijoVO) usuario);
		}
	}
	
	public TipoUsuario loguearUsuario(UsuarioVO usuario) {
		TipoUsuario tipo = TipoUsuario.INCORRECTO;
		String contrasena = usuario.getContrasena();
		
		try {
			this.usuarioActual = new UsuarioDAO().loguearUsuario(usuario);
			if(usuarioActual.getContrasena().equals(contrasena)) {
				tipo = this.usuarioActual.getTipo();
			}else {
				tipo = TipoUsuario.INCORRECTO;
			}
		}catch(SQLException e){
			tipo = TipoUsuario.INCORRECTO;	
		}
		
		return tipo;
	}
	
	public void agregarHijoAPadre(HijoVO hijo) throws SQLException, KidHubException{
		StringBuffer error = new StringBuffer();
		if(hijo.getNombreUsuario().equals("") || hijo.getContrasena().equals("")) {
			error.append("Campos sin rellenar");
		}
		//TODO mas comprobaciones?
		if(error.length() != 0) {
			throw new KidHubException(error.toString());
		}
		new PadreDAO().agregarHijoAPadre(hijo, (PadreVO) this.usuarioActual);
	}
	
	public void crearActividad(ActividadVO actividadVO) {
				
		actividadVO.setMonitor((MonitorVO) this.usuarioActual); //TODO comprobar esto
		
		new ActividadDAO().crearActividad(actividadVO);
	}
	
	public void modificarActividad(ActividadVO actividadVO) {
		actividadVO.setMonitor((MonitorVO) this.usuarioActual); //TODO comprobar esto
		
		new ActividadDAO().modificarActividad(actividadVO);
	}
	
	public void borrarActividad(ActividadVO actividadVO) {		
		new ActividadDAO().borrarActividad(actividadVO);
	}

	public void mostrarUsuarios() {
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		usuarioDAO.mostrarUsuarios();
	}

	public boolean comprobarDatosCorrectos() {
		// TODO Auto-generated method stub
		return false;
	}
}
