
import modelo.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import junit.framework.Assert;
import modelo.vo.ActividadVO;
import modelo.vo.Direccion;
import modelo.vo.HijoVO;
import modelo.vo.MonitorVO;
import modelo.vo.PadreVO;
import modelo.vo.TrayectoVO;
import modelo.vo.TrayectoVO.TipoTrayecto;
import modelo.vo.UsuarioVO.TipoUsuario;

public class LogicaTests {

	PadreVO padre;
	MonitorVO monitor;
	HijoVO hijo;
	ActividadVO actividad;
	TrayectoVO trayecto;
	Logica logica;
	
	
	@Before
	public void setUp() throws Exception {
		padre = new PadreVO("usuario1", "03216694B", "passwd","padre@kidhub.com", "Alberto", "Perez", "12/12/2000" ,TipoUsuario.PADRE, "792016552");
		monitor = new MonitorVO("usuario2", "03815694B", "passwd","monitor@kidhub.com", "Juan", "Lebron", "12/12/2000" ,TipoUsuario.MONITOR, "799016552", "Deporte");
		hijo = new HijoVO("usuario3", "01816654Z", "passwd","hijo@kidhub.com", "Pablo", "Iglesias", "12/12/2000" ,TipoUsuario.HIJO);
		actividad = new ActividadVO("Ajedrez", LocalDateTime.of(2021, 1, 1, 17, 30), LocalDateTime.of(2021, 1, 1, 18, 30), 5, new Direccion("Calle Ancha", 3, "24007", "Leon"), "Deportiva");
		trayecto = new TrayectoVO(actividad, 4, TipoTrayecto.IDA, LocalDateTime.of(2021, 1, 1, 17, 00), new Direccion("Calle Santos", 9, "24008", "Leon"));
		
		logica = Logica.getLogica();
	}

	@Test
	public void registrarPadreTest() throws KidHubException, SQLException {
		logica.borrarUsuario(padre);
		
		logica.registrarUsuario(padre);
		
		assertEquals(TipoUsuario.PADRE, logica.loguearUsuario(padre));
		
	}
	
	@Test
	public void registrarMonitorTest() throws SQLException, KidHubException {
		logica.borrarUsuario(monitor);
				
		logica.registrarUsuario(monitor);
		
		assertEquals(TipoUsuario.MONITOR, logica.loguearUsuario(monitor));

	}
	
	@Test
	public void registrarHijoTest() throws SQLException, KidHubException {
		this.registrarPadreTest();
		
		logica.setUsuarioActual(padre);
		
		logica.borrarUsuario(hijo);
				
		logica.registrarUsuario(hijo);
		
		assertEquals(TipoUsuario.HIJO, logica.loguearUsuario(hijo));

	}
	
	@Test
	(expected = KidHubException.class)
	public void registrarUsuarioCamposSinRellenarTest() throws KidHubException, SQLException {
		padre.setNombreUsuario("");
		padre.setNombre("");
		
		logica.registrarUsuario(padre);
	}
	
	@Test
	(expected = KidHubException.class)
	public void loginCredencialesIncorrectasTest() throws KidHubException, SQLException {
		
		hijo.setContrasena("p");
		
		logica.loguearUsuario(hijo);
	}
	
	@Test
	(expected = KidHubException.class)
	public void loginContrasenaVaciaTest() throws KidHubException, SQLException {
		
		hijo.setContrasena("");
		
		logica.loguearUsuario(hijo);
	}
	
	@Test
	(expected = KidHubException.class)
	public void loginUsuarioVacioTest() throws KidHubException, SQLException {
		
		hijo.setNombreUsuario("");
		
		logica.loguearUsuario(hijo);
	}
	
	@Test
	public void crearActividadTest() throws SQLException, KidHubException {
		this.registrarMonitorTest(); //TODO esto no se si esta bien
		
		logica.setUsuarioActual(monitor);
		
		logica.borrarActividad(actividad);
		
		logica.crearActividad(actividad);
		
		ArrayList<ActividadVO> actividades = logica.getActividades(monitor);
		
		int i = 0;
		boolean actividadEncontrada = false;
		
		while(actividades.get(i)!=null && !actividadEncontrada) {
			if(actividades.get(i).getIdActividad() == actividad.getIdActividad()) {
				actividadEncontrada = true;
			}
		}
		
		assertTrue(actividadEncontrada);
		
	}
	
