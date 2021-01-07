package modelo.vo;

import java.util.ArrayList;

import org.apache.log4j.Logger;

/**
 * Clase Value Object que sirve de almacen para la informacion de un padre
 * @version 1.0
 * @author Diego Simon Gonzalez, Pablo Bayon Gutierrez y Santiago Valbuena Rubio
 */
public class PadreVO extends UsuarioVO{
	
	private String telefono;
	private Direccion direccion;
	private ArrayList<HijoVO> hijos;
	private ArrayList<TrayectoVO> trayectos;
	static Logger logger = Logger.getLogger(PadreVO.class);
	
	public PadreVO() {
		this.setTipo(TipoUsuario.PADRE);
	}
	
	public PadreVO(String nombreUsuario, String dni, String contrasena, String email, String nombre, String apellidos, String fechaNacimiento, TipoUsuario tipo, String telefono) {
		logger.trace("Creando PadreVO");
		this.setNombreUsuario(nombreUsuario);
		this.setDni(dni);
		this.setContrasena(contrasena);
		this.setEmail(email);
		this.setNombre(nombre);
		this.setApellidos(apellidos);
		this.setFechaNacimiento(fechaNacimiento);
		this.setTipo(tipo);
		this.setTelefono(telefono);
		
	}
	
	public String getTelefono() {
		return telefono;
	}
	
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	public Direccion getDireccion() {
		return direccion;
	}
	
	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}
	
	public ArrayList<HijoVO> getHijos() {
		return hijos;
	}
	
	public void setHijo(ArrayList<HijoVO> hijos) {
		this.hijos = hijos;
	}
	
	public ArrayList<TrayectoVO> getTrayectos() {
		return trayectos;
	}
	
	public void setTrayectos(ArrayList<TrayectoVO> trayectos) {
		this.trayectos = trayectos;
	}
}
