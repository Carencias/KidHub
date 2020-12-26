package modelo.vo;

import java.util.ArrayList;

public class TrayectoVO {
	
	public enum tipoTrayecto{
		IDA, VUELTA
	}
	
	private int idTrayecto;
	private int capacidad;
	private tipoTrayecto tipo;
	private int actividadID;
	private String padre;
	private String hijo;
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
	public int getActividadID() {
		return actividadID;
	}
	public void setActividadID(int actividadID) {
		this.actividadID = actividadID;
	}
	public String getPadre() {
		return padre;
	}
	public void setPadre(String padre) {
		this.padre = padre;
	}
	public String getHijo() {
		return hijo;
	}
	public void setHijo(String hijo) {
		this.hijo = hijo;
	}
	public ArrayList<ParadaVO> getParadas() {
		return paradas;
	}
	public void setParadas(ArrayList<ParadaVO> paradas) {
		this.paradas = paradas;
	}
	
}

