package modelo.vo;

public class ParadaVO {
	
	public enum tipoParada{
		INICIO, FIN, INTERMEDIA
	}
	
	private int idTrayecto;
	private int number;
	private String date;
	private int duration;
	private tipoParada tipo;
	private Direccion direccion;
	
	public int getIdTrayecto() {
		return idTrayecto;
	}
	public void setIdTrayecto(int idTrayecto) {
		this.idTrayecto = idTrayecto;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
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
