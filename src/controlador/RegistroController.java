package controlador;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;

public class RegistroController extends Controller{

    @FXML
    private JFXTextField nombre, dni, apellidos, contra2, usuario, email, telefono, contra1, ano, mes, dia;

    @FXML
    private JFXRadioButton padre, monitor;

    @FXML
    private ToggleGroup grupo1;

    @FXML
    private Button confirmar, cancelar;
        
    @FXML
    void cancelar(MouseEvent event) {
		System.out.println("Cancelando");
		this.cerrarVentana(event);
		this.mostrarVentana("Login");
    }

    
    
    @FXML
    void registrar(MouseEvent event) {
		System.out.println("Registrando");
		
		String errores[] = new String[1];
		
		if(!comprobarDatosCorrectos(errores)) {
			this.muestraError("ERROR", "Se produjo un error", errores[0]);
		}else {
			if(monitor.isSelected()) {
				System.out.println("Es monitor");
				this.mostrarVentana("MonitorInicio");			
			} else {
				System.out.println("Es padre");
				this.mostrarVentana("PadreInicio");
			}
			
			this.cerrarVentana(event);
		}
    }
	
    
	private boolean comprobarDatosCorrectos(String errores[]) {
		System.out.println("COmprobando datos");
		if(!contra1.getText().equals(contra2.getText())) {
			System.out.println("Son distintas");
			errores[0] = "Las contrasenas no son iguales";
			return false;
		}
		
		// TODO Mas comprobaciones
		
		return true;
	}

}
