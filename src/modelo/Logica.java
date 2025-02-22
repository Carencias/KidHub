package modelo;

import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import modelo.vo.*;
import modelo.dao.*;
import modelo.vo.UsuarioVO.TipoUsuario;

/**
 * Clase que implementa el patron Singleton, encargada de la logica de kidhub, actuando de intermediaria entre el modelo y los controladores,
 * realizando las comprobaciones necesaria en cada operacion.
 * @version 1.0
 * @author Diego Simon Gonzalez, Pablo Bayon Gutierrez, Santiago Valbuena Rubio
 */
public class Logica {
	
	/**
	 * Usuario que actualmente se encuentra logueado en la aplicacion
	 */
	private UsuarioVO usuarioActual;
	
	/**
	 * Unica instancia de la logica (Singleton)
	 */
	private static Logica logica;
	static Logger logger = Logger.getLogger(Logica.class);
	
	/**
	 * Constructor privado de la clase, crea un objeto Logica
	 */
	private Logica() {}
	
	/**
	 * Devuelve la unica instancia de la clase. Si esta no esta creada aun, la crea.
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
	 * Asigna el usuario actual
	 * @param usuario
	 *  Usuario que ha iniciado sesion
	 */
	public void setUsuarioActual(UsuarioVO usuario){
		this.usuarioActual = usuario;
		logger.trace("Asignando el usuario actual");
	}
	
	/**
	 * Comprueba que los valores introducidos son correctos, y se comunica con el modelo para registrar un usuario en la aplicacion
	 * @param usuario
	 *  Contiene la informacion del usuario a registrar
	 * @throws KidHubException
	 *  Si algun dato introducido es invalido
	 * @throws SQLException
	 *  Si se ha producido algun error relacionado con la base de datos
	 */
	public void registrarUsuario(UsuarioVO usuario) throws KidHubException, SQLException{
		if(camposRegistroUsuarioIncompletos(usuario)) {
			logger.error("Se ha intentado registrar un usuario sin introducir todos los campos necesarios");
			throw new KidHubException("Hay campos sin rellenar");
		}
		
		logger.trace("Se han introducido todos los campos para registar a un usuario");
		new UsuarioDAO().registrarUsuario(usuario);
		
		if(usuario.getTipo()==TipoUsuario.HIJO) {
			agregarHijoAPadre((HijoVO) usuario);
		}
	}
	
	/**
	 * Comprueba si alguno de los campos que hay que rellenar para registrar a un usuario esta vacio
	 * @param usuario
	 * contiene los datos introducidos para registrar a un usuario
	 * @return
	 * true si alguno de los campos esta vacio
	 */
	private boolean camposRegistroUsuarioIncompletos(UsuarioVO usuario) {
		return usuario.getNombre().equals("") || usuario.getApellidos().equals("") || 
				usuario.getContrasena().equals("") || usuario.getNombreUsuario().equals("") || 
				usuario.getFechaNacimiento().equals("") || usuario.getDni().equals("") || usuario.getEmail().equals("");
	}
	
	/**
	 * Comprueba que los campos necesarios no estan vacios y se comunica con el modelo para agregar un hijo a un padre
	 * @param hijo
	 *  contiene los datos del hijo para agregar al padre
	 * @throws SQLException
	 *  Si se ha producido algun error relacionado con la base de datos
	 * @throws KidHubException
	 *  Si algun dato introducido es invalido
	 */
	public void agregarHijoAPadre(HijoVO hijo) throws SQLException, KidHubException{
		logger.trace("Agregando hijo a padre");

		comprobarCamposLoginNoVacios(hijo);
		
		new PadreDAO().agregarHijoAPadre(hijo, (PadreVO) this.usuarioActual);
	}
	
	/**
	 * Permite que el usuario acceda a la aplicacion si ha introducido sus credenciales correctamente
	 * @param usuario
	 *  Contiene los datos del usuario que se esta logueando
	 * @return
	 *  tipo de usuario logueado
	 * @throws KidHubException
	 *  Si hay campos sin rellenar o si el usuario o la contrasena son incorrectos
	 * @throws SQLException
	 *  Si se ha producido un error con la base de datos
	 */
	public TipoUsuario loguearUsuario(UsuarioVO usuario) throws KidHubException, SQLException{
		logger.trace("Logueando usuario");
		
		comprobarCamposLoginNoVacios(usuario);
		
		TipoUsuario tipo;
		
		if(credencialesCorrectas(usuario)) {
			logger.trace("Usuario y contrasena correctos");
			logger.info("Usuario: "+ usuario.getNombreUsuario() +" ha accedido a su cuenta correctamente");
			usuario = new UsuarioDAO().loguearUsuario(usuario); //Se agregan los datos basicos al usuario al acceder a la BBDD
			this.usuarioActual = usuario;
			tipo = this.usuarioActual.getTipo();
		}else {
			throw new KidHubException("Usuario y/o contrasena incorrectos");
		}
				
		return tipo;
	}
	
