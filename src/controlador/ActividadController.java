package controlador;

import java.sql.SQLException;
import java.time.LocalDateTime;

import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import modelo.KidHubException;
import modelo.Logica;
import modelo.vo.ActividadVO;
import modelo.vo.Direccion;
import modelo.vo.MonitorVO;

public class ActividadController extends Controller {

    @FXML
    private JFXTextField nombreAct, tipo, ciudad, calle, num, codPostal,
    					 aforo, diaInicio, mesInicio, anoInicio, horaInicio, minInicio,
    					 diaFin, mesFin, anoFin, horaFin, minFin;
    
    @FXML
    private Button confirmar, cancelar;
    
    
    private ActividadVO actividad;
    
    private boolean modificacion;
    
    
    void initData(ActividadVO actividad, boolean modificacion) {
    	System.out.println("estoy iniciando datos");
    	this.actividad = actividad;
    	this.modificacion = modificacion;
    	
    	if(modificacion) {
    		this.setDatosActividad(actividad);
    	}
    }
    
    
    @FXML
    void confirmar(MouseEvent event) {
    	System.out.println("Confirmando");
    	this.getDatosActividad(actividad);
    	
    	if(this.modificacion) {	
    		try {
    			Logica.getLogica().modificarActividad(actividad);  			
			}catch(Exception e) {
				if(e instanceof KidHubException) {
					muestraError("ERROR","Se produjo un error.", e.getMessage());
				}else if(e instanceof SQLException) {
					muestraError("ERROR","Se produjo un error.", "Campos invalidos.");
				}
			}
    	}else {
    		try {
    			Logica.getLogica().crearActividad(actividad);
			}catch(Exception e) {
				if(e instanceof KidHubException) {
					muestraError("ERROR","Se produjo un error.", e.getMessage());
				}else if(e instanceof SQLException) {
					muestraError("ERROR","Se produjo un error.", "Campos invalidos.");
				}
			}
    	}
    	
    	System.out.println("He salido del if-else");
    	this.cerrarVentana(event);
    	this.mostrarVentana("MonitorInicio");

    }
    
    
    @FXML
    void cancelar(MouseEvent event) {
    	System.out.println("Cancelando");
    	this.cerrarVentana(event);
    	this.mostrarVentana("MonitorInicio");
    }
    
    private void setDatosActividad(ActividadVO actividad) {
		this.nombreAct.setText(actividad.getNombre());
    	this.tipo.setText(actividad.getTipo());
    	this.aforo.setText(Integer.toString(actividad.getCapacidad()));
    	this.ciudad.setText(actividad.getDireccion().getCiudad());
    	this.calle.setText(actividad.getDireccion().getCalle());
    	this.num.setText(Integer.toString(actividad.getDireccion().getNumero()));
    	this.codPostal.setText(actividad.getDireccion().getCodigoPostal());
    	this.diaInicio.setText(Integer.toString(actividad.getInicio().getDayOfMonth()));
    	this.mesInicio.setText(Integer.toString(actividad.getInicio().getMonthValue()));
    	this.anoInicio.setText(Integer.toString(actividad.getInicio().getYear()));
    	this.horaInicio.setText(Integer.toString(actividad.getInicio().getHour()));
    	this.minInicio.setText(Integer.toString(actividad.getInicio().getMinute()));
    	this.diaFin.setText(Integer.toString(actividad.getFin().getDayOfMonth()));
    	this.mesFin.setText(Integer.toString(actividad.getFin().getMonthValue()));
    	this.anoFin.setText(Integer.toString(actividad.getFin().getYear()));
    	this.horaFin.setText(Integer.toString(actividad.getFin().getHour()));
    	this.minFin.setText(Integer.toString(actividad.getFin().getMinute()));
	}
	
	
	private void getDatosActividad(ActividadVO actividad) {
		actividad.setNombre(this.nombreAct.getText());
		actividad.setTipo(this.tipo.getText());
		Direccion direccion = new Direccion(this.calle.getText(), Integer.parseInt(this.num.getText()), this.codPostal.getText(), this.ciudad.getText());
		actividad.setDireccion(direccion);
		if(diaInicio.getText().length() == 1) {
			diaInicio.setText("0"+diaInicio.getText());
		}
		if(mesInicio.getText().length() == 1) {
			mesInicio.setText("0"+mesInicio.getText());
		}
		if(horaInicio.getText().length() == 1) {
			horaInicio.setText("0"+horaInicio.getText());
		}
		if(minInicio.getText().length() == 1) {
			minInicio.setText("0"+minInicio.getText());
		}
		if(diaFin.getText().length() == 1) {
			diaFin.setText("0"+diaFin.getText());
		}
		if(mesFin.getText().length() == 1) {
			mesFin.setText("0"+mesFin.getText());
		}
		if(horaFin.getText().length() == 1) {
			horaFin.setText("0"+horaFin.getText());
		}
		if(minFin.getText().length() == 1) {
			minFin.setText("0"+minFin.getText());
		}
		LocalDateTime inicio = LocalDateTime.of(Integer.parseInt(this.anoInicio.getText()), Integer.parseInt(this.mesInicio.getText()), Integer.parseInt(this.diaInicio.getText()), Integer.parseInt(this.horaInicio.getText()), Integer.parseInt(this.minInicio.getText()));
		LocalDateTime fin = LocalDateTime.of(Integer.parseInt(this.anoFin.getText()), Integer.parseInt(this.mesFin.getText()), Integer.parseInt(this.diaFin.getText()), Integer.parseInt(this.horaFin.getText()), Integer.parseInt(this.minFin.getText()));
		actividad.setInicio(inicio);
		actividad.setFin(fin);
		actividad.setDuracion();
		actividad.setCapacidad(Integer.parseInt(this.aforo.getText()));
		actividad.setMonitor((MonitorVO) Logica.getLogica().getUsuarioActual());
	}
}
