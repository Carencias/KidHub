package controlador;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.*;

public class HijoInicioController {

    @FXML
    private Button actividades;

    @FXML
    public void mostrarActividades(MouseEvent event) {
    	System.out.println("Mostrando actividades");
    }
    
    @FXML
    void mostrarTrayectos(MouseEvent event) {
    	System.out.println("Mostrando trayectos ");
    }
}
