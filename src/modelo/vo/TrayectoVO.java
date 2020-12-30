package modelo.vo;

import java.util.ArrayList;

public class TrayectoVO {
	
	public enum tipoTrayecto{
		IDA, VUELTA
	}
	
	private int idTrayecto;
	private int capacidad;
	private tipoTrayecto tipo;
	private ActividadVO actividad;
	private PadreVO padre;
	private ArrayList<HijoVO> hijos;
	private ArrayList<ParadaVO> paradas;
	
	public int getIdTrayecto() {
		return idTrayecto;
	}
	
	public void setIdTrayecto(int idTrayecto) {
		this.idTrayecto = idTrayecto;
	}
	
	public int getCapacidad() {
		return capacidad;
	}
	
	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}
	
	public tipoTrayecto getTipo() {
		return tipo;
	}
	
	public void setTipo(tipoTrayecto tipo) {
		this.tipo = tipo;
	}
	
	public ActividadVO getActividad() {
		return actividad;
	}
	
	public void setActividad(ActividadVO actividad) {
		this.actividad = actividad;
	}
	
	public PadreVO getPadre() {
		return padre;
	}
	
	public void setPadre(PadreVO padre) {
		this.padre = padre;
	}
	
	public ArrayList<HijoVO> getHijos() {
		return hijos;
	}
	
	public void setHijos(ArrayList<HijoVO> hijos) {
		this.hijos = hijos;
	}
	
	public ArrayList<ParadaVO> getParadas() {
		return paradas;
	}
	
	public void setParadas(ArrayList<ParadaVO> paradas) {
		this.paradas = paradas;
	}
}

