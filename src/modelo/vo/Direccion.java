package modelo.vo;

public class Direccion {

	private String calle;
	
	private int numero;
	
	private int codigoPostal;
	
	private String ciudad;
	
	private String direccionCompleta;
	
	public Direccion(String calle, int numero, int codigoPostal, String ciudad ) {
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

	public int getCodigoPostal() {
		return codigoPostal;
	}
	
	public String getCiudad() {
			return ciudad;
	}
	
	public String getTextoDireccion() {
		return calle + "," + Integer.toString(numero) +"," + Integer.toString(codigoPostal);
	}
	
	public String toString() {
		return calle + "," + ciudad;
	}
	
	
}
