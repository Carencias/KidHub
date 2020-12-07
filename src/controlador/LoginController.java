package controlador;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class LoginController {
	
	@FXML
	private TextField textoUsuario;
	
	@FXML 
	private TextField textoContrasena;
	
	
	public void login() {
		
		String usuario, contrasena;
		
		usuario = textoUsuario.getText();
		contrasena = textoContrasena.getText();
		
		if(comprobarCredencialesOK(usuario, contrasena)) {
			//loginOK
		}else {
			//throw new WrongPassWordException;
		}
		
		
		System.out.println("Comprobando usuario:" + usuario + contrasena);
	}
	
	private boolean comprobarCredencialesOK(String usuario, String contrasena) {
		// TODO Auto-generated method stub
		return false;
	}

	public void registrar() {
		
	}
}
