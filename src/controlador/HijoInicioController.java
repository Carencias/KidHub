package controlador;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import com.jfoenix.controls.RecursiveTreeItem;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.util.Callback;
import modelo.Logica;
import modelo.vo.ActividadVO;
import modelo.vo.TrayectoVO;
import vista.ActividadTabla;
import vista.TrayectoTabla;

/**
 * Clase controladora de la ventana del hijo
 * @version 1.0
 *
 */
public class HijoInicioController extends Controller{

	@FXML
    private Button cerrar;
    
    @FXML
    private JFXButton inicio, actividades, trayectos, cerrarSesion, crear;
    
    @FXML
    private Pane panoInicio, panoActividades, panoTrayectos, panoCerrar;
    
    @FXML
    private Label nombre;
    
    @FXML
    private JFXTreeTableView<ActividadTabla> actividadesTree;
    
    @FXML
    private JFXTreeTableView<TrayectoTabla> trayectosTree;
    
    static Logger logger = Logger.getLogger(HijoInicioController.class);
    
    @FXML
    public void initialize() {
    	nombre.setText(Logica.getLogica().getUsuarioActual().getNombre());

    	ArrayList<TrayectoVO> trayectosPrueba = new ArrayList<TrayectoVO>();
    	trayectosPrueba.add(new TrayectoVO());
    	
    }
    
    
    @FXML
    public void elegirPanel(ActionEvent actionEvent) {
    	
        if (actionEvent.getSource() == inicio) {      	
        	
            mostrarPanelesActividadesCerrarInicioTrayectos(false, false, true, false);

        } else if (actionEvent.getSource() == actividades) {
        	try {
        		this.inicializarTablaActividades(Logica.getLogica().getActividades(Logica.getLogica().getUsuarioActual())); 
	        }catch(SQLException e) {
	        		logger.error("No se han podido obtener las actividades");
	        		this.muestraError("ERROR", "Actividades", "No se han podido obtener las actividades");
	        }      	
            mostrarPanelesActividadesCerrarInicioTrayectos(true, false, false, false);

        	
        }else if (actionEvent.getSource() == trayectos) {
        	this.inicializarTablaTrayectos(Logica.getLogica().getTrayectos(Logica.getLogica().getUsuarioActual()));
            
        	mostrarPanelesActividadesCerrarInicioTrayectos(false, false, false, true);
    	
    	}else if (actionEvent.getSource() == cerrarSesion) {
            mostrarPanelesActividadesCerrarInicioTrayectos(false, true, false, false);

        }
    }
    
    private void mostrarPanelesActividadesCerrarInicioTrayectos(boolean actividades, boolean cerrar, boolean inicio, boolean trayectos) {
    	panoActividades.setVisible(actividades);
    	panoCerrar.setVisible(cerrar);  
    	panoInicio.setVisible(inicio);
    	panoTrayectos.setVisible(trayectos);
    	
    }

    @FXML
    public void mostrarActividadTablas(MouseEvent event) {
    	logger.trace("Intentando mostrar actividades del hijo");
    	
    	try {
    		this.inicializarTablaActividades(Logica.getLogica().getActividades(Logica.getLogica().getUsuarioActual()));
    		logger.trace("Actividades del hijo mostradas");
        }catch(SQLException e) {
        		logger.error("No se han podido obtener las actividades");
        		this.muestraError("ERROR", "Actividades", "No se han podido obtener las actividades");
        }
    }


