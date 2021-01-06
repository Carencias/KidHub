package controlador;

import java.sql.SQLException;
import java.util.ArrayList;

import com.jfoenix.controls.JFXComboBox;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import modelo.Logica;
import modelo.vo.HijoVO;
import modelo.vo.TrayectoVO;

public class ElegirTrayectoController extends Controller{
	@FXML
	private Button confirmar, cancelar;
	
	@FXML
	private JFXComboBox<String> selectHijo;

	private TrayectoVO trayecto;
	
	private Stage stage;
	
	void initData(ArrayList<HijoVO> hijos, TrayectoVO trayecto, Stage stage) {

		this.trayecto = trayecto;
		this.stage = stage;
		
		for(HijoVO hijo: hijos) {
			this.selectHijo.getItems().add(hijo.getNombreUsuario());
		}
    }
	
	@FXML
	void confirmar(MouseEvent event) {
		
		String nombre = this.selectHijo.getSelectionModel().getSelectedItem();
		
		if(nombre == null) {
			this.muestraError("ERROR", "Hijo", "Debe seleccionar uno de sus hijos.");
			
		}else {		
			HijoVO hijo = new HijoVO();
			hijo.setNombreUsuario(nombre);
			try {
				Logica.getLogica().apuntarHijoATrayecto(hijo, this.trayecto);
				this.cerrarVentana(event);
				this.recuperarVentana(this.stage);
			} catch (SQLException e) {
				logger.error("Error al apuntar a "+nombre+" en el trayecto");
				this.muestraError("ERROR", "Actividades", "Error al apuntar a "+nombre+" en el trayecto");
			}					
		}
	}
	
	@FXML
    void cancelar(MouseEvent event) {

    	this.cerrarVentana(event);
    	this.recuperarVentana(this.stage);
    }
}
