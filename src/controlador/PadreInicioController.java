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
    private Button cerrar, registrar, registrarExistente;
	
	private JFXTextField nombre, nombreExistente, contraExistente, dni, apellidos, contra2, usuario, email, telefono, contra1, ano, mes, dia;
    
    @FXML
    private JFXButton inicio, actividades, actividadesHijo, trayectos, trayectosHijo, registrarHijo, cerrarSesion, apuntarActividad, desapuntarActividad, apuntarTrayecto, desapuntarTrayecto;
    
    @FXML
    private Pane panoInicio, panoActividades, panoTrayectos, panoRegistrarHijo, panoCerrar;
    
    @FXML
    private Label nombreLabel;
	
    
    @FXML
    public void initialize() {
    	//TODO pedir el nombre a la base de datos
    	nombreLabel.setText("Nombre del padre"); 
    }
    
    
    @FXML
    public void elegirPanel(ActionEvent actionEvent) {
    	
    	if (actionEvent.getSource() == inicio) {      	
        	panoInicio.setVisible(true);
        	panoCerrar.setVisible(false);
        	panoActividades.setVisible(false);
        	panoTrayectos.setVisible(false);
    		panoRegistrarHijo.setVisible(false); 
        	
        } else if (actionEvent.getSource() == actividades) {
        	panoActividades.setVisible(true);       	
        	panoCerrar.setVisible(false);
        	panoInicio.setVisible(false);
        	panoTrayectos.setVisible(false);
    		panoRegistrarHijo.setVisible(false); 
        	
        }else if (actionEvent.getSource() == trayectos) {
        	panoTrayectos.setVisible(true);
        	panoCerrar.setVisible(false);  
        	panoInicio.setVisible(false);
        	panoActividades.setVisible(false);
    		panoRegistrarHijo.setVisible(false); 
    	
    	}else if(actionEvent.getSource() == registrarHijo) {
    		panoRegistrarHijo.setVisible(true);
    		panoTrayectos.setVisible(false);
        	panoCerrar.setVisible(false);  
        	panoInicio.setVisible(false);
        	panoActividades.setVisible(false);
        
    	}else if (actionEvent.getSource() == cerrarSesion) {
        	panoCerrar.setVisible(true);  
        	panoInicio.setVisible(false);
        	panoActividades.setVisible(false);
        	panoTrayectos.setVisible(false);
    		panoRegistrarHijo.setVisible(false); 
        }
    }
	
    @FXML
    public void registrar(MouseEvent event) {
    	System.out.println("Registrando hijo");
    }
    
    @FXML
    public void agregarHijo(MouseEvent event) {
    	System.out.println("Agregando hijo existente");
    }
    
    @FXML
    public void crearTrayecto(MouseEvent event) {
    	System.out.println("Creando trayecto");
    }
    
    @FXML
    public void modificarTrayecto(MouseEvent event) {
    	System.out.println("Modificando trayecto");
    }
    
    @FXML
    public void borrarTrayecto(MouseEvent event) {
    	System.out.println("Borrando trayecto");
    }
    
    @FXML
    public void apuntarHijoActividad(MouseEvent event) {
    	System.out.println("Apuntando hijo a actividad");
    }
    
    @FXML
    public void desapuntarHijoActividad(MouseEvent event) {
    	System.out.println("Desapuntando hijo de actividad");
    }
    
    @FXML
    public void apuntarHijoTrayecto(MouseEvent event) {
    	System.out.println("Apuntando hijo a trayecto");
    }
    
    @FXML
    public void desapuntarHijoTrayecto(MouseEvent event) {
    	System.out.println("Desapuntando hijo de trayecto");
    }
    
    @FXML
    //??? Tiene que llamar a consultarActividades del hijo?
    public void consultarActividadesHijo(MouseEvent event) {
    	System.out.println("Mostrando actividades hijo");
    }
    
    @FXML
    public void consultarTrayectos(MouseEvent event) {
    	System.out.println("Mostrando trayectos");
    }
    
    @FXML
    public void consultarTrayectosHijo(MouseEvent event) {
    	System.out.println("Mostrando trayectos hijo");
    }
}