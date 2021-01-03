package modelo.vo;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import modelo.vo.ParadaVO.TipoParada;

public class TrayectoVO {
	
	public enum TipoTrayecto{
		IDA, VUELTA
	}
	
	private int idTrayecto;
	private int capacidad;
	private int duracion;
	private TipoTrayecto tipo;
	private ActividadVO actividad;
	private PadreVO padre;
	private ArrayList<HijoVO> hijos;
	private ParadaVO origen;
	private ParadaVO destino;
	
	public TrayectoVO(ActividadVO actividad, int capacidad, TipoTrayecto tipo, LocalDateTime fecha, Direccion direccion) {
		this.actividad = actividad;
		this.capacidad = capacidad;
		this.tipo = tipo;
		
		if(this.tipo==TipoTrayecto.IDA) {
			crearTrayectoIda(fecha, direccion);
		}else {
			crearTrayectoVuelta(fecha, direccion);
		}
		
		this.setDuracion();
	}
	
	public void crearTrayectoIda(LocalDateTime fecha, Direccion direccion) {
		this.origen = new ParadaVO(fecha, TipoParada.ORIGEN, direccion);
		this.destino = new ParadaVO(actividad.getInicio(), TipoParada.DESTINO, actividad.getDireccion());
	}
	
	public void crearTrayectoVuelta(LocalDateTime fecha, Direccion direccion) {
		this.origen = new ParadaVO(actividad.getFin(), TipoParada.ORIGEN, actividad.getDireccion());
		this.destino = new ParadaVO(fecha, TipoParada.DESTINO, direccion);
	}
	
	public TrayectoVO() {
		
	}

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
	
	public TipoTrayecto getTipo() {
		return tipo;
	}
	
	public void setTipo(TipoTrayecto tipo) {
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
	
	public ParadaVO getOrigen() {
		return this.origen;
	}
	
	public ParadaVO getDestino() {
		return this.destino;
	}
	
	public void setOrigen(ParadaVO origen) {
		this.origen = origen ;
	}
	
	public void setDestino(ParadaVO destino) {
		this.destino = destino;
	}

	public int getDuracion() {
		return this.duracion;
	}
	
	public void setDuracion() {
		this.duracion = (int) this.origen.getFecha().until(this.destino.getFecha(), ChronoUnit.MINUTES);
	}
	
	
}

