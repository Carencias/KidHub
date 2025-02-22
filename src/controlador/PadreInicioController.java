package controlador;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

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

/**
 * Clase controladora de la ventana del padre
 * @version 1.0
 * @author Diego Simon Gonzalez, Pablo Bayon Gutierrez, Santiago Valbuena Rubio
 */
public class PadreInicioController extends Controller{

	@FXML
    private Button cerrar, registrar, registrarExistente;
	
	@FXML
	private JFXTextField nombre, nombreExistente, contraExistente, dni, apellidos, contra2, usuario,
						 email, telefono, contra1, ano, mes, dia;
    
    @FXML
    private JFXButton inicio, actividades, actividadesHijo, trayectos, trayectosHijo, registrarHijo, cerrarSesion, ayuda, ayudaTrayectos,
    				  apuntarActividad, desapuntarActividad, apuntarTrayecto, desapuntarTrayecto, borrarTrayecto, crear, modificar;
    
    @FXML
    private Pane panoInicio, panoActividades, panoTrayectos, panoRegistrarHijo, panoCerrar;
    
    @FXML
    private Label nombreLabel, hijosVacio, trayectosVacio;
    
    @FXML
    private JFXComboBox<String> selectHijoActividad, selectHijoTrayecto;
    
    @FXML
    private JFXTreeTableView<ActividadTabla> actividadesTree;
    
    @FXML
    private JFXTreeTableView<TrayectoTabla> trayectosTree;
    
    private ArrayList<HijoVO> hijos; 

    
    static Logger logger = Logger.getLogger(PadreInicioController.class);
    
    /**
     * Inicializa la ventana de Monitor, fijando el nombre de usuario del monitor
     */
    @FXML
    public void initialize() {
    	nombreLabel.setText(Logica.getLogica().getUsuarioActual().getNombre());
    }
    
    /**
     * Registra a un hijo en la BBDD
     * @param event
     * Evento que desencadena la accion
     */
    @FXML
	public void registrar(MouseEvent event) {
    	logger.trace("Registrando hijo");
		
		if(!contra1.getText().equals(contra2.getText())) {
			muestraError("ERROR","Se produjo un error.", "Las contrasenas no coinciden.");
		}else if(esUsuarioReservado(usuario.getText())) {
			logger.error("El nombre de usuario elegido esta reservado");
			muestraError("ERROR","Se produjo un error.", "El nombre de usuario elegido esta reservado.");
    	}else {
			
			try {
				Logica.getLogica().registrarUsuario(rellenarHijo());
				logger.info("Se ha registrado el usuario de tipo hijo con exito");
				this.muestraInfo("Registro", "Registro realizado", "Se ha asignado su hijo correctamente.");
			}catch(KidHubException e) {
				muestraError("ERROR","Se produjo un error.", e.getMessage());
			}catch(SQLException e) {
				if(e.getErrorCode() == 1062) {
					logger.error("Se intenta registrar un hijo ya existente");
					muestraError("ERROR","Se produjo un error.", "El hijo ya existe, prueba a registrar hijo ya existente.");
				}else {
					logger.error("Los campos introducidos en el registro del hijo son invalidos");
					muestraError("ERROR","Se produjo un error.", "Campos invalidos.");
				}
					
			}catch(DateTimeParseException e) {
				logger.error("Se ha introducido una fecha invalida en el registro del hijo");
				muestraError("ERROR","Se produjo un error.", ".");
			}

		}
	}
    
    /**
     * Comprueba si el nombre de usuario introducido esta reservado
     * @return
     * True si el nombre de usuario esta reservado
     */
    private boolean esUsuarioReservado(String usuario) {
    	return (usuario.toLowerCase().equals("propios") || usuario.toLowerCase().equals("propios"));
    }
    
