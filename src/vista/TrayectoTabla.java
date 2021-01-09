package vista;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import modelo.Logica;
import modelo.vo.ParadaVO;
import modelo.vo.TrayectoVO;

public class TrayectoTabla extends RecursiveTreeObject<TrayectoTabla> {
    StringProperty actividad;
    StringProperty tipo;
    StringProperty padre;
    StringProperty aforo;
    StringProperty origenTexto;
    StringProperty destinoTexto;
    ParadaVO origen;
    ParadaVO destino;
    private int id;

    public TrayectoTabla(TrayectoVO trayecto) {
    	
    	String actividad = Integer.toString(trayecto.getActividad().getIdActividad()) + "-" + trayecto.getActividad().getNombre();
    	this.actividad = new SimpleStringProperty(actividad);
    	this.tipo = new SimpleStringProperty(trayecto.getTipo().toString());
    	this.padre = new SimpleStringProperty(trayecto.getPadre().getNombre());
    	this.aforo = new SimpleStringProperty(Integer.toString(trayecto.getCapacidad()));
    	this.id = trayecto.getIdTrayecto();
    	this.origen = trayecto.getOrigen();
    	this.origenTexto = new SimpleStringProperty(origen.getTextoFecha() + " " + origen.getDireccion().toString());
    	this.destino = trayecto.getDestino();
    	this.destinoTexto = new SimpleStringProperty(destino.getTextoFecha() + " " + destino.getDireccion().toString());
    	
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

	public StringProperty getOrigenTexto() {
		return origenTexto;
	}

	public StringProperty getDestinoTexto() {
		return destinoTexto;
	}

	public int getId() {
		return id;
	}
    
}