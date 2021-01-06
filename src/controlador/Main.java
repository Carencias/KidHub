package controlador;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

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
	static ActividadVO actividad = new ActividadVO("Ajedrez", LocalDateTime.of(2021, 1, 1, 17, 30), LocalDateTime.of(2021, 1, 1, 18, 30), 5, new Direccion("Calle Ancha", 3, "24007", "Leon"), "Deportiva");
	static TrayectoVO trayecto = new TrayectoVO(actividad, 4, TipoTrayecto.IDA, LocalDateTime.of(2021, 1, 1, 17, 00), new Direccion("Calle Santos", 9, "24008", "Leon"));

	
	@Override
	public void start(Stage primaryStage) {
		mostrarLogin(primaryStage);
	}
	
	static Logger logger = Logger.getLogger(Main.class);
	
	public static void main(String[] args) {
		
		//TODO estos metodos son para probar los DAO
		//crearMonitor();
		//crearActividad();
		//crearPadre();
		//crearHijo();
		//apuntarHijoAActividad();
		//desapuntarHijoDeActividad();
		//modificarActividad();
		//borrarActividad();
		//crearTrayecto();
		//apuntarHijoATrayecto();
		//desapuntarHijoDeTrayecto();
		modificarTrayecto();
		DOMConfigurator.configure("./logs/log4j.xml");
		logger.info("La aplicacion se ha iniciado");
		//launch(args);
	}
	
    private void mostrarLogin(Stage primaryStage) {
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("../vista/Login.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
			logger.info("Se ha mostrado la ventana de inicio de sesion");
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
    }
    
    public static void crearActividad() {
    	Logica logica = Logica.getLogica();
    	
    	logica.setUsuarioActual(monitor);
    	
    	try {
			logica.crearActividad(actividad);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public static void modificarActividad() {
    	Logica logica = Logica.getLogica();
    	
    	logica.setUsuarioActual(monitor);
    	    	
    	actividad.setCapacidad(60);
    	actividad.setNombre("AjedrezModificado");
    	
    	try {
			logica.modificarActividad(actividad);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public static void borrarActividad() {
    	Logica logica = Logica.getLogica();
    	try {
			logica.borrarActividad(actividad);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public static void apuntarHijoAActividad() {
    	Logica.getLogica().setUsuarioActual(padre);
    	try {
			Logica.getLogica().apuntarHijoAActividad(hijo, actividad);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    
    public static void desapuntarHijoDeActividad() {
    	Logica.getLogica().setUsuarioActual(padre);
    	try {
			Logica.getLogica().desapuntarHijoDeActividad(hijo, actividad);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
  
    
    public static void crearTrayecto() {
    	Logica.getLogica().setUsuarioActual(padre);
    	try {
			Logica.getLogica().crearTrayecto(trayecto);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public static void modificarTrayecto() {
    	Logica logica = Logica.getLogica();
    	
    	logica.setUsuarioActual(padre);
    	   
    	trayecto.setIdTrayecto(11); //TODO esto deberia ser automatico
    	trayecto.setCapacidad(80);
    	
    	try {
			logica.modificarTrayecto(trayecto);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public static void apuntarHijoATrayecto() {
    	Logica.getLogica().setUsuarioActual(padre);
    	Logica.getLogica().apuntarHijoATrayecto(hijo, trayecto);
    }
    
    public static void desapuntarHijoDeTrayecto() {
    	Logica.getLogica().setUsuarioActual(padre);
    	//TODO aqui se le pasa TrayectoID=0, no se si es porque trayecto es estatico
    	trayecto.setIdTrayecto(1);
    	Logica.getLogica().desapuntarHijoDeTrayecto(hijo, trayecto);
    }
    
    
}









