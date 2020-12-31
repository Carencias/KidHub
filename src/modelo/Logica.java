package modelo;

import modelo.dao.UsuarioDAO;
import modelo.vo.PadreVO;

public class Logica {
	
	public Logica() {
		
	}
	
	public static void registrarPadre(PadreVO padre) {
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		
		usuarioDAO.registrarUsuario(padre);
	}
	
	public static void mostrarUsuarios() {
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		usuarioDAO.mostrarUsuarios();
	}
}
