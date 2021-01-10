package controlador;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;

/**
 * @version 1.0
 * @author Diego Simon Gonzalez, Pablo Bayon Gutierrez, Santiago Valbuena Rubio
 */
public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		mostrarLogin(primaryStage);
	}
	
	static Logger logger = Logger.getLogger(Main.class);
	
	public static void main(String[] args) {
	
		DOMConfigurator.configure("./logs/log4j.xml");
		logger.info("La aplicacion se ha iniciado");
		launch(args);
		
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
}









