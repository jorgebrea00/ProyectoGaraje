package menus;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dao.ClienteDao;
import dao.LoginDao;
import dao.PreferenciasClientesDao;
import dao.SesionesDao;
import estructuraBaseDeDatos.EstructuraBbdd;
import main.ListadoEntity;
import main.Principal;
import mensajes.Mensajes;
import model.Cliente;
import model.Login;
import model.PreferenciasCliente;
import model.Sesiones;

public class MenuLoginAltaCliente extends Menu {

	public MenuLoginAltaCliente(EstructuraBbdd estructuraTablas, Connection conexionAbierta) {
		super(estructuraTablas, conexionAbierta);	
	}
	
	public void ejecutarMenuInicial() throws SQLException {
		
		Sesiones sesion=null;
		int seleccionMenu;
		
		do {
			System.out.println(Mensajes.SALTOLINEA);
			seleccionMenu=opcionSeleccionadaDelMenu(cadenaMenu("Menú de acceso a MOTOR RACING",new String[] {"Salir aplicación","Acceso clientes", "Alta nuevo usuario"}));
			switch (seleccionMenu) {
				case 0:						// Volver
					if (sesion!=null) sesionFinalizada(sesion);
					break;
					
				case 1:						// Acceso clientes
					sesion=accesoClientes(sesion);
					break;
					
				case 2:						// Alta nuevo cliente
					sesion=altaCliente(sesion);
					break;
				}
		}while (seleccionMenu!=0);	
	}
	
	public Sesiones accesoClientes(Sesiones sesion) throws SQLException {
		Login login;
		int numeroMaximoIntentos=3;
		int numeroIntentos=0;
		
		do{
			login=loguearse();
			if (login!=null) {
				sesion=sesionIniciadaOK(login);
				Cliente cliente=leeDatosClienteLogueado(login);
				if (cliente!=null) {
					if (login.getEmail().equalsIgnoreCase("admin@motorracing.com")) {
						MenuAdministracion menuAdmin=new MenuAdministracion(getEstructuraTablas(), getConexionAbierta());
						menuAdmin.ejecutaMenuAdministracion();
					}else {
						MenuAccesoCliente menuCliente=new MenuAccesoCliente(cliente, getEstructuraTablas(), getConexionAbierta());
						menuCliente.ejecutaMenuAccesoCliente();
					}
				}
				numeroIntentos=numeroMaximoIntentos+1;
			}else {
				System.out.println(Mensajes.ERROR_ACCESO_LOGIN);
				sesion=null;
			}	
			numeroIntentos++;
		}while(numeroIntentos<numeroMaximoIntentos);
		return sesion;
	}
	
	public Sesiones altaCliente(Sesiones sesion) throws SQLException {
		Login login;
		login=altaNuevoCliente();
		if (login!=null) {
			sesion=sesionIniciadaOK(login);
			Cliente cliente=grabaDatosClienteEnBD(login); 				
			if (cliente!=null) {
				PreferenciasCliente preferenciasCliente=introducePreferenciasCliente(cliente);
				grabaPreferenciasCliente(preferenciasCliente);
				
				
				MenuVehiculoCliente menuCliente=new MenuVehiculoCliente(cliente, getEstructuraTablas(), getConexionAbierta());
				menuCliente.ejecutaMenuCliente();						
			}
		}else {
			sesion=null;
		}
		return sesion;
	}
	
	
	public void grabaPreferenciasCliente(PreferenciasCliente preferenciasCliente) {
		PreferenciasClientesDao preferenciasDao=new PreferenciasClientesDao(getEstructuraTablas(),getConexionAbierta());
		preferenciasDao.insert(preferenciasCliente);
	}
	
	public Login altaNuevoCliente() {
		LoginDao logDao=new LoginDao(getEstructuraTablas(), getConexionAbierta());
		Login login;
		String passIntroducido;
		String emailIntroducido;
		
		System.out.println("* * ALTA NUEVO CLIENTE * *");
		System.out.println("Por favor, Introduzca su e-mail:");
		emailIntroducido=Principal.introTextoTeclado(30);
		if (Principal.validarCorreo(emailIntroducido)) {
			login=logDao.read(emailIntroducido, "email");	
			if (login!=null) {
				System.out.println(Mensajes.ERROR_USUARIO_YA_EXISTE);
				login=null;
			}else {
				login=new Login();
				login.setEmail(emailIntroducido);
				passIntroducido=creaAcceso();
				login.setPassHash(passIntroducido);	
				login.setId(logDao.insert(login));
				login=borraPassEnMemoria(login);				
			}
		}else {
			System.out.println(Mensajes.DATO_INTRODUCIDO_ERRONEO);
			login=null;
		}	
		return login;
	}
	
	public void imprimeBienvenida(Login login, Date inicioSesion) {
		String mensaje=String.format("%sBienvenido %s                   Fecha: %s%s",Mensajes.SALTOLINEA,login.getEmail(),inicioSesion,Mensajes.SALTOLINEA);
		String caracter="_";
		String linea=caracter.repeat(mensaje.length());
		String cabecera=linea+mensaje+linea;

		System.out.println(cabecera);
	}
	
	public Sesiones sesionIniciadaOK(Login login) {
		Sesiones sesion;
		sesion=grabaInicioSesionEnBD(login);
		if (sesion.getId()!=0) imprimeBienvenida(login,sesion.getInicioSesion());
		return sesion;
	}
	
	public Sesiones grabaInicioSesionEnBD(Login login) {
		Sesiones sesion=new Sesiones();
		SesionesDao sesionDao=new SesionesDao(getEstructuraTablas(),getConexionAbierta());
		sesion.setLogin(login);
		sesion.setInicioSesion(fechaActual());
		sesion.setId(sesionDao.insert(sesion));
		return sesion;
	}
	
