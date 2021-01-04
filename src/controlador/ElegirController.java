package controlador;

import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import modelo.Logica;
import modelo.vo.ActividadVO;
import modelo.vo.HijoVO;

/**
 * Clase controladora de la ventana que permite apuntar un hijo a una actividad
 * @version 1.0
 *
 */
public class ElegirController extends Controller{

	
	@FXML
	private Button confirmar, cancelar;
	
	@FXML
	private JFXComboBox<String> selectHijo;

	private ActividadVO actividad;
	
	private Stage stage;
	
	static Logger logger = Logger.getLogger(ElegirController.class);
	
	/**
	 * Metodo que inicializa la ventana con los hijos del usuario y la actividad
	 * @param hijos
	 *  Hijos del usuario
	 * @param actividad
	 *  ActividadVO con la informacion de la actividad
	 * @param stage
	 *  Stage
	 */
	void initData(ArrayList<HijoVO> hijos, ActividadVO actividad, Stage stage) {
		this.actividad = actividad;
		this.stage = stage;
		
		for(HijoVO hijo: hijos) {
			this.selectHijo.getItems().add(hijo.getNombreUsuario());
		}
    }
	
	/**
	 * Metodo que escucha el boton de confirmar y apunta un hijo a la actividad
	 * @param event
	 *  Pulsado boton de confirmar
	 */
	@FXML
	void confirmar(MouseEvent event) {
		
		logger.trace("Boton de confirmar apuntar a hijo a actividad pulsado");
		String nombre = this.selectHijo.getSelectionModel().getSelectedItem();
		if(nombre == null) {
			logger.error("Hijo no seleccionado");
			this.muestraError("ERROR", "Hijo", "Debe seleccionar uno de sus hijos.");
			
		}else {
			HijoVO hijo = new HijoVO();
			hijo.setNombreUsuario(nombre);
			try {
				Logica.getLogica().apuntarHijoAActividad(hijo, this.actividad);
			} catch (SQLException e) {
				logger.error("Error al apuntar al hijo a la actividad: "+e.getMessage());
				muestraError("ERROR","Se produjo un error.", "Error al apuntar al hijo a la actividad");
			}		
			this.cerrarVentana(event);
			this.recuperarVentana(this.stage);
		}
	}
	
	/**
	 * Metodo que cancela la operacion
	 * @param event
	 *  Pulsado boton de cancelar
	 */
	@FXML
    void cancelar(MouseEvent event) {
		logger.trace("Cancelado apuntar hijo a actividad");
    	this.cerrarVentana(event);
    	this.recuperarVentana(this.stage);
    }
	
}
