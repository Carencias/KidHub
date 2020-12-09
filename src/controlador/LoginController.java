package controlador;

import java.io.IOException;

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
	private TextField textoUsuario;
	
	@FXML 
	private TextField textoContrasena;
	
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
		
		tipoUsuario = comprobarCredencialesOK(usuario, contrasena);
		
		if(tipoUsuario == LOGIN_INCORRECTO) {
			//TODO hacen falta excepciones?? en que paquete se meten las excepciones??? modelo??
			//throw new KidHubException("Las credenciales no corresponden con ningun usuario registrado.");
			//TODO refactorizar esto en un metodo??
			System.out.println();
			muestraError("ERROR","Se produjo un error.", "Credenciales inválidas.");
			
		}else {
			
			try {		
				
				BorderPane ventana = null;
				System.out.println(tipoUsuario);
				
				switch (tipoUsuario) {
				
					case MONITOR: {
						System.out.println("Monitor loggeado");
						ventana = (BorderPane)FXMLLoader.load(getClass().getResource("../vista/MonitorInicio.fxml"));
						this.cerrarVentana(event);
						break;
					}
					
					case PADRE: {
						System.out.println("Padre loggeado");
						ventana = (BorderPane)FXMLLoader.load(getClass().getResource("../vista/PadreInicio.fxml"));
						this.cerrarVentana(event);
						break;
					}
					
					case HIJO: {
						System.out.println("Hijo loggeado");
						ventana = (BorderPane)FXMLLoader.load(getClass().getResource("../vista/HijoInicio.fxml"));
						this.cerrarVentana(event);
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
		}
	}
	
	private int comprobarCredencialesOK(String usuario, String contrasena) {
		// TODO consultar el tipo de usuario con la base de datos
		return LOGIN_INCORRECTO;
	}

	public void registrar(MouseEvent event) {
		
		try {
			BorderPane ventana = (BorderPane)FXMLLoader.load(getClass().getResource("../vista/Registro.fxml"));
			this.cerrarVentana(event);
			Scene scene = new Scene(ventana,600,400);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setResizable(false); 
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	@FXML
    void logOut(MouseEvent event) {
		
	}
	
}
