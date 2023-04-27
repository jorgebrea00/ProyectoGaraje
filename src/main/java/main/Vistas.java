package main;
import util.Formularios;
import util.ImprimeTablas;

public class Vistas {

	private static ImprimeTablas imprime = new ImprimeTablas();

	public static void menuInicial() {		
		if(main.getClienteLogeado() != null) {
			System.out.println("3");
			main.getControlador().cerrarSesion();  // cerramos sesión y 
				  // deslogeamos al cliente cuando volvemos al menu principal 
		}										 // (falta controlar cierre sesion ante cierre random)
		switch (main.menu.setTitulo("¡Bienvenido a MotorRacing!").exe("Ingreso", "Registro")) {
		case "Ingreso":
			if (Formularios.ingresoCliente()) {
				main.getControlador().iniciarSesion();
				menuCliente();
			}
			break;
		case "Registro":
			Formularios.altaClienteYAutenticacion();
			break;
		case "Volver":
			Formularios.salir();
			break;
		}
	}

	public static void menuCliente() {
		switch (main.menu.setTitulo("Area Cliente").exe("Mis datos", "Mis Citas", "Mis vehiculos")) {
		case "Mis datos":
			menuMisDatos();
			break;
		case "Mis Citas":
			menuMisCitas();
			break;
		case "Mis vehiculos":
			menuMisVehiculos();
			break;
		case "Volver":
			main.getControlador().imprimirMensajeFinSesion(); //Cerrar sesión
			main.getControlador().cerrarSesion();
			break;
		}
	}

	private static void menuMisDatos() {
		// imprimir datos cliente
		imprime.imprimeDatosPorCliente(main.getClienteLogeado());
		switch (main.menu.setTitulo("Mis datos personales").exe("Editar")) {
		case "Editar":
			Formularios.actualizarDatosCliente();
			menuCliente();	
			break;
		case "Volver":
			menuCliente();	
			break;
		}
	}

	private static void menuMisCitas() {
		// imprimir datos cliente
		imprime.imprimeCitasPorCliente(main.getClienteLogeado());
		switch (main.menu.setTitulo("Mis Citas").exe("Solicitar cita", "Cancelar cita")) {
		case "Solicitar cita":
			menuCliente();	
			break;
		case "Cancelar cita":
			menuCliente();	
			break;
		case "Volver":
			menuCliente();	
			break;
		}
	}

	private static void menuMisVehiculos() {
		// imprimir vehiculos cliente
		imprime.imprimeVehiculosPorCliente(main.getClienteLogeado());		
		switch (main.menu.setTitulo("Mis vehiculos").exe("Añadir", "Borrar")) {
		case "Añadir":
			Formularios.altaVehiculo();
			menuCliente();			
			break;
		case "Borrar":
			Formularios.bajaVehiculo();
			menuCliente();
			break;
		case "Volver":
			menuCliente();	
			break;
		}
	}
}