	private void inicializarTablaActividades(ArrayList<ActividadVO> actividades) {
		
		/**
		 * CREACION DE LOS HEADERS DE LA TABLA
		 */
		JFXTreeTableColumn<ActividadTabla, String> nameCol = new JFXTreeTableColumn<>("Name");
		nameCol.setPrefWidth(145);
		nameCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ActividadTabla, String>, ObservableValue<String>>() {
	        @Override
	        public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ActividadTabla, String> param) {
	            return param.getValue().getValue().getNombre();
	        }
	    });
	
	    JFXTreeTableColumn<ActividadTabla, String> inicioCol = new JFXTreeTableColumn<>("Inicio");
	    inicioCol.setPrefWidth(145);
	    inicioCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ActividadTabla, String>, ObservableValue<String>>() {
	        @Override
	        public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ActividadTabla, String> param) {
	            return param.getValue().getValue().getInicio();
	        }
	    });
	
	    JFXTreeTableColumn<ActividadTabla, String> finCol = new JFXTreeTableColumn<>("Fin");
	    finCol.setPrefWidth(145);
	    finCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ActividadTabla, String>, ObservableValue<String>>() {
	        @Override
	        public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ActividadTabla, String> param) {
	            return param.getValue().getValue().getFin();
	        }
	    });
	    
	    JFXTreeTableColumn<ActividadTabla, String> lugarCol = new JFXTreeTableColumn<>("Lugar");
	    lugarCol.setPrefWidth(145);
	    lugarCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ActividadTabla, String>, ObservableValue<String>>() {
	        @Override
	        public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ActividadTabla, String> param) {
	            return param.getValue().getValue().getLugar();
	        }
	    });
	    
	    JFXTreeTableColumn<ActividadTabla, String> aforoCol = new JFXTreeTableColumn<>("Aforo");
	    aforoCol.setPrefWidth(145);
	    aforoCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ActividadTabla, String>, ObservableValue<String>>() {
	        @Override
	        public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ActividadTabla, String> param) {
	            return param.getValue().getValue().getAforo();
	        }
	    });
	    
	    /**
	     * LISTA DE ACTIVIDADES PARA INCLUIR EN LA TABLA
	     */
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
	    
	    logger.trace("Tabla de actividades del hijo inicializada");
	}


	private void inicializarTablaTrayectos(ArrayList<TrayectoVO> trayectos) {
		
		/**
		 * CREACION DE LOS HEADERS DE LA TABLA
		 */
		JFXTreeTableColumn<TrayectoTabla, String> actividadCol = new JFXTreeTableColumn<>("Actividad");
		actividadCol.setPrefWidth(145);
		actividadCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<TrayectoTabla, String>, ObservableValue<String>>() {
	        @Override
	        public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<TrayectoTabla, String> param) {
	            return param.getValue().getValue().getActividad();
	        }
	    });
	
	    JFXTreeTableColumn<TrayectoTabla, String> tipoCol = new JFXTreeTableColumn<>("Tipo");
	    tipoCol.setPrefWidth(145);
	    tipoCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<TrayectoTabla, String>, ObservableValue<String>>() {
	        @Override
	        public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<TrayectoTabla, String> param) {
	            return param.getValue().getValue().getTipo();
	        }
	    });
	
	    JFXTreeTableColumn<TrayectoTabla, String> padreCol = new JFXTreeTableColumn<>("Padre");
	    padreCol.setPrefWidth(145);
	    padreCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<TrayectoTabla, String>, ObservableValue<String>>() {
	        @Override
	        public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<TrayectoTabla, String> param) {
	            return param.getValue().getValue().getPadre();
	        }
	    });
	    
	    JFXTreeTableColumn<TrayectoTabla, String> aforoCol = new JFXTreeTableColumn<>("Aforo");
	    aforoCol.setPrefWidth(145);
	    aforoCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<TrayectoTabla, String>, ObservableValue<String>>() {
	        @Override
	        public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<TrayectoTabla, String> param) {
	            return param.getValue().getValue().getAforo();
	        }
	    });
	    
	    /**
	     * LISTA DE ACTIVIDADES PARA INCLUIR EN LA TABLA
	     */
	    ObservableList<TrayectoTabla> obsTrayectos = FXCollections.observableArrayList();
	    TrayectoTabla trayecto;
	    for(TrayectoVO tr:trayectos) {
	    	trayecto = new TrayectoTabla(tr);
	    	obsTrayectos.add(trayecto);
	    }
	
	    final TreeItem<TrayectoTabla> root = new RecursiveTreeItem<TrayectoTabla>(obsTrayectos, RecursiveTreeObject::getChildren);
	    trayectosTree.getColumns().setAll(actividadCol, tipoCol, padreCol, aforoCol);
	    trayectosTree.setRoot(root);
	    trayectosTree.setShowRoot(false);
	    
	    trayectosTree.setVisible(true);
	    
	    logger.trace("Tabla de trayectos del hijo inicializada");

	}
    
}
