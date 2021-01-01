package modelo.vo;

import java.time.LocalDate;
import java.util.ArrayList;

import modelo.vo.UsuarioVO.TipoUsuario;

public class MonitorVO extends UsuarioVO{
	
	private String telefono;
	private String especialidad;
	private ArrayList<ActividadVO> actividades;
	
	public MonitorVO(String nombreUsuario, String dni, String contrasena, String email, String nombre, String apellidos, LocalDate fechaNacimiento, TipoUsuario tipo, String telefono, String especialidad) {
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
		// TODO Auto-generated constructor stub
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
