package controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Popup;
import javafx.stage.Stage;
import modelo.Logica;

public class Controller {
	
	protected Logica logica;
	
	protected void cerrarVentana(MouseEvent event) {
		Node source = (Node) event.getSource();
	    Stage stage = (Stage) source.getScene().getWindow();
	    stage.close();
	}
	
	protected void muestraError(String titulo, String encabezado, String contenido) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(titulo);
		alert.setHeaderText(encabezado);
		alert.setContentText(contenido);
		alert.showAndWait();
	}
	
    protected void mostrarVentana(String ventana) {
		AnchorPane root;
		try {
			root = (AnchorPane) FXMLLoader.load(getClass().getResource("../vista/" + ventana + ".fxml"));
			//scene.getStylesheets().add(getClass().getResource("vista/application.css").toExternalForm());
			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.setResizable(false);
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @FXML
    protected void logOut(MouseEvent event) {
    	System.out.println("Cerrando la sesion y volviendo al login.");

    	this.mostrarVentana("Login");
    	
    	this.cerrarVentana(event);
    }
}
