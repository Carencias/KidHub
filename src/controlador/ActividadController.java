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
import modelo.vo.UsuarioVO;

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
     * Inicializa la clase y inicializa los campos de la pantalla con los datos de la actividad si se esta modificando
     * @param actividad
     * Contenedor con los datos de la actividad a modificar
     * @param modificacion
     *  true si se esta modificando la actividad, false si se esta creando
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
     * Escucha el boton de confirmar e indica a la logica que cree o modifique una actividad
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
    	}catch(KidHubException e) {
			logger.error(e.getMessage());
			muestraError("ERROR","Se produjo un error.", e.getMessage());
    	}catch(SQLException e) {
			logger.error("Formato de algun campo introducido invalido");
			muestraError("ERROR","Se produjo un error.", "Formato de algun campo introducido invalido");
		}catch(ClassCastException e) {
			
		}
    	
    	this.cerrarVentana(event);
    	this.mostrarVentana("MonitorInicio");
    }
        
    /**
     * Escucha el boton de cancelar la creacion/modificacion de la actividad
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
     * Actualiza los campos de la interfaz con los datos de la actividad recibida
     * @param actividad
     *  Contenedor con los datos de la actividad
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
	 * Recoge los datos de la actividad y comprueba que todos los datos son validos
	 * @param actividad
	 *  Contenedor en la que se almacenan los datos recogidos
	 * @throws KidHubException
	 *  Si los datos son invalidos
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
	
	/**
	 * Asigna las fechas a la actividad
	 * @return
	 * Cadena de texto que muestra el error en caso de que ocurra 
	 */
	private String setFechas() {
		String error = "";
		LocalDateTime inicio = LocalDateTime.of(Integer.parseInt(this.anoInicio.getText()), Integer.parseInt(this.mesInicio.getText()), Integer.parseInt(this.diaInicio.getText()), Integer.parseInt(this.horaInicio.getText()), Integer.parseInt(this.minInicio.getText()));
		LocalDateTime fin = LocalDateTime.of(Integer.parseInt(this.anoFin.getText()), Integer.parseInt(this.mesFin.getText()), Integer.parseInt(this.diaFin.getText()), Integer.parseInt(this.horaFin.getText()), Integer.parseInt(this.minFin.getText()));
		actividad.setInicio(inicio);
		actividad.setFin(fin);
		actividad.setDuracion();
		if(inicio.isAfter(fin)) {
			error = "La actividad no puede terminar antes de empezar\n";
		}
		
		return error;
	}

	/**
	 * Comprueba si alguno de los campos introducidos esta vacio
	 * @param campos
	 * Campos de texto en los que se introducen los datos de la actividad
	 * @return
	 * True si algun campo de los introducidos esta vacio
	 */
	private boolean hayCamposVacios(JFXTextField...campos) {
		for(JFXTextField campo: campos) {
			if(campo.getText().isEmpty()) {
				return true;
			}
		}
		
		return false;
	}

	/**
	 * Mejora el formato de los campos de texto para mostrarlos por la interfaz grafica
	 * @param campos
	 * Campos de texto a los que hay que aplicar el formato
	 */
	private void darFormatoVista(JFXTextField...campos) {
		
		for(JFXTextField campo: campos) {
			if(campo.getText().length() == 1) {
				campo.setText("0"+campo.getText());
			}
		}	

	}
}