    /**
     * Relena los datos del contenedor de informacion del hijo
     * @return
     * Devuelve el hijo relleno
     */
    private HijoVO rellenarHijo() {
    	String fechaString;
		LocalDate fecha;
		HijoVO hijovo = new HijoVO();
		
		hijovo.setNombre(nombre.getText());
		hijovo.setApellidos(apellidos.getText());
		hijovo.setDni(dni.getText());
		hijovo.setNombreUsuario(usuario.getText());
		hijovo.setEmail(email.getText());
		hijovo.setContrasena(contra1.getText());
		
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
		hijovo.setEdad(Period.between(fecha, LocalDate.now()).getYears());
		
		logger.trace("Se han obtenido los datos del hijo introducidos por el usuario");
		
		return hijovo;
    }

    /**
     * Agrega un hijo a los hijos del padre
     * @param event
     * Evento que desencadena la accion
     */
	@FXML
	public void agregarHijo(MouseEvent event) {
		logger.trace("Agregando hijo existente");
		Logica logica = Logica.getLogica();
		HijoVO hijovo = new HijoVO();
		hijovo.setNombreUsuario(nombreExistente.getText());
		hijovo.setContrasena(contraExistente.getText());
		try {
			logica.agregarHijoAPadre(hijovo);
			logger.info("Hijo agregado a su padre con exito");
			this.muestraInfo("Registro", "Registro realizadoo", "Se ha asignado su hijo correctamente.");
		}catch(Exception e) {
			if(e instanceof KidHubException) {
				muestraError("ERROR","Se produjo un error.", e.getMessage());
			}else if(e instanceof SQLException && ((SQLException) e).getErrorCode() == 1062) {
				muestraError("ERROR","Se produjo un error.", "El hijo no existe");
			}else if(e instanceof SQLException){
				muestraError("ERROR","Se produjo un error.", "Campos invalidos.");
			}
		}	
	}

	/**
	 * Elige el panel a mostrar en la ventana
	 * @param actionEvent
	 * Evento que desencadena la accion
	 */
	@FXML
    public void elegirPanel(ActionEvent actionEvent) {
    	
    	if (actionEvent.getSource() == inicio) {      	
    		
    		mostrarPanelesActividadesCerrarInicioTrayectosHijo(false, false, true, false, false);
        	
        } else if (actionEvent.getSource() == actividades) {
    		
    		mostrarPanelesActividadesCerrarInicioTrayectosHijo(true, false, false, false, false);
    		
    		this.setHijosComboActividad();
        	
        }else if (actionEvent.getSource() == trayectos) {

    		mostrarPanelesActividadesCerrarInicioTrayectosHijo(false, false, false, true, false);
    		
    		this.setHijosComboTrayecto();
    	
    	}else if(actionEvent.getSource() == registrarHijo) {

    		mostrarPanelesActividadesCerrarInicioTrayectosHijo(false, false, false, false, true);

    	}else if (actionEvent.getSource() == cerrarSesion) {
    		
    		mostrarPanelesActividadesCerrarInicioTrayectosHijo(false, true, false, false, false);

        }
    }
	
	/**
	 * Muestra u oculta los paneles segun se le indique mediante variables boolean
	 * @param actividades
	 * @param cerrar
	 * @param inicio
	 * @param trayectos
	 * @param hijo
	 */
    private void mostrarPanelesActividadesCerrarInicioTrayectosHijo(boolean actividades, boolean cerrar, boolean inicio, boolean trayectos, boolean hijo) {
    	panoActividades.setVisible(actividades);
    	panoCerrar.setVisible(cerrar);  
    	panoInicio.setVisible(inicio);
    	panoTrayectos.setVisible(trayectos);
    	panoRegistrarHijo.setVisible(hijo);
    	
    }
    
   //Actividades
	
