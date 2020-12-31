package modelo.vo;

import java.time.LocalDate;
import java.util.ArrayList;

public class PadreVO extends UsuarioVO{
	
	private String telefono;
	private Direccion direccion;
	private ArrayList<HijoVO> hijos;
	private ArrayList<TrayectoVO> trayectos;
	
	
	public PadreVO() {
		
	}
	
	public PadreVO(String nombreUsuario, String dni, String email, String nombre, String apellidos, LocalDate fechaNacimiento, TipoUsuario tipo, String telefono) {
		this.setNombreUsuario(nombreUsuario);
		this.setDni(dni);
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
