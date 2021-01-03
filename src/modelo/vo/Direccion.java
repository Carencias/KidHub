package modelo.vo;

public class Direccion {

	private String calle;
	
	private int numero;
	
	private String codigoPostal;
	
	private String ciudad;
	
	private String direccionCompleta;
	
	public Direccion(String calle, int numero, String codigoPostal, String ciudad ) {
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
		return calle + "," + Integer.toString(numero) +"," + ciudad + " (" +codigoPostal +")";
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
		return calle + "," + Integer.toString(numero) +"," + " (" +codigoPostal +")";
	}
	
	public String toString() {
		return calle + "," + ciudad;
	}
	
	
}
