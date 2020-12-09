package controlador;

import java.io.IOException;

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
   
   
    
}
