package controlador;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MonitorInicioController {

    @FXML
    private Button crear;

    @FXML
    private Button modificar;

    @FXML
    private Button borrar;

    @FXML
    void borrarActividad(MouseEvent event) {
    	System.out.println("Borrando actividad");
    }

    @FXML
    void crearActividad(MouseEvent event) {
    	System.out.println("Creando actividad");
    }

    @FXML
    void modificarActividad(MouseEvent event) {
    	System.out.println("Modificando actividad");
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
    
    private void cerrarVentana(MouseEvent event) {
		Node source = (Node) event.getSource();
	    Stage stage = (Stage) source.getScene().getWindow();
	    stage.close();
	}
}
