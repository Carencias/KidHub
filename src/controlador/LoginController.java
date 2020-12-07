package controlador;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import modelo.InvalidLoginException;

public class LoginController {
	
	@FXML
	private TextField textoUsuario;
	
	@FXML 
	private TextField textoContrasena;
	
	static final int MONITOR = 0; 
	
	static final int PADRE = 1;
	
	static final int HIJO = 2;
	
	
	public void login() throws InvalidLoginException{
		
		String usuario, contrasena;
		int tipoUsuario;
		
		usuario = textoUsuario.getText();
		contrasena = textoContrasena.getText();
		
		System.out.println("Comprobando usuario:" + usuario + " " + contrasena);
		
		tipoUsuario = comprobarCredencialesOK(usuario, contrasena);
		
		if(tipoUsuario != -1) {
			//login correcto
			
			try {		
				
				BorderPane ventana = null;
				System.out.println(tipoUsuario);
				
				switch (tipoUsuario) {
				
					case MONITOR: {
						System.out.println("Monitor loggeado");
						ventana = (BorderPane)FXMLLoader.load(getClass().getResource("../vista/MonitorInicio.fxml"));
						break;
					}
					
					case PADRE: {
						System.out.println("Padre loggeado");
						ventana = (BorderPane)FXMLLoader.load(getClass().getResource("../vista/PadreInicio.fxml"));
						break;
					}
					
					case HIJO: {
						System.out.println("Hijo loggeado");
						ventana = (BorderPane)FXMLLoader.load(getClass().getResource("../vista/HijoInicio.fxml"));
						break;
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
			//throw new WrongPassWordException, yo (bayon) diria que la excepcion fuera mejor algo como credenciales invalidas
			//porque puede que no valga el login porque no exista el user, no porque este mal la passwd
			//TODO en que paquete se meten las excepciones??? modelo??
			//Por algun motivo esta excpecion no funciona no se que pasa jeje
			System.out.println("He entrado en el else");
			System.err.println("Las credenciales no corresponden con ningun usuario registrado.");
			//throw new InvalidLoginException("Las credenciales no corresponden con ningun usuario registrado.");
		}
	}
	
	private int comprobarCredencialesOK(String usuario, String contrasena) {
		// TODO consultar el tipo de usuario con la base de datos
		return -1;
	}

	public void registrar() {
		//TODO lanzar la ventana de registro
	}
}
