package controlador;

import java.io.IOException;

import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import modelo.KidHubException;


public class LoginController extends Controller{
	
	@FXML
	private JFXTextField textoUsuario, textoContrasena;
	
	@FXML
    private Button logOut;
	
	static final int MONITOR = 0; 
	
	static final int PADRE = 1;
	
	static final int HIJO = 2;
	
	static final int LOGIN_INCORRECTO = -1;
	
	
	public void login(MouseEvent event) throws KidHubException{
		
		String usuario, contrasena;
		int tipoUsuario;
		
		usuario = textoUsuario.getText();
		contrasena = textoContrasena.getText();
		
		System.out.println("Comprobando usuario:" + usuario + " " + contrasena);
		
		tipoUsuario = obtenerTipoUsuario(usuario, contrasena);
		
		if(tipoUsuario == LOGIN_INCORRECTO) {
			muestraError("ERROR","Se produjo un error.", "Credenciales inválidas.");
		}else {
			this.mostrarVentana(elegirVentana(tipoUsuario));
			this.cerrarVentana(event);
		}
	}
	
	
	private int obtenerTipoUsuario(String usuario, String contrasena) {
		if(comprobarCredencialesOK(usuario, contrasena)) {
			//TODO comprobar usuario con BBDD
			return HIJO;
		}else {
			return LOGIN_INCORRECTO;
		}
	}
	
	
	private boolean comprobarCredencialesOK(String usuario, String contrasena) {
		//TODO comprobar si usuario y contrasena son correctos
		return true;
	}
	

	public void registrar(MouseEvent event) {
		
		this.mostrarVentana("Registro");
		this.cerrarVentana(event);

	}
	
	
	String elegirVentana(int tipoUsuario) {
		switch (tipoUsuario) {
		
		case MONITOR: 
			return "MonitorInicio";
		
		case PADRE:
			return "PadreInicio";
		
		default:
			return "HijoInicio";
		}
	}
	
}
