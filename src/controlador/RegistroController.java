package controlador;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import modelo.KidHubException;
import modelo.Logica;
import modelo.vo.*;
import modelo.vo.UsuarioVO.TipoUsuario;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

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
		String fechaString;
		LocalDate fecha;
		DateTimeFormatter formatter;
		Logica logica = Logica.getLogica();
		PadreVO padrevo = new PadreVO();
		MonitorVO monitorvo = new MonitorVO();
		if(!contra1.getText().equals(contra2.getText())) {
			muestraError("ERROR","Se produjo un error.", "Las contrasenas no coinciden.");
		}else {
			if(padre.isSelected()) {
				System.out.println("Es padre");
				padrevo.setNombre(nombre.getText());
				padrevo.setApellidos(apellidos.getText());
				padrevo.setNombreUsuario(usuario.getText());
				padrevo.setContrasena(contra1.getText());
				padrevo.setDni(dni.getText());
				padrevo.setEmail(email.getText());
				
				fechaString = dia.getText()+"-"+mes.getText()+"-"+ano.getText();
				formatter=DateTimeFormatter.ofPattern("dd-MM-yyyy");
				fecha = LocalDate.parse(fechaString, formatter);
				padrevo.setFechaNacimiento(fechaString);
				//TODO añadir al VO la el numero de telefono
				LocalDate ahora = LocalDate.now();
				Period periodo = Period.between(fecha, ahora);
				padrevo.setEdad(periodo.getYears());
				
				try {
					logica.registrarUsuario(padrevo);
					logica.setUsuarioActual(padrevo);
					this.mostrarVentana("PadreInicio");
					this.cerrarVentana(event);
				}catch(Exception e) {
					if(e instanceof KidHubException) {
						muestraError("ERROR","Se produjo un error.", e.getMessage());
					}else if(e instanceof SQLException && ((SQLException) e).getErrorCode() == 1062)
						muestraError("ERROR","Se produjo un error.", "El usuario ya existe.");
					else if(e instanceof SQLException){
						System.out.println(((SQLException) e).getErrorCode());
						muestraError("ERROR","Se produjo un error.", "Campos invalidos.");
					}
				}			
			}else {
				System.out.println("Es monitor");
				monitorvo.setNombre(nombre.getText());
				monitorvo.setApellidos(apellidos.getText());
				monitorvo.setNombreUsuario(usuario.getText());
				monitorvo.setContrasena(contra1.getText());
				monitorvo.setDni(dni.getText());
				monitorvo.setEmail(email.getText());
				
				fechaString = dia.getText()+"-"+mes.getText()+"-"+ano.getText();
				formatter=DateTimeFormatter.ofPattern("dd-MM-yyyy");
				fecha = LocalDate.parse(fechaString, formatter);
				monitorvo.setFechaNacimiento(fechaString);
				//TODO añadir al VO la especialidad y no se porque no se crea el monitor en la bbdd mientras que el padre si se crea
				LocalDate ahora = LocalDate.now();
				Period periodo = Period.between(fecha, ahora);
				monitorvo.setEdad(periodo.getYears());
				
				try {
					logica.registrarUsuario(monitorvo);
					logica.setUsuarioActual(monitorvo);
					//TODO no se porque no muestra la venta del monitor
					this.mostrarVentana("MonitorInicio");
					this.cerrarVentana(event);
				}catch(Exception e) {
					if(e instanceof KidHubException) {
						muestraError("ERROR","Se produjo un error.", e.getMessage());
					}else if(e instanceof SQLException && ((SQLException) e).getErrorCode() == 1062)
						muestraError("ERROR","Se produjo un error.", "El usuario ya existe.");
					else if(e instanceof SQLException){
						System.out.println(((SQLException) e).getErrorCode());
						muestraError("ERROR","Se produjo un error.", "Campos invalidos");
					}
				}	
			}
		}	
    }
}
