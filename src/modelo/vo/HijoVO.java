package modelo.vo;

import java.time.LocalDate;
import java.util.ArrayList;

import modelo.vo.UsuarioVO.TipoUsuario;

public class HijoVO extends UsuarioVO{
	
	private ArrayList<ActividadVO> actividades;
	private ArrayList<TrayectoVO> trayectos;
	
	public HijoVO(String nombreUsuario, String dni, String contrasena, String email, String nombre, String apellidos, String fechaNacimiento, TipoUsuario tipo) {
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
		this.setTipo(TipoUsuario.PADRE);
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
