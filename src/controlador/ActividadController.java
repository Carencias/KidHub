package controlador;

import java.sql.SQLException;
import java.time.LocalDateTime;
import org.apache.log4j.Logger;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import modelo.KidHubException;
import modelo.Logica;
import modelo.vo.ActividadVO;
import modelo.vo.Direccion;

/**
 * Clase controladora de las ventanas de creacion de actividades
 * @version 1.0
 */
public class ActividadController extends Controller {

    @FXML
    private JFXTextField nombreAct, tipo, ciudad, calle, num, codPostal,
    					 aforo, diaInicio, mesInicio, anoInicio, horaInicio, minInicio,
    					 diaFin, mesFin, anoFin, horaFin, minFin;
    
    @FXML
    private Button confirmar, cancelar;
    
    /**
     * Atributo en el que se almacena/utiliza para coger los datos de la actividad
     */
    private ActividadVO actividad;
    
    /**
     * Boolean que es true si se esta modificando o false si se esta creando una actividad
     */
    private boolean modificacion;
    
    private Stage stage;
    
    private MonitorInicioController controladorMonitor;
    
    static Logger logger = Logger.getLogger(ActividadController.class);
    
    /**
     * Metodo que inicializa la clase y inicializa los campos de la pantalla con los datos de la actividad si se esta modificando
     * @param actividad
     *  ActividadVO con los datos de la actividad a modificar
     * @param modificacion
     *  Boleean que indica si se esta modificando la actividad o creando
     * @param stage
     *  Stage
     */
    void initData(ActividadVO actividad, boolean modificacion, Stage stage, MonitorInicioController controladorMonitor) {
    	this.actividad = actividad;
    	this.modificacion = modificacion;
    	this.stage = stage;
    	this.controladorMonitor = controladorMonitor;
  	
    	if(modificacion) {
    		this.setDatosActividad(actividad);
    	}
    }
    
    /**
     * Metodo que escucha el boton de confirmar y indica a la logica que cree o modifique una actividad
     * @param event
     *  Pulsado boton de confirmar
     */
    @FXML
    void confirmar(MouseEvent event) {
    	
    	try {
    		this.getDatosActividad(actividad);
    		
	    	if(this.modificacion) {	
	    		logger.trace("Boton de confirmar modificacion de actividad pulsado");
	    		Logica.getLogica().modificarActividad(actividad);

	    	}else {
	    		logger.trace("Boton de confirmar creacion de actividad pulsado");
    			Logica.getLogica().crearActividad(actividad);
				
	    	}
			controladorMonitor.inicializarTablaActividades(Logica.getLogica().getActividades(Logica.getLogica().getUsuarioActual()));
    	}catch(Exception e) {
			if(e instanceof KidHubException) {
				logger.error(e.getMessage());
				muestraError("ERROR","Se produjo un error.", e.getMessage());
			}else if(e instanceof SQLException) {
				logger.error("Formato de algun campo introducido invalido");
				muestraError("ERROR","Se produjo un error.", "Formato de algun campo introducido invalido");
			}
		}
    	
    	this.cerrarVentana(event);
    	this.recuperarVentana(this.stage);
    }
        
    /**
     * Metodo que escucha el boton de cancelar la creacion/modificacion de la actividad
     * @param event
     *  Pulsado boton de cancelar
     */
    @FXML
    void cancelar(MouseEvent event) {
    	logger.trace("Boton de cancelar pulsado");
    	this.cerrarVentana(event);
    	this.recuperarVentana(this.stage);
    }
    
    /**
     * Metodo privado que actualiza los campos de la interfaz con los datos de la actividad recibida
     * @param actividad
     *  ActividadVO con los datos de la actividad
     */
    private void setDatosActividad(ActividadVO actividad) {
    	logger.trace("Seteando datos en la interfaz");
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
	
	/**
	 * Metodo privado que recoge los datos de la actividad y comprueba que todos los datos son validos
	 * @param actividad
	 *  ActividadVO en la que se almacenan los datos recogidos
	 * @throws KidHubException
	 *  Lanza la excepcion si los datos son invalidos
	 */
	private void getDatosActividad(ActividadVO actividad) throws KidHubException{
		logger.trace("Rellenando la actividad con los datos de la interfaz");
		StringBuffer error = new StringBuffer();
		int numero = 1, aforo = 1;
		
		if(hayCamposVacios(this.nombreAct, this.tipo, this.calle, this.codPostal, this.ciudad, this.diaInicio, this.mesInicio, this.anoInicio, this.horaInicio, this.minInicio, this.diaFin, this.mesFin, this.anoFin, this.horaFin, this.minFin)) {
			error.append("Campos sin rellenar\n");
		}
		
		try {
			numero = Integer.parseInt(this.num.getText());
			aforo = Integer.parseInt(this.aforo.getText());
		}catch(Exception e) {
			error.append("Numero o aforo invalidos\n");
		}
		if(aforo <= 0 || numero < 0) {
			error.append("Aforo <= 0, o numero < 0\n");
		}
		actividad.setNombre(this.nombreAct.getText());
		actividad.setTipo(this.tipo.getText());
		Direccion direccion = new Direccion(this.calle.getText(), Integer.parseInt(this.num.getText()), this.codPostal.getText(), this.ciudad.getText());
		actividad.setDireccion(direccion);
		
		darFormatoVista(diaInicio, mesInicio, horaInicio, minInicio, diaFin, mesFin, horaFin, minFin);
		
		try {
			error.append(this.setFechas());

		}catch(Exception e) {
			error.append("Formato de fecha invalido\n");
		}
		if(error.length() != 0) {
			throw new KidHubException(error.toString());
		}
		actividad.setCapacidad(Integer.parseInt(this.aforo.getText()));
	}
	
	private String setFechas() {
		String error;
		LocalDateTime inicio = LocalDateTime.of(Integer.parseInt(this.anoInicio.getText()), Integer.parseInt(this.mesInicio.getText()), Integer.parseInt(this.diaInicio.getText()), Integer.parseInt(this.horaInicio.getText()), Integer.parseInt(this.minInicio.getText()));
		LocalDateTime fin = LocalDateTime.of(Integer.parseInt(this.anoFin.getText()), Integer.parseInt(this.mesFin.getText()), Integer.parseInt(this.diaFin.getText()), Integer.parseInt(this.horaFin.getText()), Integer.parseInt(this.minFin.getText()));
		actividad.setInicio(inicio);
		actividad.setFin(fin);
		actividad.setDuracion();
		if(inicio.isAfter(fin)) {
			error = "La actividad no puede terminar antes de empezar\n";
		}else {
			error = "";
		}
		
		return error;
	}

	private boolean hayCamposVacios(JFXTextField...campos) {
		for(JFXTextField campo: campos) {
			if(campo.getText().isEmpty()) {
				return true;
			}
		}
		
		return false;
	}

	
	private void darFormatoVista(JFXTextField...campos) {
		
		for(JFXTextField campo: campos) {
			if(campo.getText().length() == 1) {
				campo.setText("0"+campo.getText());
			}
		}	

	}
}
