package menus;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import dao.ClienteDao;
import dao.LoginDao;
import dao.PreferenciasClientesDao;
import estructuraBaseDeDatos.EstructuraBbdd;
import main.ListadoEntity;
import main.Principal;
import mensajes.Mensajes;
import model.Cliente;
import model.Login;
import model.PreferenciasCliente;

public class MenuDatosCliente extends Menu {
	Cliente cliente;

	public MenuDatosCliente (Cliente cliente, EstructuraBbdd estructuraTablas, Connection conexionAbierta) {
		super(estructuraTablas, conexionAbierta);	
		this.cliente=cliente;		
	}
	
	public void ejecutaMenuDatosCliente() {
		int seleccionMenu;
		
		do {
			System.out.println(Mensajes.SALTOLINEA);
			seleccionMenu=opcionSeleccionadaDelMenu(cadenaMenu("Menú datos de "+cliente.getNombre(),new String[] {"Volver","Ver mis datos","Ver mis preferencias", "Modificar dirección", "Modificar teléfono", "Modificar preferencias", "Cambiar contraseña"}));
			switch (seleccionMenu) {
				case 0:						// Volver
					break;
				case 1:						//"Ver mis datos"
					verMisDatos(cliente);
					break;
				case 2:						// "Ver mis preferencias"
					verMisPreferencias(cliente);
					break;
				
				case 3:						// "Modificar dirección"
					modificarDireccion (cliente);
					break;
					
				case 4:						//  "Modificar teléfono"
					modificarTelefono(cliente);
					
					break;
				case 5:						// "Modificar preferencias"
					modificarPreferencias(cliente);
					
					break;
				case 6:						// "Cambiar contraseña"
					actualizaAcceso();
					break;
				}
		}while (seleccionMenu!=0);	
	}
	
	public void verMisDatos(Cliente cliente) {
		List <Cliente> resultadoConsulta=new ArrayList<>();
		resultadoConsulta.add(cliente);
		boolean mostrarIndices=false;
		String tituloListado="* * Datos grabados en el sistema * *";
		ListadoEntity listadoSesiones=new ListadoEntity(tituloListado, Principal.nombreDeLaTablaAsociadaALaEntity(Cliente.class), mostrarIndices, getEstructuraTablas(), resultadoConsulta);
		listadoSesiones.listarDatosTablas();
	}
	
	public void verMisPreferencias(Cliente cliente) {
		List <PreferenciasCliente> resultadoConsultapreferencias=new ArrayList<>();
		PreferenciasClientesDao preferenciasClientesDao=new PreferenciasClientesDao(getEstructuraTablas(), getConexionAbierta());
		resultadoConsultapreferencias=preferenciasClientesDao.readIdCliente(cliente.getId());
		boolean mostrarIndices=false;
		String tituloListado="* * Listado de preferencias grabadas en el sistema * *";
		ListadoEntity listadoPreferencias=new ListadoEntity(tituloListado, Principal.nombreDeLaTablaAsociadaALaEntity(PreferenciasCliente.class), mostrarIndices, getEstructuraTablas(), resultadoConsultapreferencias);
		listadoPreferencias.listarDatosTablas();
	}	
	
	public Cliente modificarDireccion (Cliente cliente) {
		Cliente nuevaDireccion=new Cliente();
		
		muestraTituloCentrado("Modificación de dirección");	
		introduceDireccionCliente(nuevaDireccion);
		if (confirmaGuardarCambios()) {
			cliente=asignaNuevaDireccionAlCliente(cliente, nuevaDireccion);
			grabaDatosActualizadosCliente(cliente);						
		}
		return cliente;
	}
	
	public Cliente modificarTelefono(Cliente cliente) {
		Cliente nuevoTelefono=new Cliente();
		
		muestraTituloCentrado("Modificación de telefono");	
		introduceTelefonoCliente(nuevoTelefono);
		if (confirmaGuardarCambios()) {
			cliente=asignaNuevoTelefonoAlCliente(cliente, nuevoTelefono);
			grabaDatosActualizadosCliente(cliente);						
		}
		return cliente;
	}
	
	public void modificarPreferencias(Cliente cliente) {
		PreferenciasCliente nuevasPreferencias=new PreferenciasCliente();

		nuevasPreferencias=introducePreferenciasCliente(cliente);
		if (confirmaGuardarCambios()) {		
			grabaNuevasPreferenciasCliente (nuevasPreferencias);					
		}
	}
	
	public void grabaDatosActualizadosCliente(Cliente cliente) {
		
		ClienteDao clienteDao=new ClienteDao(getEstructuraTablas(), getConexionAbierta());
		if(clienteDao.update(cliente)>0) System.out.println(Mensajes.CAMBIO_GUARDADO_OK); else System.out.println(Mensajes.CAMBIO_GUARDADO_KO);
	}
	
	public void actualizaAcceso() {
		String passIntroducido;
		muestraTituloCentrado("Cambio contraseña");
		System.out.println(Mensajes.SALTOLINEA+"Para el cambio de contraseña debe logarse nuevamente.");
		Login login;
		login=loguearse();
		if(login!=null) {
			System.out.println(Mensajes.SALTOLINEA+"Indíquenos su nueva clave de acceso:");
			passIntroducido=creaAcceso();
			login.setPassHash(passIntroducido);	
			LoginDao logDao=new LoginDao(getEstructuraTablas(), getConexionAbierta());
			logDao.update(login);
			System.out.println(Mensajes.CAMBIO_GUARDADO_OK);
		}else {
				System.out.println(Mensajes.ERROR_ACCESO_LOGIN);
		}
	}
	
	public void grabaNuevasPreferenciasCliente (PreferenciasCliente preferenciasCliente) {
		PreferenciasClientesDao preferenciasClientesDao=new PreferenciasClientesDao(getEstructuraTablas(), getConexionAbierta());
		if(preferenciasClientesDao.update(preferenciasCliente)>0) System.out.println(Mensajes.CAMBIO_GUARDADO_OK); else System.out.println(Mensajes.CAMBIO_GUARDADO_KO);
	}

}
