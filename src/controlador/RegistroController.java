package controlador;

import javafx.fxml.*;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class RegistroController extends Controller{
	@FXML
	TextField textoNombre, textoApellidos, textoDNI, textoEmail, textoTfno, textoUsuario, textoContra1, textoContra2;
	
	@FXML
	DatePicker nacimiento;
	
	@FXML
	RadioButton botonMonitor, botonPadre;

	@FXML
	Button botonConfirmar, botonCancelar;
	
	public void registrar() {
		System.out.println("Registrando");
		//TODO crear una tabla en la BBDD con los datos del nuevo usuario
		
		if(botonMonitor.isSelected()) {
			System.out.println("Es monitor");
			//TODO mostrar ventana especialidad
		}
		
	}
	
	public void cancelar() {
		System.out.println("Cancelando");
	}
	
	
}
