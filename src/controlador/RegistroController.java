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
import modelo.vo.UsuarioVO.TipoUsuario;

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
		
    	UsuarioVO usuarioVO;
    	
		if(!contra1.getText().equals(contra2.getText())) {
			logger.error("Las contrasenas no coinciden");
			muestraError("ERROR","Se produjo un error.", "Las contrasenas no coinciden.");
		}else if(esUsuarioReservado(usuario.getText())) {
			logger.error("El nombre de usuario elegido esta reservado");
			muestraError("ERROR","Se produjo un error.", "El nombre de usuario elegido esta reservado.");
    	}else {
			try {
				if(padre.isSelected()) {
					usuarioVO = new PadreVO();
					usuarioVO.setTipo(TipoUsuario.PADRE);
					//TODO set telefono
				}else{
					usuarioVO = new MonitorVO();
					usuarioVO.setTipo(TipoUsuario.MONITOR);
					//TODO set especialidad
				}
				
				registrarUsuario(event, usuarioVO);
				this.mostrarVentana(usuarioVO.getTextoTipo() + "Inicio");
				this.cerrarVentana(event);
				
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
    
    /**
     * Comprueba si el nombre de usuario introducido esta reservado
     * @return
     * True si el nombre de usuario esta reservado
     */
    private boolean esUsuarioReservado(String usuario) {
    	return (usuario.toLowerCase().equals("propios") || usuario.toLowerCase().equals("propios"));
    }
    
    /**
     * Conecta con la logica de la aplicacion para registrar al usuario en la BBDD
     * @param event
     * Evento que dispara el registro
     * @param usuario
     * Contenedor donde se van a almacenar los datos introducidos
     * @throws KidHubException
     * @throws SQLException
     */
    private void registrarUsuario(MouseEvent event, UsuarioVO usuario) throws KidHubException, SQLException {
		Logica logica = Logica.getLogica();
    	logger.trace("Opcion de registrar padre seleccionada");
		rellenarVO(usuario);
		logica.registrarUsuario(usuario);
		logica.setUsuarioActual(usuario);
    }
    
    /**
     * Rellena un UsuarioVO con los datos introducidos
     * @param usuariovo
     * Contenedor donde se guardaran los datos
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
