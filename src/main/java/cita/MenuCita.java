
package cita;

import java.util.Scanner;

import cita.server.CitaServer;
import mensajes.Mensajes;
import model.CabecerasDiagnostico;
import model.Cliente;

public class MenuCita {
	
	Cliente cliente;
	CabecerasDiagnostico cabecerasDiagnostico;
	
	public MenuCita() {
		super();
	}

	public MenuCita(Cliente cliente, CabecerasDiagnostico cabecerasDiagnostico) {
		super();
		this.cliente = cliente;
		this.cabecerasDiagnostico = cabecerasDiagnostico;
	}

	public MenuCita(Cliente cliente) {
		super();
		this.cliente = cliente;
	}

	public  void darMenu() {

		System.out.println(Mensajes.CITA_WELCOME);

		boolean salir = false;

		while (salir != true) {

			salir = pedirOpcion();

		}
	}

	public  boolean pedirOpcion() {
		
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);

		int opcion = 0;

		System.out.println(Mensajes.CITA_OPCIONES);
		opcion = sc.nextInt();

	
		
		switch (opcion) {
		case 1: {
			//Cliente cliente = new Cliente();
			//cliente.setId(2);
			CabecerasDiagnostico cabecerasDiagnostico = new CabecerasDiagnostico();
			
			CitaServer.insertarCitaServer(this.cliente, cabecerasDiagnostico);
			
			return false;
		}
		case 2: {

			return false;
		}
		case 3: {

			return false;
		}
		case 4: {
			
			
			return false;
		}
		case 5: {
			System.out.println(Mensajes.CITA_DESPEDIDA);
			return true;	
			
		}
		default:
			System.out.println(Mensajes.CITA_OPCION_VALOR_INCORRECTO);
			return	false;
		}

	}
	

}