	public void sesionFinalizada(Sesiones sesion) {
		if (grabaFinSesionEnBD(sesion)!=0) System.out.println(Mensajes.AGRADECIMIENTO);	
	}
	
	public int grabaFinSesionEnBD(Sesiones sesion) {
		SesionesDao sesionDao=new SesionesDao(getEstructuraTablas(),getConexionAbierta());
		sesion.setFinSesion(fechaActual());
		return sesionDao.update(sesion);
	}
	
	public void listadoSesiones() {
		List <Sesiones> resultadoConsulta;
		SesionesDao sesionDao=new SesionesDao(getEstructuraTablas(),getConexionAbierta());
		resultadoConsulta=sesionDao.getAll();
		boolean mostrarIndices=true;
		String tituloListado="* * Listado de sesiones * *";
		ListadoEntity listadoSesiones=new ListadoEntity(tituloListado, Principal.nombreDeLaTablaAsociadaALaEntity(Sesiones.class), mostrarIndices, getEstructuraTablas(), resultadoConsulta);
		listadoSesiones.listarDatosTablas();
	}
	
	public void listadoLogins() {
		List <Login> resultadoConsulta;
		LoginDao loginDao=new LoginDao(getEstructuraTablas(),getConexionAbierta());
		resultadoConsulta=loginDao.getAll();
		boolean mostrarIndices=true;
		String tituloListado="* * Listado de login * *";
		ListadoEntity listadoLogin=new ListadoEntity(tituloListado, Principal.nombreDeLaTablaAsociadaALaEntity(Login.class), mostrarIndices, getEstructuraTablas(), resultadoConsulta);
		listadoLogin.listarDatosTablas();
	}
	
	public Cliente introduceDatosCliente(Login login) {
		Cliente cliente=new Cliente();
		boolean dniValido;
		int maxNumeroIntendosDni=3;
		int numeroIntentosDni=0;
			
		System.out.println(Mensajes.SALTOLINEA+"          * PROCESO DE ALTA *"+Mensajes.SALTOLINEA);
		do {
			cliente.setDni(Principal.introduceAtributoPorTeclado("su número de DNI",Cliente.class, "dni", getEstructuraTablas()));
			dniValido=validarDNI(cliente.getDni());
			if (!dniValido || compruebaExisteDniEnBDCliente(cliente.getDni())) {
				System.out.println(Mensajes.DNI_NO_VALIDO);	// Si el formato no es el adecuado o ya existe el dni en la BD
				dniValido=false;
			}
			numeroIntentosDni++;
		}while(!dniValido && numeroIntentosDni<maxNumeroIntendosDni );
		if (dniValido) {
			cliente.setNombre(Principal.introduceAtributoPorTeclado("su nombre",Cliente.class, "nombre", getEstructuraTablas()));
			cliente.setApellido1(Principal.introduceAtributoPorTeclado("su primer apellido",Cliente.class, "apellido1", getEstructuraTablas()));
			cliente.setApellido2(Principal.introduceAtributoPorTeclado("su segundo apellido",Cliente.class, "apellido2", getEstructuraTablas()));
			cliente=introduceTelefonoCliente(cliente);
			cliente=introduceDireccionCliente(cliente);
			cliente.setLogin(login);
		}else {
			cliente=null;
		}
		return cliente;
	}
	
	public boolean compruebaExisteDniEnBDCliente(String dni) {
		 boolean existeDni=false;
		 
		 ClienteDao clienteDao=new ClienteDao(getEstructuraTablas(),getConexionAbierta());
		 List<Cliente> clientes=clienteDao.read(dni, Principal.nombreDelCampoAsociadoALaEntity(Cliente.class, "dni"));
		 if (!clientes.isEmpty()) existeDni=true;
		 return existeDni;		 
	 }
	
	public Cliente grabaDatosClienteEnBD(Login login) {
		Cliente cliente;
		ClienteDao clienteDao=new ClienteDao(getEstructuraTablas(), getConexionAbierta());
		cliente=introduceDatosCliente(login);
		if(cliente!=null) {
			cliente.setId(clienteDao.insert(cliente));
		}else {				// Borra los datos de sesion y los datos correspondientes al login del cliente que no completó el proceso de alta correctamente.
			int sesionEliminada=0;
			int loginEliminado;
			List<Sesiones> listaResultado=new ArrayList<>();
			SesionesDao sesionDao=new SesionesDao(getEstructuraTablas(), getConexionAbierta());
			listaResultado=sesionDao.readIdLogin(login.getId());
			if (!listaResultado.isEmpty()) sesionEliminada=sesionDao.delete(listaResultado.get(0));			// Al ser un alta de cliente nuevo, sólo puede haber un único inicio de sesión, por lo que borra ese inicio de sesión.
			LoginDao loginDao=new LoginDao(getEstructuraTablas(), getConexionAbierta());
			loginEliminado=loginDao.delete(login);
			if(sesionEliminada==0 || loginEliminado==0) System.out.println(Mensajes.ERROR_BORRAR_REGISTRO);	
		}
		return cliente;
	}
	
	public Cliente leeDatosClienteLogueado(Login login) {
		ClienteDao clienteDao=new ClienteDao(getEstructuraTablas(), getConexionAbierta());
		List<Cliente> clientes=clienteDao.read(Integer.toString(login.getId()), Principal.nombreDelCampoAsociadoALaEntity(Cliente.class, "login"));
		if (clientes.isEmpty()) return  null; else return clientes.get(0); 		// Sólo puede existir un registro de cliente por cada idLogin, por eso devuelve sólo uno.
	} 
	 
}
