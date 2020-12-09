package controlador;

import java.util.ArrayList;

import javafx.fxml.*;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class RegistroController extends Controller{
	@FXML
	TextField nombre, apellidos, dni, email, telefono, usuario, contra1, contra2;
	
	@FXML
	DatePicker nacimiento;
	
	@FXML
	RadioButton monitor, padre;

	@FXML
	Button confirmar, cancelar;
	
	public void registrar() {
		System.out.println("Registrando");
		//TODO crear una tabla en la BBDD con los datos del nuevo usuario
		
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

	public void cancelar(MouseEvent event) {
		System.out.println("Cancelando");
		this.cerrarVentana(event);
	}
	
	
}
