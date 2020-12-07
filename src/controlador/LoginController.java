package controlador;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class LoginController {
	
	@FXML
	private TextField textoUsuario;
	
	@FXML 
	private TextField textoContrasena;
	
	
	public void login() {
		
		String usuario, contrasena;
		int tipoUsuario;
		
		usuario = textoUsuario.getText();
		contrasena = textoContrasena.getText();
		
		System.out.println("Comprobando usuario:" + usuario + " " + contrasena);
		
		tipoUsuario = comprobarCredencialesOK(usuario, contrasena);
		
		if(tipoUsuario != -1) {
			//loginOK
			try {
				
				BorderPane ventana = null;
				
				switch (tipoUsuario) {
					case 0: { //Es un monitor
						ventana = (BorderPane)FXMLLoader.load(getClass().getResource("../vista/MonitorInicio.fxml"));
					}
					case 1: { //Es un padre
						ventana = (BorderPane)FXMLLoader.load(getClass().getResource("../vista/PadreInicio.fxml"));
					}
					case 2: { //Es un hijo
						ventana = (BorderPane)FXMLLoader.load(getClass().getResource("../vista/HijoInicio.fxml"));
					}
				}
				
				Scene scene = new Scene(ventana,600,400);
				Stage stage = new Stage();
				stage.setScene(scene);
				stage.setResizable(false);
				stage.show();
				
			}catch(Exception e) {
				System.err.println("Error al abrir la ventana de usuario");
				e.printStackTrace();
			}
		}else {
			//throw new WrongPassWordException;
			System.err.println("Las credenciales no corresponden con ningun usuario registrado.");
		}
	}
	
	private int comprobarCredencialesOK(String usuario, String contrasena) {
		// TODO Auto-generated method stub
		return 2;
	}

	public void registrar() {
		
	}
}
