package modelo.vo;

import java.util.ArrayList;

public class MonitorVO extends UsuarioVO{
	
	private String telefono;
	private ArrayList<String> especialidades;
	private ArrayList<ActividadVO> actividades;
	
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public ArrayList<String> getEspecialidades() {
		return especialidades;
	}
	public void setEspecialidades(ArrayList<String> especialidades) {
		this.especialidades = especialidades;
	}
	public ArrayList<ActividadVO> getActividades() {
		return actividades;
	}
	public void setActividades(ArrayList<ActividadVO> actividades) {
		this.actividades = actividades;
	}
}
