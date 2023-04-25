package cita.server;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import cita.calendario.CrearCalendario;
import dao.CitaDao;

import model.CabecerasDiagnostico;
import model.Cita;
import model.Cliente;
import model.Vehiculo;

public class CitaServer {

	public static void insertarCitaServer(Cliente cliente, CabecerasDiagnostico CabecerasDiagnostico) {

		// creamos el contructor para las funciones de insertar
		CitaServerInsertar citaServerInsertar = new CitaServerInsertar();
		
      //pedimos el vehiculo donde quiere la cita 
		Vehiculo vehiculoCliente = citaServerInsertar.PedirVehiculo(cliente);
			
		CabecerasDiagnostico.setId(1);
		
		// creamos la cita para insertar
		Cita cita = new Cita();

		CrearCalendario.printCalendario();

		LocalDate ld = citaServerInsertar.Pedirfecha();

		cita.setFechaHora(ld);
		cita.setCabecerasDiagnostico(CabecerasDiagnostico);
		cita.setCliente(cliente);
		cita.setVehiculo(vehiculoCliente);

		CitaDao.insert(cita);
		System.out.println(" ");

	}

	public static ArrayList<Cita> mostrarCitaCliente(Cliente cliente) {

		ArrayList<Cita> arrayListcita = CitaDao.readOneCliente(cliente);
		int valor = 0;

		for (Cita cita : arrayListcita) {
			System.out.println(valor + " = " + cita.getFechaHora());
			valor++;
		}

		return arrayListcita;

	}

	public static void borrarCitaCliente(Cliente cliente) {

		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);

		ArrayList<Cita> arrayListCita = mostrarCitaCliente(cliente);

		Boolean salir = false;

		while (salir != true) {

			System.out.println("eliga una cita para borrarla");

			int valor = sc.nextInt();

			if (valor < arrayListCita.size() && valor >= 0) {
				CitaDao.delete(arrayListCita.get(valor).getFechaHora());
				System.out.println("cita borrada");
				salir = true;
			} else
				System.out.println("valor incorrecto");

		}

	}

	public static void modificarCita(Cliente cliente) {

		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		
		CitaServerInsertar citaServerInsertar = new CitaServerInsertar();

		ArrayList<Cita> arrayListCita = mostrarCitaCliente(cliente);

		Boolean salir = false;

		while (salir != true) {

			System.out.println("eliga una cita para modificarla");

			int valor = sc.nextInt();

			if (valor < arrayListCita.size() && valor >= 0) {
				
				CrearCalendario.printCalendario();
				
				LocalDate localDate = citaServerInsertar.Pedirfecha();
				
				CitaDao.update(localDate, cliente);
				
				System.out.println("cita modificada");
				
				salir = true;
				
			} else System.out.println("valor incorrecto");

		}

	}

}
