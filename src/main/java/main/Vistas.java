package main;

import util.Formularios;
import util.ImprimeTablas;


public class Vistas {
	
	private ImprimeTablas imprime = new ImprimeTablas();

	public static void menuInicial() {
		switch (main.menu.setTitulo("¡Bienvenido a MotorRacing!").exe("Ingreso", "Registro")) {
		case "Ingreso":
			Formularios.ingresoCliente();
			break;
		case "Registro":
			Formularios.registroCliente();
			break;
		case "Volver":
			Formularios.salir();
			break;
		}
	}

	public static void menuCliente() {
		switch (main.menu.setTitulo("Area Cliente").exe("Mis datos", "Mis Citas", "Mis vehiculos")) {
		case "Mis datos":
			
			Formularios.ingresoCliente();
			break;
		case "Mis Citas":
			
			break;
		case "Mis vehiculos":
			Formularios.salir();
			break;
		case "Volver":			
			break;
		}
	}

}
