package controlador;

import java.io.IOException;

import org.apache.log4j.Logger;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import modelo.Logica;

/**
 * Controlador principal que se encarga de mostrar ventanas, cerrar ventanas, esconder ventanas, recuperar ventanas y mostrar errores
 * del que heredan el resto de controladores.
 * @version 1.0
 */
public class Controller {
	
	/**
	 * Atributo logica, en el que se encuentra la logica de la aplicacion
	 */
	protected Logica logica;
	static Logger logger = Logger.getLogger(Controller.class);
	/**
	 * Metodo que cierra la ventana
	 * @param event
	 *  Pulsado boton cerrar ventana
	 */
	protected void cerrarVentana(MouseEvent event) {
		logger.trace("Cerrando ventana");
		Node source = (Node) event.getSource();
	    Stage stage = (Stage) source.getScene().getWindow();
	    stage.close();
	}
	
	/**
	 * Metodo que muestra una ventana
	 * @param ventana
	 *  Nombre de la ventanan a mostrar
	 */
	protected void mostrarVentana(String ventana) {
		AnchorPane root;
		try {
			logger.trace("Mostrando ventana: "+ventana);
			root = (AnchorPane) FXMLLoader.load(getClass().getResource("../vista/" + ventana + ".fxml"));
			//scene.getStylesheets().add(getClass().getResource("vista/application.css").toExternalForm());
			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.setResizable(false);
			stage.show();
		} catch (IOException e) {
			logger.fatal("Error al mostrar la ventana: "+e.getMessage());
		}
    }
	
	/**
	 * Metodo que esconde una ventana
	 * @param event
	 *  Pulsado esconder ventana
	 * @return
	 *  Devuelve el stage
	 */
	protected Stage esconderVentana(MouseEvent event) {
		logger.trace("Minimizando ventana");
		Node source = (Node) event.getSource();
	    Stage stage = (Stage) source.getScene().getWindow();
	    stage.hide();
	    return stage;
	}
	
	/**
	 * Metodo que recupera una ventana
	 * @param stage
	 *  Ventana a recuperar
	 */
	protected void recuperarVentana(Stage stage) {
		logger.trace("mostrando ventana");
		stage.show();
	}
	
	/**
	 * Metodo que muestra un error
	 * @param titulo
	 *  Titulo del error
	 * @param encabezado
	 *  Encabezado del error
	 * @param contenido
	 *  Contenido del error
	 */
	protected void muestraError(String titulo, String encabezado, String contenido) {
		logger.trace("Mostrando error");
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(titulo);
		alert.setHeaderText(encabezado);
		alert.setContentText(contenido);
		alert.showAndWait();
	}
	
	/**
	 * Metodo que muestra informacion
	 * @param titulo
	 *  Titulo de la informacion
	 * @param encabezado
	 *  Encabezado de la informacion
	 * @param contenido
	 *  Contenido de la informacion
	 */
	protected void muestraInfo(String titulo, String encabezado, String contenido) {
		logger.trace("Mostrando informacion");
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(titulo);
		alert.setHeaderText(encabezado);
		alert.setContentText(contenido);
		alert.showAndWait();
	}
	
	/**
	 * Metodo que muestra la ventana de login
	 * @param event
	 *  Boton de cerrar sesion pulsado
	 */
    @FXML
    protected void logOut(MouseEvent event) {
    	logger.trace("Cerrando la sesion y volviendo al login");
    	this.mostrarVentana("Login");
    	this.cerrarVentana(event);
    }
}
