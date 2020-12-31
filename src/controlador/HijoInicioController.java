package controlador;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class HijoInicioController extends Controller{

	@FXML
    private Button cerrar;
    
    @FXML
    private JFXButton inicio, actividades, trayectos, cerrarSesion, crear;
    
    @FXML
    private Pane panoInicio, panoActividades, panoCerrar;
    
    @FXML
    private Label nombre;

    @FXML
    public void mostrarActividades(MouseEvent event) {
    	System.out.println("Mostrando actividades");
    }
    
    @FXML
    public void mostrarTrayectos(MouseEvent event) {
    	System.out.println("Mostrando trayectos.");
    }
   
    
   @FXML
   public void cerrarSesion(MouseEvent event) {
	   this.logOut(event);
   }
   
    
}