    /**
     * Obtiene los hijos del padre y los setea en la lista de hijos del padre para las actividades
     */
    private void setHijosComboActividad() {
    	this.selectHijoActividad.getItems().clear();
    	this.selectHijoActividad.getItems().add("TODOS");
    	try {
			this.hijos = Logica.getLogica().getHijos();
			for(HijoVO hijo: hijos) {
	    		this.selectHijoActividad.getItems().add(hijo.getNombreUsuario());
	    	} 
		} catch (SQLException e) {
			logger.error("No se han podido obtener las hijos");
       		this.muestraError("ERROR", "Hijos", "No se han podido obtener los hijos");
		}   	   	
    }
    
    /**
     * Carga del modelo todas las actividades del hijo
     * @param hijo
     *  HijoVO con la informacion del hijo del que se cargaran las actividades
     */
    private void cargarActividades(HijoVO hijo) {
    	logger.trace("Cargando las actividades del hijo");
    	try {
    		this.inicializarTablaActividades(Logica.getLogica().getActividades(hijo)); 
       	}catch(SQLException e) {
       		logger.error("No se han podido obtener las actividades");
       		this.muestraError("ERROR", "Actividades", "No se han podido obtener las actividades");
       	}
		this.hijosVacio.setVisible(false);
		this.ayuda.setVisible(false);
	}

    /**
     * Obtiene el hijo del combobox
     * @return
     */
	private HijoVO getHijo() {
		String nombre = this.selectHijoActividad.getSelectionModel().getSelectedItem();
		HijoVO hijo = new HijoVO();
		hijo.setNombreUsuario(nombre);
		return hijo;
	}

	/**
	 * Elige el hijo del combobox
	 * @param event
	 * Evento que desencadena la accion
	 */
	@FXML
	void hijoElegidoActividad(ActionEvent event) {
		
		HijoVO hijo = this.getHijo();
		
		if(hijo.getNombreUsuario() == null) {
			this.actividadesTree.setVisible(false);
			this.hijosVacio.setVisible(true);
			this.ayuda.setVisible(true);
		}else {
			
			if(hijo.getNombreUsuario().equals("TODOS")) {
				this.apuntarActividad.setDisable(false);
				this.desapuntarActividad.setDisable(true);
			}else {
				this.apuntarActividad.setDisable(true);
				this.desapuntarActividad.setDisable(false);
			}
		
			this.cargarActividades(hijo);
		}

	}

	/**
	 * Muestra la ventana para apuntar a un hijo a una actividad
	 * @param event
	 *  Pulsado boton apuntar hijo a actividad
	 */
	@FXML
	public void apuntarHijoActividad(MouseEvent event) {
		logger.trace("Boton de apuntar hijo a actividad pulsado");
		
		if(actividadesTree.getSelectionModel().getSelectedItem() == null) {
			logger.error("Ninguna actividad seleccionada a la que apuntar");
			this.muestraError("ERROR", "Actividades", "No hay ninguna actividad seleccionada");
		}else if(this.getHijo().getNombreUsuario().equals("Todo")) {
			logger.error("Ninguna hijo seleccionado al que apuntar");
			this.muestraError("ERROR", "Actividades", "Seleccione el hijo que quiere apuntar");
		}else {
			ActividadVO actividad = new ActividadVO();
			ActividadTabla actividadTabla = actividadesTree.getSelectionModel().getSelectedItem().getValue();
			actividad.setIdActividad(actividadTabla.getId());    
			Stage stage = this.esconderVentana(event);
			this.mostrarDialogo(hijos, actividad, stage);
			logger.info("Se ha apuntado el hijo");
		}
	}

