package modelo.vo;

import java.util.ArrayList;

public class PadreVO extends UsuarioVO{
	
	private String telefono;
	private Direccion direccion;
	private ArrayList<HijoVO> hijos;
	private ArrayList<TrayectoVO> trayectos;
	
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
