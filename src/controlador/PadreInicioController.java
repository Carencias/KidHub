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
import modelo.vo.TrayectoVO;
import modelo.vo.UsuarioVO;
import modelo.vo.UsuarioVO.TipoUsuario;
import vista.ActividadTabla;
import vista.TrayectoTabla;

public class PadreInicioController extends Controller{

	@FXML
    private Button cerrar, registrar, registrarExistente;
	
	@FXML
	private JFXTextField nombre, nombreExistente, contraExistente, dni, apellidos, contra2, usuario,
						 email, telefono, contra1, ano, mes, dia;
    
    @FXML
    private JFXButton inicio, actividades, actividadesHijo, trayectos, trayectosHijo, registrarHijo,
    				  cerrarSesion, apuntarActividad, desapuntarActividad, apuntarTrayecto, desapuntarTrayecto, crear, modificar;
    
    @FXML
    private Pane panoInicio, panoActividades, panoTrayectos, panoRegistrarHijo, panoCerrar;
    
    @FXML
    private Label nombreLabel, hijosVacio, trayectosVacio;
    
    @FXML
    private JFXComboBox<String> selectHijoActividad, selectHijoTrayecto;
    
    @FXML
    private JFXTreeTableView<ActividadTabla> actividadesTree;
    
    private ArrayList<HijoVO> hijos; 
    
    @FXML
    private JFXTreeTableView<TrayectoTabla> trayectosTree;
	
    
    @FXML
    public void initialize() {
    	nombreLabel.setText(Logica.getLogica().getUsuarioActual().getNombre()); 
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
				this.muestraInfo("Registro", "Registro realizadoo", "Se ha asignado su hijo correctamente.");
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
			this.muestraInfo("Registro", "Registro realizadoo", "Se ha asignado su hijo correctamente.");
		}catch(Exception e) {
			if(e instanceof KidHubException) {
				muestraError("ERROR","Se produjo un error.", e.getMessage());
			}else if(e instanceof SQLException && ((SQLException) e).getErrorCode() == 1062) {
				muestraError("ERROR","Se produjo un error.", "El hijo no existe");
			}else if(e instanceof SQLException){
				System.out.println(((SQLException) e).getErrorCode());
				muestraError("ERROR","Se produjo un error.", "Campos invalidos.");
			}
		}	
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
    		
