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

    public ActividadTabla(ActividadVO actividad) {
    	
    	this.nombre = new SimpleStringProperty(actividad.getNombre());
    	this.inicio = new SimpleStringProperty(actividad.getTextoInicio());
    	this.fin = new SimpleStringProperty(actividad.getTextoFin());
    	this.lugar = new SimpleStringProperty(actividad.getDireccion().toString());
    	this.aforo = new SimpleStringProperty(Integer.toString(actividad.getCapacidad()));
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