package modelo.vo;

import java.util.ArrayList;

import org.apache.log4j.Logger;

/**
 * Clase Value Object que sirve de almacen para la informacion de un monitor
 * @version 1.0
 * @author Diego Simon Gonzalez, Pablo Bayon Gutierrez y Santiago Valbuena Rubio
 */
public class MonitorVO extends UsuarioVO{
	
	private String telefono;
	private String especialidad;
	private ArrayList<ActividadVO> actividades;
	static Logger logger = Logger.getLogger(MonitorVO.class);
	
	public MonitorVO(String nombreUsuario, String dni, String contrasena, String email, String nombre, String apellidos, String fechaNacimiento, TipoUsuario tipo, String telefono, String especialidad) {
		logger.trace("Creando MonitorVO");
		this.setNombreUsuario(nombreUsuario);
		this.setDni(dni);
		this.setContrasena(contrasena);
		this.setEmail(email);
		this.setNombre(nombre);
		this.setApellidos(apellidos);
		this.setFechaNacimiento(fechaNacimiento);
		this.setTipo(tipo);
		this.setTelefono(telefono);
		this.setEspecialidad(especialidad);
	}
	
	public MonitorVO(String nombreUsuario) {
		this.setNombreUsuario(nombreUsuario);
	}

	public MonitorVO() {
		this.setTipo(TipoUsuario.MONITOR);
	}

	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getEspecialidad() {
		return especialidad;
	}
	public void setEspecialidad(String especialidad) {
		this.especialidad = especialidad;
	}
	public ArrayList<ActividadVO> getActividades() {
		return actividades;
	}
	public void setActividades(ArrayList<ActividadVO> actividades) {
		this.actividades = actividades;
	}
}