	/**
	 * Desapunta un hijo de una actividad
	 * @param event
	 *  Pulsado boton despauntar hijo
	 */
	@FXML
	public void desapuntarHijoActividad(MouseEvent event) {
		logger.trace("Boton de desapuntar hijo pulsado");

		if(actividadesTree.getSelectionModel().getSelectedItem() == null) {
			logger.error("Ninguna actividad seleccionada a la que desapuntar");
			this.muestraError("ERROR", "Actividades", "No hay ninguna actividad seleccionada");
		}else if(this.getHijo().getNombreUsuario().equals("Todo")) {
			logger.error("Ninguna hijo seleccionado a el que desapuntar");
			this.muestraError("ERROR", "Actividades", "Seleccione el hijo que quiere desapuntar");
		}else {
			ActividadVO actividad = new ActividadVO();
			ActividadTabla actividadTabla = actividadesTree.getSelectionModel().getSelectedItem().getValue();
			actividad.setIdActividad(actividadTabla.getId());  
			try {
				Logica.getLogica().desapuntarHijoDeActividad(this.getHijo(), actividad);
				this.inicializarTablaActividades(Logica.getLogica().getActividades(this.getHijo()));
			}catch(SQLException e) {
				logger.error("Error en la base de datos: "+e.getMessage());
				muestraError("ERROR","Se produjo un error.", "Formato de algun campo introducido invalido");
			}catch(KidHubException e) {
				logger.error("No hay plazas disponibles en la actividad");
				muestraError("ERROR","Se produjo un error.", "No hay plazas disponibles en la actividad");
			}
		}
	}

	//Trayectos
	
	/**
	 * Obtiene los hijos y los setea en la lista de hijos del padre para los trayectos
	 */
	private void setHijosComboTrayecto() {
    	this.selectHijoTrayecto.getItems().clear();
    	this.selectHijoTrayecto.getItems().add("TODOS");
    	this.selectHijoTrayecto.getItems().add("PROPIOS");
    	try {
			this.hijos = Logica.getLogica().getHijos();
			for(HijoVO hijo: hijos) {
	    		this.selectHijoTrayecto.getItems().add(hijo.getNombreUsuario());
	    	}    
		} catch (SQLException e) {
			logger.error("No se han podido obtener las actividades");
       		this.muestraError("ERROR", "Actividades", "No se han podido obtener las actividades");
		} 		
    }
    
	/**
	 * Carga los trayectos en la tabla
	 * @param usuario
	 * Usuario cuyos trayectos se cargan
	 */
    private void cargarTrayectos(UsuarioVO usuario) {
    	try {
	    	this.inicializarTablaTrayectos(Logica.getLogica().getTrayectos(usuario));
	    	this.trayectosVacio.setVisible(false);
	    	this.ayudaTrayectos.setVisible(false);
    	}catch(SQLException e) {
    		logger.error("No se han podido obtener los trayectos");
       		this.muestraError("ERROR", "Actividades", "No se han podido obtener los trayectos");
    	}
    }
    
    /**
     * Obtiene el usuario del que se quieren ver los trayectos del combobox
     * @return
     * Usuario del que se quieren ver los trayectos
     */
    private UsuarioVO getUsuarioTrayecto() {
    	logger.trace("Obteniendo usuario del que visualizar los traeyctos");
		String nombre = this.selectHijoTrayecto.getSelectionModel().getSelectedItem();
		UsuarioVO usuario = new UsuarioVO();
		
		if(nombre != null) {
			
			if(nombre.equals("PROPIOS") || nombre.equals("TODOS")) {		
				usuario.setTipo(TipoUsuario.PADRE);
			}else {
				usuario.setTipo(TipoUsuario.HIJO);
			}
			
			usuario.setNombreUsuario(nombre);
		}
		return usuario;
	}

    /**
     * Obtiene el hijo seleccionado por el usuario en el combo box y despliega las opciones disponibles en cada caso
     * @param event
     * Evento que desencadena la accion
     */
	@FXML
    void hijoElegidoTrayecto(ActionEvent  event) {
    	
    	UsuarioVO usuario = this.getUsuarioTrayecto();
    	
    	if(usuario.getNombreUsuario() == null) {
    		
    		this.trayectosTree.setVisible(false);
    		this.trayectosVacio.setVisible(true);
    		this.ayudaTrayectos.setVisible(true);
    		
    	}else {
    	
	    	if(usuario.getNombreUsuario().equals("TODOS")) {
	    		
	    		mostrarBotones(true, false, false, false, false);
	    		
	    	}else if(usuario.getNombreUsuario().equals("PROPIOS")){	
	
	    		mostrarBotones(false, true, true, false, true);
	
	    	}else {
	    		mostrarBotones(false, false, false, true, false);
	
	    	}
	    	this.trayectosVacio.setVisible(false);
	    	this.cargarTrayectos(usuario);
    	}
    }
	