	/**
	 * Comprueba que la conterasena y el usuario introducidos sean correctos
	 * @param usuario
	 * Contiene el usuario y la contrasena introducidos
	 * @return
	 * true si son correctos, false si no
	 * @throws SQLException 
	 */
	private boolean credencialesCorrectas(UsuarioVO usuario) throws SQLException {	
		
		return new UsuarioDAO().credencialesCorrectas(usuario); 
	}
	
	/**
	 * Comprueba que los campos usuario y contrasena introducidos no esten vacios
	 * @param usuario
	 * Contiene el usuario y la contrasena a comprobar
	 * @throws KidHubException
	 * Si alguno de los campos esta vacio
	 */
	private void comprobarCamposLoginNoVacios(UsuarioVO usuario) throws KidHubException {
		if(usuario.getNombreUsuario().equals("") || usuario.getContrasena().equals("")) {
			logger.error("El usuario o la contrasena estan vacios");
			throw new KidHubException("Hay campos sin rellenar");
		}
	}
	
	/**
	 * Agrega el nombre de usuario del monitor a la actividad, y se comunica con el modelo para crear la actividad
	 * @param actividadVO
	 *  ActividadVO con los datos de la actividad para crear
	 * @throws SQLException
	 */
	public void crearActividad(ActividadVO actividadVO) throws SQLException, KidHubException{
		logger.trace("Creando  actividad");
		actividadVO.setMonitor((MonitorVO) this.usuarioActual);
		if(comprobarDisponibilidadActividad(actividadVO, this.usuarioActual)) {
			new ActividadDAO().crearActividad(actividadVO);
		}else {
			throw new KidHubException("Ya tiene programada una actividad en ese horario");
		}
	}

	/**
	 * Metodo que agrega el nombre de usuario del monitor a la actividad, y se comunica con el modelo para crear la actividad
	 * @param actividadVO
	 *  ActividadVO con los datos de la actividad para modificar
	 * @throws SQLException
	 */
	public void modificarActividad(ActividadVO actividadVO) throws SQLException, KidHubException{
		logger.trace("Modificando actividad");
		actividadVO.setMonitor((MonitorVO) this.usuarioActual);
		if(comprobarDisponibilidadActividad(actividadVO,this.usuarioActual)) {
			new ActividadDAO().modificarActividad(actividadVO);
		}else {
			throw new KidHubException("Ya tiene programada una actividad en ese horario");
		}
	}
	
	/**
	 * Metodo que se comunica con el modelo para borrar la actividad
	 * @param actividadVO
	 *  ActividadVO con los datos de la actividad para borrar
	 * @throws SQLException
	 */
	public void borrarActividad(ActividadVO actividadVO) throws SQLException{		
		new ActividadDAO().borrarActividad(actividadVO);
		logger.trace("Actividad borrada correctamente");
	}

	/**
	 * Metodo que se comunica con el modelo para obtener los datos de la actividad sabiendo su ID
	 * @param actividad
	 *  ActividadVO con la actividad para rellenar
	 */
	public void rellenarActividad(ActividadVO actividad) throws SQLException{
		new ActividadDAO().rellenarActividad(actividad);
		logger.trace("Actividad rellenada correctamente");
	}
	
	/**
	 * Metodo que se comunica con el modelo para apuntar a un hijo a una actividad
	 * @param hijo
	 *  HijoVO con la informacion del hijo
	 * @param actividad
	 *  ActividadVO con la informacion de la actividad
	 * @throws SQLException
	 */
	public void apuntarHijoAActividad(HijoVO hijo, ActividadVO actividad) throws SQLException, KidHubException{
		logger.trace("Apuntando hijo a actividad");	
		if(comprobarDisponibilidadActividad(actividad, hijo)) {
			new ActividadDAO().apuntarHijoAActividad(hijo, actividad);
		}else {
			throw new KidHubException("Ya tiene programada una actividad en ese horario");
		}
	}
	
	/**
	 * Metodo que se comunica con el modelo para desapuntar a un hijo de una actividad
	 * @param hijo
	 *  HijoVO con la informacion del hijo
	 * @param actividad
	 *  ActividadVO con la informacion de la actividad
	 * @throws SQLException
	 * @throws KidHubException 
	 */
	public void desapuntarHijoDeActividad(HijoVO hijo, ActividadVO actividad) throws SQLException, KidHubException{
		new ActividadDAO().desapuntarHijoDeActividad(hijo, actividad);
		logger.trace("Hijo desapuntado correctamente");
	}
	