	@Test
	public void modificarActividadTest() throws SQLException, KidHubException {
		
		this.crearActividadTest();
		
		actividad.setNombre("AjedrezModificado");
		
		logica.modificarActividad(actividad);
		
		ArrayList<ActividadVO> actividades = logica.getActividades(monitor);
		
		int i = 0;
		boolean actividadEncontrada = false;
		
		while(actividades.get(i)!=null && !actividadEncontrada) {
			if(actividades.get(i).getIdActividad() == actividad.getIdActividad()) {
				actividadEncontrada = true;
			}
		}
		
		assertTrue(actividadEncontrada);
		
		assertEquals("AjedrezModificado", actividades.get(i).getNombre());
		
		
	}
	
	@Test
	public void rellenarActividadTest() throws SQLException, KidHubException {
		ActividadVO actividadVacia = new ActividadVO();
		
		this.crearActividadTest();
		
		actividadVacia.setIdActividad(actividad.getIdActividad());
		
		logica.rellenarActividad(actividadVacia);
		
		assertEquals(actividad.getNombre(), actividadVacia.getNombre());
		
	}
	
	@Test
	public void apuntarHijoAActividadTest() throws KidHubException, SQLException {
		this.registrarHijoTest();
		this.crearActividadTest();
		
		
		logica.setUsuarioActual(padre);
		
		logica.apuntarHijoAActividad(hijo, actividad);
		
		ArrayList<ActividadVO> actividades = logica.getActividades(hijo);
		
		int i = 0;
		boolean actividadEncontrada = false;
		
		while(actividades.get(i)!=null && !actividadEncontrada) {
			if(actividades.get(i).getIdActividad() == actividad.getIdActividad()) {
				actividadEncontrada = true;
			}
		}
		
		assertTrue(actividadEncontrada);
		
		
	}
	
	@Test
	public void desapuntarHijoDeActividadTest() throws KidHubException, SQLException {
		this.apuntarHijoAActividadTest();
		
				
		logica.desapuntarHijoDeActividad(hijo, actividad);
		
		ArrayList<ActividadVO> actividades = logica.getActividades(hijo);
		
		int i = 0;
		boolean actividadEncontrada = false;
		
		while(!actividades.isEmpty() && actividades.get(i)!=null && !actividadEncontrada) {
			if(actividades.get(i).getIdActividad() == actividad.getIdActividad()) {
				actividadEncontrada = true;
			}
		}
		
		assertFalse(actividadEncontrada);
		
		
	}
	
	@Test
	public void getHijosTest() throws SQLException, KidHubException {
		this.registrarHijoTest();
		
		logica.setUsuarioActual(padre);
				
		ArrayList<HijoVO> hijos = logica.getHijos();
		
		int i = 0;
		boolean hijoEncontrado = false;
		
		while(i<hijos.size() && !hijoEncontrado) {
			if(hijos.get(i).getNombreUsuario().equals(hijo.getNombreUsuario())) {
				hijoEncontrado = true;
			}
			i++;
		}
		
		assertTrue(hijoEncontrado);		
	}
	
	@Test
	public void getUsuarioActualTest() {
		logica.setUsuarioActual(padre);
		padre.equals(logica.getUsuarioActual());
	}
	
	@Test
	public void crearTrayectoTest() throws KidHubException, SQLException {
		this.apuntarHijoAActividadTest();
		
		logica.crearTrayecto(trayecto);
		
		
		ArrayList<TrayectoVO> trayectos = logica.getTrayectos(padre);

		
		int i = 0;
		boolean trayectoEncontrado = false;
		
		while(!trayectos.isEmpty() && trayectos.get(i)!=null && !trayectoEncontrado) {
			if(trayectos.get(i).getIdTrayecto() == trayecto.getIdTrayecto()) {
				trayectoEncontrado = true;
			}
		}
		
		assertTrue(trayectoEncontrado);
	}
	
