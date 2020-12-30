package modelo.vo;
import java.time.LocalDate;

public class ParadaVO {
	
	public enum tipoParada{
		INICIO, FIN, INTERMEDIA
	}
	
	private TrayectoVO trayecto;
	private int numero;
	private LocalDate fecha;
	private int duracion;
	private tipoParada tipo;
	private Direccion direccion;
	
	public TrayectoVO getTrayecto() {
		return trayecto;
	}
	
	public void setTrayecto(TrayectoVO trayecto) {
		this.trayecto = trayecto;
	}
	
	public int getNumero() {
		return numero;
	}
	
	public void setNumero(int numero) {
		this.numero = numero;
	}
	
	public LocalDate getFecha() {
		return fecha;
	}
	
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
	
	public int getDuracion() {
		return duracion;
	}
	
	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}
	
	public tipoParada getTipo() {
		return tipo;
	}
	
	public void setTipo(tipoParada tipo) {
		this.tipo = tipo;
	}
	
	public Direccion getDireccion() {
		return direccion;
	}
	
	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}
}