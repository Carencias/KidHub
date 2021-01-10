package modelo.vo;

import org.apache.log4j.Logger;

/**
 * Clase Value Object que sirve de almacen para la informacion de una direccion
 * @version 1.0
 * @author Diego Simon Gonzalez, Pablo Bayon Gutierrez y Santiago Valbuena Rubio
 * @author Diego Simon Gonzalez, Pablo Bayon Gutierrez, Santiago Valbuena Rubio
 */
public class Direccion {

	private String calle;
	
	private int numero;
	
	private String codigoPostal;
	
	private String ciudad;
	
	static Logger logger = Logger.getLogger(Direccion.class);
	
	public Direccion(String calle, int numero, String codigoPostal, String ciudad ) {
		logger.trace("Creando direccionVO");
		this.calle = calle;
		this.numero = numero;
		this.codigoPostal = codigoPostal;
		this.ciudad = ciudad;
	}
	
	public String getCalle() {
		return calle;
	}

	public int getNumero() {
		return numero;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}
	
	public String getCiudad() {
		return ciudad;
	}
	
	public String getDireccionCompleta() {
		return calle + "," + Integer.toString(numero) +"," + ciudad + ", " +codigoPostal;
	}
	
	public void setCalle(String calle) {
		this.calle = calle;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	
	public String getTextoDireccion() {
		return calle + "," + Integer.toString(numero) +"," + codigoPostal;
	}
	
	public String toString() {
		return calle + "," + ciudad;
	}
	
	
}
