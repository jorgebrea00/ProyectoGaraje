package cita.server;

import java.time.LocalDate;

import cita.calendario.CrearCalendario;
import dao.CitaDao;

import model.CabecerasDiagnostico;
import model.Cita;
import model.Cliente;
import model.Vehiculo;

public class CitaServer {



	public static void insertarCitaServer(Cliente cliente, CabecerasDiagnostico CabecerasDiagnostico) {
		
CitaServerInsertar citaServerInsertar = new CitaServerInsertar();


		Vehiculo vehiculoCliente = citaServerInsertar.PedirVehiculo(cliente);
		System.out.println(vehiculoCliente.getId());
		CabecerasDiagnostico.setId(1);
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

}
