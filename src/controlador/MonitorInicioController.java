package controlador;

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
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
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
    
    
    //Ventana de crear/modificar actividad
    
    @FXML
    private JFXTextField nombreAct, tipo, ciudad, calle, num, codPostal,
    					 aforo, diaInicio, mesInicio, anoInicio, horaInicio, minInicio,
    					 diaFin, mesFin, anoFin, horaFin, minFin;
    
    @FXML
    private Button confirmar, cancelar;
    
    
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
    	ActividadTabla actividadTabla;
    	ActividadVO actividad = new ActividadVO();
    	if(actividadesTree.getSelectionModel().getSelectedItem() == null) {
    		this.muestraError("ERROR", "Actividades", "No hay ninguna actividad seleccionada");
    	}else {
    		actividadTabla = actividadesTree.getSelectionModel().getSelectedItem().getValue();
    		actividad.setIdActividad(actividadTabla.getId());
    		this.logica.borrarActividad(actividad);
    	}
    	    	
    }

    @FXML
    void crearActividad(MouseEvent event) {
    	System.out.println("Creando actividad");
    	this.cerrarVentana(event);
    	this.mostrarVentana("Actividad");
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
        	this.mostrarVentana("Actividad");
        	actividad.setIdActividad(actividadTabla.getId());
        	this.logica.rellenarActividad(actividad);
        	this.setDatosActividad(actividad);
    	}
    }

	private void setDatosActividad(ActividadVO actividad) {
		
		this.nombreAct.setText(actividad.getNombre());
    	this.tipo.setText(actividad.getTipo());
    	this.ciudad.setText(actividad.getDireccion().getCiudad());
    	this.calle.setText(actividad.getDireccion().getCalle());
    	this.num.setText(Integer.toString(actividad.getDireccion().getNumero()));
    	this.codPostal.setText(Integer.toString(actividad.getDireccion().getCodigoPostal()));
    	this.diaInicio.setText(Integer.toString(actividad.getInicio().getDayOfMonth()));
    	this.mesInicio.setText(Integer.toString(actividad.getInicio().getMonthValue()));
    	this.anoInicio.setText(Integer.toString(actividad.getInicio().getYear()));
    	this.diaFin.setText(Integer.toString(actividad.getFin().getDayOfMonth()));
    	this.mesFin.setText(Integer.toString(actividad.getFin().getMonthValue()));
    	this.anoFin.setText(Integer.toString(actividad.getFin().getYear()));
	}
	
	
	private void getDatosActividad(ActividadVO actividad) {
		actividad.setNombre(this.nombreAct.getText());
		actividad.setTipo(this.tipo.getText());
		Direccion direccion = new Direccion(this.calle.getText(), Integer.parseInt(this.num.getText()), Integer.parseInt(this.codPostal.getText()), this.ciudad.getText());
		actividad.setDireccion(direccion);
		LocalDateTime inicio = LocalDateTime.of(Integer.parseInt(this.anoInicio.getText()), Integer.parseInt(this.mesInicio.getText()), Integer.parseInt(this.diaInicio.getText()), Integer.parseInt(this.horaInicio.getText()), Integer.parseInt(this.minInicio.getText()));
		LocalDateTime fin = LocalDateTime.of(Integer.parseInt(this.anoFin.getText()), Integer.parseInt(this.mesFin.getText()), Integer.parseInt(this.diaFin.getText()), Integer.parseInt(this.horaFin.getText()), Integer.parseInt(this.minFin.getText()));
		actividad.setInicio(inicio);
		actividad.setFin(fin);
		actividad.setCapacidad(Integer.parseInt(this.aforo.getText()));
		actividad.setMonitor((MonitorVO) this.logica.getUsuarioActual());
	}
    
	
    
    /**
     * Metodos para controlar ventana actividad
     */
    @FXML
    void confirmar(MouseEvent event) {
    	System.out.println("Confirmando");
    	ActividadVO actividad = null;
    	this.getDatosActividad(actividad);  
    	
    	if(this.modificacion) {
    		this.logica.modificarActividad(actividad);
    		this.modificacion = false;
    	}else {
    		this.logica.crearActividad(actividad);
    	}
    	
    	this.cerrarVentana(event);
    	this.mostrarVentana("MonitorInicio");
    }
    
    
    @FXML
    void cancelar(MouseEvent event) {
    	System.out.println("Cancelando");
    	this.cerrarVentana(event);
    	this.mostrarVentana("MonitorInicio");
    }

}
