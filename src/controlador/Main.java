package controlador;
import java.io.IOException;
import java.sql.*;

import modelo.conexion.Conexion;
import modelo.dao.*;

import javafx.application.Application;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		mostrarLogin(primaryStage);
	}
	
	public static void main(String[] args) {
		Conexion con = new Conexion();
		ResultSet resultSet;
		con.openConnection();
		Statement statement = con.getSt();
			
            // Create and execute a SELECT SQL statement.
            String selectSql = "SELECT Username, UserPassword FROM USERS;";
            String insert = "INSERT INTO USERS(Username, DNI, UserPassword, Email, FirstName, SecondName, BirthDate, Type) VALUES('PACO', '02770768G', 'JUANCARLOS', 'sant@san.com', 'santi', 'poidaw', '2008-7-04', 'PADRE');";
            try {
				statement.executeUpdate(insert);
            	resultSet = statement.executeQuery(selectSql);
				
				while (resultSet.next()) {
				    System.out.println(resultSet.getString(1) + " " + resultSet.getString(2));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		launch(args);
	}
	
    private void mostrarLogin(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("../vista/Login.fxml"));
			Scene scene = new Scene(root,600,400);
			//scene.getStylesheets().add(getClass().getResource("vista/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}

    }
}