	/**
	 * Muestra u oculta los botones del apartado de trayectos de un padre
	 * @param trayecto
	 * True para mostrar el boton Apuntar
	 * @param crear
	 * True para mostrar el boton Crear
	 * @param modificar
	 * True para mostrar el boton Modificar
	 * @param desapuntar
	 * True para mostrar el boton Desapuntar
	 */
	private void mostrarBotones(boolean apuntar, boolean crear, boolean modificar, boolean desapuntar, boolean borrar) {
		this.apuntarTrayecto.setDisable(!apuntar);
		this.apuntarTrayecto.setVisible(apuntar);
		this.crear.setDisable(!crear);
		this.crear.setVisible(crear);
		this.modificar.setDisable(!modificar);
		this.modificar.setVisible(modificar);
		this.desapuntarTrayecto.setDisable(!desapuntar);
		this.desapuntarTrayecto.setVisible(desapuntar);
		this.borrarTrayecto.setDisable(!borrar);
		this.borrarTrayecto.setVisible(borrar);
	}
    
    /**
     * Crea un trayecto nuevo
     * @param event
     * Evento que desencadena la accion
     */
    @FXML
    public void crearTrayecto(MouseEvent event) {
    	logger.trace("Creando trayecto");
    	Stage stage = this.esconderVentana(event);
    	
    	ArrayList<ActividadVO> actividades = new ArrayList<>();
    	HijoVO hijo = new HijoVO();
    	hijo.setNombreUsuario("TODOS");
    	try {
    		actividades = Logica.getLogica().getActividades(hijo);
    	}catch(SQLException e) {
			logger.error("Error al obtener la actividad: "+e.getMessage());
			this.muestraError("ERROR", "Actividades", "Error al obtener la actividad");
		}
    	this.mostrarVentanaTrayecto(actividades, new TrayectoVO(), stage, false);
    }
    
    /**
     * Modifica un trayecto existente
     * @param event
     * Evento que desencadena la accion
     */
    @FXML
    public void modificarTrayecto(MouseEvent event) {
    	logger.trace("Modificando trayecto");
    	TrayectoTabla trayectoTabla;
    	TrayectoVO trayecto = new TrayectoVO();
    	
    	if(trayectosTree.getSelectionModel().getSelectedItem() == null) {
    		logger.error("No hay ningun trayecto seleccionado");
			this.muestraError("ERROR", "Actividades", "No hay ningun trayecto seleccionado");
    	}else {
    		trayectoTabla = trayectosTree.getSelectionModel().getSelectedItem().getValue();
    		trayecto.setIdTrayecto(trayectoTabla.getId());
	    	Stage stage = this.esconderVentana(event);
	    	ArrayList<ActividadVO> actividades = new ArrayList<ActividadVO>();
	    	HijoVO hijo = new HijoVO();
	    	hijo.setNombreUsuario("TODOS");
	    	try {
		    	actividades = Logica.getLogica().getActividades(hijo);
		    	Logica.getLogica().rellenarTrayecto(trayecto);
	    	}catch(SQLException e) {
	    		logger.error("Error al obtener el trayecto: "+e.getMessage());
				this.muestraError("ERROR", "Actividades", "Error al obtener datos");
	    	}
	    	this.mostrarVentanaTrayecto(actividades, trayecto, stage, true);
    	}
    }
    
