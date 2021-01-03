package modelo.vo;
import java.time.LocalDateTime;

public class ParadaVO {
	
	public enum TipoParada{
		ORIGEN, DESTINO
	}
	
	private TrayectoVO trayecto;
	private int numero;
	private LocalDateTime fecha;
	private int duracion;
	private TipoParada tipo;
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
	
	public LocalDateTime getFecha() {
		return fecha;
	}
	
	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}
	
	public int getDuracion() {
		return duracion;
	}
	
	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}
	
	public TipoParada getTipo() {
		return tipo;
	}
	
	public void setTipo(TipoParada tipo) {
		this.tipo = tipo;
	}
	
	public Direccion getDireccion() {
		return direccion;
	}
	
	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}
	
	public String getTextoFecha() {
		StringBuilder texto = new StringBuilder();
		
		texto.append(this.fecha.getDayOfMonth() + "/");
		texto.append(this.fecha.getMonthValue() + "/");
		texto.append(this.fecha.getYear() + " ");
		texto.append(this.fecha.getHour() + ":");
		texto.append(this.fecha.getMinute());
		
		return texto.toString();
	}
}