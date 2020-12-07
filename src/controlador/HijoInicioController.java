package controlador;

import java.awt.Button;
import java.awt.Label;

import javafx.fxml.FXML;
import javafx.scene.*;

public class HijoInicioController {
	
	@FXML
	private Button actividades;
	
	@FXML
	private Button trayectos;
	
	
	public void mostrarActividades() {
		System.out.println("Aqui se mostraran las actividades del hijo.");
	}
	
	
	public void mostrarTrayectos() {
		System.out.println("Aqui se mostraran los trayectos del hijo.");
	}
}
