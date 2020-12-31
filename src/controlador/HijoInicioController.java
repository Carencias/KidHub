package controlador;


import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;

public class HijoInicioController extends Controller{

	@FXML
    private Button cerrar;
    
    @FXML
    private JFXButton inicio, actividades, trayectos, cerrarSesion, crear;
    
    @FXML
    private Pane panoInicio, panoActividades, panoTrayectos, panoCerrar;
    
    @FXML
    private Label nombre;
    
    
    @FXML
    public void elegirPanel(ActionEvent actionEvent) {
    	
        if (actionEvent.getSource() == inicio) {      	
        	panoInicio.setVisible(true);
        	panoCerrar.setVisible(false);
        	panoActividades.setVisible(false);
        	panoTrayectos.setVisible(false);
        	
        } else if (actionEvent.getSource() == actividades) {
        	panoActividades.setVisible(true);       	
        	panoCerrar.setVisible(false);
        	panoInicio.setVisible(false);
        	panoTrayectos.setVisible(false);
        	
        }else if (actionEvent.getSource() == trayectos) {
        	panoTrayectos.setVisible(true);
        	panoCerrar.setVisible(false);  
        	panoInicio.setVisible(false);
        	panoActividades.setVisible(false);
    	
    	}else if (actionEvent.getSource() == cerrarSesion) {
        	panoCerrar.setVisible(true);  
        	panoInicio.setVisible(false);
        	panoActividades.setVisible(false);
        	panoTrayectos.setVisible(false);
        }
    } 

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
