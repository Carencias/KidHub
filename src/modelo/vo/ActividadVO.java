package modelo.vo;
import java.time.LocalDate;
import java.util.ArrayList;

public class ActividadVO {
	
	
	private int idActividad;
	private String nombre;
	private LocalDate fechaInicio;
	private LocalDate fechaFin;
	private int duracion;
	private int capacidad;
	private Direccion direccion;
	private ArrayList<HijoVO> hijos;
	private MonitorVO monitor;
	private String tipo;
	
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
	
	public LocalDate getFechaInicio() {
		return fechaInicio;
	}
	
	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	
	public LocalDate getFechaFin() {
		return fechaFin;
	}
	
	public void setFechaFin(LocalDate fechaFin) {
		this.fechaFin = fechaFin;
	}
	
	public int getDuracion() {
		return duracion;
	}
	
	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}
	
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