    /**
     * Borra un trayecto existente
     * @param event
     * Evento que desencadena la accion
     */
    @FXML
    public void borrarTrayecto(MouseEvent event) {
    	logger.trace("Borrando trayecto");
    	
    	if(trayectosTree.getSelectionModel().getSelectedItem() == null) {
    		logger.error("No hay ningun trayecto seleccionada");
			this.muestraError("ERROR", "Actividades", "No hay ningun trayecto seleccionada");
		}else {
			//Los trayectos seleccionados siempre van a ser creados por el padre, si no no tendria disponible el boton
			TrayectoTabla trayectoTabla;
			TrayectoVO trayecto = new TrayectoVO();
			trayectoTabla = this.trayectosTree.getSelectionModel().getSelectedItem().getValue();
			trayecto.setIdTrayecto(trayectoTabla.getId());
			
			try {
				Logica.getLogica().borrarTrayecto(trayecto);
				this.inicializarTablaTrayectos(Logica.getLogica().getTrayectos(Logica.getLogica().getUsuarioActual()));
			} catch (SQLException e) {
	    		logger.error("Error al borrar el trayecto");
				this.muestraError("ERROR", "Actividades", "Error al borrar el trayecto");
			}
		}
    }
    
    /**
     * Apunta a un hijo a un trayecto
     * @param event
     * Evento que desencadena la accion
     */
    @FXML
	public void apuntarHijoTrayecto(MouseEvent event) {
		logger.trace("Apuntando hijo a trayecto");
		if(trayectosTree.getSelectionModel().getSelectedItem() == null) {
			logger.error("No hay ningun trayecto seleccionada");
			this.muestraError("ERROR", "Actividades", "No hay ningun trayecto seleccionada");
		}else {
    		TrayectoTabla trayectoTabla = trayectosTree.getSelectionModel().getSelectedItem().getValue();
    		TrayectoVO trayecto = new TrayectoVO();
    		trayecto.setIdTrayecto(trayectoTabla.getId());
    		Stage stage = this.esconderVentana(event);
    		this.mostrarDialogo(hijos, trayecto, stage);
    	}
	}

    /**
     * Desapunta a un hijo de un trayecto
     * @param event
     * Evento que desencadena la accion
     */
	@FXML
	public void desapuntarHijoTrayecto(MouseEvent event) {
		logger.trace("Desapuntando hijo de trayecto");
		
		TrayectoTabla trayectoTabla;
		UsuarioVO usuario = new UsuarioVO();
		HijoVO hijo = new HijoVO();
		TrayectoVO trayecto = new TrayectoVO();
		
		if(trayectosTree.getSelectionModel().getSelectedItem() == null) {
			logger.error("No hay ningun trayecto seleccionada");
			this.muestraError("ERROR", "Actividades", "No hay ningun trayecto seleccionada");
		
		}else if(hijoSinSeleccionar()) {
			logger.error("Seleccione el hijo que quiere desapuntar");
			this.muestraError("ERROR", "Actividades", "Seleccione el hijo que quiere desapuntar");
		}else {
			//Solo cuando sea un hijo lo que este en el combo se va a poder desapuntar, luego uso un objeto hijoVO
			usuario = this.getUsuarioTrayecto();
			hijo.setNombreUsuario(usuario.getNombreUsuario());
			hijo.setTipo(TipoUsuario.HIJO);
			trayectoTabla = trayectosTree.getSelectionModel().getSelectedItem().getValue();
			trayecto.setIdTrayecto(trayectoTabla.getId());		
			try {
				Logica.getLogica().desapuntarHijoDeTrayecto(hijo, trayecto);
				this.inicializarTablaTrayectos(Logica.getLogica().getTrayectos(hijo));
			} catch (SQLException e) {
	    		logger.error("Error al desapuntar hijo de trayecto");
				this.muestraError("ERROR", "Actividades", "Error al desapuntar hijo de trayecto");
			} catch (KidHubException e) {
	    		logger.error("Error al desapuntar hijo de trayecto: " + e.getMessage());
				this.muestraError("ERROR", "Actividades", e.getMessage());
			}	
		}	
	}
	/**
	 * Comprueba si hay algun hijo seleccionado o no
	 * @return
	 * True si no se ha seleccionado ningun hijo
	 */
	private boolean hijoSinSeleccionar() {
		return this.getUsuarioTrayecto().getNombreUsuario().equals("Todo") 
				|| this.getUsuarioTrayecto().getNombreUsuario().equals(Logica.getLogica().getUsuarioActual().getNombreUsuario());
	}