	/**
	 * Metodo que se comunica con el modelo para obtener todas las actividades de un usuario
	 * @param usuario
	 *  Usuario del que se obtendran las actividades
	 * @return
	 *  Devuelve una lista con las actividades de ese usuario
	 */
	public ArrayList<ActividadVO> getActividades(UsuarioVO usuario) throws SQLException{
		logger.trace("Obteniendo actividades");
		return new ActividadDAO().mostrarActividades(usuario);
	}

	/**
	 * Metodo que se comunica con el modelo para obtener todos los hijos de un padre
	 * @return
	 *  Devuelve una lista con los hijos del padre
	 */
	public ArrayList<HijoVO> getHijos() throws SQLException{
		logger.trace("Obteninendo hijos");
		return new PadreDAO().mostrarHijos(this.usuarioActual);
	}

	/**
	 * Devuelve el usuario actualmente logueado en la aplicacion
	 * @return
	 *  Usuario actualmente logueado en la aplicacion
	 */
	public UsuarioVO getUsuarioActual() {
		logger.trace("Obteniendo usuario actual");
		return this.usuarioActual;
	}

	/**
	 * Metodo que se comunica con el modelo para obtener todos los trayectos de un usuario
	 * @param usuario
	 *  UsuarioVO con la informacion del que se obtendran los trayectos
	 * @return
	 *  Lista con los trayectos del usuario
     * @throws SQLException
	 */
	public ArrayList<TrayectoVO> getTrayectos(UsuarioVO usuario) throws SQLException{
		logger.trace("Obteniendo trayectos");
		return new TrayectoDAO().mostrarTrayectos(usuario);
	}
	
	/**
	 * Metodo que se comunica con el modelo para crear un trayecto
	 * @param trayecto
	 *  TrayectoVO con los datos del trayecto
	 * @throws SQLException
	 */
	public void crearTrayecto(TrayectoVO trayecto) throws SQLException, KidHubException {
		logger.trace("Creando trayectos");
		trayecto.setPadre((PadreVO) this.usuarioActual);
		if(comprobarDisponibilidadTrayecto(trayecto, this.usuarioActual)) {
			new TrayectoDAO().crearTrayecto(trayecto);
		}else {
			throw new KidHubException("Ya tiene programada una actividad en ese horario");
		}			
	}
	
	/**
	 * Metodo que se comunica con el modelo para modificar un trayecto
	 * @param trayecto
	 *  TrayectoVO con los datos del trayecto
	 * @throws SQLException
	 */
	public void modificarTrayecto(TrayectoVO trayecto) throws SQLException, KidHubException{
		logger.trace("modificando trayectos");
		trayecto.setPadre((PadreVO) this.usuarioActual);
		if(comprobarDisponibilidadTrayecto(trayecto, this.usuarioActual)) {
			new TrayectoDAO().modificarTrayecto(trayecto);
		}else {
			throw new KidHubException("Ya tiene programada una actividad en ese horario");
		}	
	}
	
	/**
	 * Metodo que se comunica con el modelo para rellenar un trayectoVO
	 * @param trayecto
	 *  TrayectoVO en el que se guardaran los datos
	 * @throws SQLException
	 */
	public void rellenarTrayecto(TrayectoVO trayecto) throws SQLException{
		logger.trace("Rellenando trayectos");
		trayecto.setPadre((PadreVO) this.usuarioActual);
		new TrayectoDAO().rellenarTrayecto(trayecto);;
	}
	
	/**
	 * Metodo que se comunica con el modelo para borrar un trayecto
	 * @param trayecto
	 *  TrayectoVO con los datos del trayecto
	 * @throws SQLException
	 */
	public void borrarTrayecto(TrayectoVO trayecto) throws SQLException{
		logger.trace("Borrando trayecto");
		new TrayectoDAO().borrarTrayecto(trayecto);
	}

	/**
	 * Metodo que se comunica con el modelo para apuntar un hijo a un trayecto
	 * @param hijo
	 *  HijoVO con los datos del hijo
	 * @param trayecto
	 *  TrayectoVO con los datos del trayecto
	 * @throws SQLException
	 */
	public void apuntarHijoATrayecto(HijoVO hijo, TrayectoVO trayecto) throws SQLException, KidHubException{
		logger.trace("Apuntando hijo a trayecto");
		StringBuilder mensaje = new StringBuilder();
		if(!comprobarDisponibilidadTrayecto(trayecto, hijo)) {
			mensaje.append("Ya tiene programado un trayecto en ese horario\n");
		}if(!comprobarHijoApuntadoAActividad(hijo, trayecto.getActividad())){
			mensaje.append("Su hijo no puede apuntarse al trayecto, ya que no tiene la actividad asociada programada\n");
		}
		if(mensaje.length() != 0) {
			throw new KidHubException(mensaje.toString());
		}
		new TrayectoDAO().apuntarHijoATrayecto(hijo, trayecto);		
	}

