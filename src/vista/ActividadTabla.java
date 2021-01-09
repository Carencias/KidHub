package vista;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import modelo.vo.ActividadVO;

public class ActividadTabla extends RecursiveTreeObject<ActividadTabla> {
    StringProperty nombre;
    StringProperty inicio;
    StringProperty fin;
    StringProperty lugar;
    StringProperty aforo;
    StringProperty tipo;
    StringProperty idString;
    private int id;

    public ActividadTabla(ActividadVO actividad) {
    	
    	this.nombre = new SimpleStringProperty(actividad.getNombre());
    	this.inicio = new SimpleStringProperty(actividad.getTextoInicio());
    	this.fin = new SimpleStringProperty(actividad.getTextoFin());
    	this.lugar = new SimpleStringProperty(actividad.getDireccion().toString());
    	this.aforo = new SimpleStringProperty(Integer.toString(actividad.getCapacidad()));
    	this.tipo = new SimpleStringProperty(actividad.getTipo());
    	this.idString = new SimpleStringProperty(Integer.toString(actividad.getIdActividad()));
    	this.id = actividad.getIdActividad();
    }
    
	public int getId() {
		return id;
	}
	
	public StringProperty getIdString() {
		return idString;
	}

	public StringProperty getNombre() {
		return nombre;
	}

	public StringProperty getInicio() {
		return inicio;
	}

	public StringProperty getFin() {
		return fin;
	}

	public StringProperty getLugar() {
		return lugar;
	}

	public StringProperty getAforo() {
		return aforo;
	}
    
    
}