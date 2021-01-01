package controlador;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class PadreInicioController extends Controller{

	@FXML
    private Button cerrar;
	
	private JFXTextField nombre, dni, apellidos, contra2, usuario, email, telefono, contra1, ano, mes, dia;
    
    @FXML
    private JFXButton inicio, actividades, actividadesHijo, trayectos, trayectosHijo, registrarHijo, cerrarSesion, crear;
    
    @FXML
    private Pane panoInicio, panoActividades, panoActividadesHijo, panoTrayectos, panoTrayectosHijo, panoRegistrarHijo, panoCerrar;
    
    @FXML
    private Label nombreLabel;
	
    
    
    @FXML
    public void elegirPanel(ActionEvent actionEvent) {
    	
    	if (actionEvent.getSource() == inicio) {      	
        	panoInicio.setVisible(true);
        	panoCerrar.setVisible(false);
        	panoActividades.setVisible(false);
        	panoTrayectos.setVisible(false);
        	panoTrayectosHijo.setVisible(false);
    		panoActividadesHijo.setVisible(false);
    		panoRegistrarHijo.setVisible(false); 
        	
        } else if (actionEvent.getSource() == actividades) {
        	panoActividades.setVisible(true);       	
        	panoCerrar.setVisible(false);
        	panoInicio.setVisible(false);
        	panoTrayectos.setVisible(false);
        	panoTrayectosHijo.setVisible(false);
    		panoActividadesHijo.setVisible(false);
    		panoRegistrarHijo.setVisible(false); 
        	
        }else if (actionEvent.getSource() == trayectos) {
        	panoTrayectos.setVisible(true);
        	panoCerrar.setVisible(false);  
        	panoInicio.setVisible(false);
        	panoActividades.setVisible(false);
        	panoTrayectosHijo.setVisible(false);
    		panoActividadesHijo.setVisible(false);
    		panoRegistrarHijo.setVisible(false); 
    	
    	}else if(actionEvent.getSource() == registrarHijo) {
    		panoRegistrarHijo.setVisible(true);
    		panoActividadesHijo.setVisible(false);
    		panoTrayectosHijo.setVisible(false);
    		panoTrayectos.setVisible(false);
        	panoCerrar.setVisible(false);  
        	panoInicio.setVisible(false);
        	panoActividades.setVisible(false);
    		
    		
    	}else if(actionEvent.getSource() == actividadesHijo) {
    		panoActividadesHijo.setVisible(true);
    		panoRegistrarHijo.setVisible(false);    		
    		panoTrayectosHijo.setVisible(false);
    		panoTrayectos.setVisible(false);
        	panoCerrar.setVisible(false);  
        	panoInicio.setVisible(false);
        	panoActividades.setVisible(false);
    		
    		
    	}else if(actionEvent.getSource() == trayectosHijo) {
    		panoTrayectosHijo.setVisible(true);
    		panoActividadesHijo.setVisible(false);
    		panoRegistrarHijo.setVisible(false);    		    		
    		panoTrayectos.setVisible(false);
        	panoCerrar.setVisible(false);  
        	panoInicio.setVisible(false);
        	panoActividades.setVisible(false);
        
    	}else if (actionEvent.getSource() == cerrarSesion) {
        	panoCerrar.setVisible(true);  
        	panoInicio.setVisible(false);
        	panoActividades.setVisible(false);
        	panoTrayectos.setVisible(false);
        	panoTrayectosHijo.setVisible(false);
    		panoActividadesHijo.setVisible(false);
    		panoRegistrarHijo.setVisible(false); 
        }
    }
	
    @FXML
    public void registrarHijo(MouseEvent event) {
    	System.out.println("Mostrando actividades");
    }
    
    @FXML
    public void agregarHijo(MouseEvent event) {
    	System.out.println("Mostrando actividades");
    }
    
    @FXML
    public void crearTrayecto(MouseEvent event) {
    	System.out.println("Mostrando actividades");
    }
    
    @FXML
    public void modificarTrayecto(MouseEvent event) {
    	System.out.println("Mostrando actividades");
    }
    
    @FXML
    public void borrarTrayecto(MouseEvent event) {
    	System.out.println("Mostrando actividades");
    }
    
    @FXML
    public void apuntarHijoActividad(MouseEvent event) {
    	System.out.println("Mostrando actividades");
    }
    
    @FXML
    public void desapuntarHijoActividad(MouseEvent event) {
    	System.out.println("Mostrando actividades");
    }
    
    @FXML
    public void apuntarHijoTrayecto(MouseEvent event) {
    	System.out.println("Mostrando actividades");
    }
    
    @FXML
    public void desapuntarHijoTrayecto(MouseEvent event) {
    	System.out.println("Mostrando actividades");
    }
    
    @FXML
    //??? Tiene que llamar a consultarActividades del hijo?
    public void consultarActividadesHijo(MouseEvent event) {
    	System.out.println("Mostrando actividades");
    }
    
    @FXML
    public void consultarTrayectos(MouseEvent event) {
    	System.out.println("Mostrando actividades");
    }
    
    @FXML
    public void consultarTrayectosHijo(MouseEvent event) {
    	System.out.println("Mostrando actividades");
    }
}