package controlador;

import java.io.IOException;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
import modelo.Logica;
import modelo.vo.ActividadVO;
import java.sql.SQLException;
import vista.ActividadTabla;

/**
 *Clase controladora de los usuarios de tipo Monitor, que controla su ventana, y escucha sus acciones comunicandose con la Logica.
 *@version 1.0
 */
public class MonitorInicioController extends Controller{
	
	
    @FXML
    private Button cerrar;
    
    @FXML
    private JFXButton inicio, actividades, cerrarSesion, crear, modificar, borrar;
    
    @FXML
    private Pane panoInicio, panoActividades, panoCerrar;
    
    @FXML
    private Label nombreMonitor;
    
    @FXML
    private JFXTreeTableView<ActividadTabla> actividadesTree;
    
    //Para saber si creo actividad o modifico ya que es la misma ventana
    @SuppressWarnings("unused")
	private boolean modificacion = false;

    static Logger logger = Logger.getLogger(MonitorInicioController.class);
   
    /**
     * Inicializa la ventana de Monitor, fijando el nombre de usuario del monitor
     */
    @FXML
    public void initialize() {
    	nombreMonitor.setText(Logica.getLogica().getUsuarioActual().getNombre());  	
    }
    
    /**
     * Metodo que elige el panel que se mostrara en la ventana, dependiendo de si se ha pulsado el boton de las actividades, inicio o cerrar sesion
     * @param actionEvent
     *  Evento capturado
     */
    @FXML
    public void elegirPanel(ActionEvent actionEvent) {
    	logger.error("Eligiendo panel monitor");
        if (actionEvent.getSource() == inicio) {
        	logger.trace("Mostrando pantalla de inicio del monitor");
        	panoInicio.setVisible(true);
        	panoCerrar.setVisible(false);
        	panoActividades.setVisible(false);
        	
        } else if (actionEvent.getSource() == actividades) {
        	logger.trace("Mostrando pantalla de actividades del monitor");
        	this.inicializarTablaActividades(Logica.getLogica().getActividades(Logica.getLogica().getUsuarioActual()));
        	panoActividades.setVisible(true);       	
        	panoCerrar.setVisible(false);
        	panoInicio.setVisible(false);
        	
        }else if (actionEvent.getSource() == cerrarSesion) {
        	logger.trace("Mostrando pantalla de cerrarSesion del monitor");
        	panoCerrar.setVisible(true);  
        	panoInicio.setVisible(false);
        	panoActividades.setVisible(false);
        }
    }    
    
    /**
     * Metodo que escucha el boton de borrar actividad e indica a la logica que borre la actividad seleccionada
     * @param event
     *  Pulsado boton borrar
     */
    @FXML
    public void borrarActividad(MouseEvent event) {
    	logger.trace("Borrando actividad del monitor");
    	ActividadTabla actividadTabla;
    	ActividadVO actividad = new ActividadVO();
    	if(actividadesTree.getSelectionModel().getSelectedItem() == null) {
    		logger.warn("Ninguna actividad seleccionada para borrar");
    		this.muestraError("ERROR", "Actividades", "No hay ninguna actividad seleccionada");
    	}else {
    		logger.trace("Borrando actividad");
    		actividadTabla = actividadesTree.getSelectionModel().getSelectedItem().getValue();
    		logger.trace("La actividad seleccionada es:\n + " + actividadTabla.getNombre());
    		actividad.setIdActividad(actividadTabla.getId());
    		try {
    			Logica.getLogica().borrarActividad(actividad);
    			this.inicializarTablaActividades(Logica.getLogica().getActividades(Logica.getLogica().getUsuarioActual()));
        		logger.trace("Actividad borrada");
    		}catch(SQLException e) {
    			logger.error("Error al borrar la actividad: "+e.getMessage());
    			this.muestraError("ERROR", "Actividades", "Error al borrar la actividad");
    		}
    	}   	
    }

    /**
     * Metodo que muestra la ventana encargada de crear una actividad
     * @param event
     *  Pulsado boton crear actividad
     */
    @FXML
    public void crearActividad(MouseEvent event) {
    	logger.trace("Cambiando a vista de creacion de actividad");
    	Stage stage = this.esconderVentana(event);
    	this.mostrarVentanaActividades(new ActividadVO(), false, stage);
    	logger.trace("Actividad creada correctamente");
    }