	/**
	 * Muestra el cuadro de dialogo para elegir uno de sus hijos, que se apuntara a una actividad
	 * @param hijos
	 * Contiene los datos de los hijos del padre que realiza la accion
	 * @param actividad
	 * Contiene los datos de la activdad a la que se va a apuntar al hijo
	 * @param stage2
	 */
	private void mostrarDialogo(ArrayList<HijoVO> hijos, ActividadVO actividad, Stage stage2) {
		
		FXMLLoader loader = mostrarGenerico("Elegir");
    	ElegirController controller = loader.getController();
    	controller.initData(hijos, actividad, stage2);	
    }
	
	/**
	 * Muestra la ventana de trayectos
	 * @param actividades
	 * Contiene los datos de las actividades
	 * @param trayecto
	 * Contiene los datos del trayecto
	 * @param stage2
	 * @param modificacion
	 * True si se esta modificando el trayecto, false si se esta creando
	 */
	private void mostrarVentanaTrayecto(ArrayList<ActividadVO> actividades, TrayectoVO trayecto, Stage stage2, boolean modificacion) {
		FXMLLoader loader = mostrarGenerico("Trayecto");
		
    	TrayectoController controller = loader.getController();    	
    	controller.initData(actividades, trayecto, stage2, modificacion);

	}
	
	/**
	 * Muestra la ventana que se le indique y devuelve el loader usado
	 * @return
	 * loader usado para mostrar la ventana
	 */
	private FXMLLoader mostrarGenerico(String ventana) {
		AnchorPane root;
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("../vista/" + ventana + ".fxml"));
    	Stage stage = new Stage();
    	
    	try {
    		root = loader.load();
    		stage.setScene(new Scene(root));
    		stage.setResizable(false);
    	}catch (IOException e) {
			e.printStackTrace();
		}
    	stage.show();
    	
