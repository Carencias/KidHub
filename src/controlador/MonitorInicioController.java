package controlador;

import java.time.LocalDateTime;
import java.util.ArrayList;

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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import modelo.vo.ActividadVO;
import modelo.vo.Direccion;
import vista.ActividadTabla;

public class MonitorInicioController extends Controller{

    @FXML
    private Button cerrar;
    
    @FXML
    private JFXButton inicio, actividades, cerrarSesion, crear, modificar, borrar;
    
    @FXML
    private Pane panoInicio, panoActividades, panoCerrar;
    
    @FXML
    private Label nombre;
    
    @FXML
    private JFXTreeTableView<ActividadTabla> actividadesTree;
    
    
    
    @FXML
    public void initialize() {

    	//TODO pedir el nombre a la base de datos
    	nombre.setText("Nombre del monitor"); 
    	
    }
    
    
    private void inicializarTablaActividades(ArrayList<ActividadVO> actividades) {
    	
    	/**
    	 * CREACION DE LOS HEADERS DE LA TABLA
    	 */
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
        	Direccion palomera = new Direccion("av. universidad", 3, 24008, "leon");
        	
        	ArrayList<ActividadVO> actividadesPrueba = new ArrayList<ActividadVO>();
        	actividadesPrueba.add(new ActividadVO("Baloncesto", LocalDateTime.now(), LocalDateTime.now(), 3, palomera, "baloncesto"));
        	actividadesPrueba.add(new ActividadVO("Futbol", LocalDateTime.now(), LocalDateTime.now(), 7, palomera, "futbol"));
        	actividadesPrueba.add(new ActividadVO("Hockey", LocalDateTime.now(), LocalDateTime.now(), 8, palomera, "hockey"));
        	
        	this.inicializarTablaActividades(actividadesPrueba);
        	
        	
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
    }

    @FXML
    void crearActividad(MouseEvent event) {
    	System.out.println("Creando actividad");
    }

    @FXML
    void modificarActividad(MouseEvent event) {
    	System.out.println("Modificando actividad");
    }

}