    /**
     * Metodo que muestra la ventana de modificacion de actividades con los campos previos de la actividad
     * @param event
     *  Pulsado boton modificar actividad
     */
    @FXML
    public void modificarActividad(MouseEvent event) {
    	logger.trace("Modificando actividad");
    	ActividadTabla actividadTabla;
    	ActividadVO actividad = new ActividadVO();
    	
    	if(actividadesTree.getSelectionModel().getSelectedItem() == null) {
    		logger.warn("No hay actividad seleccionada para modificar");
    		this.muestraError("ERROR", "Actividades", "No hay ninguna actividad seleccionada");
    	}else {
    		actividadTabla = actividadesTree.getSelectionModel().getSelectedItem().getValue();
    		logger.trace("La actividad seleccionada es:\n + " + actividadTabla.getNombre());
    		Stage stage = this.esconderVentana(event);
    		actividad.setIdActividad(actividadTabla.getId());
    		try{
    			Logica.getLogica().rellenarActividad(actividad);
    			this.mostrarVentanaActividades(actividad, true, stage);
            	logger.trace("Actividad modificada correctamente");
    		}catch(SQLException e) {
    			logger.error("Error al obtener la actividad: "+e.getMessage());
    			this.muestraError("ERROR", "Actividades", "Error al obtener la actividad");
    		}
    	}
    }
    
    /**
     * Metodo privado que muestra la ventana para modificar y crear actividades
     * @param actividad
     *  Actividad a modificar si es el caso
     * @param modificacion
     *  Boolean que indica si la actividad se va a modificar o crear. Verdadero si se va a modificar
     * @param stage2
     */
    private void mostrarVentanaActividades(ActividadVO actividad, boolean modificacion, Stage stage2) {
    	AnchorPane root;  	
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("../vista/Actividad.fxml"));
    	Stage stage = new Stage();
    	
    	try {
    		root = loader.load();
    		stage.setScene(new Scene(root));
    		stage.setResizable(false);
    	}catch (IOException e) {
    		logger.error("No se pudo mostrar la ventana de creacion de actividad: "+e.getMessage());
		}
    
    	ActividadController controller = loader.getController();
    	controller.initData(actividad, modificacion, stage2);
    	stage.show();
    }

    /**
     * Metodo privado que inicializa la tabla en la que se muestran las actividades
     * @param actividades
     *  Actividades del monitor que se mostraran
     */
	private void inicializarTablaActividades(ArrayList<ActividadVO> actividades) {
		//Inicializacion de los headers de las tablas
		logger.trace("Inicializando headers de las tablas de actividades");
		//TODO incluir el tipo de la actividad
		JFXTreeTableColumn<ActividadTabla, String> nameCol = new JFXTreeTableColumn<>("Name");
		nameCol.setPrefWidth(138);
		nameCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ActividadTabla, String>, ObservableValue<String>>() {
	        @Override
	        public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ActividadTabla, String> param) {
	            return param.getValue().getValue().getNombre();
	        }
	    });
	
	    JFXTreeTableColumn<ActividadTabla, String> inicioCol = new JFXTreeTableColumn<>("Inicio");
	    inicioCol.setPrefWidth(138);
	    inicioCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ActividadTabla, String>, ObservableValue<String>>() {
	        @Override
	        public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ActividadTabla, String> param) {
	            return param.getValue().getValue().getInicio();
	        }
	    });
	
	    JFXTreeTableColumn<ActividadTabla, String> finCol = new JFXTreeTableColumn<>("Fin");
	    finCol.setPrefWidth(138);
	    finCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ActividadTabla, String>, ObservableValue<String>>() {
	        @Override
	        public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ActividadTabla, String> param) {
	            return param.getValue().getValue().getFin();
	        }
	    });
	    
	    JFXTreeTableColumn<ActividadTabla, String> lugarCol = new JFXTreeTableColumn<>("Lugar");
	    lugarCol.setPrefWidth(140);
	    lugarCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ActividadTabla, String>, ObservableValue<String>>() {
	        @Override
	        public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ActividadTabla, String> param) {
	            return param.getValue().getValue().getLugar();
	        }
	    });
	    
	    JFXTreeTableColumn<ActividadTabla, String> aforoCol = new JFXTreeTableColumn<>("Aforo");
	    aforoCol.setPrefWidth(138);
	    aforoCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ActividadTabla, String>, ObservableValue<String>>() {
	        @Override
	        public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ActividadTabla, String> param) {
	            return param.getValue().getValue().getAforo();
	        }
	    });
	    
	   //Creando lista de actividades para incluir en la tabla
	    logger.trace("Agragando actividades a la tabla");
	    ObservableList<ActividadTabla> obsActividades = FXCollections.observableArrayList();
	    ActividadTabla actividad;
	    for(ActividadVO act:actividades) {
	    	actividad = new ActividadTabla(act);
	    	obsActividades.add(actividad);
	    }
	
	    final TreeItem<ActividadTabla> root = new RecursiveTreeItem<ActividadTabla>(obsActividades, RecursiveTreeObject::getChildren);
	    actividadesTree.getColumns().setAll(nameCol, inicioCol, finCol, lugarCol, aforoCol);
	    actividadesTree.setRoot(root);
	    actividadesTree.setShowRoot(false);
	    actividadesTree.setVisible(true);
	    logger.trace("Tabla inicializada correctamente");
	}
}
