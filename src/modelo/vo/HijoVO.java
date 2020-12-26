package modelo.vo;

import java.util.ArrayList;

public class HijoVO extends UsuarioVO{
	
	private ArrayList<ActividadVO> actividades;
	private ArrayList<TrayectoVO> trayectos;
	
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
