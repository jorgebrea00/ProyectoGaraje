package menus;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import dao.ClienteDao;
import dao.LoginDao;
import dao.SesionesDao;
import estructuraBaseDeDatos.EstructuraBbdd;
import main.ListadoEntity;
import main.Principal;
import mensajes.Mensajes;
import model.Cliente;
import model.Sesiones;

public class MenuAdministracionCliente extends Menu{

	public MenuAdministracionCliente(EstructuraBbdd estructuraTablas, Connection conexionAbierta) {
		super(estructuraTablas, conexionAbierta);	
	}
	
	public void ejecutaMenuAdministracionCliente() {
		int seleccionMenu;
		
		do {
			System.out.println(Mensajes.SALTOLINEA);
			seleccionMenu=opcionSeleccionadaDelMenu(cadenaMenu("Menú de Administración de CLIENTES",new String[] {"Volver","Consulta cliente", "Consulta sesiones","Listado clientes", "Baja"}));
			switch (seleccionMenu) {
				case 0:						// Volver
					break;
				case 1:						// "Consulta"
					consultaCliente();
					break;
					
				case 2:						// "Sesiones cliente"
					consultaSesionesCliente();
					break;
				
				case 3:						// "Listado"
					listadoClientes ();
					break;	
				
				case 4:						// "Baja"
					bajaClientes();
					break;
				}
		}while (seleccionMenu!=0);	
	}
	
	public void consultaCliente() {
		Cliente cliente;
		MenuAdministracionBusquedaCliente menuBusqueda=new MenuAdministracionBusquedaCliente(getEstructuraTablas(), getConexionAbierta());
		cliente=menuBusqueda.EjecutarMenuAdministracionBusquedaCliente();
		if (cliente!=null) {
			List <Cliente> resultadoConsulta=new ArrayList<>();
			resultadoConsulta.add(cliente);
			boolean mostrarIndices=true;
			String tituloListado="* * Datos cliente buscado * *";
			ListadoEntity listadoSesiones=new ListadoEntity(tituloListado, Principal.nombreDeLaTablaAsociadaALaEntity(Cliente.class), mostrarIndices, getEstructuraTablas(), resultadoConsulta);
			listadoSesiones.listarDatosTablas();
			LoginDao loginDao=new LoginDao(getEstructuraTablas(), getConexionAbierta());
			cliente.setLogin(loginDao.read(cliente.getLogin().getId()));
			muestraTituloCentrado("E-MAIL: "+cliente.getLogin().getEmail());
		}
	}
	
	public void consultaSesionesCliente() {
		Cliente cliente;
		MenuAdministracionBusquedaCliente menuBusqueda=new MenuAdministracionBusquedaCliente(getEstructuraTablas(), getConexionAbierta());
		cliente=menuBusqueda.EjecutarMenuAdministracionBusquedaCliente();
		if (cliente!=null) {
			
			List <Sesiones> resultadoConsulta;
			SesionesDao sesionesDao=new SesionesDao(getEstructuraTablas(), getConexionAbierta());
			resultadoConsulta=sesionesDao.readIdLogin(cliente.getLogin().getId());
			if(!resultadoConsulta.isEmpty()) {
				boolean mostrarIndices=true;
				System.out.printf("Cliente: %s %s %s ",cliente.getNombre(), cliente.getApellido1(), cliente.getApellido2()+Mensajes.SALTOLINEA);
				String tituloListado="* * Datos de inicio y fin de sesión * *";
				ListadoEntity listadoSesiones=new ListadoEntity(tituloListado, Principal.nombreDeLaTablaAsociadaALaEntity(Sesiones.class), mostrarIndices, getEstructuraTablas(), resultadoConsulta);
				listadoSesiones.listarDatosTablas();
			}
		}
	}
	
	public void listadoClientes () {
		ClienteDao clienteDao=new ClienteDao(getEstructuraTablas(), getConexionAbierta());
		List <Cliente> resultadoConsulta;
		
		resultadoConsulta=clienteDao.getAll();
		boolean mostrarIndices=true;
		String tituloListado="* * Clientes grabados en el sistema * *";
		ListadoEntity listadoSesiones=new ListadoEntity(tituloListado, Principal.nombreDeLaTablaAsociadaALaEntity(Cliente.class), mostrarIndices, getEstructuraTablas(), resultadoConsulta);
		listadoSesiones.listarDatosTablas();
	}
	
	public void bajaClientes() {
		Cliente cliente;
		MenuAdministracionBusquedaCliente menuBusqueda=new MenuAdministracionBusquedaCliente(getEstructuraTablas(), getConexionAbierta());
		cliente=menuBusqueda.EjecutarMenuAdministracionBusquedaCliente();
		if (cliente!=null) {
			System.out.println("Se eliminará el cliente "+cliente.getNombre()+" "+cliente.getApellido1()+" "+cliente.getApellido2()+" con NIF: "+cliente.getDni());
			if (confirmaGuardarCambios()) {
				LoginDao loginDao=new LoginDao(getEstructuraTablas(), getConexionAbierta());
				if(loginDao.delete(cliente.getLogin())>0) System.out.println(Mensajes.REGISTRO_BORRADO_OK);				
			}
		}
	}

}
