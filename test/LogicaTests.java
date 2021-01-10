
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
	MonitorVO monitor, monitor2;
	HijoVO hijo;
	ActividadVO actividad, actividad2;
	TrayectoVO trayecto, trayecto2;
	Logica logica;
	
	
	@Before
	public void setUp() throws Exception {
		padre = new PadreVO("usuario1", "03216694B", "passwd","padre@kidhub.com", "Alberto", "Perez", "12/12/2000" ,TipoUsuario.PADRE, "792016552");
		monitor = new MonitorVO("usuario2", "03815694B", "passwd","monitor@kidhub.com", "Juan", "Lebron", "12/12/2000" ,TipoUsuario.MONITOR, "799016552", "Deporte");
		hijo = new HijoVO("usuario3", "01816654Z", "passwd","hijo@kidhub.com", "Pablo", "Iglesias", "12/12/2000" ,TipoUsuario.HIJO);
		actividad = new ActividadVO("Ajedrez", LocalDateTime.of(2021, 1, 1, 17, 30), LocalDateTime.of(2021, 1, 1, 18, 30), 5, new Direccion("Calle Ancha", 3, "24007", "Leon"), "Deportiva");
		trayecto = new TrayectoVO(actividad, 4, TipoTrayecto.IDA, LocalDateTime.of(2021, 1, 1, 17, 00), new Direccion("Calle Santos", 9, "24008", "Leon"));
		actividad2 = new ActividadVO("Parchis", LocalDateTime.of(2021, 1, 1, 19, 30), LocalDateTime.of(2021, 1, 1, 20, 30), 5, new Direccion("Calle Ancha", 3, "24007", "Leon"), "Recreativa");
		trayecto2 = new TrayectoVO(actividad2, 4, TipoTrayecto.IDA, LocalDateTime.of(2021, 1, 1, 19, 00), new Direccion("Calle Santos", 9, "24008", "Leon"));
		monitor2 = new MonitorVO("usuario4", "03372694B", "passwd","monitor2@kidhub.com", "Alfredo", "Montes", "12/12/2000" ,TipoUsuario.MONITOR, "727416552", "Deporte");

		
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
	(expected = KidHubException.class)
	public void modificarActividadErrorTest() throws SQLException, KidHubException {
		
		this.crearActividadTest();
		
		logica.borrarActividad(actividad2);
				
		logica.crearActividad(actividad2);
		
		actividad2.setInicio(LocalDateTime.of(2021, 1, 1, 18, 00));
				
		logica.modificarActividad(actividad2);
		
		
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
	(expected = KidHubException.class)
	public void apuntarHijoAActividadErrorHorarioTest() throws KidHubException, SQLException {
		try {
			this.apuntarHijoAActividadTest();
	
			logica.borrarUsuario(monitor2);
			logica.registrarUsuario(monitor2);
			
			logica.setUsuarioActual(monitor2);
			
			logica.borrarActividad(actividad2);
			
			actividad2.setInicio(LocalDateTime.of(2021, 1, 1, 18, 00));
			
			actividad2.setFin(LocalDateTime.of(2021, 1, 1, 19, 00));
			
			logica.crearActividad(actividad2);
			
			logica.setUsuarioActual(padre);
			
			logica.apuntarHijoAActividad(hijo, actividad2);
		}finally {
			logica.setUsuarioActual(monitor2);
			logica.borrarActividad(actividad2);
		}
		
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
		//this.crearActividadTest();
		this.registrarPadreTest();
		
		logica.setUsuarioActual(padre);
		
		logica.borrarTrayecto(trayecto);
		
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
	(expected = KidHubException.class)
	public void crearTrayectoSolapadoTest() throws KidHubException, SQLException {
		this.crearTrayectoTest();
		
		logica.borrarUsuario(monitor2);
		logica.registrarUsuario(monitor2);
		logica.setUsuarioActual(monitor2);
		
		actividad2.setInicio(LocalDateTime.of(2021, 1, 1, 17, 15));
		actividad2.setFin(LocalDateTime.of(2021, 1, 1, 18, 00));
		logica.borrarActividad(actividad2);
		logica.crearActividad(actividad2);
		
		logica.setUsuarioActual(padre);
		
		TrayectoVO trayecto3 = new TrayectoVO(actividad2, 4, TipoTrayecto.IDA, LocalDateTime.of(2021, 1, 1, 16, 45), new Direccion("Calle Santos", 9, "24008", "Leon"));
		
		logica.crearTrayecto(trayecto3);	

	}
	
	@Test
	public void crearTrayectoNoSolapadoTest() throws KidHubException, SQLException {
		this.crearTrayectoTest();
		
		logica.borrarUsuario(monitor2);
		logica.registrarUsuario(monitor2);
		logica.setUsuarioActual(monitor2);
		
		actividad2.setInicio(LocalDateTime.of(2021, 1, 1, 16, 45));
		actividad2.setFin(LocalDateTime.of(2021, 1, 1, 19, 00));
		logica.borrarActividad(actividad2);
		logica.crearActividad(actividad2);
		
		logica.setUsuarioActual(padre);
		
		TrayectoVO trayecto3 = new TrayectoVO(actividad2, 4, TipoTrayecto.IDA, LocalDateTime.of(2021, 1, 1, 16, 30), new Direccion("Calle Santos", 9, "24008", "Leon"));
		
		logica.crearTrayecto(trayecto3);	

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
	(expected = KidHubException.class)
	public void modificarTrayectoErrorHorarioTest() throws SQLException, KidHubException {
		try {
			this.crearTrayectoTest();
			
			logica.setUsuarioActual(monitor);
			
			logica.borrarActividad(actividad2);
			
			logica.crearActividad(actividad2);
			
			logica.setUsuarioActual(padre);
			
			logica.borrarTrayecto(trayecto2);
			
			logica.crearTrayecto(trayecto2);
			
			trayecto2.getOrigen().setFecha(LocalDateTime.of(2021, 1, 1, 17, 15));
			
			logica.modificarTrayecto(trayecto2);
		}finally {
			logica.setUsuarioActual(padre);
			logica.borrarTrayecto(trayecto2);
			logica.setUsuarioActual(monitor);
			logica.borrarActividad(actividad2);

		}
		
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
		this.crearTrayectoTest();
		
		logica.setUsuarioActual(padre);
		
		logica.apuntarHijoATrayecto(hijo, trayecto);
		
		ArrayList<TrayectoVO> trayectos = logica.getTrayectos(hijo);
		
		assertTrue(existeTrayecto(trayectos));		
		
	}
	
	@Test
	(expected = KidHubException.class)
	public void apuntarHijoATrayectoNoActividadTest() throws KidHubException, SQLException {
		this.crearTrayectoTest();
		
		logica.setUsuarioActual(padre);
		
		logica.desapuntarHijoDeActividad(hijo, actividad);
		
		logica.apuntarHijoATrayecto(hijo, trayecto);	
		
	}
	
	@Test
	(expected=KidHubException.class)
	public void apuntarHijoATrayectoYaTieneTest() throws KidHubException, SQLException {
		this.crearTrayectoTest();
		
		logica.setUsuarioActual(padre);
		
		logica.apuntarHijoATrayecto(hijo, trayecto);
		
		ActividadVO actividad3 = new ActividadVO("Bolos", LocalDateTime.of(2021, 1, 1, 16, 00), LocalDateTime.of(2021, 1, 1, 16, 30), 5, new Direccion("Calle Ancha", 3, "24007", "Leon"), "Recreativa");

		logica.setUsuarioActual(monitor);
		
		logica.borrarActividad(actividad3);

		logica.crearActividad(actividad3);
		
		PadreVO padre2 = new PadreVO("usuario5", "03229694B", "passwd","padre2@kidhub.com", "Pepa", "Martinez", "12/12/2000" ,TipoUsuario.PADRE, "792976552");
		
		logica.borrarUsuario(padre2);
		
		logica.registrarUsuario(padre2);
		
		logica.setUsuarioActual(padre2);
		
		TrayectoVO trayecto3 = new TrayectoVO(actividad3, 4, TipoTrayecto.VUELTA, LocalDateTime.of(2021, 1, 1, 17, 15), new Direccion("Calle Santos", 9, "24008", "Leon"));
		
		logica.crearTrayecto(trayecto3);
		
		logica.setUsuarioActual(padre);
		
		logica.apuntarHijoATrayecto(hijo, trayecto3);
		
		
	
		
	}
	
	@Test
	(expected = KidHubException.class)
	public void apuntarHijoATrayectoErrorHorarioTest() throws KidHubException, SQLException {
		ActividadVO actividad3 = new ActividadVO("Bolos", LocalDateTime.of(2021, 1, 1, 18, 35), LocalDateTime.of(2021, 1, 1, 18, 45), 5, new Direccion("Calle Ancha", 3, "24007", "Leon"), "Recreativa");
		TrayectoVO trayecto3 = new TrayectoVO(actividad3, 4, TipoTrayecto.IDA, LocalDateTime.of(2021, 1, 1, 17, 15), new Direccion("Calle Santos", 9, "24008", "Leon"));
		PadreVO padre2 = new PadreVO("usuario5", "03229694B", "passwd","padre2@kidhub.com", "Pepa", "Martinez", "12/12/2000" ,TipoUsuario.PADRE, "792976552");

		try {	
			this.apuntarHijoATrayectoTest();
			
			logica.borrarUsuario(monitor2);
			logica.registrarUsuario(monitor2);
			
			logica.setUsuarioActual(monitor2);
			
		
			logica.crearActividad(actividad3);
			
	
			logica.borrarUsuario(padre2);
			logica.registrarUsuario(padre2);
			
			logica.setUsuarioActual(padre2);
			
			
			logica.crearTrayecto(trayecto3);
			
			logica.setUsuarioActual(padre);
			
			logica.apuntarHijoAActividad(hijo, actividad3);
			
			logica.apuntarHijoATrayecto(hijo, trayecto3);
		}finally {
			logica.setUsuarioActual(padre2);
			logica.borrarTrayecto(trayecto3);
			
			logica.borrarUsuario(padre2);
			
			logica.setUsuarioActual(monitor2);
			logica.borrarActividad(actividad3);
			
			logica.borrarUsuario(monitor2);
		}
		
	}
	
	@Test
	(expected = KidHubException.class)
	public void apuntarHijoATrayectoErrorHorario2Test() throws KidHubException, SQLException {
		ActividadVO actividad3 = new ActividadVO("Bolos", LocalDateTime.of(2021, 1, 1, 16, 00), LocalDateTime.of(2021, 1, 1, 16, 30), 5, new Direccion("Calle Ancha", 3, "24007", "Leon"), "Recreativa");
		TrayectoVO trayecto3 = new TrayectoVO(actividad3, 4, TipoTrayecto.VUELTA, LocalDateTime.of(2021, 1, 1, 17, 15), new Direccion("Calle Santos", 9, "24008", "Leon"));
		PadreVO padre2 = new PadreVO("usuario5", "03229694B", "passwd","padre2@kidhub.com", "Pepa", "Martinez", "12/12/2000" ,TipoUsuario.PADRE, "792976552");

		try {	
			this.apuntarHijoATrayectoTest();
			
			logica.borrarUsuario(monitor2);
			logica.registrarUsuario(monitor2);
			
			logica.setUsuarioActual(monitor2);
			
		
			logica.crearActividad(actividad3);
			
	
			logica.borrarUsuario(padre2);
			logica.registrarUsuario(padre2);
			
			logica.setUsuarioActual(padre2);
			
			
			logica.crearTrayecto(trayecto3);
			
			logica.setUsuarioActual(padre);
			
			logica.apuntarHijoAActividad(hijo, actividad3);
			
			logica.apuntarHijoATrayecto(hijo, trayecto3);
		}finally {
			logica.setUsuarioActual(padre2);
			logica.borrarTrayecto(trayecto3);
			
			logica.borrarUsuario(padre2);
			
			logica.setUsuarioActual(monitor2);
			logica.borrarActividad(actividad3);
			
			logica.borrarUsuario(monitor2);
		}
		
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
	public void conflictoHorarioActividadTest2() throws SQLException, KidHubException{
		ActividadVO actividadMal = new ActividadVO("Ajedrez", LocalDateTime.of(2021, 1, 1, 17, 00), LocalDateTime.of(2021, 1, 1, 18, 00), 5, new Direccion("Calle Ancha", 3, "24007", "Leon"), "Deportiva");
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
		this.crearTrayectoTest();
		
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

