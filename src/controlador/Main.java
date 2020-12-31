package controlador;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

import modelo.Logica;
import modelo.conexion.Conexion;
import modelo.dao.*;
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
		
		//LocalDate fecha = new LocalDate();
		
		PadreVO usuario = new PadreVO("usuario2", "09816624B", "hol2a@adios.com", "Alberto", "Leon", LocalDate.of(2000, 2, 5) ,TipoUsuario.PADRE, "699066552");
		
		StringBuilder insertQuery = new StringBuilder();
		insertQuery.append("'" + usuario.getNombreUsuario() + "', '" + usuario.getDni() + "', '" + usuario.getContrasena() + "', '" + usuario.getEmail() + "', '");
		insertQuery.append(usuario.getNombre() + "', '" + usuario.getApellidos() + "', '" + usuario.getFechaNacimiento() + "', '" + usuario.getEmail() + "'");
		
		
		System.out.println(insertQuery.toString());
		
		Logica.registrarPadre(usuario);
		Logica.mostrarUsuarios();

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
}
