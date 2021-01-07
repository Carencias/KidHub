package modelo;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.time.LocalDateTime;

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
	
	
	@Before
	public void setUp() throws Exception {
		padre = new PadreVO("usuario1", "03216694B", "passwd","padre@kidhub.com", "Alberto", "Perez", "12/12/2000" ,TipoUsuario.PADRE, "792016552");
		monitor = new MonitorVO("usuario2", "03815694B", "passwd","monitor@kidhub.com", "Juan", "Lebron", "12/12/2000" ,TipoUsuario.MONITOR, "799016552", "Deporte");
		hijo = new HijoVO("usuario3", "01816654Z", "passwd","hijo@kidhub.com", "Pablo", "Iglesias", "12/12/2000" ,TipoUsuario.HIJO);
		actividad = new ActividadVO("Ajedrez", LocalDateTime.of(2021, 1, 1, 17, 30), LocalDateTime.of(2021, 1, 1, 18, 30), 5, new Direccion("Calle Ancha", 3, "24007", "Leon"), "Deportiva");
		trayecto = new TrayectoVO(actividad, 4, TipoTrayecto.IDA, LocalDateTime.of(2021, 1, 1, 17, 00), new Direccion("Calle Santos", 9, "24008", "Leon"));
	}

	@Test
	public void testRegistrarPadre() throws KidHubException, SQLException {
		Logica.getLogica().borrarUsuario(padre);
		
		Logica.getLogica().registrarUsuario(padre);
		
		assertEquals(TipoUsuario.PADRE, Logica.getLogica().loguearUsuario(padre));
		
	}
	
	@Test
	(expected = KidHubException.class)
	public void registrarUsuarioCamposSinRellenar() throws KidHubException, SQLException {
		padre.setNombreUsuario("");
		padre.setNombre("");
		
		Logica.getLogica().registrarUsuario(padre);
	}

}

