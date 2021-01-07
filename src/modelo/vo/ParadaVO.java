package modelo.vo;
import java.time.LocalDateTime;

import org.apache.log4j.Logger;

/**
 * Clase Value Object que sirve de almacen para la informacion de una parada
 * @version 1.0
 * @author Diego Simon Gonzalez, Pablo Bayon Gutierrez y Santiago Valbuena Rubio
 */
public class ParadaVO {
	
	public enum TipoParada{
		ORIGEN, DESTINO
	}
	
	private LocalDateTime fecha;
	private TipoParada tipo;
	private Direccion direccion;
	static Logger logger = Logger.getLogger(ParadaVO.class);
	
	public ParadaVO(LocalDateTime fecha, TipoParada tipo, Direccion direccion) {
		logger.trace("Creando ParadaVO");
		this.fecha = fecha;
		this.tipo = tipo;
		this.direccion = direccion;
	}
	
	public ParadaVO() {}
	
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