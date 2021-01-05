package vista;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import modelo.Logica;
import modelo.vo.TrayectoVO;

public class TrayectoTabla extends RecursiveTreeObject<TrayectoTabla> {
    StringProperty actividad;
    StringProperty tipo;
    StringProperty padre;
    StringProperty aforo;
    private int id;

    public TrayectoTabla(TrayectoVO trayecto) {
    	
    	String actividad = Integer.toString(trayecto.getActividad().getIdActividad()) + "-" + trayecto.getActividad().getNombre();
    	this.actividad = new SimpleStringProperty(actividad);
    	this.tipo = new SimpleStringProperty(trayecto.getTipo().toString());
    	this.padre = new SimpleStringProperty(trayecto.getPadre().getNombre());
    	this.aforo = new SimpleStringProperty(Integer.toString(trayecto.getCapacidad()));
    	this.id = trayecto.getIdTrayecto();
    	
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
	
	public int getId() {
		return id;
	}
    
}