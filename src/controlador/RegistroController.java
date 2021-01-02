package controlador;

import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import modelo.KidHubException;
import modelo.Logica;
import modelo.vo.*;

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
		Logica logica = Logica.getLogica();
		PadreVO padrevo = new PadreVO();
		MonitorVO monitorvo = new MonitorVO();
		if(!contra1.getText().equals(contra2.getText())) {
			muestraError("ERROR","Se produjo un error.", "Las contrasenas no coinciden.");
		}else {
			if(padre.isSelected()) {
				System.out.println("Es padre");
				rellenarVO(padrevo);
				//TODO queda poner el telefono
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
			}else{
				System.out.println("Es monitor");
				rellenarVO(monitorvo);
				//TODO queda poner las especialidades
				try {
					logica.registrarUsuario(monitorvo);
					logica.setUsuarioActual(monitorvo);
					//TODO no se porque no muestra la venta del monitor
					this.mostrarVentana("MonitorInicio");
					this.cerrarVentana(event);
				}catch(Exception e) {
					if(e instanceof KidHubException) {
						muestraError("ERROR","Se produjo un error.", e.getMessage());
					}else if(e instanceof SQLException && ((SQLException) e).getErrorCode() == 1062) {
						muestraError("ERROR","Se produjo un error.", "El usuario ya existe.");
					}else if(e instanceof SQLException){
						System.out.println(((SQLException) e).getErrorCode());
						muestraError("ERROR","Se produjo un error.", "Campos invalidos");
					}
				}	
			}
		}	
    }
    
    private void rellenarVO(UsuarioVO usuariovo) {
    	usuariovo.setNombre(nombre.getText());
    	usuariovo.setApellidos(apellidos.getText());
    	usuariovo.setNombreUsuario(usuario.getText());
    	usuariovo.setContrasena(contra1.getText());
    	usuariovo.setDni(dni.getText());
    	usuariovo.setEmail(email.getText());
		if(dia.getText().length() == 1) {
			dia.setText("0"+dia.getText());
		}
		if(mes.getText().length() == 1) {
			mes.setText("0"+mes.getText());
		}
		String fechaString = dia.getText()+"/"+mes.getText()+"/"+ano.getText();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate fecha = LocalDate.parse(fechaString, formatter);
		usuariovo.setFechaNacimiento(fechaString);
		LocalDate ahora = LocalDate.now();
		Period periodo = Period.between(fecha, ahora);
		usuariovo.setEdad(periodo.getYears());
    }
}
