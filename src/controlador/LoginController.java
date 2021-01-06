package controlador;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import modelo.KidHubException;
import modelo.Logica;
import modelo.vo.UsuarioVO;
import modelo.vo.UsuarioVO.TipoUsuario;

/**
 * Clase controladora de la ventana de login
 * @version 1.0
 */
public class LoginController extends Controller{
	
	@FXML
	private JFXTextField textoUsuario, contrasenaVisible;
	
	@FXML
	private JFXPasswordField textoContrasena;
	
	@FXML
	private JFXCheckBox mostrar;
	
	@FXML
    private Button logOut;
	
	static Logger logger = Logger.getLogger(LoginController.class);
	
	/**
	 * Oculta los caracteres de la contrasena o los muestra.
	 */
	@FXML
	public void cambiarCaracteres() {
		if(this.mostrar.isSelected()) {	
			mostrarContrasena();
		}else {
			ocultarContrasena();
		}
	}
	
	/**
	 * Muestra los caracteres introducidos en el campo de la contrasena
	 */
	private void mostrarContrasena() {
		logger.trace("Mostrando contrasena");
		this.contrasenaVisible.setText(this.textoContrasena.getText());
		this.textoContrasena.setVisible(false);
		this.contrasenaVisible.setVisible(true);
	}
	
	/**
	 * Oculta los caracteres introducidos en el campo de la contrasena
	 */
	private void ocultarContrasena() {
		logger.trace("Ocultando contrasena");
		this.textoContrasena.setText(this.contrasenaVisible.getText());
		this.textoContrasena.setVisible(true);
		this.contrasenaVisible.setVisible(false);
	}

	/**
	 * Metodo que escucha el boton de login y realiza las acciones necesarias para intentar loguear al usuario
	 * @param event
	 *  Pulsado boton loguear
	 */
	@FXML
	public void login(MouseEvent event){
		logger.info("Intentando loguear usuario");
		String nombreUsuario, contrasena;
		TipoUsuario tipoUsuario;
		
		nombreUsuario = textoUsuario.getText();
		if(contrasenaVisible.isVisible()) {
			contrasena = contrasenaVisible.getText();
		}else {
			contrasena = textoContrasena.getText();
		}
		logger.trace("Comprobando usuario:" + nombreUsuario + " " + contrasena);
				
		UsuarioVO usuario = new UsuarioVO();
		usuario.setNombreUsuario(nombreUsuario);
		usuario.setContrasena(contrasena);
		
		try {
			tipoUsuario = Logica.getLogica().loguearUsuario(usuario);
			
			this.mostrarVentana(elegirVentana(tipoUsuario));
			this.cerrarVentana(event);
		} catch (KidHubException e) {
			logger.warn(e.getMessage());
			muestraError("ERROR","Se produjo un error.", e.getMessage());
		} catch (SQLException e) {
			logger.fatal("Error en la base de datos: "+e.getMessage());
			muestraError("ERROR","Se produjo un error.", "Error en la base de datos");
		}
	}
	
	/**
	 * Metodo que escucha el boton de registrar y cambia a la ventana de registro
	 * @param event
	 * 	Pulsado boton registrar
	 */
	@FXML
	public void registrar(MouseEvent event) {
		logger.trace("Cambiando a vista de registro");
		this.mostrarVentana("Registro");
		this.cerrarVentana(event);
	}
	
	/**
	 * Metodo privado que devuelve el texto que identifica a cada tipo de usuario
	 * @param tipoUsuario
	 *  Tipo de usuario
	 * @return
	 *  exto que identifica a cada tipo de usuario
	 */
	private String elegirVentana(TipoUsuario tipoUsuario) {
		String salida;
		switch (tipoUsuario) {
			case MONITOR: 
				salida="MonitorInicio";
			break;
			case PADRE:
				salida="PadreInicio";
			break;
			default:
				salida="HijoInicio";
		}
		logger.trace("Devolviedno tipo de usuario");
		return salida;
	}
}
