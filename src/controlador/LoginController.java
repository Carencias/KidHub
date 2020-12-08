package controlador;

import javafx.event.ActionEvent;
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

public class LoginController {
	
	@FXML
	private TextField textoUsuario;
	
	@FXML 
	private TextField textoContrasena;
	
	@FXML
    private Button logOut;
	
	static final int MONITOR = 0; 
	
	static final int PADRE = 1;
	
	static final int HIJO = 2;
	
	
	public void login(MouseEvent event) throws KidHubException{
		
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
		}else {
			//TODO hacen falta excepciones?? en que paquete se meten las excepciones??? modelo??
			//throw new KidHubException("Las credenciales no corresponden con ningun usuario registrado.");
			//TODO refactorizar esto en un metodo??
			System.out.println();
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR");
			alert.setHeaderText("Se produjo un error.");
			alert.setContentText("Credenciales invalidas.");
			alert.showAndWait();
		}
	}
	
	private int comprobarCredencialesOK(String usuario, String contrasena) {
		// TODO consultar el tipo de usuario con la base de datos
		return MONITOR;
	}

	public void registrar() {
		//TODO lanzar la ventana de registro
	}
	
	@FXML
    void logOut(MouseEvent event) {
		
	}
	
	private void cerrarVentana(MouseEvent event) {
		Node source = (Node) event.getSource();
	    Stage stage = (Stage) source.getScene().getWindow();
	    stage.close();
	}
}
