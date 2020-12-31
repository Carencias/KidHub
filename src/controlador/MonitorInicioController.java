package controlador;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MonitorInicioController extends Controller{

    @FXML
    private Button cerrar;
    
    @FXML
    private JFXButton inicio, actividades, cerrarSesion;
    
    @FXML
    private Pane panoInicio, panoActividades, panoCerrar;

    public void elegirPanel(ActionEvent actionEvent) {
    	System.out.println("Eligiendo actividades");
    	
        if (actionEvent.getSource() == inicio) {
        	System.out.println("Trayendo inicio al frente");        	
        	panoInicio.setVisible(true);
        	panoCerrar.setVisible(false);
        	panoActividades.setVisible(false);
        } else if (actionEvent.getSource() == actividades) {
        	System.out.println("Trayendo actividades al frente");
        	panoActividades.setVisible(true);       	
        	panoCerrar.setVisible(false);
        	panoInicio.setVisible(false);
        }else if (actionEvent.getSource() == cerrarSesion) {
        	System.out.println("Trayendo cerrar al frente");
        	panoCerrar.setVisible(true);  
        	panoInicio.setVisible(false);
        	panoActividades.setVisible(false);
        }
    }
    
    
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
    void cerrarSesion(MouseEvent event) {
    	this.logOut(event);
    }
    
}