	@Test
	public void modificarTrayectoTest() throws SQLException, KidHubException {
		
		this.crearTrayectoTest();
		
		trayecto.setCapacidad(80);
		
		logica.modificarTrayecto(trayecto);
		
		ArrayList<TrayectoVO> trayectos = logica.getTrayectos(padre);

		int i = 0;
		boolean trayectoEncontrado = false;
		
		while(!trayectos.isEmpty() && trayectos.get(i)!=null && !trayectoEncontrado) {
			if(trayectos.get(i).getIdTrayecto() == trayecto.getIdTrayecto()) {
				trayectoEncontrado = true;
			}
		}
		
		assertTrue(trayectoEncontrado);
		
		assertEquals(80, trayectos.get(i).getCapacidad());
		
		
	}
	
	@Test
	public void rellenarTrayectoTest() throws SQLException, KidHubException {
		TrayectoVO trayectoVacio = new TrayectoVO();
		
		this.crearTrayectoTest();
		
		trayectoVacio.setIdTrayecto(trayecto.getIdTrayecto());
		
		logica.rellenarTrayecto(trayectoVacio);
		
		assertEquals(trayecto.getCapacidad(), trayectoVacio.getCapacidad());
		
	}
	
	@Test
	public void borrarTrayectoTest() throws SQLException, KidHubException {
		
		this.crearTrayectoTest();
				
		logica.borrarTrayecto(trayecto);
		
		ArrayList<TrayectoVO> trayectos = logica.getTrayectos(padre);
		
		assertFalse(existeTrayecto(trayectos));
		
		
	}
	
	@Test
	public void apuntarHijoATrayectoTest() throws KidHubException, SQLException {
		this.apuntarHijoAActividadTest();
		this.crearTrayectoTest();
		
		logica.setUsuarioActual(padre);
		
		logica.apuntarHijoATrayecto(hijo, trayecto);
		
		ArrayList<TrayectoVO> trayectos = logica.getTrayectos(hijo);
		
		assertTrue(existeTrayecto(trayectos));
		
		
	}
	
	@Test
	public void desapuntarHijoDeTrayectoTest() throws KidHubException, SQLException {
		this.apuntarHijoATrayectoTest();
				
		logica.desapuntarHijoDeTrayecto(hijo, trayecto);
		
		ArrayList<TrayectoVO> trayectos = logica.getTrayectos(hijo);
		
		assertFalse(existeTrayecto(trayectos));
		
		
	}
	
	@Test (expected = KidHubException.class)
	public void conflictoHorarioActividadTest() throws SQLException, KidHubException{
		ActividadVO actividadMal = new ActividadVO("Ajedrez", LocalDateTime.of(2021, 1, 1, 18, 0), LocalDateTime.of(2021, 1, 1, 18, 30), 5, new Direccion("Calle Ancha", 3, "24007", "Leon"), "Deportiva");
		this.registrarMonitorTest();
		
		logica.setUsuarioActual(monitor);
		logica.borrarActividad(actividad);
		logica.crearActividad(actividad);
		logica.crearActividad(actividadMal);
	}
	
	@Test (expected = KidHubException.class)
	public void conflictoHorarioTrayectoTest() throws SQLException, KidHubException{
		TrayectoVO trayectoMal = new TrayectoVO(actividad, 4, TipoTrayecto.IDA, LocalDateTime.of(2021, 1, 1, 17, 15), new Direccion("Calle Santos", 9, "24008", "Leon"));
		this.apuntarHijoAActividadTest();
		
		logica.crearTrayecto(trayecto);
		logica.crearTrayecto(trayectoMal);
	}
	
	private boolean existeTrayecto(ArrayList<TrayectoVO> trayectos) {
		int i = 0;
		boolean trayectoEncontrado = false;
		
		while(!trayectos.isEmpty() && trayectos.get(i)!=null && !trayectoEncontrado) {
			if(trayectos.get(i).getIdTrayecto() == trayecto.getIdTrayecto()) {
				trayectoEncontrado = true;
			}
		}
		
		return trayectoEncontrado;
	}
}