    		this.setHijosComboActividad();
        	
        }else if (actionEvent.getSource() == trayectos) {
        	panoTrayectos.setVisible(true);
        	panoCerrar.setVisible(false);  
        	panoInicio.setVisible(false);
        	panoActividades.setVisible(false);
    		panoRegistrarHijo.setVisible(false); 
    		
    		this.setHijosComboTrayecto();
    	
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
    
    /**
     * ACTIVIDADES
     */
    
    private void setHijosComboActividad() {
    	this.selectHijoActividad.getItems().clear();
    	this.selectHijoActividad.getItems().add("Todos");
    	this.hijos = Logica.getLogica().getHijos();
    	for(HijoVO hijo: hijos) {
    		this.selectHijoActividad.getItems().add(hijo.getNombreUsuario());
    	}    	
    }
    
    private void cargarActividades(HijoVO hijo) {
		this.inicializarTablaActividades(Logica.getLogica().getActividades(hijo)); 
		this.hijosVacio.setVisible(false);
	}


	private HijoVO getHijo() {
		String nombre = this.selectHijoActividad.getSelectionModel().getSelectedItem();
		HijoVO hijo = new HijoVO();
		hijo.setNombreUsuario(nombre);
		return hijo;
	}


	@FXML
	void hijoElegidoActividad(ActionEvent event) {
		
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


	/**
     * TRAYECTOS
     */
	
	private void setHijosComboTrayecto() {
    	this.selectHijoTrayecto.getItems().clear();
    	this.selectHijoTrayecto.getItems().add("Todos");
    	this.selectHijoTrayecto.getItems().add(Logica.getLogica().getUsuarioActual().getNombreUsuario());
    	this.hijos = Logica.getLogica().getHijos();
    	for(HijoVO hijo: hijos) {
    		this.selectHijoTrayecto.getItems().add(hijo.getNombreUsuario());
    	}    	
    }
    
    private void cargarTrayectos(UsuarioVO usuario) {
    	this.inicializarTablaTrayectos(Logica.getLogica().getTrayectos(usuario));
    	this.trayectosVacio.setVisible(false);
    }
    
    private UsuarioVO getUsuarioTrayecto() {
    	
		String nombre = this.selectHijoTrayecto.getSelectionModel().getSelectedItem();
		UsuarioVO usuario = new UsuarioVO();
		if(nombre.equals(Logica.getLogica().getUsuarioActual().getNombreUsuario())) {
			
			usuario.setTipo(TipoUsuario.PADRE);
		}else {
			usuario.setTipo(TipoUsuario.HIJO);
		}
		
		//busque en USERS y le setee al VO el tipo de usuario
		
		
		usuario.setNombreUsuario(nombre);
		return usuario;
	}

	@FXML
    void hijoElegidoTrayecto(ActionEvent  event) {
    	
    	UsuarioVO usuario = this.getUsuarioTrayecto();
    	
    	if(usuario.getNombreUsuario().equals("Todos")) {
    		this.apuntarTrayecto.setDisable(false);
    		this.apuntarTrayecto.setVisible(true);
    		this.crear.setDisable(true);
    		this.crear.setVisible(false);
    		this.modificar.setDisable(true);
    		this.modificar.setVisible(false);
    		this.desapuntarTrayecto.setDisable(true);
    		this.desapuntarTrayecto.setVisible(false);
    		
    		
    	}else if(usuario.getTipo().equals(TipoUsuario.PADRE)){    	
    		this.apuntarTrayecto.setDisable(true);
    		this.apuntarTrayecto.setVisible(false);
    		this.crear.setDisable(false);
    		this.crear.setVisible(true);
    		this.modificar.setDisable(false);
    		this.modificar.setVisible(true);
    		this.desapuntarTrayecto.setDisable(true);
    		this.desapuntarTrayecto.setVisible(false);
    	}else {
    		//hijo
    		this.desapuntarTrayecto.setDisable(false);
    		this.desapuntarTrayecto.setVisible(true);
    		this.apuntarTrayecto.setDisable(true);
    		this.apuntarTrayecto.setVisible(false);
    		this.crear.setDisable(true);
    		this.crear.setVisible(false);
    		this.modificar.setDisable(true); 
    		this.modificar.setVisible(false);
    	}
    	//TODO IMPORTANTE gestionar el tipo del VO que se manda
    	this.trayectosVacio.setVisible(false);
    	this.cargarTrayectos(usuario);
    }
    
    @FXML
    public void crearTrayecto(MouseEvent event) {
    	System.out.println("Creando trayecto");
    	Stage stage = this.esconderVentana(event);
    	this.mostrarVentana("Trayecto");
    }
    
    @FXML
    public void modificarTrayecto(MouseEvent event) {
    	System.out.println("Modificando trayecto");
    	if(trayectosTree.getSelectionModel().getSelectedItem() == null) {
			this.muestraError("ERROR", "Actividades", "No hay ningun trayecto seleccionada");
    	}else {
	    	Stage stage = this.esconderVentana(event);
	    	this.mostrarVentana("Trayecto");
	    	//TODO initData para mandarle los datos del trayecto seleccionado
    	}
    }
    
    @FXML
    public void borrarTrayecto(MouseEvent event) {
    	//TODO esta sin probar
    	System.out.println("Borrando trayecto");
    	
    	if(trayectosTree.getSelectionModel().getSelectedItem() == null) {
			this.muestraError("ERROR", "Actividades", "No hay ningun trayecto seleccionada");
			
		//Si no estan seleccionados los trayectos del padre
		}else if(!this.getUsuarioTrayecto().getNombreUsuario().equals(Logica.getLogica().getUsuarioActual().getNombreUsuario())) {
			this.muestraError("ERROR", "Actividades", "Solo puede borrar los trayectos que haya creado usted.");
		}else {
			
			TrayectoTabla trayectoTabla;
			TrayectoVO trayecto = new TrayectoVO();
			trayectoTabla = this.trayectosTree.getSelectionModel().getSelectedItem().getValue();
			trayecto.setIdTrayecto(trayectoTabla.getId());
			//TODO ese metodo en TrayectoDAO esta vacio
			Logica.getLogica().borrarTrayecto(trayecto);
			this.inicializarTablaTrayectos(Logica.getLogica().getTrayectos(Logica.getLogica().getUsuarioActual()));
		}
    }
    
    @FXML
	public void apuntarHijoTrayecto(MouseEvent event) {
		System.out.println("Apuntando hijo a trayecto");
		if(trayectosTree.getSelectionModel().getSelectedItem() == null) {
			this.muestraError("ERROR", "Actividades", "No hay ningun trayecto seleccionada");
			
			//No hago comprobacioens del usuario porque los botones desaparecen, no hace falta
		}else {
    		TrayectoTabla trayectoTabla = trayectosTree.getSelectionModel().getSelectedItem().getValue();
    		TrayectoVO trayecto = new TrayectoVO();
    		trayecto.setIdTrayecto(trayectoTabla.getId());
    		Stage stage = this.esconderVentana(event);
    		this.mostrarDialogo(hijos, trayecto, stage);
    	}
	}


	@FXML
	public void desapuntarHijoTrayecto(MouseEvent event) {
		//TODO esta sin probar
		System.out.println("Desapuntando hijo de trayecto");
		TrayectoTabla trayectoTabla;
		HijoVO hijo = new HijoVO();
		TrayectoVO trayecto = new TrayectoVO();
		if(trayectosTree.getSelectionModel().getSelectedItem() == null) {
			
			this.muestraError("ERROR", "Actividades", "No hay ningun trayecto seleccionada");
		
		}else if(this.getUsuarioTrayecto().getNombreUsuario().equals("Todo") 
				|| this.getUsuarioTrayecto().getNombreUsuario().equals(Logica.getLogica().getUsuarioActual().getNombreUsuario())) {
			
			this.muestraError("ERROR", "Actividades", "Seleccione el hijo que quiere desapuntar");
		}else {
			//Bayon dice que le hace el cast sin miedo porque al comprobar el tipo de usuario en el metodo
			//hijoElegidoTrayecto se hara invisible este boton cuando el usuario no sea un hijo asique no se podra invocar
			//este metodo si no es un hijo
			hijo = (HijoVO) this.getUsuarioTrayecto();
			trayectoTabla = trayectosTree.getSelectionModel().getSelectedItem().getValue();
			trayecto.setIdTrayecto(trayectoTabla.getId());		
			Logica.getLogica().desapuntarHijoDeTrayecto(hijo, trayecto);
			this.inicializarTablaActividades(Logica.getLogica().getActividades(this.getHijo()));
		}
		
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
	
	
	private void mostrarDialogo(ArrayList<HijoVO> hijos, TrayectoVO trayecto, Stage stage2) {
    	
    	AnchorPane root;
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("../vista/ElegirTrayecto.fxml"));
    	
    	Stage stage = new Stage();
    	
    	try {
    		root = loader.load();
    		stage.setScene(new Scene(root));
    		stage.setResizable(false);
    	}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	ElegirTrayectoController controller = loader.getController();
    	controller.initData(hijos, trayecto, stage2);
    	
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
	}
}