package controlador;


import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import modelo.KidHubException;
import modelo.Logica;
import modelo.vo.UsuarioVO;
import modelo.vo.UsuarioVO.TipoUsuario;


public class LoginController extends Controller{
	
	@FXML
	private JFXTextField textoUsuario, contrasenaVisible;
	
	@FXML
	private JFXPasswordField textoContrasena;
	
	@FXML
	private JFXCheckBox mostrar;
	
	@FXML
    private Button logOut;
	
	static final int MONITOR = 0; 
	
	static final int PADRE = 1;
	
	static final int HIJO = 2;
	
	static final int LOGIN_INCORRECTO = -1;
	
	@FXML
	public void cambiarCaracteres() {
		
		if(this.mostrar.isSelected()) {			
			this.contrasenaVisible.setText(this.textoContrasena.getText());
			this.textoContrasena.setVisible(false);
			this.contrasenaVisible.setVisible(true);
		}else {
			this.textoContrasena.setText(this.contrasenaVisible.getText());
			this.textoContrasena.setVisible(true);
			this.contrasenaVisible.setVisible(false);
		}
	}

	
	@FXML
	public void login(MouseEvent event) throws KidHubException{
		
		String nombreUsuario, contrasena;
		TipoUsuario tipoUsuario;
		
		nombreUsuario = textoUsuario.getText();
		contrasena = textoContrasena.getText();
		
		System.out.println("Comprobando usuario:" + nombreUsuario + " " + contrasena);
				
		UsuarioVO usuario = new UsuarioVO();
		usuario.setNombreUsuario(nombreUsuario);
		usuario.setContrasena(contrasena);
		
		this.logica = new Logica();
		
		tipoUsuario = logica.loguearUsuario(usuario);
		
		if(tipoUsuario == TipoUsuario.INCORRECTO) {
			muestraError("ERROR","Se produjo un error.", "Credenciales invalidas.");
		}else {
			this.mostrarVentana(elegirVentana(tipoUsuario));
			this.cerrarVentana(event);
		}
	}
	
	
	/*private int obtenerTipoUsuario(String usuario, String contrasena) {
		if(comprobarCredencialesOK(usuario, contrasena)) {
			//TODO comprobar usuario con BBDD
			return HIJO;
		}else {
			return LOGIN_INCORRECTO;
		}
	}*/
	
	
	/*private boolean comprobarCredencialesOK(String usuario, String contrasena) {
		//TODO comprobar si usuario y contrasena son correctos
		return true;
	}*/
	
	@FXML
	public void registrar(MouseEvent event) {
		
		this.mostrarVentana("Registro");
		this.cerrarVentana(event);

	}
	
	
	String elegirVentana(TipoUsuario tipoUsuario) {
		switch (tipoUsuario) {
		
		case MONITOR: 
			return "MonitorInicio";
		
		case PADRE:
			return "PadreInicio";
		
		default:
			return "HijoInicio";
		}
	}
	
}
