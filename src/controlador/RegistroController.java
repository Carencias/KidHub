package controlador;

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
		
		String error[] = null;
		
		if(!comprobarDatosCorrectos(error)) {
			this.muestraError("ERROR", "Se produjo un error", error[0]);
		}else {
			if(monitor.isSelected()) {
				System.out.println("Es monitor");
				//TODO mostrar ventana especialidad
			}
		}
		

		
	}
	
	private boolean comprobarDatosCorrectos(String[] error) {
		
		if(!contra1.getText().equals(contra2.getText())) {
			error[0] = "Las contraseñas no son iguales";
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
