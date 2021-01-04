package controlador;

import java.util.ArrayList;

import com.jfoenix.controls.JFXComboBox;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import modelo.Logica;
import modelo.vo.ActividadVO;
import modelo.vo.HijoVO;

public class ElegirController extends Controller{

	
	@FXML
	private Button confirmar, cancelar;
	
	@FXML
	private JFXComboBox<String> selectHijo;

	private ActividadVO actividad;
	
	private Stage stage;
	
	void initData(ArrayList<HijoVO> hijos, ActividadVO actividad, Stage stage) {

		this.actividad = actividad;
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
			Logica.getLogica().apuntarHijoAActividad(hijo, this.actividad);		
			this.cerrarVentana(event);
			this.recuperarVentana(this.stage);
		}
	}
	
	@FXML
    void cancelar(MouseEvent event) {

    	this.cerrarVentana(event);
    	this.recuperarVentana(this.stage);
    }
	
}
