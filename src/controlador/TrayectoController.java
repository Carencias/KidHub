package controlador;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import modelo.KidHubException;
import modelo.Logica;
import modelo.vo.ActividadVO;
import modelo.vo.Direccion;
import modelo.vo.ParadaVO;
import modelo.vo.TrayectoVO;
import modelo.vo.ParadaVO.TipoParada;
import modelo.vo.TrayectoVO.TipoTrayecto;

/**
 * Clase controladora de la ventana que permite gestionar los trayectos
 * @version 1.0
 */
public class TrayectoController extends Controller{
	   	@FXML
	    private JFXTextField dia, mes, ano, aforo, codPostal, ciudad, calle, num, diaDestino, mesDestino, anoDestino, ciudadDestino,
	    calleDestino, numDestino, codPostalDestino, hora, min, horaDestino, minDestino;
	   
	    @FXML
	    private JFXRadioButton ida, vuelta;

	    @FXML
	    private ToggleGroup grupo1;
	    
	    @FXML
	    private Button confirmar, cancelar;

	    @FXML
	    private JFXComboBox<String> actividad;
	    
	    @FXML
	    private Label nombreActividad;
	    
	    private Stage stage;
	    
	    private TrayectoVO trayecto;
	    
	    private boolean modificacion;
	    
		static Logger logger = Logger.getLogger(TrayectoController.class);
		
		/**
		 * Inicializa los datos
		 * @param actividades
		 * @param trayecto
		 * @param stage
		 * @param modificacion
		 */
	    void initData(ArrayList<ActividadVO> actividades, TrayectoVO trayecto, Stage stage, boolean modificacion) {
	    	this.trayecto = trayecto;
	    	this.modificacion = modificacion;
	    	this.stage = stage;	    	  	
	    	this.setDatosCombo(actividades);
	    	
	    	if(modificacion) {
	    		this.setDatosTrayecto();
	    	}
	    	
	    }
	    
	    /**
	     * Setea los datos del trayecto
	     */
	    private void setDatosTrayecto() {
	    	
	    	ActividadVO actividad = this.trayecto.getActividad();
	    	this.nombreActividad.setText("Actividad: " + actividad.getIdActividad() + "-" + actividad.getNombre());	    

	    	LocalDateTime fechaOrigen = this.trayecto.getOrigen().getFecha();
	    		    	
	    	this.dia.setText(Integer.toString(fechaOrigen.getDayOfMonth()));
	    	this.mes.setText(Integer.toString(fechaOrigen.getMonthValue()));
	    	this.ano.setText(Integer.toString(fechaOrigen.getYear()));
	    	this.hora.setText(Integer.toString(fechaOrigen.getHour()));
	    	this.min.setText(Integer.toString(fechaOrigen.getMinute()));
	    	
	    	LocalDateTime fechaDestino = this.trayecto.getDestino().getFecha();
	    	
	    	this.diaDestino.setText(Integer.toString(fechaDestino.getDayOfMonth()));
	    	this.mesDestino.setText(Integer.toString(fechaDestino.getMonthValue()));
	    	this.anoDestino.setText(Integer.toString(fechaDestino.getYear()));
	    	this.horaDestino.setText(Integer.toString(fechaDestino.getHour()));
	    	this.minDestino.setText(Integer.toString(fechaDestino.getMinute()));
	    	
	    	
	    	Direccion direccionOrigen = this.trayecto.getOrigen().getDireccion();
	    	
	    	this.ciudad.setText(direccionOrigen.getCiudad());
	    	this.calle.setText(direccionOrigen.getCalle());
	    	this.num.setText(Integer.toString(direccionOrigen.getNumero()));
	    	this.codPostal.setText(direccionOrigen.getCodigoPostal());
	    	
	    	Direccion direccionDestino = this.trayecto.getDestino().getDireccion();
	    	
	    	this.ciudadDestino.setText(direccionDestino.getCiudad());
	    	this.calleDestino.setText(direccionDestino.getCalle());
	    	this.numDestino.setText(Integer.toString(direccionDestino.getNumero()));
	    	this.codPostalDestino.setText(direccionDestino.getCodigoPostal());
	    	
	    	this.aforo.setText(Integer.toString(this.trayecto.getCapacidad()));
	    	
	    	if(this.trayecto.getTipo().equals(TipoTrayecto.IDA)) {
	    		this.ida.setSelected(true);
	    		this.disableFields(TipoTrayecto.IDA);
	    	}else {
	    		this.vuelta.setSelected(true);
	    		this.disableFields(TipoTrayecto.VUELTA);
	    	}
    		
	    }
	    
