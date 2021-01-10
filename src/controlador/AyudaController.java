package controlador;

import com.jfoenix.controls.JFXTextArea;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class AyudaController extends Controller{

	@FXML
	private Button entendido;
	
	@FXML
	private JFXTextArea ayuda;
	
	private String actividades = "Bienvenido a la zona de gestion de las actividades.\nDesde este panel podras apuntar o desapuntar"
			+ "a tus hijos de las distintas actividades disponibles. En el selector, podras elegir entre visualizar todas las "
			+ "actividades pinchando en 'TODOS', o en las actividades a las que esten apuntados tus hijos pinchando en su nombre"
			+ "de usuario.\nAl visualizar todas ellas, se activara el boton de apuntar. Si seleccionas una de las actividades de "
			+ "la tabla y clicas en apuntar, podras elegir cual de tus hijos asistira a la actividad.\nAl seleccionar uno de tus"
			+ "hijos, podras desapuntarle de alguna de sus actividades seleccionandola y clicando en el boton de desapuntar, que"
			+ "se habra activado.";
	
	private String trayectos = "Bienvenido a la zona de gestion de los trayectos. \nDesde este panel podras apuntar o desapuntar"
			+ "a tus hijos de los distintos trayectos, ademas de crear, borrar o modificar aquellos que esten a tu cargo. En el"
			+ "selector podras elegir entre visualizar todos los trayectos con la opcion 'TODOS', los que hayas creado tu bajo "
			+ "'PROPIOS' o a los que esten apuntados cada uno de tus hijos seleccionando su nombre de usuario. Para desapuntar a "
			+ "uno de tus hijos de un trayecto, pincha en su nombre de usuario. Para apuntar a uno de tus hijos a un trayecto, "
			+ "deberas acceder al display de todos los trayectos. Por ultimo, accede a tus propios trayectos para crear, borrar"
			+ " o modificar.";
	
		
	void initData(boolean esActividad) {
		if(esActividad) {
			this.ayuda.setText(actividades);
		}else {
			this.ayuda.setText(trayectos);
		}
	}
	
	
	@FXML
    void salir(MouseEvent event) {
		this.cerrarVentana(event);
		this.mostrarVentana("PadreInicio");
    }
}
