package modelo.vo;

import java.util.ArrayList;

import org.apache.log4j.Logger;

/**
 * Clase Value Object que sirve de almacen para la informacion de un hijo
 * @version 1.0
 * @author Diego Simon Gonzalez, Pablo Bayon Gutierrez y Santiago Valbuena Rubio
 */
public class HijoVO extends UsuarioVO{
	
	private ArrayList<ActividadVO> actividades;
	private ArrayList<TrayectoVO> trayectos;
	static Logger logger = Logger.getLogger(Direccion.class);
	
	public HijoVO(String nombreUsuario, String dni, String contrasena, String email, String nombre, String apellidos, String fechaNacimiento, TipoUsuario tipo) {
		logger.trace("Creando HijoVO");
		this.setNombreUsuario(nombreUsuario);
		this.setDni(dni);
		this.setContrasena(contrasena);
		this.setEmail(email);
		this.setNombre(nombre);
		this.setApellidos(apellidos);
		this.setFechaNacimiento(fechaNacimiento);
		this.setTipo(tipo);
	}
	
	public HijoVO() {
		logger.trace("Creando HijoVO");
		this.setTipo(TipoUsuario.HIJO);
	}

	public ArrayList<ActividadVO> getActividades() {
		return actividades;
	}
	
	public void setActividades(ArrayList<ActividadVO> actividades) {
		this.actividades = actividades;
	}
	
	public ArrayList<TrayectoVO> getTrayectos() {
		return trayectos;
	}
	
	public void setTrayectos(ArrayList<TrayectoVO> trayectos) {
		this.trayectos = trayectos;
	}
}