	    private void disableFields(TipoTrayecto tipo) {
	    	
	    	this.ida.setDisable(true);
    		this.vuelta.setDisable(true);
    		this.nombreActividad.setVisible(true);
    		this.actividad.setVisible(false);
    		
	    	if(tipo.equals(TipoTrayecto.IDA)) {
	    		this.dia.setDisable(true);
	    		this.mes.setDisable(true);
	    		this.ano.setDisable(true);
	    		this.hora.setDisable(true);
	    		this.min.setDisable(true);
	    		
	    		this.ciudad.setDisable(true);
	    		this.calle.setDisable(true);
	    		this.num.setDisable(true);
	    		this.codPostal.setDisable(true);
	    	}else {
	    		this.diaDestino.setDisable(true);
	    		this.mesDestino.setDisable(true);
	    		this.anoDestino.setDisable(true);
	    		this.horaDestino.setDisable(true);
	    		this.minDestino.setDisable(true);
	    		
	    		this.ciudadDestino.setDisable(true);
	    		this.calleDestino.setDisable(true);
	    		this.numDestino.setDisable(true);
	    		this.codPostalDestino.setDisable(true);
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
	    
	    private void getDatosTrayecto() throws KidHubException, SQLException{
	    	
	    	//TODO los metodos estos de control como hayCamposVacios, darFormato etc habria que meterlos al Controller padre
	    	logger.trace("Rellenando la actividad con los datos de la interfaz");
	    	StringBuffer error = new StringBuffer();
	    	ParadaVO paradaOrigen, paradaDestino;
	    	LocalDateTime fechaOrigen = null, fechaDestino = null;
	    	Direccion direccionOrigen, direccionDestino;

	    	int numero = 1, aforo = 1, numeroDestino = 1;
	    	if(hayCamposVacios(this.dia, this.mes, this.ano, this.min, this.hora, this.diaDestino, this.mesDestino, this.anoDestino,
	    	                   this.aforo, this.codPostal, this.ciudad, this.calle, this.num, this.codPostalDestino,
	    	                   this.ciudadDestino, this.calleDestino,this.numDestino)) {	    		
	    		error.append("Campos sin rellenar\n");	    		
	    	}
	    	
	    	if(!modificacion) {
		    	if(this.actividad.getSelectionModel().getSelectedItem() == null) {
		    		error.append("Seleccione la actividad relacionada con el trayecto.\n");
		    	}
	    	}
	    	
	    	try {
				numero = Integer.parseInt(this.num.getText());
				numeroDestino = Integer.parseInt(this.numDestino.getText());
				aforo = Integer.parseInt(this.aforo.getText());
				
			}catch(Exception e) {
				error.append("Formato de numero o aforo invalidos\n");
			}
	    	
	    	this.trayecto.setCapacidad(aforo);
	    		    	
	    	direccionOrigen = new Direccion(this.calle.getText(), numero, this.codPostal.getText(), this.ciudad.getText());
	    	direccionDestino = new Direccion(this.calleDestino.getText(), numeroDestino, this.codPostalDestino.getText(), this.ciudadDestino.getText());

	    	try {
	    		fechaOrigen = LocalDateTime.of(Integer.parseInt(this.ano.getText()), Integer.parseInt(this.mes.getText()), Integer.parseInt(this.dia.getText()),
						Integer.parseInt(this.hora.getText()), Integer.parseInt(this.min.getText()));
				fechaDestino = LocalDateTime.of(Integer.parseInt(this.anoDestino.getText()), Integer.parseInt(this.mesDestino.getText()), Integer.parseInt(this.diaDestino.getText()),
										 Integer.parseInt(this.horaDestino.getText()), Integer.parseInt(this.minDestino.getText()));
				
				if(fechaOrigen.isAfter(fechaDestino) || fechaOrigen.isEqual(fechaDestino)) {
					error.append("El trayecto debe comenzar antes de acabar\n");
				}
	    		
	    	}catch(Exception e) {
	    		error.append("Formato de fecha invalido.\n");
	    	}
	    		
	    	if(error.length() != 0) {
				throw new KidHubException(error.toString());
			}
	    	
	    	paradaOrigen = new ParadaVO(fechaOrigen, TipoParada.ORIGEN, direccionOrigen);
	    	paradaDestino = new ParadaVO(fechaDestino, TipoParada.DESTINO, direccionDestino);
	    	
	    	trayecto.setOrigen(paradaOrigen);
	    	trayecto.setDestino(paradaDestino);
	    	trayecto.setActividad(this.getActividad());
	    	
	    	if(this.ida.isSelected()) {
	    		trayecto.setTipo(TipoTrayecto.IDA);
	    	}else {
	    		trayecto.setTipo(TipoTrayecto.VUELTA);
	    	}
	    }	
	    
	    @FXML
	    void setDatosActividad(ActionEvent event) throws SQLException{
	    	//TODO este es el evento del combobox, pero como gestiono al cambiar luego lo de ida y vuelta??
	    	
	    	this.ida.setDisable(true);
	    	this.vuelta.setDisable(true);
	    	
	    	ActividadVO actividad = this.getActividad();
	    	
	    	if(this.ida.isSelected()) {
	    		this.setDatosOrigen(actividad);
	    	}else {
	    		this.setDatosDestino(actividad);
	    	}
	    	
	    }
	    
	    
	    private ActividadVO getActividad() throws SQLException{
	    	
	    	String nombreActividad = "";
	    	if(!this.modificacion) {
		    	//Nombre de actividad
	    		nombreActividad = this.actividad.getSelectionModel().getSelectedItem();	    			
	    	}else {
	    		nombreActividad = this.nombreActividad.getText();
	    		System.out.println("Antes del substirng es: " + nombreActividad);
	    		// Siempre empieza por 'Actividad: '
	    		nombreActividad = nombreActividad.substring(11);
	    		System.out.println("Despues del substring es: " + nombreActividad);
	    	}
	    	//La actividad en el combo es id-tabla, asique hago un substring para obtener el id
	    	
	    	String[] partes = nombreActividad.split("-");
	    	
	    	String idActividad = partes[0];
	    	
	    	ActividadVO actividad = new ActividadVO();
	    	
	    	actividad.setIdActividad(Integer.parseInt(idActividad));
	    	
	    	//TODO comprobar este try catch, lo hizo Bayon rapido
	    	Logica.getLogica().rellenarActividad(actividad);
	    	return actividad;
	    	
	    	
	    }
	    
		
	    
	    private boolean hayCamposVacios(JFXTextField...campos) {
			for(JFXTextField campo: campos) {
				if(campo.getText().isEmpty()) {
					return true;
				}
			}
			
			return false;
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
	    	try {
	    		
	    		this.getDatosTrayecto();
	    		
	    		if(this.modificacion) {
	    			logger.trace("Boton de confirmar modificacion de actividad pulsado");
	    			Logica.getLogica().modificarTrayecto(trayecto);
	    		}else {
	    			logger.trace("Boton de confirmar creacion de actividad pulsado");
	    			Logica.getLogica().crearTrayecto(trayecto);
	    		}
	    	
	    	}catch(Exception e) {
				if(e instanceof KidHubException) {
					logger.error(e.getMessage());
					muestraError("ERROR","Se produjo un error.", e.getMessage());
				}/*else if(e instanceof SQLException) {
					logger.error("Formato de algun campo introducido invalido");
					muestraError("ERROR","Se produjo un error.", "Formato de algun campo introducido invalido");
				}*/else {
					logger.error("Error desconocido.");
					e.printStackTrace();
					muestraError("ERROR","Se produjo un error.", "Error en alguno de los campos introducidos");
				}
			}
	    	
	    	
	    	
	    	//TODO recargar la tabla de trayectos a traves de un objeto controller que se reciba en el initData.
	    	this.mostrarVentana("PadreInicio");
	    	this.cerrarVentana(event);
	    }
}
