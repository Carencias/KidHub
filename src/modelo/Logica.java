package modelo;

import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import controlador.MonitorInicioController;
import modelo.vo.*;
import modelo.dao.*;
import modelo.vo.UsuarioVO.TipoUsuario;

/**
 * Clase que implementa el patron Singleton, encargada de la logica de kidhub, actuando de intermediaria entre el modelo y los controladores,
 * realizando las comprobaciones necesaria en cada operacion.
 * @version 1.0
 */
public class Logica {
	
	/**
	 * Atributo que guarda la informacion del usuario que actualmente se encuentra logueado en la aplicacion
	 */
	private UsuarioVO usuarioActual;
	
	/**
	 * Atributo privado que guarda la unica instancia de la aplicacion
	 */
	private static Logica logica;
	static Logger logger = Logger.getLogger(Logica.class);
	/**
	 * Constructor privado de la clase, crea un objeto Logica
	 */
	private Logica() {}
	
	/**
	 * Getter que devuelve la unica instancia de la clase, si esta no esta creada aun la crea.
	 * @return
	 *  Instancia de la clase Logica
	 */
	public static Logica getLogica() {
		if(logica == null) {
			logger.trace("La instancia Logica aun no esta creada, creandola");
			logica = new Logica();
		}
		logger.trace("Devolviendo la instancia de la logica");
		return logica;
	}
	
	/**
	 * Metodo que setea el usuario actual
	 * @param usuario
	 *  Usuario actualmente logueado
	 */
	public void setUsuarioActual(UsuarioVO usuario){
		logger.trace("Devolviendo el usuario actual");
		this.usuarioActual = usuario;
	}
	
	/**
	 * Metodo que comprueba que los valores introducidos son correctos, y se comunica con el modelo para registrar un usuario en la aplicacion
	 * @param usuario
	 *  Objeto de tipo UsuarioVO con la informacion del usuario a registrar
	 * @throws KidHubException
	 *  Excepcion de tipo KidHub si algun dato introducido es invalido
	 * @throws SQLException
	 *  Excepcion de tipo SQLException si se ha producido algun error relacionado con la base de datos
	 */
	public void registrarUsuario(UsuarioVO usuario) throws KidHubException, SQLException{
		logger.trace("Registrando usuario");
		if(usuario.getNombre().equals("") || usuario.getApellidos().equals("") || usuario.getContrasena().equals("") || usuario.getNombreUsuario().equals("") || usuario.getFechaNacimiento().equals("") || usuario.getDni().equals("") || usuario.getEmail().equals("")) {
			throw new KidHubException("Hay campos sin rellenar");
		}
		new UsuarioDAO().registrarUsuario(usuario);
		
		if(usuario.getTipo()==TipoUsuario.HIJO) {
			agregarHijoAPadre((HijoVO) usuario);
		}
	}
	
	/**
	 * Metodo que comprueba que los valores introducidos son correctos, y se comunica con el modelo para agregar un hijo a un padre
	 * @param hijo
	 *  HijoVO con los datos del hijo para agregar al padre
	 * @throws SQLException
	 *  Excepcion de tipo SQLException si se ha producido algun error relacionado con la base de datos
	 * @throws KidHubException
	 *  Excepcion de tipo KidHub si algun dato introducido es invalido
	 */
	public void agregarHijoAPadre(HijoVO hijo) throws SQLException, KidHubException{
		logger.trace("Agregando hijo a padre");
		if(hijo.getNombreUsuario().equals("") || hijo.getContrasena().equals("")) {
			throw new KidHubException("Hay campos sin rellenar");
		}
		new PadreDAO().agregarHijoAPadre(hijo, (PadreVO) this.usuarioActual);
	}
	
	/**
	 * Metodo que comprueba que los valores introducidos son correctos, y se comunica con el modelo para loguear un usuario en la aplicacion
	 * @param usuario
	 *  UsuarioVO con los datos del usuario que se esta logueando
	 * @return
	 *  Devuelve el tipo de usuario logueado
	 * @throws KidHubException
	 *  Lanza la excepcion si hay campos sin rellenar o si el usuario o la contrasena son incorrectos
	 * @throws SQLException
	 *  Lanza la excepcion si se ha producido un error con la base de datos
	 */
	public TipoUsuario loguearUsuario(UsuarioVO usuario) throws KidHubException, SQLException{
		logger.trace("Logueando usuario");
		if(usuario.getNombreUsuario().equals("") || usuario.getContrasena().equals("")) {
			throw new KidHubException("Hay campos sin rellenar");
		}
		TipoUsuario tipo = TipoUsuario.INCORRECTO;
		String contrasena = usuario.getContrasena();
		String usuarion = usuario.getNombreUsuario();
		
		this.usuarioActual = new UsuarioDAO().loguearUsuario(usuario);
		if(usuarioActual.getContrasena().equals(contrasena) || usuarioActual.getNombreUsuario().equals(usuarion)) {
			logger.trace("Usuario y contrasena correctos");
			logger.trace("Usuario: "+usuarion+" ha accedido a su cuenta correctamente");
			tipo = this.usuarioActual.getTipo();
		}else {
			throw new KidHubException("Usuario y/o contrasena incorrecto");
		}
		return tipo;
	}
	
