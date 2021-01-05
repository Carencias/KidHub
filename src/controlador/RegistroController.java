package controlador;

import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import modelo.KidHubException;
import modelo.Logica;
import modelo.vo.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.apache.log4j.Logger;

/**
 * Clase controladora de la ventana de registro
 * @version 1.0
 */
public class RegistroController extends Controller{

    @FXML
    private JFXTextField nombre, dni, apellidos, contra2, usuario, email, telefono, contra1, ano, mes, dia;

    @FXML
    private JFXRadioButton padre, monitor;

    @FXML
    private ToggleGroup grupo1;

    @FXML
    private Button confirmar, cancelar;
    
    static Logger logger = Logger.getLogger(RegistroController.class);
    
    /**
     * Metodo que cancela el registro y muestra la ventana de login
     * @param event
     *  Pulsado boton cancelar
     */
    @FXML
    void cancelar(MouseEvent event) {
    	logger.trace("Registro cancelado");
		this.cerrarVentana(event);
		this.mostrarVentana("Login");
    }

    /**
     * Metodo que registra al usuario
     * @param event
     *  Pulsado boton registrar
     */
    @FXML
    void registrar(MouseEvent event) {
    	logger.trace("Pulsado boton de registrar");
		PadreVO padrevo = new PadreVO();
		MonitorVO monitorvo = new MonitorVO();
		if(!contra1.getText().equals(contra2.getText())) {
			logger.error("Las contrasenas no coinciden");
			muestraError("ERROR","Se produjo un error.", "Las contrasenas no coinciden.");
		}else {
			try {
				if(padre.isSelected()) {
					registrarPadre(event, padrevo);
				}else{
					registrarMonitor(event, monitorvo);
				}
			}catch(Exception e) {
				if(e instanceof KidHubException) {
					logger.error(e.getMessage());
					muestraError("ERROR","Se produjo un error.", e.getMessage());
				}else if(e instanceof SQLException && ((SQLException) e).getErrorCode() == 1062) {
					logger.error("El usuario ya existe");
					muestraError("ERROR","Se produjo un error.", "El usuario ya existe.");
				}else if(e instanceof DateTimeParseException) {
					logger.error("El formato de la fecha no es correcto");
					muestraError("ERROR","Se produjo un error.", "El formato de la fecha no es correcto.");
				}else if(e instanceof SQLException){
					logger.error(((SQLException) e).getErrorCode());
					muestraError("ERROR","Se produjo un error.", "Formato de algun campo introducido invalido");
				}
			}	
		}	
    }
    
    private void registrarPadre(MouseEvent event, PadreVO padrevo) throws KidHubException, SQLException {
		Logica logica = Logica.getLogica();
    	logger.trace("Opcion de registrar padre seleccionada");
		rellenarVO(padrevo);
		//TODO queda poner el telefono
		logica.registrarUsuario(padrevo);
		logica.setUsuarioActual(padrevo);
		this.mostrarVentana("PadreInicio");
		this.cerrarVentana(event);

    }
    
    private void registrarMonitor(MouseEvent event, MonitorVO monitorvo) throws KidHubException, SQLException {
		Logica logica = Logica.getLogica();
		logger.trace("Opcion de registrar monitor seleccionada");
		rellenarVO(monitorvo);
		//TODO queda poner las especialidades
		logica.registrarUsuario(monitorvo);
		logica.setUsuarioActual(monitorvo);
		this.mostrarVentana("MonitorInicio");
		this.cerrarVentana(event);

    }
    
    /**
     * Metodo privado que rellena un UsuarioVO con los datos introducidos
     * @param usuariovo
     *  UsuarioVO donde se guardaran los datos
     */
    private void rellenarVO(UsuarioVO usuariovo) {
    	logger.trace("Rellenando usuario con los datos introducidos");
    	usuariovo.setNombre(nombre.getText());
    	usuariovo.setApellidos(apellidos.getText());
    	usuariovo.setNombreUsuario(usuario.getText());
    	usuariovo.setContrasena(contra1.getText());
    	usuariovo.setDni(dni.getText());
    	usuariovo.setEmail(email.getText());
		if(dia.getText().length() == 1) {
			dia.setText("0"+dia.getText());
		}
		if(mes.getText().length() == 1) {
			mes.setText("0"+mes.getText());
		}
		String fechaString = dia.getText()+"/"+mes.getText()+"/"+ano.getText();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate fecha = LocalDate.parse(fechaString, formatter);
		usuariovo.setFechaNacimiento(fechaString);
		LocalDate ahora = LocalDate.now();
		Period periodo = Period.between(fecha, ahora);
		usuariovo.setEdad(periodo.getYears());
    }
}
