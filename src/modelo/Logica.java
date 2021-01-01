package modelo;

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
	
	private Controller controlador;
	
	public Logica() {
	}
	
	public void setUsuarioActual(UsuarioVO usuario){
		this.usuarioActual = usuario;
	}
	
	public void setControlador(Controller controlador) {
		this.controlador = controlador;
	}
	
	public void registrarUsuario(UsuarioVO usuario) {		
		new UsuarioDAO().registrarUsuario(usuario);
		
		if(usuario.getTipo()==TipoUsuario.HIJO) {
			agregarHijoAPadre((HijoVO) usuario);
		}
	}
	
	public void agregarHijoAPadre(HijoVO hijo) {
		new PadreDAO().agregarHijoAPadre(hijo, (PadreVO) this.usuarioActual);
	}
	
	public void crearActividad(ActividadVO actividadVO) {
				
		actividadVO.setMonitor((MonitorVO) new MonitorVO("usuario6"));
		
		new ActividadDAO().crearActividad(actividadVO);
	}
	
	
	public void mostrarUsuarios() {
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		usuarioDAO.mostrarUsuarios();
	}
}
