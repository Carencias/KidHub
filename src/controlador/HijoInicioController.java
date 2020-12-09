package controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class HijoInicioController extends Controller{

    @FXML
    private Button actividades;

    @FXML
    public void mostrarActividades(MouseEvent event) {
    	System.out.println("Mostrando actividades");
    }
    
    @FXML
    public void mostrarTrayectos(MouseEvent event) {
    	System.out.println("Mostrando trayectos.");
    }
    
    @FXML
    public  void logOut(MouseEvent event) {
    	System.out.println("Cerrando la sesion y volviendo al login.");
    	try {
    		BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("../vista/Login.fxml"));
			Scene scene = new Scene(root,600,400);
			//scene.getStylesheets().add(getClass().getResource("vista/application.css").toExternalForm());
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setResizable(false);
			stage.show();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	this.cerrarVentana(event);
    }
    
}
