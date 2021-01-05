package controlador;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import modelo.Logica;
import modelo.vo.ActividadVO;
import modelo.vo.Direccion;
import modelo.vo.TrayectoVO;
import modelo.vo.TrayectoVO.TipoTrayecto;

public class TrayectoController extends Controller{
	   @FXML
	    private JFXTextField dia;

	    @FXML
	    private JFXTextField mes;

	    @FXML
	    private JFXTextField ano;

	    @FXML
	    private JFXRadioButton ida;

	    @FXML
	    private ToggleGroup grupo1;

	    @FXML
	    private JFXRadioButton vuelta;

	    @FXML
	    private JFXTextField aforo;

	    @FXML
	    private JFXTextField codPostal;

	    @FXML
	    private JFXTextField ciudad;

	    @FXML
	    private JFXTextField calle;

	    @FXML
	    private JFXTextField num;
	    
	    @FXML
	    private JFXTextField diaDestino, mesDestino, anoDestino, ciudadDestino, calleDestino, numDestino, codPostalDestino, 
	    					 hora, min, horaDestino, minDestino;

	    @FXML
	    private Button confirmar;

	    @FXML
	    private Button cancelar;

	    @FXML
	    private JFXComboBox<String> actividad;
	    
	    private Stage stage;
	    
	    private TrayectoVO trayecto;
	    
	    private boolean modificacion;
	    
	    
	    void initData(ArrayList<ActividadVO> actividades, TrayectoVO trayecto, Stage stage, boolean modificacion) {
	    	
	    	System.out.println("Estoy iniciando datos");
	    	this.trayecto = trayecto;
	    	this.modificacion = modificacion;
	    	this.stage = stage;	    	
	    	
	    	this.setDatosCombo(actividades);
	    	
	    	if(modificacion) {
	    		this.setDatosTrayecto();
	    	}
	    	
	    }
	    
	    private void setDatosTrayecto() {
	    	//TODO
	    	LocalDateTime fechaOrigen = this.trayecto.getOrigen().getFecha();
	    	LocalDateTime fechaDestino = this.trayecto.getDestino().getFecha();
	    	Direccion direccionOrigen = this.trayecto.getOrigen().getDireccion();
	    	Direccion direccionDestino = this.trayecto.getDestino().getDireccion();
	    	
	    	
	    	if(this.trayecto.getTipo().equals(TipoTrayecto.IDA)) {
	    		this.ida.setSelected(true);
	    	}else {
	    		this.vuelta.setSelected(false);
	    	}
	    	
	    }
	    
	    
	    private void setDatosCombo(ArrayList<ActividadVO> actividades) {
	    	//TODO
	    	String nombreActividad;
	    	for(ActividadVO actividad: actividades) {
	    		nombreActividad = actividad.getIdActividad() + "-" + actividad.getNombre();
	    		this.actividad.getItems().add(nombreActividad);
	    	}
	    	
	    }
	    
	    private void getDatosTrayecto() {
	    	//TODO
	    }
	    
	    @FXML
	    void setDatosActividad(ActionEvent event) {
	    	//TODO este es el evento del combobox, pero como gestiono al cambiar luego lo de ida y vuelta??
	    	//Nombre de actividad
	    	String nombreActividad = this.actividad.getSelectionModel().getSelectedItem();	    			
	    	
	    	//La actividad en el combo es id-tabla, asique hago un substring para obtener el id
	    	
	    	String[] partes = nombreActividad.split("-");
	    	
	    	String idActividad = partes[0];
	    	
	    	ActividadVO actividad = new ActividadVO();
	    	
	    	actividad.setIdActividad(Integer.parseInt(idActividad));
	    	
	    	try{
    			Logica.getLogica().rellenarActividad(actividad);
    		}catch(SQLException e) {
    			logger.error("Error al obtener la actividad: "+e.getMessage());
    			this.muestraError("ERROR", "Actividades", "Error al obtener la actividad");
    		}
	    	
	    	if(this.ida.isSelected()) {
	    		this.setDatosOrigen(actividad);
	    	}else {
	    		this.setDatosDestino(actividad);
	    	}
	    	
	    }
	    
	    private void setDatosDestino(ActividadVO actividad) {

	    	LocalDateTime hora = actividad.getFin();
	    	
	    	this.dia.setDisable(true);
	    	this.dia.setText(Integer.toString(hora.getDayOfMonth()));
	    	
	    	this.mes.setDisable(true);
	    	this.mes.setText(Integer.toString(hora.getMonthValue()));
	    	
	    	this.ano.setDisable(true);
	    	this.ano.setText(Integer.toString(hora.getYear()));
	    	
	    	this.min.setDisable(true);
	    	this.min.setText(Integer.toString(hora.getMinute()));
	    	
	    	this.hora.setDisable(true);
	    	this.hora.setText(Integer.toString(hora.getHour()));
	    	
	    	
	    	
	    	Direccion direccion = actividad.getDireccion();
	    	
	    	this.ciudad.setDisable(true);
	    	this.ciudad.setText(direccion.getCiudad());
	    	
	    	this.calle.setDisable(true);
	    	this.calle.setText(direccion.getCalle());
	    	
	    	this.num.setDisable(true);
	    	this.num.setText(Integer.toString(direccion.getNumero()));
	    	
	    	this.codPostal.setDisable(true);
	    	this.codPostal.setText(direccion.getCodigoPostal());
	    }
	    
	    
	    private void setDatosOrigen(ActividadVO actividad) {
	    	
	    	LocalDateTime hora = actividad.getInicio();
	    		    	
	    	this.diaDestino.setDisable(true);
	    	this.diaDestino.setText(Integer.toString(hora.getDayOfMonth()));
	    	
	    	this.mesDestino.setDisable(true);
	    	this.mesDestino.setText(Integer.toString(hora.getMonthValue()));
	    	
	    	this.anoDestino.setDisable(true);
	    	this.anoDestino.setText(Integer.toString(hora.getYear()));
	    	
	    	this.minDestino.setDisable(true);
	    	this.minDestino.setText(Integer.toString(hora.getMinute()));
	    	
	    	this.horaDestino.setDisable(true);
	    	this.horaDestino.setText(Integer.toString(hora.getHour()));
	    	
	    	Direccion direccion = actividad.getDireccion();
	    	
	    	this.ciudadDestino.setDisable(true);
	    	this.ciudadDestino.setText(direccion.getCiudad());
	    	
	    	this.calleDestino.setDisable(true);
	    	this.calleDestino.setText(direccion.getCalle());
	    	
	    	this.numDestino.setDisable(true);
	    	this.numDestino.setText(Integer.toString(direccion.getNumero()));
	    	
	    	this.codPostalDestino.setDisable(true);
	    	this.codPostalDestino.setText(direccion.getCodigoPostal());
	    }

	    
	    @FXML
	    void cancelar(MouseEvent event) {
	    	System.out.println("Cancelando");
	    	this.recuperarVentana(stage);
	    	this.cerrarVentana(event);
	    }

	    @FXML
	    void confirmar(MouseEvent event) {
	    	System.out.println("Confirmando");
	    	//TODO	    
	    }
}
