package modelo;

import java.sql.SQLException;
import java.util.ArrayList;

import controlador.Controller;
import modelo.vo.*;
import modelo.dao.*;
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
	
	public void crearActividad(ActividadVO actividadVO) throws SQLException, KidHubException{
		StringBuffer error = new StringBuffer();
		if(actividadVO.getNombre().equals("") || actividadVO.getTipo().equals("") || actividadVO.getCapacidad() < 0) {
			error.append("Campos sin rellenar");
		}
		//TODO mas comprobaciones?
		if(error.length() != 0) {
			throw new KidHubException(error.toString());
		}
		actividadVO.setMonitor((MonitorVO) this.usuarioActual);
		
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
	
	public void rellenarActividad(ActividadVO actividad) {
		new ActividadDAO().rellenarActividad(actividad);
	}
	
	public UsuarioVO getUsuarioActual() {
		return this.usuarioActual;
	}

	public ArrayList<ActividadVO> getActividades() {
		return new ActividadDAO().mostrarActividades((MonitorVO)this.usuarioActual);
	}
	
	public void apuntarHijoAActividad(HijoVO hijo, ActividadVO actividad) {
		new ActividadDAO().apuntarHijoAActividad(hijo, actividad);
		//TODO tiene que recibir el nombreUsuario del hijo y la ID de la actividad
	}
	
	public void desapuntarHijoDeActividad(HijoVO hijo, ActividadVO actividad) {
		new ActividadDAO().desapuntarHijoDeActividad(hijo, actividad);
		//TODO tiene que recibir el nombreUsuario del hijo y la ID de la actividad
	}
	
	public void crearTrayecto(TrayectoVO trayecto, ParadaVO origen, ParadaVO destino) {
		trayecto.setPadre((PadreVO) this.usuarioActual);
		//TODO tiene que tener seteada la actividad a la que se va a agregar
		try {
			new TrayectoDAO().crearTrayecto(trayecto);
		} catch (SQLException e) {
			// TODO Manejar errores
			e.printStackTrace();
		}
		//TODO Aqui deberia estar seteado el ID del trayecto
		new TrayectoDAO().agregarParadaATrayecto(origen); //TODO tiene que estar seteado el trayecto
		new TrayectoDAO().agregarParadaATrayecto(destino);

	}
}










