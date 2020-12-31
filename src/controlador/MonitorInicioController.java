package controlador;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class MonitorInicioController extends Controller{

    @FXML
    private Button cerrar;
    
    @FXML
    private JFXButton inicio, actividades, cerrarSesion, crear;
    
    @FXML
    private Pane panoInicio, panoActividades, panoCerrar;
    
    @FXML
    private Label nombre;
    
    
    
    @FXML
    public void initialize() {

    	//TODO pedir el nombre a la base de datos
    	nombre.setText("Nombre del monitor");    	
    }



    public void elegirPanel(ActionEvent actionEvent) {

        if (actionEvent.getSource() == inicio) {      	
        	panoInicio.setVisible(true);
        	panoCerrar.setVisible(false);
        	panoActividades.setVisible(false);
        	
        } else if (actionEvent.getSource() == actividades) {
        	panoActividades.setVisible(true);       	
        	panoCerrar.setVisible(false);
        	panoInicio.setVisible(false);
        	
        }else if (actionEvent.getSource() == cerrarSesion) {
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
