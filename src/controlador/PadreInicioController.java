package controlador;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class PadreInicioController extends Controller{

	
	
	
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
