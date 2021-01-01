package controlador;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

import modelo.Logica;
import modelo.conexion.Conexion;
import modelo.dao.*;
import modelo.vo.ActividadVO;
import modelo.vo.Direccion;
import modelo.vo.HijoVO;
import modelo.vo.MonitorVO;
import modelo.vo.PadreVO;
import modelo.vo.UsuarioVO.TipoUsuario;
import javafx.application.Application;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		mostrarLogin(primaryStage);
	}
	
	public static void main(String[] args) {
		
		//TODO estos metodos son para probar los DAO
		//crearMonitor();
		//crearActividad();
		crearHijo();
		launch(args);
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
		
		Logica logica = new Logica();
		
		PadreVO usuario = new PadreVO("usuario6", "03816694B", "passwd","hSol2wfa@adios.com", "Alberto", "Leon", LocalDate.of(2000, 2, 5) ,TipoUsuario.PADRE, "792016552");
		
		logica.registrarUsuario(usuario);
		logica.mostrarUsuarios();
    }
    
    public static void crearMonitor() {
		//LocalDate fecha = new LocalDate();
		
		Logica logica = new Logica();
		
		MonitorVO usuario = new MonitorVO("usuario6", "03816694B", "passwd","hSol2wfa@adios.com", "Alberto", "Leon", LocalDate.of(2000, 2, 5) ,TipoUsuario.MONITOR, "792016552", "Deporte");
		
		logica.registrarUsuario(usuario);
		logica.mostrarUsuarios();
    }
    
    public static void crearHijo() {
		
		Logica logica = new Logica();
		
		HijoVO usuario = new HijoVO("usuario2", "01816654B", "passwd","hSo3l2wfa@adios.com", "Alberto", "Leon", LocalDate.of(2000, 2, 5) ,TipoUsuario.HIJO);
		
		logica.registrarUsuario(usuario);
		logica.mostrarUsuarios();
    }
    
    public static void crearActividad() {
    	Logica logica = new Logica();
    	
    	logica.setUsuarioActual(new MonitorVO("usuario6"));
    	
    	logica.crearActividad(new ActividadVO("Ajedrez", LocalDateTime.of(2021, 1, 1, 17, 30), LocalDateTime.of(2021, 1, 1, 18, 30), 5, new Direccion("Calle Ancha", 3, 24007, "Leon"), "Deportiva"));
    }
    
    
    
    
    
    
    
    
    
    
}
