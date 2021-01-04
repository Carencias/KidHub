package controlador;

import java.util.ArrayList;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import modelo.vo.ActividadVO;
import modelo.vo.TrayectoVO;

public class TrayectoController extends Controller{
	   @FXML
	    private JFXTextField dia;

	    @FXML
	    private JFXTextField mes;

	    @FXML
	    private JFXTextField ano;

	    @FXML
	    private JFXRadioButton ida;

	    @FXML
	    private ToggleGroup grupo1;

	    @FXML
	    private JFXRadioButton vuelta;

	    @FXML
	    private JFXTextField aforo;

	    @FXML
	    private JFXTextField codPostal;

	    @FXML
	    private JFXTextField ciudad;

	    @FXML
	    private JFXTextField calle;

	    @FXML
	    private JFXTextField num;

	    @FXML
	    private Button confirmar;

	    @FXML
	    private Button cancelar;

	    @FXML
	    private JFXComboBox<String> actividad;
	    
	    private Stage stage;
	    
	    private TrayectoVO trayecto;
	    
	    private boolean modificacion;
	    
	    
	    void initData(ArrayList<ActividadVO> actividades, TrayectoVO trayecto, Stage stage, boolean modificacion) {

	    	this.trayecto = trayecto;
	    	this.modificacion = modificacion;
	    	this.stage = stage;	    	
	    	
	    	if(modificacion) {
	    		this.setDatosTrayecto();
	    		this.setDatosCombo(actividades);
	    	}
	    }
	    
	    private void setDatosTrayecto() {
	    	//TODO
	    }
	    
	    
	    private void setDatosCombo(ArrayList<ActividadVO> actividades) {
	    	//TODO
	    }
	    
	    private void getDatosTrayecto() {
	    	//TODO
	    }
	    

	    @FXML
	    void cancelar(MouseEvent event) {
	    	System.out.println("Cancelando");
	    	this.recuperarVentana(stage);
	    	this.cerrarVentana(event);
	    }

	    @FXML
	    void confirmar(MouseEvent event) {
	    	System.out.println("Confirmando");
	    	//TODO	    
	    }
}
