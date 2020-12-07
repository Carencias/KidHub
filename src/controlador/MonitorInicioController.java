package controlador;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

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

}
