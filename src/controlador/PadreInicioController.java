package controlador;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
import modelo.KidHubException;
import modelo.Logica;
import modelo.vo.ActividadVO;
import modelo.vo.HijoVO;
import vista.ActividadTabla;

public class PadreInicioController extends Controller{

	@FXML
    private Button cerrar, registrar, registrarExistente;
	
	@FXML
	private JFXTextField nombre, nombreExistente, contraExistente, dni, apellidos, contra2, usuario, email, telefono, contra1, ano, mes, dia;
    
    @FXML
    private JFXButton inicio, actividades, actividadesHijo, trayectos, trayectosHijo, registrarHijo, cerrarSesion, apuntarActividad, desapuntarActividad, apuntarTrayecto, desapuntarTrayecto;
    
    @FXML
    private Pane panoInicio, panoActividades, panoTrayectos, panoRegistrarHijo, panoCerrar;
    
    @FXML
    private Label nombreLabel, hijosVacio;
    
    @FXML
    private JFXComboBox<String> selectHijos;
    
    @FXML
    private JFXTreeTableView<ActividadTabla> actividadesTree;
    
    private ArrayList<HijoVO> hijos; 
	
    
    @FXML
    public void initialize() {
    	nombreLabel.setText(Logica.getLogica().getUsuarioActual().getNombre()); 
    }
    
    
    @FXML
    public void elegirPanel(ActionEvent actionEvent) {
    	
    	if (actionEvent.getSource() == inicio) {      	
        	panoInicio.setVisible(true);
        	panoCerrar.setVisible(false);
        	panoActividades.setVisible(false);
        	panoTrayectos.setVisible(false);
    		panoRegistrarHijo.setVisible(false); 
        	
        } else if (actionEvent.getSource() == actividades) {
        	
        	panoActividades.setVisible(true);       	
        	panoCerrar.setVisible(false);
        	panoInicio.setVisible(false);
        	panoTrayectos.setVisible(false);
    		panoRegistrarHijo.setVisible(false); 
    		
    		this.setHijosCombo();
        	
        }else if (actionEvent.getSource() == trayectos) {
        	panoTrayectos.setVisible(true);
        	panoCerrar.setVisible(false);  
        	panoInicio.setVisible(false);
        	panoActividades.setVisible(false);
    		panoRegistrarHijo.setVisible(false); 
    	
    	}else if(actionEvent.getSource() == registrarHijo) {
    		panoRegistrarHijo.setVisible(true);
    		panoTrayectos.setVisible(false);
        	panoCerrar.setVisible(false);  
        	panoInicio.setVisible(false);
        	panoActividades.setVisible(false);
        
    	}else if (actionEvent.getSource() == cerrarSesion) {
        	panoCerrar.setVisible(true);  
        	panoInicio.setVisible(false);
        	panoActividades.setVisible(false);
        	panoTrayectos.setVisible(false);
    		panoRegistrarHijo.setVisible(false); 
        }
    }
    
    
    private void setHijosCombo() {
    	this.selectHijos.getItems().clear();
    	this.selectHijos.getItems().add("Todos");
    	this.hijos = Logica.getLogica().getHijos();
    	for(HijoVO hijo: hijos) {
    		this.selectHijos.getItems().add(hijo.getNombreUsuario());
    	}    	
    }
    
    private void cargarActividades(HijoVO hijo) {
    	this.inicializarTablaActividades(Logica.getLogica().getActividades(hijo)); 
    	this.hijosVacio.setVisible(false);
    }
    
    @FXML
    void hijoElegido(ActionEvent  event) {
    	
    	HijoVO hijo = this.getHijo();
    	
    	if(hijo.getNombreUsuario().equals("Todos")) {
    		this.apuntarActividad.setDisable(false);
    		this.desapuntarActividad.setDisable(true);
    	}else {
    		this.apuntarActividad.setDisable(true);
    		this.desapuntarActividad.setDisable(false);
    	}

    	this.cargarActividades(hijo);
    }
    
    private HijoVO getHijo() {
    	String nombre = this.selectHijos.getSelectionModel().getSelectedItem();
    	HijoVO hijo = new HijoVO();
    	hijo.setNombreUsuario(nombre);
    	return hijo;
    }
    
    @FXML
    public void registrar(MouseEvent event) {
    	System.out.println("Registrando hijo");
    	String fechaString;
		LocalDate fecha;
    	HijoVO hijovo = new HijoVO();
    	Logica logica = Logica.getLogica();
    	hijovo.setNombre(nombre.getText());
    	hijovo.setApellidos(apellidos.getText());
    	hijovo.setDni(dni.getText());
    	hijovo.setNombreUsuario(usuario.getText());
    	hijovo.setEmail(email.getText());
    	hijovo.setContrasena(contra1.getText());
    	
    	if(!contra1.getText().equals(contra2.getText())) {
			muestraError("ERROR","Se produjo un error.", "Las contrasenas no coinciden.");
		}else {
			if(dia.getText().length() == 1) {
				dia.setText("0"+dia.getText());
			}
			if(mes.getText().length() == 1) {
				mes.setText("0"+mes.getText());
			}
			fechaString = dia.getText()+"/"+mes.getText()+"/"+ano.getText();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			fecha = LocalDate.parse(fechaString, formatter);
			hijovo.setFechaNacimiento(fechaString);
			LocalDate ahora = LocalDate.now();
			Period periodo = Period.between(fecha, ahora);
			hijovo.setEdad(periodo.getYears());
			
			try {
				logica.registrarUsuario(hijovo);
				this.mostrarVentana("PadreInicio");
				this.cerrarVentana(event);
			}catch(Exception e) {
				if(e instanceof KidHubException) {
					muestraError("ERROR","Se produjo un error.", e.getMessage());
				}else if(e instanceof SQLException && ((SQLException) e).getErrorCode() == 1062)
					muestraError("ERROR","Se produjo un error.", "El hijo ya existe, prueba a registrar hijo ya existente.");
				else if(e instanceof SQLException){
					System.out.println(((SQLException) e).getErrorCode());
					muestraError("ERROR","Se produjo un error.", "Campos invalidos.");
				}
			}	
		}
    }
    
