package vista;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import modelo.vo.TrayectoVO;

public class TrayectoTabla extends RecursiveTreeObject<TrayectoTabla> {
    StringProperty actividad;
    StringProperty tipo;
    StringProperty padre;
    StringProperty aforo;

    public TrayectoTabla(TrayectoVO trayecto) {
    	
    	this.actividad = new SimpleStringProperty(trayecto.getActividad().getNombre());
    	this.tipo = new SimpleStringProperty(trayecto.getTipo().toString());
    	this.padre = new SimpleStringProperty(trayecto.getPadre().getNombre());
    	this.aforo = new SimpleStringProperty(Integer.toString(trayecto.getCapacidad()));
    	
    }

	public StringProperty getActividad() {
		return actividad;
	}

	public StringProperty getTipo() {
		return tipo;
	}

	public StringProperty getPadre() {
		return padre;
	}

	public StringProperty getAforo() {
		return aforo;
	}
    
}