	/**
	 * Metodo que agrega el nombre de usuario del monitor a la actividad, y se comunica con el modelo para crear la actividad
	 * @param actividadVO
	 *  ActividadVO con los datos de la actividad para crear
	 * @throws SQLException
	 */
	public void crearActividad(ActividadVO actividadVO) throws SQLException{
		logger.trace("Creando  actividad");
		actividadVO.setMonitor((MonitorVO) this.usuarioActual);
		new ActividadDAO().crearActividad(actividadVO);
	}
	
	/**
	 * Metodo que agrega el nombre de usuario del monitor a la actividad, y se comunica con el modelo para crear la actividad
	 * @param actividadVO
	 *  ActividadVO con los datos de la actividad para modificar
	 * @throws SQLException
	 */
	public void modificarActividad(ActividadVO actividadVO) throws SQLException{
		logger.trace("Modificando actividad");
		actividadVO.setMonitor((MonitorVO) this.usuarioActual);
		new ActividadDAO().modificarActividad(actividadVO);
	}
	
	/**
	 * Metodo que se comunica con el modelo para borrar la actividad
	 * @param actividadVO
	 *  ActividadVO con los datos de la actividad para borrar
	 * @throws SQLException
	 */
	public void borrarActividad(ActividadVO actividadVO) throws SQLException{		
		new ActividadDAO().borrarActividad(actividadVO);
	}

	/**
	 * Metodo que se comunica con el modelo para obtener los datos de la actividad
	 * @param actividad
	 *  ActividadVO con la actividad para rellenar
	 */
	public void rellenarActividad(ActividadVO actividad) throws SQLException{
		new ActividadDAO().rellenarActividad(actividad);
	}
	
	/**
	 * Metodo que se comunica con el modelo para apuntar a un hijo a una actividad
	 * @param hijo
	 *  HijoVO con la informacion del hijo
	 * @param actividad
	 *  ActividadVO con la informacion de la actividad
	 * @throws SQLException
	 */
	public void apuntarHijoAActividad(HijoVO hijo, ActividadVO actividad) throws SQLException{
		new ActividadDAO().apuntarHijoAActividad(hijo, actividad);
	}
	
	/**
	 * Metodo que se comunica con el modelo para desapuntar a un hijo de una actividad
	 * @param hijo
	 *  HijoVO con la informacion del hijo
	 * @param actividad
	 *  ActividadVO con la informacion de la actividad
	 * @throws SQLException
	 */
	public void desapuntarHijoDeActividad(HijoVO hijo, ActividadVO actividad) throws SQLException{
		new ActividadDAO().desapuntarHijoDeActividad(hijo, actividad);
	}
	
	public void mostrarUsuarios() {
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		usuarioDAO.mostrarUsuarios();
	}

	public boolean comprobarDatosCorrectos() {
		// TODO Auto-generated method stub
		return false;
	}
	

	
	public UsuarioVO getUsuarioActual() {
		return this.usuarioActual;
	}

	public ArrayList<ActividadVO> getActividades(UsuarioVO usuario) {
		return new ActividadDAO().mostrarActividades(usuario);
	}
	
	public ArrayList<TrayectoVO> getTrayectos(UsuarioVO usuario) {
		return new TrayectoDAO().mostrarTrayectos(usuario);
	}
	
	public ArrayList<HijoVO> getHijos(){
		return new PadreDAO().mostrarHijos(this.usuarioActual);
	}
	
	
	public void crearTrayecto(TrayectoVO trayecto) {
		trayecto.setPadre((PadreVO) this.usuarioActual);
		try {
			new TrayectoDAO().crearTrayecto(trayecto);
		} catch (SQLException e) {
			// TODO Manejar errores
			e.printStackTrace();
		}

	}
	
	//TODO
	public void borrarTrayecto(TrayectoVO trayecto) {
		new TrayectoDAO().borrarTrayecto(trayecto);
	}

	public void apuntarHijoATrayecto(HijoVO hijo, TrayectoVO trayecto) {
		
		new TrayectoDAO().apuntarHijoATrayecto(hijo,trayecto);
		
	}

	public void desapuntarHijoDeTrayecto(HijoVO hijo, TrayectoVO trayecto) {
		new TrayectoDAO().desapuntarHijoDeTrayecto(hijo,trayecto);		
	}
}










