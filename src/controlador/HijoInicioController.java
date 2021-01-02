package controlador;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.mysql.fabric.xmlrpc.base.Array;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;

//EJEMPLO TABLA
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;
import com.jfoenix.controls.RecursiveTreeItem;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.util.Callback;
import javafx.util.converter.LocalDateTimeStringConverter;
import modelo.vo.ActividadVO;
import modelo.vo.Direccion;
import modelo.vo.TrayectoVO;
import vista.ActividadTabla;
import vista.TrayectoTabla;

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
    
    
    
    @FXML
    public void initialize() {
    	//TODO pedir el nombre a la base de datos
    	//nombre.setText(this.logica.getUsuarioActual().getNombre());

    	ArrayList<TrayectoVO> trayectosPrueba = new ArrayList<TrayectoVO>();
    	trayectosPrueba.add(new TrayectoVO());
    	
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
    }
    
    
    //TODO hace falta??
    private void actualizarTablaActividades() {
    	//Creo que no renta de ninguna manera, renta volver a invocar inicializarTabla(actividades)
    	/*
    	final TreeItem<ActividadTabla> root = new RecursiveTreeItem<ActividadTabla>(obsActividades, RecursiveTreeObject::getChildren);
        actividadesTree.getColumns().setAll(nameCol, inicioCol, finCol, lugarCol, aforoCol);
        actividadesTree.setRoot(root);
        actividadesTree.setShowRoot(false);
        */
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
        trayectosTree.getColumns().setAll(tipoCol, tipoCol, padreCol, aforoCol);
        trayectosTree.setRoot(root);
        trayectosTree.setShowRoot(false);
    }
    
    @FXML
    public void elegirPanel(ActionEvent actionEvent) {
    	
        if (actionEvent.getSource() == inicio) {      	
        	panoInicio.setVisible(true);
        	panoCerrar.setVisible(false);
        	panoActividades.setVisible(false);
        	panoTrayectos.setVisible(false);
        	
        } else if (actionEvent.getSource() == actividades) {
        	//ININICIALIZAR LA TABLA
        	Direccion palomera = new Direccion("av. universidad", 3, "24008", "leon");
        	
        	ArrayList<ActividadVO> actividadesPrueba = new ArrayList<ActividadVO>();
        	actividadesPrueba.add(new ActividadVO("Baloncesto", LocalDateTime.now(), LocalDateTime.now(), 3, palomera, "baloncesto"));
        	actividadesPrueba.add(new ActividadVO("Futbol", LocalDateTime.now(), LocalDateTime.now(), 7, palomera, "futbol"));
        	actividadesPrueba.add(new ActividadVO("Hockey", LocalDateTime.now(), LocalDateTime.now(), 8, palomera, "hockey"));
        	
        	this.inicializarTablaActividades(actividadesPrueba);
        	
        	
        	panoActividades.setVisible(true);       	
        	panoCerrar.setVisible(false);
        	panoInicio.setVisible(false);
        	panoTrayectos.setVisible(false);
        	
        }else if (actionEvent.getSource() == trayectos) {
        	panoTrayectos.setVisible(true);
        	panoCerrar.setVisible(false);  
        	panoInicio.setVisible(false);
        	panoActividades.setVisible(false);
    	
    	}else if (actionEvent.getSource() == cerrarSesion) {
        	panoCerrar.setVisible(true);  
        	panoInicio.setVisible(false);
        	panoActividades.setVisible(false);
        	panoTrayectos.setVisible(false);
        }
    } 

    @FXML
    public void mostrarActividadTablaes(MouseEvent event) {
    	System.out.println("Mostrando Actividades");
    }
    
    @FXML
    public void mostrarTrayectos(MouseEvent event) {
    	System.out.println("Mostrando trayectos.");
    }
    
}