    	return loader;

	}
	
	@FXML
    void mostrarAyudaActividad(MouseEvent event) {
		this.cerrarVentana(event);
		FXMLLoader loader = mostrarGenerico("Ayuda");
    	AyudaController controller = loader.getController();
    	controller.initData(true);  	
    }
	
	@FXML
    void mostrarAyudaTrayectos(MouseEvent event) {
		this.cerrarVentana(event);
		FXMLLoader loader = mostrarGenerico("Ayuda");
    	AyudaController controller = loader.getController();
    	controller.initData(false);  	
    }
	
	/**
	 * Muestra el cuadro de dialogo para la eleccion del hijo que apuntar al trayecto
	 * @param hijos
	 * Contiene los datos de los hijos del padre que ejecuta la accion
	 * @param trayecto
	 * Contiene los datos del trayecto al que se va a apuntar un hijo
	 * @param stage2
	 */
	private void mostrarDialogo(ArrayList<HijoVO> hijos, TrayectoVO trayecto, Stage stage2) {   	
    	FXMLLoader loader = mostrarGenerico("ElegirTrayecto");
    	ElegirTrayectoController controller = loader.getController();
    	controller.initData(hijos, trayecto, stage2);  	
    }
	
	/**
	 *Inicializa la tabla donde se muestran las actividades en la interfaz grafica 
	 * @param actividades
	 * Contiene los datos de las actividades a mostrar
	 */
	@SuppressWarnings("unchecked")
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
	    aforoCol.setPrefWidth(122);
	    aforoCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ActividadTabla, String>, ObservableValue<String>>() {
	        @Override
	        public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ActividadTabla, String> param) {
	            return param.getValue().getValue().getAforo();
	        }
	    });
	    
	    JFXTreeTableColumn<ActividadTabla, String> idCol = new JFXTreeTableColumn<>("Id");
	    idCol.setPrefWidth(65);
	    idCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ActividadTabla, String>, ObservableValue<String>>() {
	        @Override
	        public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ActividadTabla, String> param) {
	            return param.getValue().getValue().getIdString();
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
	    actividadesTree.getColumns().setAll(nameCol, idCol, inicioCol, finCol, lugarCol, aforoCol);
	    actividadesTree.setRoot(root);
	    actividadesTree.setShowRoot(false);
	    
	    actividadesTree.setVisible(true);
	}
	
	
	/**
	 * Inicializa la tabla donde se muestran los trayectos en la interfaz grafica
	 * @param trayectos
	 * Contiene los datos de los trayectos a mostrar
	 */
	@SuppressWarnings("unchecked")
	private void inicializarTablaTrayectos(ArrayList<TrayectoVO> trayectos) {
		
		/**
		 * CREACION DE LOS HEADERS DE LA TABLA
		 */
		JFXTreeTableColumn<TrayectoTabla, String> actividadCol = new JFXTreeTableColumn<>("Actividad");
		actividadCol.setPrefWidth(115);
		actividadCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<TrayectoTabla, String>, ObservableValue<String>>() {
	        @Override
	        public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<TrayectoTabla, String> param) {
	            return param.getValue().getValue().getActividad();
	        }
	    });
	
	    JFXTreeTableColumn<TrayectoTabla, String> tipoCol = new JFXTreeTableColumn<>("Tipo");
	    tipoCol.setPrefWidth(100);
	    tipoCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<TrayectoTabla, String>, ObservableValue<String>>() {
	        @Override
	        public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<TrayectoTabla, String> param) {
	            return param.getValue().getValue().getTipo();
	        }
	    });
	
	    JFXTreeTableColumn<TrayectoTabla, String> padreCol = new JFXTreeTableColumn<>("Padre");
	    padreCol.setPrefWidth(115);
	    padreCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<TrayectoTabla, String>, ObservableValue<String>>() {
	        @Override
	        public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<TrayectoTabla, String> param) {
	            return param.getValue().getValue().getPadre();
	        }
	    });
	    
	    JFXTreeTableColumn<TrayectoTabla, String> aforoCol = new JFXTreeTableColumn<>("Aforo");
	    aforoCol.setPrefWidth(60);
	    aforoCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<TrayectoTabla, String>, ObservableValue<String>>() {
	        @Override
	        public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<TrayectoTabla, String> param) {
	            return param.getValue().getValue().getAforo();
	        }
	    });
	    
	    JFXTreeTableColumn<TrayectoTabla, String> origenCol = new JFXTreeTableColumn<>("Origen");
	    origenCol.setPrefWidth(175);
	    origenCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<TrayectoTabla, String>, ObservableValue<String>>() {
	        @Override
	        public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<TrayectoTabla, String> param) {
	            return param.getValue().getValue().getOrigenTexto();
	        }
	    });
	    
	    JFXTreeTableColumn<TrayectoTabla, String> destinoCol = new JFXTreeTableColumn<>("Destino");
	    destinoCol.setPrefWidth(175);
	    destinoCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<TrayectoTabla, String>, ObservableValue<String>>() {
	        @Override
	        public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<TrayectoTabla, String> param) {
	            return param.getValue().getValue().getDestinoTexto();
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
	    trayectosTree.getColumns().setAll(actividadCol, tipoCol, padreCol, aforoCol, origenCol, destinoCol);
	    trayectosTree.setRoot(root);
	    trayectosTree.setShowRoot(false);
	    
	    trayectosTree.setVisible(true);
	}
}