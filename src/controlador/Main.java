package controlador;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

import modelo.KidHubException;
import modelo.Logica;
import modelo.conexion.Conexion;
import modelo.dao.*;
import modelo.vo.ActividadVO;
import modelo.vo.Direccion;
import modelo.vo.HijoVO;
import modelo.vo.MonitorVO;
import modelo.vo.PadreVO;
import modelo.vo.ParadaVO;
import modelo.vo.TrayectoVO;
import modelo.vo.ParadaVO.TipoParada;
import modelo.vo.TrayectoVO.TipoTrayecto;
import modelo.vo.UsuarioVO.TipoUsuario;
import javafx.application.Application;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	
	static PadreVO padre = new PadreVO("usuario1", "03216694B", "passwd","padre@kidhub.com", "Alberto", "Perez", "12/12/2000" ,TipoUsuario.PADRE, "792016552");
	static MonitorVO monitor = new MonitorVO("usuario2", "03815694B", "passwd","monitor@kidhub.com", "Juan", "Lebron", "12/12/2000" ,TipoUsuario.MONITOR, "799016552", "Deporte");
	static HijoVO hijo = new HijoVO("usuario3", "01816654Z", "passwd","hijo@kidhub.com", "Pablo", "Iglesias", "12/12/2000" ,TipoUsuario.HIJO);
	static ActividadVO actividad = new ActividadVO("Ajedrez", LocalDateTime.of(2021, 1, 1, 17, 30), LocalDateTime.of(2021, 1, 1, 18, 30), 5, new Direccion("Calle Ancha", 3, 24007, "Leon"), "Deportiva");
	static TrayectoVO trayecto = new TrayectoVO(actividad, 4, TipoTrayecto.IDA, LocalDateTime.of(2021, 1, 1, 17, 00), new Direccion("Calle Santos", 9, 24008, "Leon"));

	
	@Override
	public void start(Stage primaryStage) {
		mostrarLogin(primaryStage);
	}
	
	public static void main(String[] args) {
		
		//TODO estos metodos son para probar los DAO
		crearMonitor();
		crearActividad();
		crearPadre();
		crearHijo();
		apuntarHijoAActividad();
		//desapuntarHijoDeActividad();
		modificarActividad();
		//borrarActividad();
		crearTrayecto();
		//launch(args);
	}
	
    private void mostrarLogin(Stage primaryStage) {
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("../vista/Login.fxml"));
			Scene scene = new Scene(root);
			//scene.getStylesheets().add(getClass().getResource("vista/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}

    }
    
    //TODO metodos de prueba para la BBDD
    public static void crearPadre() {
		//LocalDate fecha = new LocalDate();
		
		Logica logica = Logica.getLogica();
				
		try {
			logica.registrarUsuario(padre);
		} catch (KidHubException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logica.mostrarUsuarios();
    }
    
    public static void crearMonitor() {
		//LocalDate fecha = new LocalDate();
		
		Logica logica = Logica.getLogica();
				
		try {
			logica.registrarUsuario(monitor);
		} catch (KidHubException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logica.mostrarUsuarios();
    }
    
    public static void crearHijo() {
		
		Logica logica = Logica.getLogica();
		
		logica.setUsuarioActual(padre);
				
		try {
			logica.registrarUsuario(hijo);
		} catch (KidHubException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logica.mostrarUsuarios();
    }
    
    public static void crearActividad() {
    	Logica logica = Logica.getLogica();
    	
    	logica.setUsuarioActual(monitor);
    	
    	try {
			logica.crearActividad(actividad);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KidHubException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public static void modificarActividad() {
    	Logica logica = Logica.getLogica();
    	
    	logica.setUsuarioActual(monitor);
    	
    	//setters
    	
    	actividad.setCapacidad(60);
    	actividad.setNombre("AjedrezModificado");
    	actividad.setIdActividad(1);//TODO ojo esto habria que sacarlo de la BBDD
    	
    	logica.modificarActividad(actividad);
    }
    
    public static void borrarActividad() {
    	Logica logica = Logica.getLogica();

    	actividad.setIdActividad(1);
    	
    	logica.borrarActividad(actividad);
    }
    
    public static void apuntarHijoAActividad() {
    	//actividad.setIdActividad(1);
    	Logica.getLogica().apuntarHijoAActividad(hijo, actividad);
    }
    
    
    public static void desapuntarHijoDeActividad() {
    	//actividad.setIdActividad(1);
    	Logica.getLogica().desapuntarHijoDeActividad(hijo, actividad);
    }
  
    
    public static void crearTrayecto() {
    	Logica.getLogica().setUsuarioActual(padre);
    	Logica.getLogica().crearTrayecto(trayecto);
    }
    
    
}









