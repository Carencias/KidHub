package controlador;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MonitorInicioController extends Controller{

    @FXML
    private Button crear, modificar, borrar;


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
    
}