	/**
	 * Comprueba si un hijo esta apuntado a una actividad concreta
	 * @param hijo
	 *  HijoVO con la informacion del hijo
	 * @param actividadComprobar
	 *  ActividadVO a la que comprobar si esta apuntado
	 * @return
	 *   Verdadero si esta apuntado o falso si no lo esta
	 * @throws SQLException
	 * @throws KidHubException
	 */
	private boolean comprobarHijoApuntadoAActividad(HijoVO hijo, ActividadVO actividadComprobar) throws SQLException, KidHubException{
		ArrayList<ActividadVO> actividades = getActividades(hijo);
		logger.trace("Comprobando si esta apuntado a la actividad");
		for(ActividadVO actividad: actividades) {
			if(actividad.getIdActividad() == actividadComprobar.getIdActividad()) {
				logger.trace("Esta apuntado a la actividad");
				return true;
			}
		}
		logger.trace("No esta apuntado a la actividad");
		return false;
	}

	/**
	 * Metodo que se comunica con el modelo para desapuntar un hijo a un trayecto
	 * @param hijo
	 *  HijoVO con los datos del hijo
	 * @param trayecto
	 *  TrayectoVO con los datos del trayecto
	 * @throws SQLException
	 * @throws KidHubException 
	 */
	public void desapuntarHijoDeTrayecto(HijoVO hijo, TrayectoVO trayecto) throws SQLException, KidHubException{
		logger.trace("Despauntando hijo a trayecto");
		new TrayectoDAO().desapuntarHijoDeTrayecto(hijo,trayecto);		
	}

	public void borrarUsuario(UsuarioVO usuario) throws SQLException {
		logger.trace("Borrando el usuario: " + usuario.getNombreUsuario());
		new UsuarioDAO().borrarUsuario(usuario);
	}
	
	/**
	 * Metodo privado que comprueba que la actividad que se quiere crear no coincide con alguna actividad existente
	 * @param actividadVO
	 *  Actividad a la que se quiere apuntar el hijo, o que quiere ser creada/modificada
	 * @param usuario
	 *  Usuario del que se quiere comprobar la disponibilidad
	 * @return
	 *  Falso si hay una actividad en ese horario, verdadero si no hay conflictos de horario
	 * @throws SQLException
	 */
	private boolean comprobarDisponibilidadActividad(ActividadVO actividadVO, UsuarioVO usuario) throws SQLException{
		boolean result = true;
		int i = 0;
		logger.trace("Comprobando disponibilidad horaria de la actividad");
		ArrayList<ActividadVO> actividades = this.getActividades(usuario);
		while(result && i<actividades.size()) {
			if(actividadVO.getInicio().isAfter(actividades.get(i).getInicio()) && actividadVO.getInicio().isBefore(actividades.get(i).getFin())) {
				result = false;
			}
			if(actividadVO.getFin().isAfter(actividades.get(i).getInicio()) && actividadVO.getFin().isBefore(actividades.get(i).getFin())) {
				result = false;
			}
			i++;
		}
		return result;
	}
	
	/**
	 * Metodo privado que comprueba que el trayecto que se quiere crear no coincide con algun trayecto existente
	 * @param actividadVO
	 *  Trayecto a la que se quiere apuntar el hijo, o que quiere ser creada/modificada
	 * @param usuario
	 *  Usuario del que se quiere comprobar la disponibilidad
	 * @return
	 *  Falso si hay un trayecto en ese horario, verdadero si no hay conflictos de horario
	 * @throws SQLException
	 */
	private boolean comprobarDisponibilidadTrayecto(TrayectoVO trayectoVO, UsuarioVO usuario) throws SQLException{
		boolean result = true;
		int i = 0;
		logger.trace("Comprobando disponibilidad horaria del trayecto");
		ArrayList<TrayectoVO> trayectos = this.getTrayectos(usuario);
		while(result && i<trayectos.size()) {
			if(trayectoVO.getOrigen().getFecha().isAfter(trayectos.get(i).getOrigen().getFecha()) && trayectoVO.getOrigen().getFecha().isBefore(trayectos.get(i).getDestino().getFecha())) {
				result = false;
			}
			if(trayectoVO.getDestino().getFecha().isAfter(trayectos.get(i).getOrigen().getFecha()) && trayectoVO.getDestino().getFecha().isBefore(trayectos.get(i).getDestino().getFecha())) {
				result = false;
			}
			i++;
		}
		return result;
	}
}
