package cita.server;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import cita.calendario.SaberSiExisteUnaCita;
import dao.VehiculoDao;

import main.Conexion;

import model.Cliente;
import model.Vehiculo;

public class CitaServerInsertar {

	static Scanner sc = new Scanner(System.in);

	public Vehiculo PedirVehiculo(Cliente cliente) {

		@SuppressWarnings("resource")
		Scanner sca = new Scanner(System.in);

		int valorBusqueda = 0;

		boolean salir = false;

		VehiculoDao vehiculoDao = new VehiculoDao(null, Conexion.conexionMySQL());

		String id_cliente = String.valueOf(cliente.getId());

		List<Vehiculo> arrayListVehiculo = vehiculoDao.read(id_cliente, "id_cliente");

		// esto es si el cliente no tiene vehiculos le crea uno 
		// no funciona por que le tengo que pasar al contructor una EstructuraBbdd que no tengo
	/*	if (arrayListVehiculo.isEmpty()) {
			
			EstructuraBbdd estructuraBbdd = new EstructuraBbdd();
			

			MenuVehiculoCliente menuVehiculoCliente = new MenuVehiculoCliente(cliente, estructuraBbdd, Conexion.conexionMySQL());

			menuVehiculoCliente.agregarVehiculo();
			arrayListVehiculo = vehiculoDao.read(id_cliente, "id_cliente");

		}*/

		while (salir != true) {

			System.out.println(" ");
			System.out.println("En que vehiculo quiere su cita");
			System.out.println(" ");

			for (Vehiculo vehiculo : arrayListVehiculo) {
				System.out.println(vehiculo.getMatricula());
				System.out.println("opcion");
				System.out.println(arrayListVehiculo.indexOf(vehiculo));

			}

			valorBusqueda = sca.nextInt();

			if (valorBusqueda >= 0 && valorBusqueda < arrayListVehiculo.size()
					&& arrayListVehiculo.get(valorBusqueda) != null) {
				salir = true;
			} else
				System.out.println("eliga un vehiculo correcto");

		}

		System.out.println(arrayListVehiculo.get(valorBusqueda).getMatricula());

		return arrayListVehiculo.get(valorBusqueda);

	}

	public LocalDate Pedirfecha() {

		LocalDate localDataInsertarFechaAuto = LocalDate.now();
		LocalDate localDataInsertarFechaAuto2 = LocalDate.now();
		localDataInsertarFechaAuto2 = localDataInsertarFechaAuto.plusMonths(1);

		int PedirDatosMes = 0;
		int PedirDatosDia = 0;
		boolean evaluarcita = false;
		boolean evaluardia = false;

		while (evaluarcita != true) {

			System.out.println("introduce en que mes quieres la cita");
			System.out.println("1. " + localDataInsertarFechaAuto.getMonth());
			System.out.println("2. " + localDataInsertarFechaAuto2.getMonth());

			PedirDatosMes = sc.nextInt();
			if (PedirDatosMes == 1 || PedirDatosMes == 2) {
				switch (PedirDatosMes) {

				case 1: {
					PedirDatosMes = localDataInsertarFechaAuto.getMonthValue();
					evaluarcita = true;
					break;
				}

				case 2:
					PedirDatosMes = localDataInsertarFechaAuto2.getMonthValue();
					evaluarcita = true;
					break;

				default:
					throw new IllegalArgumentException("Unexpected value: " + PedirDatosMes);
				}

			} else {
				evaluarcita = false;
				System.out.println("tienes que dar un mes valido");

			}

		}

		while (evaluardia != true) {

			System.out.println("inserte un dia valido");
			PedirDatosDia = sc.nextInt();

			if (SaberSiExisteUnaCita.evaluarSiExiste(PedirDatosMes, PedirDatosDia)) {

				System.out.println("el dia no es valido");

			} else if (PedirDatosDia < localDataInsertarFechaAuto.getDayOfMonth()
					&& PedirDatosMes == localDataInsertarFechaAuto.getMonthValue()) {
				System.out.println("el dia no es valido , por que es anterior a la fecha actual");
			} else {

				LocalDate ld = LocalDate.of(localDataInsertarFechaAuto.getYear(), PedirDatosMes, PedirDatosDia);
				ld.format((DateTimeFormatter.ofPattern("uuuu:MMM:d")));
				evaluardia = true;
				return ld;

			}

		}

		return null;

	}
}
