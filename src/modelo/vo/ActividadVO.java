package modelo.vo;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ActividadVO {
	
	
	private int idActividad;
	private String nombre;
	private LocalDateTime inicio;
	private LocalDateTime fin;
	private int duracion;
	private int capacidad;
	private Direccion direccion;
	private ArrayList<HijoVO> hijos;
	private MonitorVO monitor;
	private String tipo;
	
	public ActividadVO(String nombre, LocalDateTime inicio, LocalDateTime fin, int capacidad, Direccion direccion, String tipo) {
		this.nombre = nombre;
		this.inicio = inicio;
		this.fin = fin;
		this.capacidad = capacidad;
		this.direccion = direccion;
		this.tipo = tipo;
		
		this.setDuracion();
	}
	
	public int getIdActividad() {
		return idActividad;
	}
	
	public void setIdActividad(int idActividad) {
		this.idActividad = idActividad;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public LocalDateTime getInicio() {
		return inicio;
	}
	
	public void setInicio(LocalDateTime inicio) {
		this.inicio = inicio;
	}
	
	public LocalDateTime getFin() {
		return fin;
	}
	
	public void setFin(LocalDateTime fin) {
		this.fin = fin;
	}
	
	public int getDuracion() {
		return duracion;
	}
	
	/*public void setDuracion(int duracion) {
		this.duracion = duracion;
	}*/
	
	public int getCapacidad() {
		return capacidad;
	}
	
	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}
	
	public Direccion getDireccion() {
		return direccion;
	}
	
	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}
	
	public ArrayList<HijoVO> getHijos() {
		return hijos;
	}
	
	public void setHijos(ArrayList<HijoVO> hijos) {
		this.hijos = hijos;
	}
	
	public MonitorVO getMonitor() {
		return monitor;
	}
	
	public void setMonitor(MonitorVO monitor) {
		this.monitor = monitor;
	}
	
	public String getTipo() {
		return tipo;
	}

	public void setType(String type) {
		this.tipo = type;
	}
	
	public String getTextoInicio() {

		StringBuilder texto = new StringBuilder();
		
		texto.append(this.inicio.getDayOfMonth() + "/");
		texto.append(this.inicio.getMonthValue() + "/");
		texto.append(this.inicio.getYear() + " ");
		texto.append(this.inicio.getHour() + ":");
		texto.append(this.inicio.getMinute());
		
		return texto.toString();
	}
	
	public String getTextoFin() {
		StringBuilder texto = new StringBuilder();
		
		texto.append(this.fin.getDayOfMonth() + "/");
		texto.append(this.fin.getMonthValue() + "/");
		texto.append(this.fin.getYear() + " ");
		texto.append(this.fin.getHour() + ":");
		texto.append(this.fin.getMinute());
		
		return texto.toString();
	}
	
	private void setDuracion() {
		this.duracion = this.fin.getHour()*60 + this.fin.getMinute() - this.inicio.getHour()*60 + this.inicio.getMinute();
	}
	
	
	/*public String campo(int n) { //OJO EMPIEZA EN 1
		String campo = "";
		
		switch(n) {
		
			case 1:
				campo = "ActivityID";
				break;
				
			case 2:
				campo = "MonitorUsername";
				break;

			case 3:
				campo = "Name";
				break;

			case 4:
				campo = "StartDate";
				break;

			case 5:
				campo = "EndDate";
				break;

			case 6:
				campo = "Address";
				break;

			case 7:
				campo = "Town";
				break;

			case 8:
				campo = "Type";

			//default:
				//error
		}
		
		return campo;
		
		
	}*/
	
	
	
	
	
	
	
	
	
	
	
}