    @FXML
    public void agregarHijo(MouseEvent event) {
    	System.out.println("Agregando hijo existente");
    	Logica logica = Logica.getLogica();
    	HijoVO hijovo = new HijoVO();
    	hijovo.setNombreUsuario(nombreExistente.getText());
    	hijovo.setContrasena(contraExistente.getText());
    	try {
			logica.agregarHijoAPadre(hijovo);
			this.mostrarVentana("PadreInicio");
			this.cerrarVentana(event);
		}catch(Exception e) {
			if(e instanceof KidHubException) {
				muestraError("ERROR","Se produjo un error.", e.getMessage());
			}else if(e instanceof SQLException && ((SQLException) e).getErrorCode() == 1062)
				muestraError("ERROR","Se produjo un error.", "El hijo no existe");
			else if(e instanceof SQLException){
				System.out.println(((SQLException) e).getErrorCode());
				muestraError("ERROR","Se produjo un error.", "Campos invalidos.");
			}
		}	
    }
    
    @FXML
    public void crearTrayecto(MouseEvent event) {
    	System.out.println("Creando trayecto");
    }
    
    @FXML
    public void modificarTrayecto(MouseEvent event) {
    	System.out.println("Modificando trayecto");
    }
    
    @FXML
    public void borrarTrayecto(MouseEvent event) {
    	System.out.println("Borrando trayecto");
    }
    
    @FXML
    public void apuntarHijoActividad(MouseEvent event) {
    	System.out.println("Apuntando hijo a actividad");
    	ActividadTabla actividadTabla;
    	ActividadVO actividad = new ActividadVO();
    	if(actividadesTree.getSelectionModel().getSelectedItem() == null) {
    		this.muestraError("ERROR", "Actividades", "No hay ninguna actividad seleccionada");
    	
    	}else if(this.getHijo().getNombreUsuario().equals("Todo")) {
    		
    		this.muestraError("ERROR", "Actividades", "Seleccione el hijo que quiere apuntar");
    		
    	}else {
    		actividadTabla = actividadesTree.getSelectionModel().getSelectedItem().getValue();
    		actividad.setIdActividad(actividadTabla.getId());    
    		Stage stage = this.esconderVentana(event);
    		this.mostrarDialogo(hijos, actividad, stage);    		
    	}
    }
    
    @FXML
    public void desapuntarHijoActividad(MouseEvent event) {
    	System.out.println("Desapuntando hijo de actividad");
    	ActividadTabla actividadTabla;
    	ActividadVO actividad = new ActividadVO();
    	if(actividadesTree.getSelectionModel().getSelectedItem() == null) {
    		this.muestraError("ERROR", "Actividades", "No hay ninguna actividad seleccionada");
    	
    	}else if(this.getHijo().getNombreUsuario().equals("Todo")) {
    		this.muestraError("ERROR", "Actividades", "Seleccione el hijo que quiere desapuntar");
    	}else {
    		actividadTabla = actividadesTree.getSelectionModel().getSelectedItem().getValue();
    		actividad.setIdActividad(actividadTabla.getId());    		
    		Logica.getLogica().desapuntarHijoDeActividad(this.getHijo(), actividad);
    		this.inicializarTablaActividades(Logica.getLogica().getActividades(this.getHijo()));
    	}
    }
    
    @FXML
    public void apuntarHijoTrayecto(MouseEvent event) {
    	System.out.println("Apuntando hijo a trayecto");    	
    }
    
    @FXML
    public void desapuntarHijoTrayecto(MouseEvent event) {
    	System.out.println("Desapuntando hijo de trayecto");
    }
    
    @FXML
    //??? Tiene que llamar a consultarActividades del hijo?
    public void consultarActividadesHijo(MouseEvent event) {
    	System.out.println("Mostrando actividades hijo");
    }
    
    @FXML
    public void consultarTrayectos(MouseEvent event) {
    	System.out.println("Mostrando trayectos");
    }
    
    @FXML
    public void consultarTrayectosHijo(MouseEvent event) {
    	System.out.println("Mostrando trayectos hijo");
    }
    
    private void mostrarDialogo(ArrayList<HijoVO> hijos, ActividadVO actividad, Stage stage2) {
    	
    	AnchorPane root;
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("../vista/Elegir.fxml"));
    	
    	Stage stage = new Stage();
    	
    	try {
    		root = loader.load();
    		stage.setScene(new Scene(root));
    		stage.setResizable(false);
    	}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	ElegirController controller = loader.getController();
    	controller.initData(hijos, actividad, stage2);
    	
    	stage.show();

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
}