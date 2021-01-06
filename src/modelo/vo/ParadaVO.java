package modelo.vo;
import java.time.LocalDateTime;

public class ParadaVO {
	
	public enum TipoParada{
		ORIGEN, DESTINO
	}
	
	private LocalDateTime fecha;
	private TipoParada tipo;
	private Direccion direccion;
	
	public ParadaVO(LocalDateTime fecha, TipoParada tipo, Direccion direccion) {
		this.fecha = fecha;
		this.tipo = tipo;
		this.direccion = direccion;
	}
	
	public LocalDateTime getFecha() {
		return fecha;
	}
	
	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}
	
	public TipoParada getTipo() {
		return tipo;
	}
	
	public String getTextoTipo() {
		if(this.tipo==TipoParada.ORIGEN) {
			return "Origen";
		}else {
			return "Destino";
		}
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