package controlador;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
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
import javafx.scene.Node;
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
import modelo.KidHubException;
import modelo.Logica;
import modelo.vo.ActividadVO;
import modelo.vo.Direccion;
import modelo.vo.MonitorVO;
import vista.ActividadTabla;

public class MonitorInicioController extends Controller{
	
	//Ventana de monitor
	
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
    private boolean modificacion = false;

    
    
    @FXML
    public void initialize() {

    	//TODO pedir el nombre a la base de datos
    	//nombreMonitor.setText(Logica.getUsuarioActual().getNombre()); 
    	
    }
    
    
    private void inicializarTablaActividades(ArrayList<ActividadVO> actividades) {
    	
    	/**
    	 * CREACION DE LOS HEADERS DE LA TABLA
    	 */
    	
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


    @FXML
    public void elegirPanel(ActionEvent actionEvent) {

        if (actionEvent.getSource() == inicio) {      	
        	panoInicio.setVisible(true);
        	panoCerrar.setVisible(false);
        	panoActividades.setVisible(false);
        	
        } else if (actionEvent.getSource() == actividades) {
        	//INICIALIZAR LA TABLA
        	/*Direccion palomera = new Direccion("av. universidad", 3, "24008", "leon");
        	//TODO que se inicialice con sus actividades
        	ArrayList<ActividadVO> actividadesPrueba = new ArrayList<ActividadVO>();
        	actividadesPrueba.add(new ActividadVO("Baloncesto", LocalDateTime.now(), LocalDateTime.now(), 3, palomera, "baloncesto"));
        	actividadesPrueba.add(new ActividadVO("Futbol", LocalDateTime.now(), LocalDateTime.now(), 7, palomera, "futbol"));
        	actividadesPrueba.add(new ActividadVO("Hockey", LocalDateTime.now(), LocalDateTime.now(), 8, palomera, "hockey"));
        	this.inicializarTablaActividades(actividadesPrueba);
        	*/
        	this.inicializarTablaActividades(Logica.getLogica().getActividades());
        	
        	panoActividades.setVisible(true);       	
        	panoCerrar.setVisible(false);
        	panoInicio.setVisible(false);
        	
        }else if (actionEvent.getSource() == cerrarSesion) {
        	panoCerrar.setVisible(true);  
        	panoInicio.setVisible(false);
        	panoActividades.setVisible(false);
        }
    }    
    
    
    @FXML
    void borrarActividad(MouseEvent event) {
    	System.out.println("Borrando actividad");
    	ActividadTabla actividadTabla;
    	ActividadVO actividad = new ActividadVO();
    	if(actividadesTree.getSelectionModel().getSelectedItem() == null) {
    		this.muestraError("ERROR", "Actividades", "No hay ninguna actividad seleccionada");
    	}else {
    		actividadTabla = actividadesTree.getSelectionModel().getSelectedItem().getValue();
    		actividad.setIdActividad(actividadTabla.getId());
    		Logica.getLogica().borrarActividad(actividad);
    		this.inicializarTablaActividades(Logica.getLogica().getActividades());
    	}
    	    	
    }

    @FXML
    void crearActividad(MouseEvent event) {
    	System.out.println("Creando actividad");
    	this.cerrarVentana(event);
    	this.mostrarVentanaActividades(new ActividadVO(), false);
    	System.out.println("Se supone que ya la cree");
    }

    @FXML
    void modificarActividad(MouseEvent event) {
    	System.out.println("Modificando actividad");
    	ActividadTabla actividadTabla;
    	ActividadVO actividad = new ActividadVO();
    	
    	if(actividadesTree.getSelectionModel().getSelectedItem() == null) {
    		this.muestraError("ERROR", "Actividades", "No hay ninguna actividad seleccionada");
    	}else {
    		actividadTabla = actividadesTree.getSelectionModel().getSelectedItem().getValue();
    		System.out.println("La actividad seleccionada es:\n + " + actividadTabla.getNombre());
    		this.cerrarVentana(event);
    		actividad.setIdActividad(actividadTabla.getId());
    		Logica.getLogica().rellenarActividad(actividad);
    		System.out.println("Me voy");
        	this.mostrarVentanaActividades(actividad, true);
        	System.out.println("Se supone que ya la modifique");
    	}
    }
    
    
    private void mostrarVentanaActividades(ActividadVO actividad, boolean modificacion) {
    	
    	AnchorPane root;
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("../vista/Actividad.fxml"));

    	Stage stage = new Stage();
    	
    	try {
    		root = loader.load();
    		stage.setScene(new Scene(root));
    	}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	ActividadController controller = loader.getController();
    	controller.initData(actividad, modificacion);

    	stage.show();

    }

}
