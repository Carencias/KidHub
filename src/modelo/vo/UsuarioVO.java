package modelo.vo;

import org.apache.log4j.Logger;

/**
 * Clase Value Object que sirve de almacen para la informacion de un usuario
 * @version 1.0
 * @author Diego Simon Gonzalez, Pablo Bayon Gutierrez y Santiago Valbuena Rubio
 */
public class UsuarioVO {
	
	public enum TipoUsuario{
		PADRE,HIJO,MONITOR
	}
	
	private String nombreUsuario;
	private String dni;
	private String contrasena;
	private String email;
	private String nombre;
	private String apellidos;
	private String fechaNacimiento;
	private int edad;
	private TipoUsuario tipo;
	static Logger logger = Logger.getLogger(UsuarioVO.class);
	
	public UsuarioVO() {
		logger.trace("Creando UsuarioVO");
	}
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	
	public String getDni() {
		return dni;
	}
	
	public void setDni(String dni) {
		this.dni = dni;
	}
	
	public String getContrasena() {
		return contrasena;
	}
	
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getApellidos() {
		return apellidos;
	}
	
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	
	public String getFechaNacimiento() {
		return fechaNacimiento;
	}
	
	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	
	public int getEdad() {
		return edad;
	}
	
	public void setEdad(int edad) {
		this.edad = edad;
	}
	
	public TipoUsuario getTipo() {
		return tipo;
	}
	
	public String getTextoTipo() {
		String textoTipo = "";
		
		switch(this.tipo) {
			case PADRE:
				textoTipo = "Padre";
				break;
			case MONITOR:
				textoTipo = "Monitor";
				break;
			case HIJO:
				textoTipo = "Hijo";
				break;
		}
		
		return textoTipo;
	}
	
	public void setTipo(TipoUsuario tipo) {
		this.tipo = tipo;
	}
}
