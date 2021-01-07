package controlador;

import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.jfoenix.controls.JFXComboBox;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import modelo.KidHubException;
import modelo.Logica;
import modelo.vo.HijoVO;
import modelo.vo.TrayectoVO;

/**
 * Clase controladora de la ventana que permite apuntar un hijo a un trayecto
 * @version 1.0
 */
public class ElegirTrayectoController extends Controller{
	@FXML
	private Button confirmar, cancelar;
	
	@FXML
	private JFXComboBox<String> selectHijo;

	private TrayectoVO trayecto;
	
	private Stage stage;
	
	static Logger logger = Logger.getLogger(ElegirTrayectoController.class);
	
	/**
	 * Metodo que inicializa la ventana con los hijos del usuario y el trayecto
	 * @param hijos
	 *  Hijos del usuario
	 * @param trayecto
	 *  ActividadVO con la informacion del trayecto
	 * @param stage
	 *  Stage
	 */
	void initData(ArrayList<HijoVO> hijos, TrayectoVO trayecto, Stage stage) {

		this.trayecto = trayecto;
		this.stage = stage;
		
		for(HijoVO hijo: hijos) {
			this.selectHijo.getItems().add(hijo.getNombreUsuario());
		}
    }
	
	/**
	 * Metodo que escucha el boton de confirmar y apunta un hijo a el trayecto
	 * @param event
	 *  Pulsado boton de confirmar
	 */
	@FXML
	void confirmar(MouseEvent event) {
		logger.trace("Boton de confirmar apuntar a hijo a trayecto pulsado");
		String nombre = this.selectHijo.getSelectionModel().getSelectedItem();
		
		if(nombre == null) {
			logger.error("Hijo no seleccionado");
			this.muestraError("ERROR", "Hijo", "Debe seleccionar uno de sus hijos.");
			
		}else {		
			HijoVO hijo = new HijoVO();
			hijo.setNombreUsuario(nombre);
			try {
				Logica.getLogica().rellenarTrayecto(trayecto);
				Logica.getLogica().apuntarHijoATrayecto(hijo, this.trayecto);
				this.cerrarVentana(event);
				this.recuperarVentana(this.stage);
			} catch (SQLException e) {
				logger.error("Error al apuntar a "+nombre+" en el trayecto");
				this.muestraError("ERROR", "Actividades", "Error al apuntar a "+nombre+" en el trayecto");
			} catch (KidHubException e) {
				logger.error("Error al apuntar a "+nombre+" en el trayecto");
				this.muestraError("ERROR", "Actividades", "Error al apuntar a "+nombre+" en el trayecto");
			}
		}
	}
	
	@FXML
    void cancelar(MouseEvent event) {
		logger.trace("Cancelado apuntar hijo a trayecto");
    	this.cerrarVentana(event);
    	this.recuperarVentana(this.stage);
    }
}
